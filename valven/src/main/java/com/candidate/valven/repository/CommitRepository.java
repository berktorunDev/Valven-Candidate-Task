package com.candidate.valven.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.candidate.valven.model.Commit;

public interface CommitRepository extends JpaRepository<Commit, Long> {

}
