package com.gitrends.api;

import com.gitrends.api.AnalysisAPI.AnalysisController;
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
public class AnalysisTest {
    private final MockMvc mockMvc;

    public AnalysisTest() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AnalysisController()).build();
    }

    @DisplayName("testGetTwitterAnalysisSuccess")
    @Test
    void testGetTwitterAnalysisSuccess() throws Exception {
        Map<String, String> testMap = new HashMap<>();
        testMap.put("axios", "axios");
        testMap.put("github", "docs");
        testMap.put("pytorch", "pytorch");
        testMap.put("flutter", "flutter");
        for(Map.Entry<String, String> map : testMap.entrySet()){
            String repo = map.getValue();
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/analysis/twitter/" + repo)
                    .accept(MediaType.APPLICATION_JSON ))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("site").value("twitter"))
                    .andExpect(jsonPath("repo").value(repo));
        }
    }

    @DisplayName("testUrlRequestsNotFound")
    @Test
    void testUrlRequestsNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/analysis/twitter/axios/axios")
                .accept(MediaType.APPLICATION_JSON ))
                .andExpect(status().is4xxClientError());
    }
}
