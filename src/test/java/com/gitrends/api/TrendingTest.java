package com.gitrends.api;

import com.gitrends.api.TrendingAPI.TrendingController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class TrendingTest {
    private final MockMvc mockMvc;

    public TrendingTest() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TrendingController()).build();
    }

    @DisplayName("testGetGitHubTrendingSuccess")
    @Test
    void testGetGitHubStatsSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/trending/github")
                .accept(MediaType.APPLICATION_JSON ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("site").value("github"))
                .andExpect(jsonPath("trendingList").exists());
                //.andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("testGetGitLabTrendingSuccess")
    @Test
    void testGetGitLabStatsSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/trending/gitlab")
                .accept(MediaType.APPLICATION_JSON ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("site").value("gitlab"))
                .andExpect(jsonPath("trendingList").exists());
    }

    @DisplayName("testUrlRequestsNotFound")
    @Test
    void testUrlRequestsNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/trending/gitlab/repo")
                .accept(MediaType.APPLICATION_JSON ))
                .andExpect(status().is4xxClientError());
    }

}
