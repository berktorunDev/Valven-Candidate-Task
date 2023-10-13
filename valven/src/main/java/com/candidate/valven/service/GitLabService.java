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
        this.commitRepository = commitRepository;
    }

    public void fetchAndStoreCommits() throws GitLabApiException {
        // GitLab API ile bağlantı kurma
        GitLabApi gitLabApi = new GitLabApi("https://gitlab.com", gitlabOAuthAccessToken);

        // Kullanıcının projelerini al
        List<Project> projects = gitLabApi.getProjectApi().getProjects();

        for (Project project : projects) {
            // Projenin son 1 ay içindeki commitlerini al
            List<Commit> commits = gitLabApi.getCommitsApi().getCommits(project.getId());

            for (Commit gitlabCommit : commits) {
                com.candidate.valven.model.Commit commitEntity = new com.candidate.valven.model.Commit();
                commitEntity.setHash(gitlabCommit.getId());
                commitEntity.setTimestamp(new Timestamp(gitlabCommit.getCommittedDate().getTime()));
                commitEntity.setMessage(gitlabCommit.getMessage());
                commitEntity.setAuthor(gitlabCommit.getCommitterName());

                // GitLab'dan alınan commit verilerini CommitEntity olarak saklayın
                commitRepository.save(commitEntity);
            }
        }
    }
}
