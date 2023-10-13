package com.candidate.valven;

import java.io.IOException;

import org.gitlab4j.api.GitLabApiException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.candidate.valven.service.GitHubService;
import com.candidate.valven.service.GitLabService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
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
		// Execute the data retrieval process when the application starts
		gitHubService.fetchAndStoreCommits(); // Fetch and store commits from GitHub
		gitLabService.fetchAndStoreCommits(); // Fetch and store commits from GitLab
	}
}
