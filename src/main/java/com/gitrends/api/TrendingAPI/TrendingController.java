package com.gitrends.api.TrendingAPI;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@SpringBootApplication
@RestController
@RequestMapping("/trending")
public class TrendingController {
    @GetMapping(value = "/github", produces = "application/json")
    public String github(HttpServletResponse response) {
        GitHubResolver resolver = new GitHubResolver(response);
        return resolver.resolve();

    }

    @GetMapping(value = "/gitlab", produces = "application/json")
    public String gitlab(HttpServletResponse response) {
        GitLabResolver resolver = new GitLabResolver(response);
        return resolver.resolve();
    }

}
