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

public class GitLabResolver {
    private final HttpServletResponse httpResponse;
    public GitLabResolver(HttpServletResponse response) {
        this.httpResponse = response;
    }

    private Document getTrendingDoc() throws Exception {
        String url = "https://gitlab.com/explore/projects/trending";
        return Connector.getWebDocument(url);
    }

    private String processDoc(Document doc) {
        Gson gson = new GsonBuilder().disableInnerClassSerialization().create();
        Elements elements = doc.select("li.project-row");
        TrendingModel<TrendingModel.GitLabModel> trending = new TrendingModel<>();
        trending.setSite("gitlab").setTrendingList(new ArrayList<>());
        for(Element element : elements) {
            TrendingModel.GitLabModel repo = new TrendingModel.GitLabModel()
                    .setSite("gitlab")
                    .setOwner(element.select("span.namespace-name").text().split(" ")[0])
                    .setRepo(element.select("span.project-name").text())
                    .setDescription(element.select("div.description p").text())
                    .setUrl("https://gitlab.com" + element.select("div.project-title a").attr("href"))
                    .setStars(Tools.str2Int(element.select("a[title=Stars]").text()))
                    .setForks(Tools.str2Int(element.select("a[title=Forks]").text()));
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
