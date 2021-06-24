package com.gitrends.api.AnalysisAPI;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@SpringBootApplication
@RestController
@RequestMapping("/analysis")
public class AnalysisController {

    @GetMapping(value = "/twitter/{repo}", produces = "application/json")
    public String twitter(@PathVariable String repo, HttpServletResponse response) {
        TwitterResolver resolver = new TwitterResolver(response, repo);
        return resolver.resolve();
    }
}
