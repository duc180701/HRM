package com.training.hrm.services;

import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.*;
import com.training.hrm.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BackupEmployeeContractRepository backupEmployeeContractRepository;

    @Autowired
    private BackupPersonnelDepartmentRepository backupPersonnelDepartmentRepository;

    @Autowired
    private BackupPersonnelPositionRepository backupPersonnelPositionRepository;

    public List<Object> reportBackupContractPositionDepartmentOfAEmployee (String employeeID, LocalDate startDate, LocalDate endDate) throws ServiceRuntimeException, InvalidException {
        try {
            List<Object> listResult = new ArrayList<>();
            Employee employee = employeeRepository.findEmployeeByEmployeeID(Long.parseLong(employeeID));

            if (employee == null) {
                throw new InvalidException("Employee not found");
            }

            List<BackupEmployeeContract> listBackupEmployeeContract = backupEmployeeContractRepository.findBackupEmployeeContractByEmployeeID(Long.parseLong(employeeID));
            List<BackupPersonnelPosition> listBackupPersonnelPosition = backupPersonnelPositionRepository.findBackupPersonnelPositionByEmployeeID(Long.parseLong(employeeID));
            List<BackupPersonnelDepartment> listBackupPersonnelDepartment = backupPersonnelDepartmentRepository.findBackupPersonnelDepartmentByEmployeeID(Long.parseLong(employeeID));

            if (!listBackupEmployeeContract.isEmpty()) {
                List<BackupEmployeeContract> listAfterFilter = new ArrayList<>();
                for (BackupEmployeeContract backupEmployeeContract : listBackupEmployeeContract) {
                    if (!backupEmployeeContract.getDate().isBefore(startDate) && !backupEmployeeContract.getDate().isAfter(endDate)) {
                        listAfterFilter.add(backupEmployeeContract);
                    }
                }

                if (!listAfterFilter.isEmpty()) {
                    listResult.add(listAfterFilter);
                }
            }
            if (!listBackupPersonnelPosition.isEmpty()) {
                List<BackupPersonnelPosition> listAfterFilter = new ArrayList<>();
                for (BackupPersonnelPosition backupPersonnelPosition : listBackupPersonnelPosition) {
                    if (!backupPersonnelPosition.getDate().isBefore(startDate) && !backupPersonnelPosition.getDate().isAfter(endDate)) {
                        listAfterFilter.add(backupPersonnelPosition);
                    }
                }

                if (!listAfterFilter.isEmpty()) {
                    listResult.add(listAfterFilter);
                }
            }
            if (!listBackupPersonnelDepartment.isEmpty()) {
                List<BackupPersonnelDepartment> listAfterFilter = new ArrayList<>();
                for (BackupPersonnelDepartment backupPersonnelDepartment : listBackupPersonnelDepartment) {
                    if (!backupPersonnelDepartment.getDate().isBefore(startDate) && !backupPersonnelDepartment.getDate().isAfter(endDate)) {
                        listAfterFilter.add(backupPersonnelDepartment);
                    }
                }

                if (!listAfterFilter.isEmpty()) {
                    listResult.add(listAfterFilter);
                }
            }

            return listResult;
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while creating this report: " + e.getMessage());
        }
    }
}
