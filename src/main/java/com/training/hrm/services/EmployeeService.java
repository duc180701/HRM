package com.training.hrm.services;

import com.training.hrm.dto.EmployeeRequest;
import com.training.hrm.dto.EmployeeResponse;
import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.Employee;
import com.training.hrm.models.Person;
import com.training.hrm.models.Personnel;
import com.training.hrm.repositories.EmployeeRepository;
import com.training.hrm.repositories.PersonRepository;
import com.training.hrm.repositories.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    public Employee createEmployee(EmployeeRequest employeeRequest) throws ServiceRuntimeException {
        try {

            Employee employee = new Employee();

            employee.setContractID(employeeRequest.getContractID());
            employee.setPersonnelID(employeeRequest.getPersonnelID());
            employee.setPersonID(employeeRequest.getPersonID());

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

    public Employee updateEmployee(Employee exitsEmployee, EmployeeRequest employeeRequest) throws ServiceRuntimeException {
        try {
            exitsEmployee.setPersonID(employeeRequest.getPersonID());
            exitsEmployee.setPersonnelID(employeeRequest.getPersonnelID());
            exitsEmployee.setContractID(employeeRequest.getContractID());

            return employeeRepository.save(exitsEmployee);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while updating the employee: " + e.getMessage());
        }
    }

    public void deleteEmployee(Long id) throws ServiceRuntimeException {
        try {
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
                System.out.println("DA GAN CHO EMAIL");

                personnel = personnelRepository.findPersonnelByInternalEmail(str);
            } else {
                System.out.println("DA GAN CHO SDT");
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
}
