package com.training.hrm.controllers;

import com.training.hrm.models.WorkHistory;
import com.training.hrm.services.WorkHistoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workhistory")
public class WorkHistoryController {

    @Autowired
    private WorkHistoryService workHistoryService;

    @PostMapping("/create")
    public ResponseEntity<Object> createWorkHistory(@Valid @RequestBody WorkHistory workHistory, BindingResult result) throws Exception {
        try {
            if (result.hasErrors()) {
                return new ResponseEntity<>(result.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
            WorkHistory createWorkHistory = workHistoryService.createWorkHistory(workHistory);
            return new ResponseEntity<>(createWorkHistory, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read/{workHistoryID}")
    public ResponseEntity<Object> readWorkHistory(@PathVariable String workHistoryID) throws Exception {
        try {
            Long idNew = Long.parseLong(workHistoryID);
            WorkHistory readWorkHistory = workHistoryService.readWorkHistory(idNew);
            return new ResponseEntity<>(readWorkHistory, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update/{workHistoryID}")
    public ResponseEntity<Object> updateWorkHistory(@PathVariable String workHistoryID, @Valid @RequestBody WorkHistory workHistory, BindingResult result)  throws  Exception {
        try {
            if (result.hasErrors()) {
                return new ResponseEntity<>(result.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
            Long idNew = Long.parseLong(workHistoryID);
            WorkHistory updateWorkHistory = workHistoryService.updateWorkHistory(workHistory, idNew);
            return new ResponseEntity<>(updateWorkHistory, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete/{workHistoryID}")
    public ResponseEntity<Object> deleteWorkHistory (@PathVariable String workHistoryID) {
        try {
            Long idNew = Long.parseLong(workHistoryID);
            workHistoryService.deleteWorkHistory(idNew);
            return new ResponseEntity<>("Delete work history successful", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
