package com.training.hrm.controllers;

import com.training.hrm.exceptions.BadRequestException;
import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.Employee;
import com.training.hrm.models.Person;
import com.training.hrm.repositories.ContractRepository;
import com.training.hrm.repositories.EmployeeRepository;
import com.training.hrm.repositories.PersonRepository;
import com.training.hrm.repositories.PersonnelRepository;
import com.training.hrm.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private ContractRepository contractRepository;

    @PostMapping("/create")
    public ResponseEntity<Object> createEmployee(@Valid @RequestBody Employee employee, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new BadRequestException(result.getAllErrors().get(0).getDefaultMessage());
            }
            if (personnelRepository.findPersonnelByPersonnelID(employee.getPersonnelID()) == null) {
                throw new InvalidException("Personnel not found");
            }
            if (personRepository.findPersonByPersonID(employee.getPersonID()) == null) {
                throw new InvalidException("Person not found");
            }
            if (contractRepository.findContractByContractID(employee.getContractID()) == null) {
                throw new InvalidException("Contract not found");
            }
            if (employeeRepository.findEmployeeByPersonID(employee.getPersonID()) != null) {
                throw new BadRequestException("Person is already linked");
            }
            if (employeeRepository.findEmployeeByPersonnelID(employee.getPersonnelID()) != null) {
                throw new BadRequestException("Personnel is already linked");
            }
            Employee createEmployee = employeeService.createEmployee(employee);
            return new ResponseEntity<>(createEmployee, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid employee ID format", HttpStatus.BAD_REQUEST);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read/{employeeID}")
    public ResponseEntity<Object> readEmployee(@PathVariable String employeeID) {
        try {
            Employee employee = employeeService.readEmployee(Long.parseLong(employeeID));
            if (employee == null) {
                throw new InvalidException("Employee not found");
            }
            return new ResponseEntity<>(employee, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid employee ID format", HttpStatus.BAD_REQUEST);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
