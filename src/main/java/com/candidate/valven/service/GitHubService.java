package com.candidate.valven.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.candidate.valven.model.Commit;
import com.candidate.valven.repository.CommitRepository;

@Service
public class GitHubService {
    private final CommitRepository commitRepository;

    // Calculate the timestamp for the date one month ago
    private final long sinceTimestamp = LocalDateTime.now().minusMonths(1).toEpochSecond(ZoneOffset.UTC);

    @Value("${github.username}")
    private String githubUsername;

    @Value("${github.oauthAccessToken}")
    private String githubOAuthAccessToken;

    public GitHubService(CommitRepository commitRepository) {
        // Constructor for GitHubService, injecting the CommitRepository dependency.
        this.commitRepository = commitRepository;
    }

    /**
     * Fetches and stores commits from GitHub repositories within the last month.
     *
     * @throws IOException If there is an issue with connecting to the GitHub API.
     */
    public void fetchAndStoreCommits() throws IOException {
        // Connect to GitHub using the provided username and OAuth access token
        GitHub github = GitHub.connect(githubUsername, githubOAuthAccessToken);

        // Get a list of repositories owned by the authenticated user
        Map<String, GHRepository> repositories = github.getMyself().getRepositories();

        // Iterate through each repository to fetch commits made in the last month
        for (GHRepository repository : repositories.values()) {
            List<GHCommit> commits = repository.queryCommits().since(sinceTimestamp).list().toList();

            // Convert GitHub commits to Commit entities and store them in the database
            for (GHCommit githubCommit : commits) {
                Commit commitEntity = new Commit();
                commitEntity.setHash(githubCommit.getSHA1());
                commitEntity.setTimestamp(Timestamp.from(githubCommit.getCommitDate().toInstant()));
                commitEntity.setMessage(githubCommit.getCommitShortInfo().getMessage());
                commitEntity.setAuthor(githubCommit.getCommitter().getLogin());

                // Save the Commit entity in the database
                commitRepository.save(commitEntity);
            }
        }
    }
}
