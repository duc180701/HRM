package com.training.hrm.repositories;

import com.training.hrm.models.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    Contract findContractByContractID(Long id);
}
