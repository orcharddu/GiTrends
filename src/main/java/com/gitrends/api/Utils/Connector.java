package com.gitrends.api.Utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.Map;

public class Connector {

    public static JsonObject getJsonResponse(String url) throws Exception {
        Connection.Response response = null;
        while(response == null) {
            response = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .method(Connection.Method.GET)
                    //.ignoreHttpErrors(true)
                    .timeout(1000 * 5)
                    .execute();
        }
        return JsonParser.parseString(response.body()).getAsJsonObject();
    }

    public static JsonObject getJsonResponse(String url, Map<String, String> headers) throws Exception {
        Connection.Response response = null;
        while(response == null) {
            response = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .method(Connection.Method.GET)
                    //.ignoreHttpErrors(true)
                    .headers(headers)
                    .timeout(1000 * 5)
                    .execute();
        }
        return JsonParser.parseString(response.body()).getAsJsonObject();
    }

    public static JsonObject getJsonResponse(String url, Map<String, String> headers, String body) throws Exception {
        Connection.Response response = null;
        while(response == null) {
            response = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .method(Connection.Method.POST)
                    //.ignoreHttpErrors(true)
                    .headers(headers)
                    .timeout(1000 * 5)
                    .requestBody(body)
                    .execute();
        }
        return JsonParser.parseString(response.body()).getAsJsonObject();
    }

    public static Document getWebDocument(String url) throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36");
        Document doc = null;
        while(doc == null){
            doc = Jsoup
                    .connect(url)
                    .ignoreContentType(true)
                    .headers(headers)
                    .timeout(1000 * 5)
                    .get();
        }
        return doc;
    }

}
