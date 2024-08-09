package com.training.hrm.services;

import com.training.hrm.dto.ContractRequest;
import com.training.hrm.dto.PersonnelRequest;
import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.*;
import com.training.hrm.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BackupService {

    @Autowired
    private ApproveBackupPositionRepository approveBackupPositionRepository;

    @Autowired
    private ApproveBackupContractRepository approveBackupContractRepository;

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

    @Autowired
    private BackupEmployeeContractRepository backupEmployeeContractRepository;

    public BackupContract createBackupContract(Contract exitsContract) throws ServiceRuntimeException {
        try {
            BackupContract backupContract = new BackupContract();
            backupContract.setContractID(exitsContract.getContractID());
            backupContract.setContractType(exitsContract.getContractType());
            backupContract.setSalary(exitsContract.getSalary());
            backupContract.setStartDate(exitsContract.getStartDate());
            backupContract.setEndDate(exitsContract.getEndDate());
            backupContract.setDate(LocalDate.now());
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
            backupPersonnelPosition.setDate(LocalDate.now());
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
            backupPersonnelDepartment.setDate(LocalDate.now());
            backupPersonnelDepartment.setStatus(findPersonnel.getStatus());
            backupPersonnelDepartment.setReason("UPDATE DEPARTMENT");

            return backupPersonnelDepartmentRepository.save(backupPersonnelDepartment);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while back up this personnel position: " + e.getMessage());
        }
    }

    public BackupEmployeeContract createBackupEmployeeContract (Employee employee) throws ServiceRuntimeException {
        try {
            BackupEmployeeContract backupEmployeeContract = new BackupEmployeeContract();

            backupEmployeeContract.setContractID(employee.getContractID());
            backupEmployeeContract.setEmployeeID(employee.getEmployeeID());
            backupEmployeeContract.setDate(LocalDate.now());
            backupEmployeeContract.setReason("UPDATE CONTRACT");

            return backupEmployeeContractRepository.save(backupEmployeeContract);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while back up this employee contract: " + e.getMessage());
        }
    }

    public ApproveBackupPosition createApproveBackupPosition(Long personnelID, PersonnelRequest personnelRequest) throws ServiceRuntimeException, InvalidException {
        try {
            Personnel personnel = personnelRepository.findPersonnelByPersonnelID(personnelID);
            if (personnel == null) {
                throw new InvalidException("Personnel not found");
            }
            ApproveBackupPosition approveBackupPosition = new ApproveBackupPosition();
            approveBackupPosition.setPersonnelID(personnelID);
            approveBackupPosition.setOldPosition(personnel.getPosition());
            approveBackupPosition.setNewPosition(personnelRequest.getPosition());
            approveBackupPosition.setDate(LocalDate.now());
            approveBackupPosition.setReason("WAITING TO APPROVE");
            approveBackupPosition.setApprove(false);

            return approveBackupPositionRepository.save(approveBackupPosition);
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while creating the position change: " + e.getMessage());
        }
    }

    public List<ApproveBackupPosition> getAllApproveBackupPosition() throws ServiceRuntimeException, InvalidException {
        try {
            List<ApproveBackupPosition> listApproveBackupPosition = approveBackupPositionRepository.findAll();
            if (listApproveBackupPosition.isEmpty()) {
                throw new InvalidException("List approve backup position is empty");
            }

            return listApproveBackupPosition;
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while get approve backup position list: " + e.getMessage());
        }
    }

    public ApproveBackupPosition approveBackupPosition(Long id) throws InvalidException, ServiceRuntimeException {
        try {
            ApproveBackupPosition approveBackupPosition = approveBackupPositionRepository.findApproveBackupPositionByApproveBackupPositionID(id);
            approveBackupPosition.setApprove(true);
            approveBackupPosition.setReason("APPROVED");

            return approveBackupPositionRepository.save(approveBackupPosition);
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while approve backup position: " + e.getMessage());
        }
    }

    public ApproveBackupContract createApproveUpdateContract(Long id, ContractRequest contractRequest) throws ServiceRuntimeException, InvalidException {
        try {
            ApproveBackupContract approveBackupContract = new ApproveBackupContract();
            approveBackupContract.setContractID(id);
            approveBackupContract.setContractType(contractRequest.getContractType());
            approveBackupContract.setStartDate(contractRequest.getStartDate());
            approveBackupContract.setEndDate(contractRequest.getEndDate());
            approveBackupContract.setSalary(contractRequest.getSalary());
            approveBackupContract.setReason("WAIT TO APPROVE");
            approveBackupContract.setDate(LocalDate.now());
            approveBackupContract.setApprove(false);

            return approveBackupContractRepository.save(approveBackupContract);
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while create approve backup contract: " + e.getMessage());
        }
    }

    public List<ApproveBackupContract> getAllApproveBackupContract() throws ServiceRuntimeException, InvalidException {
        try {
            List<ApproveBackupContract> listApproveBackupContract = approveBackupContractRepository.findAll();
            if (listApproveBackupContract.isEmpty()) {
                throw new InvalidException("List approve backup contract is empty");
            }

            return listApproveBackupContract;
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while get approve backup contract list: " + e.getMessage());
        }
    }

    public ApproveBackupContract approveBackupContract(Long id) throws InvalidException, ServiceRuntimeException {
        try {
            ApproveBackupContract approveBackupContract = approveBackupContractRepository.findApproveBackupContractByApproveBackupContractID(id);
            approveBackupContract.setApprove(true);
            approveBackupContract.setReason("APPROVED");
            return approveBackupContractRepository.save(approveBackupContract);
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while approve backup contract: " + e.getMessage());
        }
    }
}
