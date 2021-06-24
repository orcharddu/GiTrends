package com.gitrends.api.AnalysisAPI;

import com.gitrends.api.Utils.Connector;
import com.gitrends.api.Utils.Token;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TwitterResolver {
    private final String repo;
    private final HttpServletResponse httpResponse;

    public TwitterResolver(HttpServletResponse response, String repo) {
        this.httpResponse = response;
        this.repo = repo;
    }

    private JsonObject getRelatedTweets(String repo) throws Exception{
        String repoAlt = repo.replace('-', ' ');
        String url = "https://api.twitter.com/2/tweets/search/recent?query="
                + "(" + repo + " OR %23" + repo;
        if(!repoAlt.equals(repo)) {
            url += " OR " + repoAlt;
        }
        url +=  ") "
                + "lang:en "
                + "-is:retweet"
                + "&tweet.fields=created_at,possibly_sensitive";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", Token.TOKEN_TWITTER);
        return Connector.getJsonResponse(url, headers);
    }

    private String processTweets(JsonObject json) {
        JsonArray jsonElements = json.getAsJsonArray("data");
        AnalysisModel<AnalysisModel.Tweet> analysis = new AnalysisModel<>();
        analysis.setSite("twitter");
        analysis.setRepo(repo);
        analysis.setCommentList(new ArrayList<>());
        if(jsonElements == null) return new Gson().toJson(analysis);
        for(JsonElement jsonElement : jsonElements) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject.get("possibly_sensitive").getAsBoolean()) continue;
            AnalysisModel.Tweet tweet = new AnalysisModel.Tweet();
            tweet.setSite("twitter")
                    .setRepo(repo)
                    .setText(jsonObject.get("text").getAsString())
                    .setCreateTime(jsonObject.get("created_at").getAsString());
            analysis.getCommentList().add(tweet);
        }

        try {
            //Watson NLP sentiment analysis
            WatsonNLP watsonNLP = new WatsonNLP(Token.TOKEN_WATSON, Token.URL_WATSON);
            StringBuilder text = new StringBuilder();
            analysis.getCommentList().forEach(x -> text.append(x.getText()).append(" "));
            watsonNLP.analyze(text.toString());
            analysis.setSentiment(watsonNLP.getSentiment());
            analysis.setEmotion(watsonNLP.getEmotion());
            analysis.setCategories(watsonNLP.getCategories());
        } catch (Exception ignored) { }
        return new Gson().toJson(analysis);
    }

    public String resolve() {
        String response;
        httpResponse.setStatus(200);
        try {
            JsonObject tweets = getRelatedTweets(repo);
            response = processTweets(tweets);
        } catch(Exception e) {
            response = "api.gitrends.com: " + e.toString();
            httpResponse.setStatus(500);
        }
        return response;
    }

}
