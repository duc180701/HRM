package com.training.hrm.services;

import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.Contract;
import com.training.hrm.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    public Contract createContract(Contract contract) throws ServiceRuntimeException{
        try {
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

    public Contract updateContract(Contract exitsContract, Contract contract) throws ServiceRuntimeException {
        try {
            exitsContract.setContractType(contract.getContractType());
            exitsContract.setStartDate(contract.getStartDate());
            exitsContract.setEndDate(contract.getEndDate());

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
