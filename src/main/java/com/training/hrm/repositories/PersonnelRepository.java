package com.training.hrm.repositories;

import com.training.hrm.models.Personnel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonnelRepository extends JpaRepository<Personnel, Long> {
    Personnel findPersonnelByInternalPhoneNumber(String phoneNumber);
    Personnel findPersonnelByInternalEmail(String email);
    Personnel findPersonnelByEmployeeAccount(String employeeAccount);
    Personnel findPersonnelByPersonnelID(Long id);
    List<Personnel> findPersonnelByStatus(String status);
}
