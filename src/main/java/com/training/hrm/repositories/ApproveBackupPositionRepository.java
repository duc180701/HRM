package com.training.hrm.repositories;

import com.training.hrm.models.ApproveBackupPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApproveBackupPositionRepository extends JpaRepository<ApproveBackupPosition, Long> {
    ApproveBackupPosition findApproveBackupPositionByApproveBackupPositionID(Long id);
}
