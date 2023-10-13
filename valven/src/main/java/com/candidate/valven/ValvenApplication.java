package com.candidate.valven;

import java.io.IOException;

import org.gitlab4j.api.GitLabApiException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.candidate.valven.service.GitHubService;
import com.candidate.valven.service.GitLabService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableAutoConfiguration
public class ValvenApplication {

	private final GitHubService gitHubService;
	private final GitLabService gitLabService;

	public ValvenApplication(GitHubService gitHubService, GitLabService gitLabService) {
		this.gitHubService = gitHubService;
		this.gitLabService = gitLabService;
	}

	public static void main(String[] args) {
		SpringApplication.run(ValvenApplication.class, args);
	}

	@PostConstruct
	public void onApplicationStartup() throws IOException, GitLabApiException {
		// Uygulama başladığında veri alma işlemini başlatın
		gitHubService.fetchAndStoreCommits();
		gitLabService.fetchAndStoreCommits();
	}

}