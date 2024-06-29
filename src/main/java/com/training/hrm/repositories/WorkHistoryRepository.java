package com.training.hrm.repositories;

import com.training.hrm.models.WorkHistory;
import org.hibernate.jdbc.WorkExecutor;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface WorkHistoryRepository extends JpaRepository<WorkHistory, Long> {
    WorkHistory findWorkHistoryByWorkHistoryID(Long workHistoryID);
    WorkHistory findWorkHistoryByPosition(String position);
    WorkHistory findWorkHistoryByDepartment(String department);
    WorkHistory findWorkHistoryByStartDate(LocalDate startDate);
    WorkHistory findWorkHistoryByEndDate(LocalDate endDate);
    Optional<WorkHistory> findWorkHistoryByEmployeeID(Long id);
}
