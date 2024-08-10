package com.training.hrm.repositories;

import com.training.hrm.recoveries.RecoveryEmployee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecoveryEmployeeRepository extends JpaRepository<RecoveryEmployee, Long> {

}
