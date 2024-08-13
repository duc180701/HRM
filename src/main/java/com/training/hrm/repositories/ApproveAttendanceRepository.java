package com.training.hrm.repositories;

import com.training.hrm.models.ApproveAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Repository
public interface ApproveAttendanceRepository extends JpaRepository<ApproveAttendance, Long> {
}
