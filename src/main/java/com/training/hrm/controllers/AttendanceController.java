package com.training.hrm.controllers;

import com.training.hrm.dto.ApproveAttendanceRequest;
import com.training.hrm.dto.MachineAttendanceRequest;
import com.training.hrm.dto.ManuallyAttendanceRequest;
import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.ApproveAttendance;
import com.training.hrm.models.Attendance;
import com.training.hrm.repositories.AttendanceRepository;
import com.training.hrm.repositories.EmployeeRepository;
import com.training.hrm.services.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.apache.poi.xddf.usermodel.LineCap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

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
            if (employeeRepository.findEmployeeByEmployeeID(manuallyAttendanceRequest.getEmployeeID()) == null) {
                throw new InvalidException("Employee not found");
            }
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
    @PostMapping(value = "/create-by-file", consumes = "multipart/form-data")
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

    @Operation(summary = "Get all attendance based on time periods")
    @GetMapping("/read-attendance-by-time")
    public ResponseEntity<Object> readAttendanceListByTime (@RequestParam(name = "start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam(name = "end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            if (!(startDate instanceof LocalDate) || !(endDate instanceof LocalDate)) {
                throw new InvalidException("Invalid input data format");
            }
            List<Attendance> listAttendance = attendanceService.getAttendanceByDate(startDate, endDate);

            return new ResponseEntity<>(listAttendance, HttpStatus.OK);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Create attendance table from attendance list")
    @GetMapping("/create-attendance-table")
    public ResponseEntity<Object> createAttendanceTable (@RequestParam(name = "start_date") LocalDate startDate, @RequestParam(name = "end_date") LocalDate endDate) {
        try {
            if (!(startDate instanceof LocalDate) || !(endDate instanceof LocalDate)) {
                throw new InvalidException("Invalid input data format");
            }
            List<ApproveAttendance> approveAttendance = attendanceService.createAttendanceTable(startDate, endDate);

            return new ResponseEntity<>(approveAttendance, HttpStatus.OK);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get all approve attendance")
    @GetMapping("/read-all-approve-attendance")
    public ResponseEntity<Object> readAllApproveAttendance() {
        try {
            List<ApproveAttendance> listApproveAttendance = attendanceService.readAllApproveAttendance();

            return new ResponseEntity<>(listApproveAttendance, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Update approve attendance by ID")
    @PostMapping("/update-approve-attendance/{approveAttendanceID}")
    public ResponseEntity<Object> updateApproveAttendance (@PathVariable String approveAttendanceID, @Valid @RequestBody ApproveAttendanceRequest approveAttendanceRequest, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new InvalidException(result.getAllErrors().get(0).getDefaultMessage());
            }
            Long id = Long.parseLong(approveAttendanceID);
            ApproveAttendance approveAttendance = attendanceService.updateApproveAttendance(id, approveAttendanceRequest);

            return new ResponseEntity<>(approveAttendance, HttpStatus.OK);
        } catch (DateTimeParseException e) {
            return new ResponseEntity<>("Invalid date format. Please use yyyy-MM-dd", HttpStatus.BAD_REQUEST);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid format approve attendance ID", HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Update attendance by ID")
    @PostMapping("/update-attendance/{attendanceID}")
    public ResponseEntity<Object> updateAttendance(@PathVariable String attendanceID, @Valid @RequestBody ManuallyAttendanceRequest manuallyAttendanceRequest, @Valid BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new InvalidException(result.getAllErrors().get(0).getDefaultMessage());
            }
            Attendance attendance = attendanceService.updateAttendance(Long.parseLong(attendanceID), manuallyAttendanceRequest);

            return new ResponseEntity<>(attendance, HttpStatus.OK);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Approve attendance table")
    @PostMapping("/approve-attendance-table/{month}")
    public ResponseEntity<Object> approveAttendanceTable(@PathVariable String month) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                attendanceService.approveAttendanceByMonth(month, username);
                return new ResponseEntity<>("Approve attendance successful", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("You must be logged in to perform this endpoint", HttpStatus.OK);
            }
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
