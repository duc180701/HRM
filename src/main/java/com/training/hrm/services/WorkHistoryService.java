package com.training.hrm.services;

import com.training.hrm.models.WorkHistory;
import com.training.hrm.repositories.WorkHistoryRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class WorkHistoryService {
    @Autowired
    private WorkHistoryRepository workHistoryRepository;

    @Autowired
    private Validator validator;

    public WorkHistoryService() {

    }

    public WorkHistoryService(WorkHistoryRepository workHistoryRepository, Validator validator) {
        this.workHistoryRepository = workHistoryRepository;
        this.validator = validator;
    }

    public WorkHistory createWorkHistory(WorkHistory workHistory) throws Exception {
        Set<ConstraintViolation<WorkHistory>> violations = validator.validate(workHistory);

        if(!violations.isEmpty()) {
            throw new Exception("Adding work history failed");
        }

//        if (workHistoryRepository.findWorkHistoryByEmployeeID(workHistory.getEmployeeID()) != null) {
//            if (workHistoryRepository.findWorkHistoryByPosition(workHistory.getPosition()) != null) {
//                if (workHistoryRepository.findWorkHistoryByDepartment(workHistory.getDepartment()) != null) {
//                    if (workHistoryRepository.findWorkHistoryByStartDate(workHistory.getStartDate()) != null) {
//                        if (workHistoryRepository.findWorkHistoryByEndDate(workHistory.getEndDate()) != null) {
//                            throw new Exception("Work history already exits");
//                        }
//                    }
//                }
//            }
//        }

        return workHistoryRepository.save(workHistory);
    }

    public WorkHistory readWorkHistory(Long id) throws Exception{
        WorkHistory exitsWorkHistory = workHistoryRepository.findWorkHistoryByWorkHistoryID(id);

        if (exitsWorkHistory == null) {
            throw new Exception("Work history not found");
        }

        return exitsWorkHistory;
    }

    public WorkHistory updateWorkHistory(WorkHistory workHistory, Long id) throws Exception{
        WorkHistory exitsWorkHistory = workHistoryRepository.findWorkHistoryByWorkHistoryID(id);

        if (exitsWorkHistory ==  null) {
            throw new Exception("Work history not found");
        }

        Set<ConstraintViolation<WorkHistory>> violations = validator.validate(workHistory);

        if (!violations.isEmpty()) {
            throw new Exception("Updating work history failed");
        }

//        if (workHistoryRepository.findWorkHistoryByEmployeeID(workHistory.getEmployeeID()) != null) {
//            if (workHistoryRepository.findWorkHistoryByPosition(workHistory.getPosition()) != null) {
//                if (workHistoryRepository.findWorkHistoryByDepartment(workHistory.getDepartment()) != null) {
//                    if (workHistoryRepository.findWorkHistoryByStartDate(workHistory.getStartDate()) != null) {
//                        if (workHistoryRepository.findWorkHistoryByEndDate(workHistory.getEndDate()) != null) {
//                            throw new Exception("Work history already exits");
//                        }
//                    }
//                }
//            }
//        }

        exitsWorkHistory.setEmployeeID(workHistory.getEmployeeID());
        exitsWorkHistory.setDepartment(workHistory.getDepartment());
        exitsWorkHistory.setPosition(workHistory.getPosition());
        exitsWorkHistory.setStartDate(workHistory.getStartDate());
        exitsWorkHistory.setEndDate(workHistory.getEndDate());
        exitsWorkHistory.setReason(workHistory.getReason());

        return workHistoryRepository.save(exitsWorkHistory);
    }

    public void deleteWorkHistory (Long id) throws Exception {
        WorkHistory exitsWorkHistory = workHistoryRepository.findWorkHistoryByWorkHistoryID(id);

        if (exitsWorkHistory == null) {
            throw new Exception("Work history not found");
        }

        workHistoryRepository.deleteById(id);
    }

}
