package com.training.hrm.controllers;

import com.training.hrm.dto.EmployeeRequest;
import com.training.hrm.dto.EmployeeResponse;
import com.training.hrm.exceptions.BadRequestException;
import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.Employee;
import com.training.hrm.models.Person;
import com.training.hrm.models.Personnel;
import com.training.hrm.repositories.ContractRepository;
import com.training.hrm.repositories.EmployeeRepository;
import com.training.hrm.repositories.PersonRepository;
import com.training.hrm.repositories.PersonnelRepository;
import com.training.hrm.services.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.OpAnd;
import org.springframework.http.HttpLogging;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            if (contractRepository.findContractByContractID(employeeRequest.getContractID()) == null) {
                throw new InvalidException("Contract not found");
            }
            if (employeeRepository.findEmployeeByPersonID(employeeRequest.getPersonID()) != null) {
                throw new BadRequestException("Person is already linked");
            }
            if (employeeRepository.findEmployeeByPersonnelID(employeeRequest.getPersonnelID()) != null) {
                throw new BadRequestException("Personnel is already linked");
            }
            Employee createEmployee = employeeService.createEmployee(employeeRequest);
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
            if (employeeRepository.findEmployeeByPersonID(employeeRequest.getPersonID()) != null) {
                throw new BadRequestException("Person is already linked");
            }
            if (employeeRepository.findEmployeeByPersonnelID(employeeRequest.getPersonnelID()) != null) {
                throw new BadRequestException("Personnel is already linked");
            }
            Employee updateEmployee = employeeService.updateEmployee(exitsEmployee, employeeRequest);
            return new ResponseEntity<>(updateEmployee, HttpStatus.OK);
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
}
