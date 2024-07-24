package com.training.hrm.repositories;

import com.training.hrm.models.BackupPersonnelPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackupPersonnelPositionRepository extends JpaRepository<BackupPersonnelPosition, Long> {

}
