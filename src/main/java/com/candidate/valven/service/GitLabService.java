package com.candidate.valven.service;

import java.sql.Timestamp;
import java.util.List;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Project;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.candidate.valven.repository.CommitRepository;

@Service
public class GitLabService {
    private final CommitRepository commitRepository;

    @Value("${gitlab.oauthAccessToken}")
    private String gitlabOAuthAccessToken;

    public GitLabService(CommitRepository commitRepository) {
        // Constructor for GitLabService, injecting the CommitRepository dependency.
        this.commitRepository = commitRepository;
    }

    /**
     * Fetches and stores commits from GitLab repositories within the last month.
     *
     * @throws GitLabApiException If there is an issue with connecting to the GitLab
     *                            API.
     */
    public void fetchAndStoreCommits() throws GitLabApiException {
        // Establish a connection with the GitLab API
        GitLabApi gitLabApi = new GitLabApi("https://gitlab.com", gitlabOAuthAccessToken);

        // Retrieve the list of projects owned by the authenticated user
        List<Project> projects = gitLabApi.getProjectApi().getProjects();

        // Iterate through each project to fetch commits made in the last month
        for (Project project : projects) {
            // Fetch commits made in the last month for the project
            List<Commit> commits = gitLabApi.getCommitsApi().getCommits(project.getId());

            // Convert GitLab commits to Commit entities and store them in the database
            for (Commit gitlabCommit : commits) {
                com.candidate.valven.model.Commit commitEntity = new com.candidate.valven.model.Commit();
                commitEntity.setHash(gitlabCommit.getId());
                commitEntity.setTimestamp(new Timestamp(gitlabCommit.getCommittedDate().getTime()));
                commitEntity.setMessage(gitlabCommit.getMessage());
                commitEntity.setAuthor(gitlabCommit.getCommitterName());

                // Save the Commit entity in the database
                commitRepository.save(commitEntity);
            }
        }
    }
}
