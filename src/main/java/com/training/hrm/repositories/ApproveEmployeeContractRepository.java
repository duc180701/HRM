package com.training.hrm.repositories;

import com.training.hrm.models.ApproveEmployeeContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApproveEmployeeContractRepository extends JpaRepository<ApproveEmployeeContract, Long> {
    ApproveEmployeeContract findApproveEmployeeContractByApproveEmployeeContractID (Long id);
}
