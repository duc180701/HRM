package com.training.hrm.repositories;

import com.training.hrm.models.ApproveBackupContract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApproveBackupContractRepository extends JpaRepository<ApproveBackupContract, Long> {
    ApproveBackupContract findApproveBackupContractByApproveBackupContractID(Long id);
    List<ApproveBackupContract> findAll();
}
