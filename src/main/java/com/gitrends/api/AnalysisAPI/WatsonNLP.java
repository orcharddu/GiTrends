package com.gitrends.api.AnalysisAPI;


import com.gitrends.api.Utils.Connector;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.*;
import java.util.stream.Collectors;

public class WatsonNLP {
    private final AnalysisModel.Emotion emotion;
    private final AnalysisModel.Label sentiment;
    private final List<AnalysisModel.Label> categories;
    private final String token;
    private final String url;

    public WatsonNLP(String token, String url) {
        this.token = token;
        this.url = url;
        this.sentiment = new AnalysisModel.Label();
        this.emotion = new AnalysisModel.Emotion();
        this.categories = new ArrayList<>();
    }

    public void analyze(String text) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", token);
        headers.put("Content-Type", "application/json");
        String body = "";
        body += "{";
        body += "  \"text\": " + new Gson().toJson(text, String.class) + ",";
        body += "  \"features\": {";
        body += "    \"sentiment\": {},";
        body += "    \"emotion\":{},";
        body += "    \"categories\": {},";
        body += "    \"concepts\": {},";
        body += "    \"entities\": {},";
        body += "    \"keywords\": {}";
        body += "  }";
        body += "}";

        JsonObject result = Connector.getJsonResponse(url, headers, body);
        JsonObject sentimentObj = result.getAsJsonObject("sentiment").getAsJsonObject("document");
        JsonObject emotionObj = result.getAsJsonObject("emotion").getAsJsonObject("document").getAsJsonObject("emotion");
        JsonArray categoryObjs = result.getAsJsonArray("categories");
        sentiment.setLabel(sentimentObj.get("label").getAsString());
        sentiment.setScore(sentimentObj.get("score").getAsDouble());
        emotion.setSadness(emotionObj.get("sadness").getAsDouble());
        emotion.setJoy(emotionObj.get("joy").getAsDouble());
        emotion.setFear(emotionObj.get("fear").getAsDouble());
        emotion.setDisgust(emotionObj.get("disgust").getAsDouble());
        emotion.setAnger(emotionObj.get("anger").getAsDouble());
        Optional.ofNullable(categoryObjs).ifPresent(xs -> {
            for(JsonElement jsonElement : xs) {
                JsonObject categoryObj = jsonElement.getAsJsonObject();
                AnalysisModel.Label category = new AnalysisModel.Label();
                category.setLabel(categoryObj.get("label").getAsString());
                category.setScore(categoryObj.get("score").getAsDouble());
                category.setLabelSegments(Arrays.stream(category.getLabel().split("/"))
                                                .filter(x -> !x.equals("")).collect(Collectors.toList()));
                categories.add(category);
            }
        });
    }

    public AnalysisModel.Emotion getEmotion() { return emotion; }

    public AnalysisModel.Label getSentiment() { return sentiment; }

    public List<AnalysisModel.Label> getCategories() { return categories; }
}
