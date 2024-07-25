package com.training.hrm.repositories;

import com.training.hrm.models.BackupPersonnelDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BackupPersonnelDepartmentRepository extends JpaRepository<BackupPersonnelDepartment, Long> {
    BackupPersonnelDepartment findBackupPersonnelDepartmentByBackupPersonnelDepartmentID (Long id);
    List<BackupPersonnelDepartment> findBackupPersonnelDepartmentByEmployeeID(Long id);
}
