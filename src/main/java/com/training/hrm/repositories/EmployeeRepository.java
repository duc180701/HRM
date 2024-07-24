package com.training.hrm.repositories;

import com.training.hrm.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findEmployeeByEmployeeID(Long id);
    Employee findEmployeeByPersonnelID(Long id);
    Employee findEmployeeByPersonID(Long id);
    Employee findEmployeeByContractID(Long id);
    List<Employee> findEmployeesByContractID(Long id);
}
