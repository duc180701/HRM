package com.training.hrm.repositories;

import com.training.hrm.models.BackupEmployeeContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BackupEmployeeContractRepository extends JpaRepository<BackupEmployeeContract, Long> {
    List<BackupEmployeeContract> findBackupEmployeeContractByEmployeeID(Long id);
    BackupEmployeeContract findBackupEmployeeContractByBackupEmployeeContractID(Long id);
}
