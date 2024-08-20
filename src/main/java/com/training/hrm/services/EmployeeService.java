package com.training.hrm.services;

import com.training.hrm.dto.EmployeeRequest;
import com.training.hrm.dto.EmployeeResponse;
import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.*;
import com.training.hrm.recoveries.RecoveryEmployee;
import com.training.hrm.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.thymeleaf.cache.TemplateCacheKey;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private BackupService backupService;

    @Autowired
    private ApproveBackupEmployeeRepository approveBackupEmployeeRepository;

    @Autowired
    private ApproveEmployeeContractRepository approveEmployeeContractRepository;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private RecoveryEmployeeRepository recoveryEmployeeRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    public Employee createEmployee(EmployeeRequest employeeRequest) throws ServiceRuntimeException {
        try {

            Employee employee = new Employee();

            employee.setContractID((long) 0);
            employee.setPersonnelID(employeeRequest.getPersonnelID());
            employee.setPersonID(employeeRequest.getPersonID());
            employee.setVersion(1);

            return employeeRepository.save(employee);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while creating the employee: " + e.getMessage());
        }
    }

    public Employee readEmployee(Long id) throws ServiceRuntimeException {
        try {
            return employeeRepository.findEmployeeByEmployeeID(id);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while reading the employee: " + e.getMessage());
        }
    }

    public EmployeeResponse readMyselfEmployee(Long employeeID) throws ServiceRuntimeException, InvalidException {
        try {
            Employee employee = employeeRepository.findEmployeeByEmployeeID(employeeID);
            if (employee == null) {
                throw new InvalidException("Employee not found");
            }
            Person person = personRepository.findPersonByPersonID(employee.getPersonID());
            if (person == null) {
                throw new InvalidException("Person not found");
            }
            Personnel personnel = personnelRepository.findPersonnelByPersonnelID(employee.getPersonnelID());
            if (personnel == null) {
                throw new InvalidException("Personnel not found");
            }

            EmployeeResponse employeeResponse = new EmployeeResponse();
            employeeResponse.setEmployeeID(employeeID);
            employeeResponse.setFullName(person.getFullName());
            employeeResponse.setPosition(personnel.getPosition());
            employeeResponse.setDepartment(personnel.getDepartment());
            employeeResponse.setStatus(personnel.getStatus());

            return employeeResponse;
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while reading the employee: " + e.getMessage());
        }
    }

    public Employee updateEmployee(Employee exitsEmployee, EmployeeRequest employeeRequest) throws ServiceRuntimeException {
        try {
            exitsEmployee.setPersonID(employeeRequest.getPersonID());
            exitsEmployee.setPersonnelID(employeeRequest.getPersonnelID());
            exitsEmployee.setContractID(employeeRequest.getContractID());
            exitsEmployee.setVersion(exitsEmployee.getVersion() + 1);

            return employeeRepository.save(exitsEmployee);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while updating the employee: " + e.getMessage());
        }
    }

    public void deleteEmployee(Long id) throws ServiceRuntimeException {
        try {
            Employee employee = employeeRepository.findEmployeeByEmployeeID(id);

            //Backup
            RecoveryEmployee recoveryEmployee = new RecoveryEmployee();
            recoveryEmployee.setEmployeeID(id);
            recoveryEmployee.setContractID(employee.getContractID());
            recoveryEmployee.setPersonID(employee.getPersonID());
            recoveryEmployee.setPersonnelID(employee.getPersonnelID());
            recoveryEmployeeRepository.save(recoveryEmployee);

            employeeRepository.deleteById(id);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while deleting the employee: " + e.getMessage());
        }
    }

    public List<Person> searchEmployeeByFullName (String str) throws ServiceRuntimeException {
        try {
            List<Person> findByName = personRepository.findByFullName(str);
            return findByName;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while searching a employee: " + e.getMessage());
        }
    }

    public Employee searchEmployeeByEmployeeID (String str) throws NumberFormatException, ServiceRuntimeException {
        try {
            Employee findByEmployeeID = employeeRepository.findEmployeeByEmployeeID(Long.parseLong(str));

            return findByEmployeeID;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Employee not found " + e.getMessage().toLowerCase());
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while searching a employee: " + e.getMessage());
        }
    }

    public Personnel searchEmployeeByPersonnelPhoneNumberOrEmail (String str) throws ServiceRuntimeException {
        try {
            Personnel findByEmail = personnelRepository.findPersonnelByInternalEmail(str);
            if (findByEmail == null) {
                Personnel findByPhoneNumber = personnelRepository.findPersonnelByInternalPhoneNumber(str);
                return findByPhoneNumber;
            } else {
                return findByEmail;
            }
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while searching a employee: " + e.getMessage());
        }
    }

    public EmployeeResponse addEmployeeResponseByPersonID(Long personID) throws ServiceRuntimeException {
        try {
            Employee employee = employeeRepository.findEmployeeByPersonID(personID);
            Person person = personRepository.findPersonByPersonID(personID);
            Personnel personnel = personnelRepository.findPersonnelByPersonnelID(employee.getPersonnelID());

            EmployeeResponse employeeResponse = new EmployeeResponse();
            employeeResponse.setEmployeeID(employee.getEmployeeID());
            employeeResponse.setFullName(person.getFullName());
            employeeResponse.setPosition(personnel.getPosition());
            employeeResponse.setDepartment(personnel.getDepartment());
            employeeResponse.setStatus(personnel.getStatus());

            return employeeResponse;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while searching a employee: " + e.getMessage());
        }
    }

    public EmployeeResponse addEmployeeResponseByEmployeeID(Long employeeID) throws ServiceRuntimeException {
        try {
            Employee employee = employeeRepository.findEmployeeByEmployeeID(employeeID);
            Person person = personRepository.findPersonByPersonID(employee.getPersonID());
            Personnel personnel = personnelRepository.findPersonnelByPersonnelID(employee.getPersonnelID());

            EmployeeResponse employeeResponse = new EmployeeResponse();
            employeeResponse.setEmployeeID(employee.getEmployeeID());
            employeeResponse.setFullName(person.getFullName());
            employeeResponse.setPosition(personnel.getPosition());
            employeeResponse.setDepartment(personnel.getDepartment());
            employeeResponse.setStatus(personnel.getStatus());

            return employeeResponse;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while searching a employee: " + e.getMessage());
        }
    }

    public EmployeeResponse addEmployeeResponseByInternalPhoneNumberOrInternalEmail(String str) throws ServiceRuntimeException {
        try {
            Personnel personnel;
            if (personnelRepository.findPersonnelByInternalPhoneNumber(str) == null) {
                personnel = personnelRepository.findPersonnelByInternalEmail(str);
            } else {
                personnel = personnelRepository.findPersonnelByInternalPhoneNumber(str);
            }

            Employee employee = employeeRepository.findEmployeeByPersonnelID(personnel.getPersonnelID());
            Person person = personRepository.findPersonByPersonID(employee.getPersonID());

            EmployeeResponse employeeResponse = new EmployeeResponse();
            employeeResponse.setEmployeeID(employee.getEmployeeID());
            employeeResponse.setFullName(person.getFullName());
            employeeResponse.setPosition(personnel.getPosition());
            employeeResponse.setDepartment(personnel.getDepartment());
            employeeResponse.setStatus(personnel.getStatus());

            return employeeResponse;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while searching a employee: " + e.getMessage());
        }
    }

    public List<EmployeeResponse> getAllEmployeeResponse() throws InvalidException, ServiceRuntimeException {
        try {
            List<Employee> listEmployee = employeeRepository.findAll();

            if (listEmployee.isEmpty()) {
                throw new InvalidException("No employees exist");
            }

            List<EmployeeResponse> listEmployeeResponse = new ArrayList<>();

            for (Employee employee : listEmployee) {
                EmployeeResponse employeeResponse = new EmployeeResponse();
                Person person = personRepository.findPersonByPersonID(employee.getPersonID());
                Personnel personnel = personnelRepository.findPersonnelByPersonnelID(employee.getPersonnelID());

                employeeResponse.setEmployeeID(employee.getEmployeeID());
                employeeResponse.setFullName(person.getFullName());
                employeeResponse.setPosition(personnel.getPosition());
                employeeResponse.setDepartment(personnel.getDepartment());
                employeeResponse.setStatus(personnel.getStatus());

                listEmployeeResponse.add(employeeResponse);
            }

            return listEmployeeResponse;
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while getting all employee: " + e.getMessage());
        }
    }

    public List<EmployeeResponse> filterEmployeeByStatus(String condition) throws ServiceRuntimeException, InvalidException {
        try {
            List<Personnel> listPersonnel = personnelRepository.findPersonnelByStatus(condition);

            if (listPersonnel.isEmpty()) {
                throw new InvalidException("No employees exist");
            }

            List<EmployeeResponse> listEmployeeResponse = new ArrayList<>();
            for (Personnel personnel : listPersonnel) {
                Employee employee = employeeRepository.findEmployeeByPersonnelID(personnel.getPersonnelID());
                EmployeeResponse employeeResponse = new EmployeeResponse();
                Person person = personRepository.findPersonByPersonID(employee.getPersonID());

                employeeResponse.setEmployeeID(employee.getEmployeeID());
                employeeResponse.setFullName(person.getFullName());
                employeeResponse.setPosition(personnel.getPosition());
                employeeResponse.setDepartment(personnel.getDepartment());
                employeeResponse.setStatus(personnel.getStatus());

                listEmployeeResponse.add(employeeResponse);
            }

            return listEmployeeResponse;
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while getting all employee: " + e.getMessage());
        }
    }

    public List<EmployeeResponse> filterEmployeeByNoContract() throws InvalidException, ServiceRuntimeException {
        try {
            Long id = (long) 0;
            List<Employee> listEmployee = employeeRepository.findEmployeesByContractID(id);

            if (listEmployee.isEmpty()) {
                throw new InvalidException("No employees exist");
            }

            List<EmployeeResponse> listEmployeeResponse = new ArrayList<>();
            for (Employee employee : listEmployee) {
                Personnel personnel = personnelRepository.findPersonnelByPersonnelID(employee.getPersonnelID());
                Person person = personRepository.findPersonByPersonID(employee.getPersonID());
                EmployeeResponse employeeResponse = new EmployeeResponse();

                employeeResponse.setFullName(person.getFullName());
                employeeResponse.setEmployeeID(employee.getEmployeeID());
                employeeResponse.setPosition(personnel.getPosition());
                employeeResponse.setDepartment(personnel.getDepartment());
                employeeResponse.setStatus(personnel.getStatus());

                listEmployeeResponse.add(employeeResponse);
            }

            return listEmployeeResponse;
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while getting all employee: " + e.getMessage());
        }
    }

    //Approve Backup Employee
    public ApproveBackupEmployee createApproveBackupEmployee (EmployeeRequest employeeRequest, Long employeeID) throws InvalidException, ServiceRuntimeException {
        try {
            Employee exitsEmployee = employeeRepository.findEmployeeByEmployeeID(employeeID);
            if (exitsEmployee == null) {
                throw new InvalidException("Employee not found");
            }

            ApproveBackupEmployee approveBackupEmployee = new ApproveBackupEmployee();
            approveBackupEmployee.setEmployeeID(employeeID);
            approveBackupEmployee.setPersonID(employeeRequest.getPersonID());
            approveBackupEmployee.setPersonnelID(employeeRequest.getPersonnelID());
            approveBackupEmployee.setOldContractID(exitsEmployee.getContractID());
            approveBackupEmployee.setNewContractID(employeeRequest.getContractID());
            approveBackupEmployee.setVersion(exitsEmployee.getVersion());
            approveBackupEmployee.setApproveBy("");
            approveBackupEmployee.setApproveDate(LocalDate.now());
            approveBackupEmployee.setStatus("PENDING");

            return approveBackupEmployeeRepository.save(approveBackupEmployee);
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while getting all employee: " + e.getMessage());
        }
    }

    public Employee approveBackupEmployee(Long approveBackupEmployeeID, String username) throws InvalidException, ServiceRuntimeException {
        try {
            ApproveBackupEmployee approveBackupEmployee = approveBackupEmployeeRepository.findApproveBackupEmployeeByApproveBackupEmployeeID(approveBackupEmployeeID);
            if (approveBackupEmployee == null) {
                throw new InvalidException("Approve backup employee not found");
            }

            approveBackupEmployee.setStatus("APPROVED");
            approveBackupEmployee.setApproveBy(username);
            approveBackupEmployee.setApproveDate(LocalDate.now());

            approveBackupEmployeeRepository.save(approveBackupEmployee);

            Employee employee = employeeRepository.findEmployeeByEmployeeID(approveBackupEmployee.getEmployeeID());
            if (employee.getVersion().equals(approveBackupEmployee.getVersion())) {
                employee.setVersion(approveBackupEmployee.getVersion() + 1);
                employee.setPersonID(approveBackupEmployee.getPersonID());
                employee.setPersonnelID(approveBackupEmployee.getPersonnelID());
                employee.setContractID(approveBackupEmployee.getNewContractID());

                // Backup
                backupService.createBackupEmployeeContract(employee);

                return employeeRepository.save(employee);
            } else {
                throw new InvalidException("Conflict detected, another user has modified this product");
            }
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while creating the approve employee contract: " + e.getMessage());
        }
    }

    //Approve Employee Contract
    public ApproveEmployeeContract createApproveEmployeeContract (EmployeeRequest employeeRequest) throws InvalidException, ServiceRuntimeException {
        try {
            ApproveEmployeeContract approveEmployeeContract = new ApproveEmployeeContract();
            approveEmployeeContract.setPersonnelID(employeeRequest.getPersonnelID());
            approveEmployeeContract.setPersonID(employeeRequest.getPersonID());
            Contract exitsContract = contractRepository.findContractByContractID(employeeRequest.getContractID());
            if (exitsContract == null) {
                throw new InvalidException("Contract not found");
            }
            if (exitsContract.getApproveBy().isEmpty()) {
                throw new InvalidException("The contract has not been signed, cannot be added");
            }
            approveEmployeeContract.setContractID(employeeRequest.getContractID());
            approveEmployeeContract.setApproveBy("");
            approveEmployeeContract.setApproveDate(LocalDate.of(0, 0, 0));
            approveEmployeeContract.setStatus("PENDING");

            return approveEmployeeContractRepository.save(approveEmployeeContract);
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while creating the approve employee contract: " + e.getMessage());
        }
    }

    public List<ApproveEmployeeContract> getAllApproveEmployeeContract() throws InvalidException, ServiceRuntimeException {
        try {
            List<ApproveEmployeeContract> listApproveEmployeeContract = approveEmployeeContractRepository.findAll();
            if (listApproveEmployeeContract.isEmpty()) {
                throw new InvalidException("No approve employee contract found");
            }

            return listApproveEmployeeContract;
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while creating the approve employee contract: " + e.getMessage());
        }
    }

    public Employee approveEmployeeContractChange(Long approveEmployeeContractID, String username) throws InvalidException, ServiceRuntimeException {
        try {
            ApproveEmployeeContract approveEmployeeContract = approveEmployeeContractRepository.findApproveEmployeeContractByApproveEmployeeContractID(approveEmployeeContractID);
            if (approveEmployeeContract == null) {
                throw new InvalidException("Approve employee contract not found");
            }
            approveEmployeeContract.setApproveDate(LocalDate.now());
            approveEmployeeContract.setApproveBy(username);
            approveEmployeeContract.setStatus("APPROVED");

            approveEmployeeContractRepository.save(approveEmployeeContract);

            Employee employee = new Employee();
            employee.setContractID(approveEmployeeContract.getContractID());
            employee.setPersonnelID(approveEmployeeContract.getPersonnelID());
            employee.setPersonID(approveEmployeeContract.getPersonID());
            employee.setVersion(1);

            return employeeRepository.save(employee);
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while creating the approve employee contract: " + e.getMessage());
        }
    }
}
