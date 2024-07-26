package com.training.hrm.controllers;

import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.BackupEmployeeContract;
import com.training.hrm.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/backup-contract-position-department-a-employee")
    public ResponseEntity<Object> reportBackupContractPositionDepartmentOfAEmployee(@RequestParam(name = "employee_id") String employeeID, @RequestParam(name = "start_date") LocalDate startDate, @RequestParam(name = "end_date") LocalDate endDate) {
        try {
            List<Object> listReport = reportService.reportBackupContractPositionDepartmentOfAEmployee(employeeID, startDate, endDate);

            if (!startDate.isBefore(endDate)) {
                throw new InvalidException("Start date must be before end date");
            }

            if (listReport.isEmpty()) {
                throw new InvalidException("This employee has no changes during this period");
            }

            return new ResponseEntity<>(listReport, HttpStatus.OK);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/statistic-employee-contract-change")
    public ResponseEntity<Object> statisticEmployeeContractChange(@RequestParam(name = "start_date") LocalDate startDate, @RequestParam(name = "end_date") LocalDate endDate) {
        try {
            List<BackupEmployeeContract> listStatistic = reportService.statisticEmployeeContract(startDate, endDate);

            if (!startDate.isBefore(endDate)) {
                throw new InvalidException("Start date must be before end date");
            }

            if (listStatistic.isEmpty()) {
                throw new InvalidException("This employee has no changes during this period");
            }

            return new ResponseEntity<>(listStatistic, HttpStatus.OK);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
