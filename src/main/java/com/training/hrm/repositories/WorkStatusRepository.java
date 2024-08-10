package com.training.hrm.repositories;

import com.training.hrm.models.WorkStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkStatusRepository extends JpaRepository<WorkStatus, Long> {
    @Query("SELECT n.name FROM WorkStatus n")
    List<String> findAllWorkStatusNames();
}
