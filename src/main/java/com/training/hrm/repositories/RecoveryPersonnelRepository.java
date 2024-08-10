package com.training.hrm.repositories;

import com.training.hrm.recoveries.RecoveryPersonnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecoveryPersonnelRepository extends JpaRepository<RecoveryPersonnel, Long> {

}
