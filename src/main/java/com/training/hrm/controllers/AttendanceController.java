package com.training.hrm.controllers;

import com.training.hrm.dto.MachineAttendanceRequest;
import com.training.hrm.dto.ManuallyAttendanceRequest;
import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.Attendance;
import com.training.hrm.repositories.AttendanceRepository;
import com.training.hrm.repositories.EmployeeRepository;
import com.training.hrm.services.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/create-by-machine-attendance")
    public ResponseEntity<Object> createByMachineAttendance (@Valid @RequestBody MachineAttendanceRequest machineAttendanceRequest, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new InvalidException(result.getAllErrors().get(0).getDefaultMessage());
            }
            if (employeeRepository.findEmployeeByEmployeeID(machineAttendanceRequest.getEmployeeID()) == null) {
                throw new InvalidException("Employee not found");
            }

            Attendance attendance = attendanceService.createByMachineAttendance(machineAttendanceRequest);

            return new ResponseEntity<>(attendance, HttpStatus.OK);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create-by-manually")
    public ResponseEntity<Object> createByManually (@Valid @RequestBody ManuallyAttendanceRequest manuallyAttendanceRequest, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new InvalidException(result.getAllErrors().get(0).getDefaultMessage());
            }
            System.out.println("After check error request");
            if (employeeRepository.findEmployeeByEmployeeID(manuallyAttendanceRequest.getEmployeeID()) == null) {
                throw new InvalidException("Employee not found");
            }
            System.out.println("Before call method createByManually");
            Attendance attendance = attendanceService.createByManually(manuallyAttendanceRequest);

            return new ResponseEntity<>(attendance, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @Operation(summary = "The system has not recognized the MultipartFile type")
    @PostMapping("/create-by-file")
    public ResponseEntity<Object> createByFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new InvalidException("Please select a file to upload");
            }
            attendanceService.importExcelFile(file);
            return new ResponseEntity<>("File uploaded and data saved successfully.", HttpStatus.OK);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (MultipartException e) {
            return new ResponseEntity<>("Failed to read multipart request", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload and process file", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
