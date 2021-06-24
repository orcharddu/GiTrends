package com.gitrends.api.StatsAPI;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@SpringBootApplication
@RestController
@RequestMapping("/stats")
public class StatsController {
    @GetMapping(value = "/github/{owner}/{repo}", produces = "application/json")
    public String github(@PathVariable String owner, @PathVariable String repo, HttpServletResponse response) {
        GitHubResolver resolver = new GitHubResolver(response, owner, repo);
        return resolver.resolve();
    }

    @GetMapping(value = "/gitlab/{owner}/{repo}", produces = "application/json")
    public String gitlab(@PathVariable String owner, @PathVariable String repo, HttpServletResponse response) {
        GitLabResolver resolver = new GitLabResolver(response, owner, repo);
        return resolver.resolve();
    }

}
