package com.training.hrm.controllers;

import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.BackupContract;
import com.training.hrm.models.BackupPersonnel;
import com.training.hrm.models.Contract;
import com.training.hrm.repositories.BackupContractRepository;
import com.training.hrm.repositories.BackupPersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/backup")
public class BackupController {

    @Autowired
    private BackupPersonnelRepository backupPersonnelRepository;

    @Autowired
    private BackupContractRepository backupContractRepository;

    @GetMapping("/read-backup-personnel/{backupPersonnelID}")
    public ResponseEntity<Object> readBackupPersonnelByBackupPersonnelID(@PathVariable String backupPersonnelID) {
        try {
            if (backupPersonnelRepository.findBackupPersonnelByBackupPersonnelID(Long.parseLong(backupPersonnelID)) == null) {
                throw new InvalidException("This backup record not found");
            }
            BackupPersonnel backupPersonnel = backupPersonnelRepository.findBackupPersonnelByBackupPersonnelID(Long.parseLong(backupPersonnelID));
            return new ResponseEntity<>(backupPersonnel, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid contract ID format", HttpStatus.BAD_REQUEST);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read-backup-contract/{backupContractID}")
    public ResponseEntity<Object> readBackupContractByBackupContractID(@PathVariable String backupContractID) {
        try {
            if (backupContractRepository.findBackupContractByBackupContractID(Long.parseLong(backupContractID)) == null) {
                throw new InvalidException("This backup record not found");
            }
            BackupContract backupContract = backupContractRepository.findBackupContractByBackupContractID(Long.parseLong(backupContractID));
            return new ResponseEntity<>(backupContract, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid contract ID format", HttpStatus.BAD_REQUEST);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
