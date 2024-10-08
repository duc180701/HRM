package com.training.hrm.services;

import com.training.hrm.dto.ContractRequest;
import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.ApproveBackupContract;
import com.training.hrm.models.Contract;
import com.training.hrm.recoveries.RecoveryContract;
import com.training.hrm.repositories.ContractRepository;
import com.training.hrm.repositories.RecoveryContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ContractService {

    @Autowired
    private RecoveryContractRepository recoveryContractRepository;

    @Autowired
    private ContractRepository contractRepository;

    public Contract createContract(ContractRequest contractRequest) throws ServiceRuntimeException{
        try {
            Contract contract = new Contract();

            contract.setContractType(contractRequest.getContractType());
            contract.setSalary(contractRequest.getSalary());
            contract.setStartDate(contractRequest.getStartDate());
            contract.setEndDate(contractRequest.getEndDate());
            contract.setApproveBy("");
            contract.setApproveDate(LocalDate.of(0, 0, 0));
            contract.setVersion(1);

            return contractRepository.save(contract);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while creating the contract: " + e.getMessage());
        }
    }

    public Contract readContract(Long id) throws ServiceRuntimeException {
        try {
            return contractRepository.findContractByContractID(id);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while reading the contract: " + e.getMessage());
        }
    }

    public Contract updateContract(ApproveBackupContract approveBackupContract, String username) throws ServiceRuntimeException {
        try {
            Contract updateContract = contractRepository.findContractByContractID(approveBackupContract.getContractID());
            updateContract.setSalary(approveBackupContract.getSalary());
            updateContract.setContractType(approveBackupContract.getContractType());
            updateContract.setStartDate(approveBackupContract.getStartDate());
            updateContract.setEndDate(approveBackupContract.getEndDate());
            updateContract.setVersion(approveBackupContract.getVersion() + 1);
            updateContract.setApproveBy(username);
            updateContract.setApproveDate(LocalDate.now());

            return contractRepository.save(updateContract);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while updating the contract: " + e.getMessage());
        }
    }

    public void deleteContract(Long id) throws ServiceRuntimeException {
        try {
            Contract contract = contractRepository.findContractByContractID(id);

            // Backup
            RecoveryContract recoveryContract = new RecoveryContract();
            recoveryContract.setContractID(id);
            recoveryContract.setContractType(contract.getContractType());
            recoveryContract.setSalary(contract.getSalary());
            recoveryContract.setStartDate(contract.getStartDate());
            recoveryContract.setEndDate(contract.getEndDate());
            recoveryContractRepository.save(recoveryContract);

            contractRepository.deleteById(id);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while deleting the contract: " + e.getMessage());
        }
    }

    public Contract approveContract(Long contractID, String username) throws InvalidException, ServiceRuntimeException {
        try {
            Contract contract = contractRepository.findContractByContractID(contractID);
            if (contract == null) {
                throw new InvalidException("Contract not found");
            }
            contract.setApproveBy(username);
            contract.setApproveDate(LocalDate.now());

            return contractRepository.save(contract);
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while deleting the contract: " + e.getMessage());
        }
    }
}
