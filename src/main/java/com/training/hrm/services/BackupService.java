package com.training.hrm.services;

import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.*;
import com.training.hrm.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BackupService {

    @Autowired
    private BackupPersonnelRepository backupPersonnelRepository;

    @Autowired
    private BackupContractRepository backupContractRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BackupPersonnelPositionRepository backupPersonnelPositionRepository;

    @Autowired
    private BackupPersonnelDepartmentRepository backupPersonnelDepartmentRepository;

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

    public BackupContract createBackupContract(Contract exitsContract, Long contractID) throws ServiceRuntimeException {
        try {
            BackupContract backupContract = new BackupContract();
            backupContract.setContractID(contractID);
            backupContract.setContractType(exitsContract.getContractType());
            backupContract.setSalary(exitsContract.getSalary());
            backupContract.setStartDate(exitsContract.getStartDate());
            backupContract.setEndDate(exitsContract.getEndDate());
            backupContract.setReason("UPDATE CONTRACT");
            return backupContractRepository.save(backupContract);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while back up this contract: " + e.getMessage());
        }
    }

    public BackupPersonnelPosition createBackupPersonnelPosition(Long personnelID) throws ServiceRuntimeException {
        try {
            Personnel findPersonnel = personnelRepository.findPersonnelByPersonnelID(personnelID);
            Employee findEmployee = employeeRepository.findEmployeeByPersonnelID(personnelID);
            Person findPerson = personRepository.findPersonByPersonID(findEmployee.getPersonID());

            BackupPersonnelPosition backupPersonnelPosition = new BackupPersonnelPosition();
            backupPersonnelPosition.setEmployeeID(findEmployee.getEmployeeID());
            backupPersonnelPosition.setFullName(findPerson.getFullName());
            backupPersonnelPosition.setPosition(findPersonnel.getPosition());
            backupPersonnelPosition.setDepartment(findPersonnel.getDepartment());
            backupPersonnelPosition.setStatus(findPersonnel.getStatus());
            backupPersonnelPosition.setReason("UPDATE POSITION");

            return backupPersonnelPositionRepository.save(backupPersonnelPosition);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while back up this personnel position: " + e.getMessage());
        }
    }

    public BackupPersonnelDepartment createBackupPersonnelDepartment(Long personnelID) throws ServiceRuntimeException {
        try {
            Personnel findPersonnel = personnelRepository.findPersonnelByPersonnelID(personnelID);
            Employee findEmployee = employeeRepository.findEmployeeByPersonnelID(personnelID);
            Person findPerson = personRepository.findPersonByPersonID(findEmployee.getPersonID());

            BackupPersonnelDepartment backupPersonnelDepartment = new BackupPersonnelDepartment();
            backupPersonnelDepartment.setEmployeeID(findEmployee.getEmployeeID());
            backupPersonnelDepartment.setFullName(findPerson.getFullName());
            backupPersonnelDepartment.setPosition(findPersonnel.getPosition());
            backupPersonnelDepartment.setDepartment(findPersonnel.getDepartment());
            backupPersonnelDepartment.setStatus(findPersonnel.getStatus());
            backupPersonnelDepartment.setReason("UPDATE POSITION");

            return backupPersonnelDepartmentRepository.save(backupPersonnelDepartment);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while back up this personnel position: " + e.getMessage());
        }
    }
}
