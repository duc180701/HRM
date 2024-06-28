package com.training.hrm.services;

import com.training.hrm.models.WorkHistory;
import com.training.hrm.repositories.WorkHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkHistoryService {
    @Autowired
    private WorkHistoryRepository workHistoryRepository;

    public WorkHistory createWorkHistory(WorkHistory workHistory) {

    }
}
