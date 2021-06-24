package com.gitrends.api.TrendingAPI;


import com.gitrends.api.Utils.Connector;
import com.gitrends.api.Utils.Tools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class GitHubResolver {
    private final HttpServletResponse httpResponse;
    public GitHubResolver(HttpServletResponse response) {
        this.httpResponse = response;
    }

    private Document getTrendingDoc() throws Exception {
        String url = "https://github.com/trending";
        return Connector.getWebDocument(url);
    }

    private String processDoc(Document doc) {
        Gson gson = new GsonBuilder().disableInnerClassSerialization().create();
        Elements elements = doc.select("div.Box article");
        TrendingModel<TrendingModel.GitHubModel> trending = new TrendingModel<>();
        trending.setSite("github").setTrendingList(new ArrayList<>());
        for(Element element : elements) {
            TrendingModel.GitHubModel repo = new TrendingModel.GitHubModel()
                    .setSite("github")
                    .setOwner(element.select("h1 span.text-normal").text().split(" ")[0])
                    .setRepo(element.select("h1 a").get(0).ownText())
                    .setDescription(element.select("p").text())
                    .setUrl("https://github.com" + element.select("h1 a").attr("href"))
                    .setStars(Tools.str2Int(element.select("div.f6 a").get(0).text()))
                    .setStarsToday(Tools.str2Int(element.select("div.f6 span.float-sm-right").get(0).ownText()))
                    .setForks(Tools.str2Int(element.select("div.f6 a").get(1).text()));
            trending.getTrendingList().add(repo);
        }

        return gson.toJson(trending);
    }


    protected String resolve() {
        String response;
        httpResponse.setStatus(200);
        try {
            Document doc = getTrendingDoc();
            response = processDoc(doc);
        } catch(Exception e) {
            response = "api.gitrends.com: " + e.toString();
            httpResponse.setStatus(500);
        }
        return response;
    }
}

