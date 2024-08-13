package com.training.hrm.controllers;

import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.recoveries.RecoveryContract;
import com.training.hrm.recoveries.RecoveryEmployee;
import com.training.hrm.recoveries.RecoveryPerson;
import com.training.hrm.recoveries.RecoveryPersonnel;
import com.training.hrm.services.RecoveryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recovery")
public class RecoveryController {

    @Autowired
    private RecoveryService recoveryService;

    @Operation(summary = "Get all information of recovery contract list")
    @GetMapping("/read-all-recovery-contract")
    public ResponseEntity<Object> readAllRecoveryContract() {
        try {
            List<RecoveryContract> listRecoveryContract = recoveryService.getAllRecoveryContract();
            return new ResponseEntity<>(listRecoveryContract, HttpStatus.OK);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get all information of recovery employee list")
    @GetMapping("/read-all-recovery-employee")
    public ResponseEntity<Object> readAllRecoveryEmployee() {
        try {
            List<RecoveryEmployee> listRecoveryEmployee = recoveryService.getAllRecoveryEmployee();
            return new ResponseEntity<>(listRecoveryEmployee, HttpStatus.OK);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get all information of recovery person list")
    @GetMapping("/read-all-recovery-person")
    public ResponseEntity<Object> readAllRecoveryPerson() {
        try {
            List<RecoveryPerson> listRecoveryPerson = recoveryService.getAllRecoveryPerson();
            return new ResponseEntity<>(listRecoveryPerson, HttpStatus.OK);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get all information of recovery personnel list")
    @GetMapping("/read-all-recovery-personnel")
    public ResponseEntity<Object> readAllRecoveryPersonnel() {
        try {
            List<RecoveryPersonnel> listRecoveryPersonnel = recoveryService.getAllRecoveryPersonnel();
            return new ResponseEntity<>(listRecoveryPersonnel, HttpStatus.OK);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
