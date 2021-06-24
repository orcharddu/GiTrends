package com.gitrends.api.StatsAPI;

import com.gitrends.api.Utils.Connector;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletResponse;

public class GitHubResolver {
    private final String owner;
    private final String repo;
    private final HttpServletResponse httpResponse;

    public GitHubResolver(HttpServletResponse response, String owner, String repo) {
        this.owner = owner;
        this.repo = repo;
        this.httpResponse = response;
    }

    private JsonObject getRepoStats() throws Exception {
        String url = "https://api.github.com/repos/" + owner + "/" + repo;
        return Connector.getJsonResponse(url);
    }

    private String processStats(JsonObject json) {
        StatsModel stats = new StatsModel()
                .setSite("github")
                .setOwner(json.getAsJsonObject("owner").get("login").getAsString())
                .setRepo(json.get("name").getAsString())
                .setUrl(json.get("html_url").getAsString())
                .setWatchers(json.get("subscribers_count").getAsInt())
                .setStars(json.get("stargazers_count").getAsInt())
                .setForks(json.get("forks").getAsInt());
        if(json.get("description").isJsonNull()){
            stats.setDescription("");
        } else {
            stats.setDescription(json.get("description").getAsString());
        }
        return new Gson().toJson(stats);
    }

    protected String resolve() {
        String response;
        httpResponse.setStatus(200);
        try {
            JsonObject repoStats = getRepoStats();
            response = processStats(repoStats);
        } catch(Exception e) {
            response = "api.gitrends.com: " + e.toString();
            httpResponse.setStatus(500);
        }
        return response;
    }
}
