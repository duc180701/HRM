package com.training.hrm.controllers;

import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.*;
import com.training.hrm.repositories.*;
import com.training.hrm.services.BackupService;
import com.training.hrm.services.ContractService;
import com.training.hrm.services.PersonnelService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/backup")
public class BackupController {

    @Autowired
    private PersonnelService personnelService;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private ApproveBackupContractRepository approveBackupContractRepository;

    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private BackupService backupService;

    @Autowired
    private BackupContractRepository backupContractRepository;

    @Autowired
    private BackupPersonnelPositionRepository backupPersonnelPositionRepository;

    @Autowired
    private BackupPersonnelDepartmentRepository backupPersonnelDepartmentRepository;

    @Autowired
    private BackupEmployeeContractRepository backupEmployeeContractRepository;

    @Operation(summary = "Read a backup contract by ID")
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

    @Operation(summary = "Get all personnel position need update in approve position list")
    @GetMapping("/read-all-approve-personnel-position")
    public ResponseEntity<Object> getAllApprovePersonnelPosition() {
        try {
            List<ApproveBackupPosition> listApproveBackupPosition = backupService.getAllApproveBackupPosition();

            return new ResponseEntity<>(listApproveBackupPosition, HttpStatus.OK);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Approve update position by backup personnel position ID")
    @PostMapping("/approve-personnel-position/{backupPersonnelPositionID}")
    public ResponseEntity<Object> approveUpdatePersonnelPosition(@PathVariable String backupPersonnelPositionID) {
        try {
            Long id = Long.parseLong(backupPersonnelPositionID);
            ApproveBackupPosition approveBackupPosition = backupService.approveBackupPosition(id); //Set approve to true
            Personnel oldPersonnel = personnelRepository.findPersonnelByPersonnelID(approveBackupPosition.getPersonnelID());
            backupService.createBackupPersonnelPosition(approveBackupPosition.getPersonnelID());
            Personnel updatePersonnel = personnelService.updatePersonnelPosition(oldPersonnel, approveBackupPosition);

            return new ResponseEntity<>(updatePersonnel, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Please enter a valid backup contact ID", HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get all contract need update in approve contract list")
    @GetMapping("/read-all-approve-contract")
    public ResponseEntity<Object> getAllApproveContract() {
        try {
            List<ApproveBackupContract> listApproveBackupContract = backupService.getAllApproveBackupContract();

            return new ResponseEntity<>(listApproveBackupContract, HttpStatus.OK);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Approve update contract by backup contract ID")
    @PostMapping("/approve-contract/{backupContractID}")
    public ResponseEntity<Object> approveUpdateContract(@PathVariable String backupContractID) {
        try {
            Long id = Long.parseLong(backupContractID);
            if (approveBackupContractRepository.findApproveBackupContractByApproveBackupContractID(id) == null) {
                throw new InvalidException("Approve backup contract not found");
            }
            ApproveBackupContract approveBackupContract = backupService.approveBackupContract(id); //Set approve to true
            Contract oldContract = contractRepository.findContractByContractID(approveBackupContract.getContractID());
            if (oldContract.getVersion() != approveBackupContract.getVersion()) {
                backupService.rejectBackupContract(id);
                throw new InvalidException("Conflict detected, another user has modified this product");
            } else {
                backupService.createBackupContract(oldContract);
                Contract updateContract = contractService.updateContract(approveBackupContract);
                return new ResponseEntity<>(updateContract, HttpStatus.OK);
            }
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Please enter a valid backup contact ID", HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
