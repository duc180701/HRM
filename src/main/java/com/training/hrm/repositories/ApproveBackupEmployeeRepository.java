package com.training.hrm.repositories;


import com.training.hrm.models.ApproveBackupEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApproveBackupEmployeeRepository extends JpaRepository<ApproveBackupEmployee, Long> {
    ApproveBackupEmployee findApproveBackupEmployeeByApproveBackupEmployeeID(Long id);
}
