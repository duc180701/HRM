package com.training.hrm.repositories;

import com.training.hrm.models.BackupContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackupContractRepository extends JpaRepository<BackupContract, Long> {
    BackupContract findBackupContractByBackupContractID(Long id);
}
