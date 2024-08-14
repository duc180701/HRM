package com.training.hrm.repositories;

import com.training.hrm.models.ApproveAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApproveAttendanceRepository extends JpaRepository<ApproveAttendance, Long> {
    ApproveAttendance findApproveAttendanceByApproveAttendanceID (Long id);
}
