package com.gitrends.api;

import com.gitrends.api.StatsAPI.GitHubResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@SpringBootApplication
@RestController
public class GitrendsApplication {
	public static void main(String[] args) {
		SpringApplication.run(GitrendsApplication.class, args);
	}

	@GetMapping(value = "/")
	public String index() {
		//auto jump to main site: gitrends.com
		return "<script type=\"text/javascript\">window.location.href=\"https://gitrends.com\";</script>";

	}
}
