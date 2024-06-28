package com.training.hrm.controllers;

import com.training.hrm.models.Employee;
import com.training.hrm.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create")
    public ResponseEntity<Object> createEmployee(@Valid @RequestBody Employee employee, BindingResult result) throws Exception{
        try {
            if(result.hasErrors()) {
                return new ResponseEntity<>(result.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
            Employee createEmployee = employeeService.createEmployee(employee);
            return new ResponseEntity<>(createEmployee, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read/{employeeID}")
    public ResponseEntity<Object> readEmployee(@PathVariable String employeeID) throws Exception{
        try {
            Long idNew = Long.parseLong(employeeID);
            Employee employee = employeeService.findEmployeeById(idNew);
            if (employee != null) {
                return new ResponseEntity<>(employee, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Employee not found!", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update/{employeeID}")
    public ResponseEntity<Object> updateEmployee(@PathVariable String employeeID, @Valid @RequestBody Employee employee, BindingResult result) throws Exception{
        try {
            if(result.hasErrors()) {
                return new ResponseEntity<>(result.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
            Long idNew = Long.parseLong(employeeID);
            Employee updateEmployee = employeeService.updateEmployee(employee, idNew);
            return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete/{employeeID}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable String employeeID) throws Exception {
        Long id = Long.parseLong(employeeID);
        try {
            employeeService.deleteEmployee(id);
            return new ResponseEntity<>("Delete employee successful!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
