package com.training.hrm.controllers;

import com.training.hrm.components.JwtUtil;
import com.training.hrm.dto.EmployeeRequest;
import com.training.hrm.dto.EmployeeResponse;
import com.training.hrm.exceptions.BadRequestException;
import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.ApproveEmployeeContract;
import com.training.hrm.models.Employee;
import com.training.hrm.models.Person;
import com.training.hrm.models.Personnel;
import com.training.hrm.repositories.*;
import com.training.hrm.services.BackupService;
import com.training.hrm.services.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private JwtUtil jwtUtil;

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

    @Autowired
    private BackupEmployeeContractRepository backupEmployeeContractRepository;

    @Autowired
    private BackupService backupService;

    @Operation(summary = "Create new employee")
    @PostMapping("/create")
    public ResponseEntity<Object> createEmployee(@Valid @RequestBody EmployeeRequest employeeRequest, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new BadRequestException(result.getAllErrors().get(0).getDefaultMessage());
            }
            if (personnelRepository.findPersonnelByPersonnelID(employeeRequest.getPersonnelID()) == null) {
                throw new InvalidException("Personnel not found");
            }
            if (personRepository.findPersonByPersonID(employeeRequest.getPersonID()) == null) {
                throw new InvalidException("Person not found");
            }
            if (contractRepository.findContractByContractID(employeeRequest.getContractID()) == null && employeeRequest.getContractID() != 0) {
                throw new InvalidException("Contract not found");
            }
            if (employeeRepository.findEmployeeByPersonID(employeeRequest.getPersonID()) != null) {
                throw new BadRequestException("Person is already linked");
            }
            if (employeeRepository.findEmployeeByPersonnelID(employeeRequest.getPersonnelID()) != null) {
                throw new BadRequestException("Personnel is already linked");
            }
            if (employeeRepository.findEmployeeByContractID(employeeRequest.getContractID()) != null) {
                throw new BadRequestException("Contract is already linked");
            }

            if (employeeRequest.getContractID().toString().isEmpty() || employeeRequest.getContractID() == 0) {
                Employee createEmployee = employeeService.createEmployee(employeeRequest);
                return new ResponseEntity<>(createEmployee, HttpStatus.OK);
            } else {
                employeeService.createApproveEmployeeContract(employeeRequest);
                return new ResponseEntity<>("Information added successfully, please wait for approval", HttpStatus.OK);
            }
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

    @Operation(summary = "Get all employee in company")
    @GetMapping("/read-all-employee")
    public ResponseEntity<Object> readAllEmployee() {
        try {
            List<EmployeeResponse> listEmployee = employeeService.getAllEmployeeResponse();
            return new ResponseEntity<>(listEmployee, HttpStatus.OK);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Read full information of a employee by employee ID")
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

    @Operation(summary = "Read your own employee information")
    @GetMapping("/read-myself-employee")
    public ResponseEntity<Object> readMyselfEmployee(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            } else {
                return new ResponseEntity<>("Invalid token", HttpStatus.BAD_REQUEST);
            }

            String employeeID = jwtUtil.extractEmployeeID(token);
            EmployeeResponse employeeResponse = employeeService.readMyselfEmployee(Long.parseLong(employeeID));

            return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
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

    @Operation(summary = "Update a employee by employee ID")
    @PostMapping("/update/{employeeID}")
    public ResponseEntity<Object> updateEmployee(@PathVariable String employeeID, @Valid @RequestBody EmployeeRequest employeeRequest, BindingResult result) {
        try {
            Employee exitsEmployee = employeeRepository.findEmployeeByEmployeeID(Long.parseLong(employeeID));
            if (exitsEmployee == null) {
                throw new InvalidException("Employee not found");
            }
            if (result.hasErrors()) {
                throw new BadRequestException(result.getAllErrors().get(0).getDefaultMessage());
            }
            if (personnelRepository.findPersonnelByPersonnelID(employeeRequest.getPersonnelID()) == null) {
                throw new InvalidException("Personnel not found");
            }
            if (personRepository.findPersonByPersonID(employeeRequest.getPersonID()) == null) {
                throw new InvalidException("Person not found");
            }
            if (contractRepository.findContractByContractID(employeeRequest.getContractID()) == null) {
                throw new InvalidException("Contract not found");
            }
            if (employeeRepository.findEmployeeByPersonID(employeeRequest.getPersonID()) != null && !exitsEmployee.getPersonID().equals(employeeRequest.getPersonID())) {
                throw new BadRequestException("Person is already linked");
            }
            if (employeeRepository.findEmployeeByPersonnelID(employeeRequest.getPersonnelID()) != null && !exitsEmployee.getPersonnelID().equals(employeeRequest.getPersonnelID())) {
                throw new BadRequestException("Personnel is already linked");
            }
            if (employeeRepository.findEmployeeByContractID(employeeRequest.getContractID()) != null && !exitsEmployee.getContractID().equals(employeeRequest.getContractID())) {
                throw new BadRequestException("Contract is already linked");
            }

            if (employeeRequest.getContractID() == 0 || employeeRequest.getContractID().toString().isEmpty()) {
                Employee updateEmployee = employeeService.updateEmployee(exitsEmployee, employeeRequest);
                return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
            } else {
                employeeService.createApproveBackupEmployee(employeeRequest, Long.parseLong(employeeID));
                return new ResponseEntity<>("Information added successfully, please wait for approval", HttpStatus.OK);
            }
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

    @Operation(summary = "Delete a employee by ID")
    @PostMapping("/delete/{employeeID}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable String employeeID) {
        try {
            if (employeeRepository.findEmployeeByEmployeeID(Long.parseLong(employeeID)) == null) {
                throw new InvalidException("Employee not found");
            }
            employeeService.deleteEmployee(Long.parseLong(employeeID));
            return new ResponseEntity<>("Delete employee successful", HttpStatus.OK);
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

    @Operation(summary = "Search employee by phone number, email, full name, employee ID")
    @PostMapping("/search/{search}")
    public ResponseEntity<Object> searchEmployee(@PathVariable String search) {
        try {
            List<Person> searchByFullName = employeeService.searchEmployeeByFullName(search);
            if (searchByFullName.isEmpty()) {
                Personnel searchByPhoneNumberOrEmail = employeeService.searchEmployeeByPersonnelPhoneNumberOrEmail(search);
                if (searchByPhoneNumberOrEmail == null) {
                    Employee searchByEmployeeID = employeeService.searchEmployeeByEmployeeID(search);
                    if (searchByEmployeeID == null) {
                        throw new InvalidException("Employee not found");
                    } else {
                        EmployeeResponse employeeResponse = employeeService.addEmployeeResponseByEmployeeID(searchByEmployeeID.getEmployeeID());
                        return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
                    }
                } else {
                    EmployeeResponse employeeResponse = employeeService.addEmployeeResponseByInternalPhoneNumberOrInternalEmail(searchByPhoneNumberOrEmail.getInternalPhoneNumber());
                    return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
                }
            } else {
                List<EmployeeResponse> listEmployeeResponse = new ArrayList<>();
                for (Person person : searchByFullName) {
                    listEmployeeResponse.add(employeeService.addEmployeeResponseByPersonID(person.getPersonID()));
                }
                return new ResponseEntity<>(listEmployeeResponse, HttpStatus.OK);
            }
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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

    @Operation(summary = "Filter employee by status, contract")
    @GetMapping("/filter/{condition}")
    public ResponseEntity<Object> filterEmployee (@PathVariable String condition) {
        try {
            List<EmployeeResponse> listEmployeeResponse;
            switch (condition) {
                case "getAllEmployee":
                    listEmployeeResponse = employeeService.getAllEmployeeResponse();
                    return new ResponseEntity<>(listEmployeeResponse, HttpStatus.OK);
                case "DA_NGHi_VIEC":
                    listEmployeeResponse = employeeService.filterEmployeeByStatus(condition);
                    return new ResponseEntity<>(listEmployeeResponse, HttpStatus.OK);
                case "THU_VIEC":
                    listEmployeeResponse = employeeService.filterEmployeeByStatus(condition);
                    return new ResponseEntity<>(listEmployeeResponse, HttpStatus.OK);
                case "DANG_LAM_VIEC":
                    listEmployeeResponse = employeeService.filterEmployeeByStatus(condition);
                    return new ResponseEntity<>(listEmployeeResponse, HttpStatus.OK);
                case "NGHI_CHE_DO":
                    listEmployeeResponse = employeeService.filterEmployeeByStatus(condition);
                    return new ResponseEntity<>(listEmployeeResponse, HttpStatus.OK);
                case "NGHI_THAI_SAN":
                    listEmployeeResponse = employeeService.filterEmployeeByStatus(condition);
                    return new ResponseEntity<>(listEmployeeResponse, HttpStatus.OK);
                case "noContract":
                    listEmployeeResponse = employeeService.filterEmployeeByNoContract();
                    return new ResponseEntity<>(listEmployeeResponse, HttpStatus.OK);
                default:
                    return new ResponseEntity<>("Filtering employees fails", HttpStatus.BAD_REQUEST);
            }
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get all approve employee contract list")
    @GetMapping("/read-all-approve-employee-contract")
    public ResponseEntity<Object> readAllApproveEmployeeContract() {
        try {
            List<ApproveEmployeeContract> listApproveEmployeeContract = employeeService.getAllApproveEmployeeContract();

            return new ResponseEntity<>(listApproveEmployeeContract, HttpStatus.OK);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Approve a approve employee contract change by ID")
    @PostMapping("/approve-employee-contract-change/{approveEmployeeContractID}")
    public ResponseEntity<Object> approveEmployeeContractChange(@PathVariable String approveEmployeeContractID) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                Employee employee = employeeService.approveEmployeeContractChange(Long.parseLong(approveEmployeeContractID), username);
                return new ResponseEntity<>(employee, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("You must be logged in to perform this endpoint", HttpStatus.OK);
            }
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Approve employee contract in pending update")
    @PostMapping("/approve-backup-employee/{approveBackupEmployeeID}")
    public ResponseEntity<Object> approveBackupEmployee(@PathVariable String approveBackupEmployeeID) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                Employee employee = employeeService.approveBackupEmployee(Long.parseLong(approveBackupEmployeeID), username);
                return new ResponseEntity<>(employee, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("You must be logged in to perform this endpoint", HttpStatus.OK);
            }
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
}
