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

    private final long sinceTimestamp = LocalDateTime.now().minusMonths(1).toEpochSecond(ZoneOffset.UTC);

    @Value("${github.username}")
    private String githubUsername;

    @Value("${github.oauthAccessToken}")
    private String githubOAuthAccessToken;

    public GitHubService(CommitRepository commitRepository) {
        this.commitRepository = commitRepository;
    }

    public void fetchAndStoreCommits() throws IOException {
        GitHub github = GitHub.connect(githubUsername, githubOAuthAccessToken);
        Map<String, GHRepository> repositories = github.getMyself().getRepositories();

        for (GHRepository repository : repositories.values()) {
            List<GHCommit> commits = repository.queryCommits().since(sinceTimestamp).list().toList();

            for (GHCommit githubCommit : commits) {
                Commit commitEntity = new Commit();
                commitEntity.setHash(githubCommit.getSHA1());
                commitEntity.setTimestamp(Timestamp.from(githubCommit.getCommitDate().toInstant()));
                commitEntity.setMessage(githubCommit.getCommitShortInfo().getMessage());
                commitEntity.setAuthor(githubCommit.getCommitter().getLogin());

                commitRepository.save(commitEntity);
            }
        }
    }
}
