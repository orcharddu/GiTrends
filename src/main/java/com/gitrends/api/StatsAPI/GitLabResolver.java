package com.gitrends.api.StatsAPI;


import com.gitrends.api.Utils.Connector;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jsoup.nodes.Document;

import javax.servlet.http.HttpServletResponse;

public class GitLabResolver {
    private final String owner;
    private final String repo;
    private final HttpServletResponse httpResponse;

    public GitLabResolver(HttpServletResponse response, String owner, String repo) {
        this.owner = owner;
        this.repo = repo;
        this.httpResponse = response;
    }

    private int getProjectID() throws Exception {
        String url = "https://gitlab.com/" + owner + "/" + repo;
        Document doc = Connector.getWebDocument(url);
        String projectID = doc.select("input[name=project_id]").val();
        return Integer.parseInt(projectID);
    }

    private JsonObject getRepoStats(int id) throws Exception {
        String url = "https://gitlab.com/api/v4/projects/" + id;
        return Connector.getJsonResponse(url);
    }

    private String processStats(JsonObject json) {
        StatsModel stats = new StatsModel()
                .setSite("gitlab")
                .setOwner(json.getAsJsonObject("namespace").get("name").getAsString())
                .setRepo(json.get("name").getAsString())
                .setUrl(json.get("web_url").getAsString())
                .setWatchers(json.get("star_count").getAsInt())
                .setStars(json.get("star_count").getAsInt())
                .setForks(json.get("forks_count").getAsInt());
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
            //gitlab need project id to access detail through gitlab api
            int projectID = getProjectID();
            JsonObject repoStats = getRepoStats(projectID);
            response = processStats(repoStats);
        } catch(Exception e) {
            response = "api.gitrends.com: " + e.toString();
            httpResponse.setStatus(500);
        }
        return response;
    }
}
