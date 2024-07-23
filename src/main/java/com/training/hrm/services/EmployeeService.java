package com.training.hrm.services;

import com.training.hrm.dto.EmployeeRequest;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.Employee;
import com.training.hrm.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

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
}
