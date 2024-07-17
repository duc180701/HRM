package com.training.hrm.repositories;

import com.training.hrm.models.BackupPersonnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BackupPersonnelRepository extends JpaRepository<BackupPersonnel, Long> {
    BackupPersonnel findBackupPersonnelByPersonnelID(Long id);
}
