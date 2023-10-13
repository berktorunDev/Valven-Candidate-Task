package com.candidate.valven.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.candidate.valven.dto.CommitDTO;
import com.candidate.valven.model.Commit;
import com.candidate.valven.repository.CommitRepository;
import com.candidate.valven.util.MapperUtil;

@Service
public class CommitService {
    private final CommitRepository commitRepository;
    private final MapperUtil mapperUtil;

    public CommitService(CommitRepository commitRepository, MapperUtil mapperUtil) {
        // Constructor for CommitService, injecting required dependencies.
        this.commitRepository = commitRepository;
        this.mapperUtil = mapperUtil;
    }

    /**
     * Retrieves commits from both GitHub and GitLab for the past month and converts
     * them into CommitDTOs.
     *
     * @return List of CommitDTOs containing commit information from both platforms.
     */
    public List<CommitDTO> getCommitsFromGitHubAndGitLab() {
        // Retrieve commits from both GitHub and GitLab for the past month
        List<Commit> commits = commitRepository.findAll();

        // Map the Commit entities to CommitDTOs using MapperUtil
        return commits.stream()
                .map(commit -> mapperUtil.convertToDTO(commit, CommitDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves commit details by its unique ID and converts it into a CommitDTO.
     *
     * @param id The unique ID of the commit.
     * @return CommitDTO containing the details of the specified commit.
     */
    public CommitDTO getCommitById(Long id) {
        // Retrieve a specific commit from the database by its ID
        Commit commit = commitRepository.findById(id).orElse(null);

        // Map the Commit entity to a CommitDTO using MapperUtil
        return mapperUtil.convertToDTO(commit, CommitDTO.class);
    }
}
