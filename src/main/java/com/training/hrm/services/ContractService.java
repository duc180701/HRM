package com.training.hrm.services;

import com.training.hrm.dto.ContractRequest;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.Contract;
import com.training.hrm.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    public Contract createContract(ContractRequest contractRequest) throws ServiceRuntimeException{
        try {
            Contract contract = new Contract();

            contract.setContractType(contractRequest.getContractType());
            contract.setSalary(contractRequest.getSalary());
            contract.setStartDate(contractRequest.getStartDate());
            contract.setEndDate(contractRequest.getEndDate());

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

    public Contract updateContract(Contract exitsContract, ContractRequest contractRequest) throws ServiceRuntimeException {
        try {
            exitsContract.setContractType(contractRequest.getContractType());
            exitsContract.setSalary(contractRequest.getSalary());
            exitsContract.setStartDate(contractRequest.getStartDate());
            exitsContract.setEndDate(contractRequest.getEndDate());

            return contractRepository.save(exitsContract);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while updating the contract: " + e.getMessage());
        }
    }

    public void deleteContract(Long id) throws ServiceRuntimeException {
        try {
            contractRepository.deleteById(id);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while deleting the contract: " + e.getMessage());
        }
    }
}
