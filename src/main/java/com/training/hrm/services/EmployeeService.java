package com.training.hrm.services;

import com.training.hrm.models.Employee;
import com.training.hrm.repositories.EmployeeRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Validator validator;

    public EmployeeService(EmployeeRepository employeeRepository, Validator validator) {
        this.employeeRepository = employeeRepository;
        this.validator = validator;
    }

    public Employee createEmployee(Employee employee) throws Exception{
        Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employee);

        if (!constraintViolations.isEmpty()) {
            throw new Exception("Adding employees failed");
        }

        if(employeeRepository.findEmployeeByEmail(employee.getEmail()) != null) {
            throw new Exception("Email already exits");
        }

        if(employeeRepository.findEmployeeByPhoneNumber(employee.getPhoneNumber()) != null) {
            throw new Exception("Phonenumber already exits");
        }
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Employee employee, Long id) throws Exception {
        Employee existingEmployee = employeeRepository.findEmployeeByEmployeeID(id);

        if(existingEmployee == null) {
            throw new Exception("Employee not found");
        }

        Set<ConstraintViolation<Employee>> constraintViolations = validator.validate(employee);

        if(!constraintViolations.isEmpty()) {
            throw new Exception("Updating employee failed");
        }

        if(employeeRepository.findEmployeeByEmail(employee.getEmail()) != null) {
            throw new Exception("Email already exits");
        }

        if(employeeRepository.findEmployeeByPhoneNumber(employee.getPhoneNumber()) != null) {
            throw new Exception("Phonenumber already exits");
        }

        existingEmployee.setDateOfBirth(employee.getDateOfBirth());
        existingEmployee.setEndDate(employee.getEndDate());
        existingEmployee.setStartDate(employee.getStartDate());
        existingEmployee.setAddress(employee.getAddress());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setGender(employee.getGender());
        existingEmployee.setPosition(employee.getPosition());
        existingEmployee.setFullName(employee.getFullName());
        existingEmployee.setPhoneNumber(employee.getPhoneNumber());
        existingEmployee.setStatus(employee.getStatus());

        return employeeRepository.save(existingEmployee);
    }

    public void deleteEmployee(Long id) throws Exception {
        Employee employee = employeeRepository.findEmployeeByEmployeeID(id);

        if (employee == null) {
            throw new Exception("Employee not found");
        }

        employeeRepository.delete(employee);
    }

    public Employee findEmployeeById(Long employeeID) {
        return employeeRepository.findEmployeeByEmployeeID(employeeID);
    }

    public Employee findEmployeeByEmail(String email) {
        return employeeRepository.findEmployeeByEmail(email);
    }

    public Employee findEmployeeByPhoneNumber(String phoneNumber) {
        return employeeRepository.findEmployeeByPhoneNumber(phoneNumber);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
}
