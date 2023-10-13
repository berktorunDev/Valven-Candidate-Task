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
        this.commitRepository = commitRepository;
        this.mapperUtil = mapperUtil;
    }

    // GitHub ve GitLab'dan son 1 ay içindeki commitleri çekmek için gerekli kod
    public List<CommitDTO> getCommitsFromGitHubAndGitLab() {
        // GitHub ve GitLab'dan son 1 ay içindeki commitleri çekin ve birleştirin
        // Sonuçları Commit sınıfına dönüştürün ve döndürün
        List<Commit> commits = commitRepository.findAll();
        return commits.stream()
                .map(commit -> mapperUtil.convertToDTO(commit, CommitDTO.class))
                .collect(Collectors.toList());
    }

    public CommitDTO getCommitById(Long id) {
        // Veritabanından belirli bir commiti ID ile çekmek için gerekli kodu ekleyin
        Commit commit = commitRepository.findById(id).orElse(null);
        return mapperUtil.convertToDTO(commit, CommitDTO.class);
    }
}
