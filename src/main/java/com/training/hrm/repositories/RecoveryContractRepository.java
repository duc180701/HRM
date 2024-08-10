package com.training.hrm.repositories;

import com.training.hrm.recoveries.RecoveryContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecoveryContractRepository extends JpaRepository<RecoveryContract, Long> {

}
