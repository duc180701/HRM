package com.training.hrm.controllers;

import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.BackupContract;
import com.training.hrm.models.BackupEmployeeContract;
import com.training.hrm.models.BackupPersonnelDepartment;
import com.training.hrm.models.BackupPersonnelPosition;
import com.training.hrm.repositories.BackupContractRepository;
import com.training.hrm.repositories.BackupEmployeeContractRepository;
import com.training.hrm.repositories.BackupPersonnelDepartmentRepository;
import com.training.hrm.repositories.BackupPersonnelPositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/backup")
public class BackupController {

    @Autowired
    private BackupContractRepository backupContractRepository;

    @Autowired
    private BackupPersonnelPositionRepository backupPersonnelPositionRepository;

    @Autowired
    private BackupPersonnelDepartmentRepository backupPersonnelDepartmentRepository;

    @Autowired
    private BackupEmployeeContractRepository backupEmployeeContractRepository;

    @GetMapping("/read-backup-contract/{backupContractID}")
    public ResponseEntity<Object> readBackupContractByBackupContractID(@PathVariable String backupContractID) {
        try {
            if (backupContractRepository.findBackupContractByBackupContractID(Long.parseLong(backupContractID)) == null) {
                throw new InvalidException("This backup record not found");
            }
            BackupContract backupContract = backupContractRepository.findBackupContractByBackupContractID(Long.parseLong(backupContractID));
            return new ResponseEntity<>(backupContract, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid backup contract ID format", HttpStatus.BAD_REQUEST);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read-backup-personnel-position/{backupPersonnelPositionID}")
    public ResponseEntity<Object> readBackupPersonnelPositionByBackupPersonnelPositionID(@PathVariable String backupPersonnelPositionID) {
        try {
            BackupPersonnelPosition backupPersonnelPosition = backupPersonnelPositionRepository.findBackupPersonnelPositionByBackupPersonnelPositionID(Long.parseLong(backupPersonnelPositionID));
            if (backupPersonnelPosition == null) {
                throw new InvalidException("This backup record not found");
            }
            return new ResponseEntity<>(backupPersonnelPosition, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid backup personnel position ID format", HttpStatus.BAD_REQUEST);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read-backup-personnel-department/{backupPersonnelDepartmentID}")
    public ResponseEntity<Object> readBackupPersonnelDepartmentByBackupPersonnelDepartmentID(@PathVariable String backupPersonnelDepartmentID) {
        try {
            BackupPersonnelDepartment backupPersonnelDepartment = backupPersonnelDepartmentRepository.findBackupPersonnelDepartmentByBackupPersonnelDepartmentID(Long.parseLong(backupPersonnelDepartmentID));
            if (backupPersonnelDepartment == null) {
                throw new InvalidException("This backup record not found");
            }
            return new ResponseEntity<>(backupPersonnelDepartment, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid backup personnel position ID format", HttpStatus.BAD_REQUEST);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read-backup-employee-contract/{backupEmployeeContractID}")
    public ResponseEntity<Object> readBackupEmployeeContractByBackupEmployeeContractID(@PathVariable String backupEmployeeContractID) {
        try {
            BackupEmployeeContract backupEmployeeContract = backupEmployeeContractRepository.findBackupEmployeeContractByBackupEmployeeContractID(Long.parseLong(backupEmployeeContractID));
            if (backupEmployeeContract == null) {
                throw new InvalidException("This backup record not found");
            }
            return new ResponseEntity<>(backupEmployeeContract, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid backup personnel position ID format", HttpStatus.BAD_REQUEST);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
