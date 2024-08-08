package com.training.hrm.controllers;

import com.training.hrm.dto.ContractRequest;
import com.training.hrm.exceptions.BadRequestException;
import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.Contract;
import com.training.hrm.repositories.ContractRepository;
import com.training.hrm.services.BackupService;
import com.training.hrm.services.ContractService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private BackupService backupService;

    @Operation(summary = "Create a new contract")
    @PostMapping("/create")
    public ResponseEntity<Object> createContract(@Valid @RequestBody ContractRequest contractRequest, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new BadRequestException(result.getAllErrors().get(0).getDefaultMessage());
            }
            Contract createContract = contractService.createContract(contractRequest);
            return new ResponseEntity<>(createContract, HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Read all information of a contract by ID")
    @GetMapping("/read/{contractID}")
    public ResponseEntity<Object> readContract(@PathVariable String contractID) {
        try {
            if (contractRepository.findContractByContractID(Long.parseLong(contractID)) == null) {
                throw new InvalidException("Contract not found");
            }
            Contract readContract = contractService.readContract(Long.parseLong(contractID));
            return new ResponseEntity<>(readContract, HttpStatus.OK);
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

    @Operation(summary = "Update contract by ID")
    @PostMapping("/update/{contractID}")
    public ResponseEntity<Object> updateContract(@PathVariable String contractID, @Valid @RequestBody ContractRequest contractRequest, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new BadRequestException(result.getAllErrors().get(0).getDefaultMessage());
            }
            Long id = Long.parseLong(contractID);
            Contract exitsContract = contractRepository.findContractByContractID(id);
            if(exitsContract == null) {
                throw new InvalidException("Contract not found");
            }

            // Call approve update contract
            if (!exitsContract.getContractType().equals(contractRequest.getContractType())
                    || !exitsContract.getSalary().equals(contractRequest.getSalary())
                    || !exitsContract.getStartDate().isEqual(contractRequest.getStartDate())
                    || !exitsContract.getEndDate().isEqual(contractRequest.getEndDate())) {
                backupService.createApproveUpdateContract(id, contractRequest);
            }

            return new ResponseEntity<>("Information added successfully, please wait for approval", HttpStatus.OK);
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

    @PostMapping("/delete/{contractID}")
    public ResponseEntity<Object> deleteContract(@PathVariable String contractID) {
        try {
            if (contractRepository.findContractByContractID(Long.parseLong(contractID)) == null) {
                throw new InvalidException("Contract not found");
            }
            contractService.deleteContract(Long.parseLong(contractID));
            return new ResponseEntity<>("Delete contract successful", HttpStatus.OK);
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
