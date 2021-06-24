package com.gitrends.api;

import com.gitrends.api.StatsAPI.StatsController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class StatsTest {
    private final MockMvc mockMvc;

    public StatsTest() {
        mockMvc = MockMvcBuilders.standaloneSetup(new StatsController()).build();
    }

    @DisplayName("testGetGitHubStatsSuccess")
    @Test
    void testGetGitHubStatsSuccess() throws Exception {
        Map<String, String> testMap = new HashMap<>();
        testMap.put("axios", "axios");
        testMap.put("github", "docs");
        testMap.put("pytorch", "pytorch");
        testMap.put("flutter", "flutter");
        for(Map.Entry<String, String> map : testMap.entrySet()){
            String owner = map.getKey();
            String repo = map.getValue();
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/stats/github/" + owner + "/" + repo)
                    .accept(MediaType.APPLICATION_JSON ))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("owner").value(owner))
                    .andExpect(jsonPath("repo").value(repo));
        }
    }

    @DisplayName("testGetGitHubStatsRepoNotExists")
    @Test
    void testGetGitHubStatsRepoNotExists() throws Exception {
        Map<String, String> testMap = new HashMap<>();
        testMap.put("jfliejlsjg", "fejslijgs");
        for(Map.Entry<String, String> map : testMap.entrySet()){
            String owner = map.getKey();
            String repo = map.getValue();
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/stats/github/" + owner + "/" + repo)
                    .accept(MediaType.APPLICATION_JSON ))
                    .andExpect(status().is5xxServerError());
        }
    }

    @DisplayName("testGetGitLabStatsSuccess")
    @Test
    void testGetGitLabStatsSuccess() throws Exception {
        Map<String, String> testMap = new HashMap<>();
        testMap.put("Antora", "Antora");
        testMap.put("Veloren", "veloren");
        for(Map.Entry<String, String> map : testMap.entrySet()){
            String owner = map.getKey();
            String repo = map.getValue();
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/stats/gitlab/" + owner + "/" + repo)
                    .accept(MediaType.APPLICATION_JSON ))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("owner").exists())
                    .andExpect(jsonPath("repo").exists());
                    //.andDo(MockMvcResultHandlers.print());
        }
    }

    @DisplayName("testGetGitLabStatsRepoNotExists")
    @Test
    void testGetGitLabRepoNotExists() throws Exception {
        Map<String, String> testMap = new HashMap<>();
        testMap.put("Afjesligjsra", "Antfgejfoilseora");
        for(Map.Entry<String, String> map : testMap.entrySet()){
            String owner = map.getKey();
            String repo = map.getValue();
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/stats/gitlab/" + owner + "/" + repo)
                    .accept(MediaType.APPLICATION_JSON ))
                    .andExpect(status().is5xxServerError());
        }
    }

    @DisplayName("testUrlRequestsNotFound")
    @Test
    void testUrlRequestsNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/stats/gitlab/axios/axios/repo")
                .accept(MediaType.APPLICATION_JSON ))
                .andExpect(status().is4xxClientError());
    }
}
