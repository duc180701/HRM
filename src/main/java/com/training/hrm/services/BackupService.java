package com.training.hrm.services;

import com.training.hrm.dto.ContractRequest;
import com.training.hrm.dto.PersonnelRequest;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.BackupContract;
import com.training.hrm.models.BackupPersonnel;
import com.training.hrm.models.Contract;
import com.training.hrm.models.Personnel;
import com.training.hrm.repositories.BackupContractRepository;
import com.training.hrm.repositories.BackupPersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BackupService {

    @Autowired
    private BackupPersonnelRepository backupPersonnelRepository;

    @Autowired
    private BackupContractRepository backupContractRepository;

    public BackupPersonnel createBackupPersonnel(Personnel personnel, Long personnelID) throws ServiceRuntimeException {
        try {
            BackupPersonnel backupPersonnel = new BackupPersonnel();
            backupPersonnel.setPersonnelID(personnelID);
            backupPersonnel.setPosition(personnel.getPosition());
            backupPersonnel.setDepartment(personnel.getDepartment());
            backupPersonnel.setReason("UPDATE PERSONNEL");
            return backupPersonnelRepository.save(backupPersonnel);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while back up this personnel: " + e.getMessage());
        }
    }

    public BackupContract createBackupContract(Contract contract, Long contractID) throws ServiceRuntimeException {
        try {
            BackupContract backupContract = new BackupContract();
            backupContract.setContractID(contractID);
            backupContract.setContractType(contract.getContractType());
            backupContract.setSalary(contract.getSalary());
            backupContract.setStartDate(contract.getStartDate());
            backupContract.setEndDate(contract.getEndDate());
            backupContract.setReason("UPDATE CONTRACT");
            return backupContractRepository.save(backupContract);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while back up this contract: " + e.getMessage());
        }
    }
}
