package com.training.hrm.repositories;

import com.training.hrm.recoveries.RecoveryPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecoveryPersonRepository extends JpaRepository<RecoveryPerson, Long> {

}
