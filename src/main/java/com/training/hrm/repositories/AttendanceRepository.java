package com.training.hrm.repositories;

import com.training.hrm.models.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Attendance findAttendanceByDate(LocalDate date);
}
