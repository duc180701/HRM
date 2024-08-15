package com.training.hrm.services;

import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.recoveries.RecoveryContract;
import com.training.hrm.recoveries.RecoveryEmployee;
import com.training.hrm.recoveries.RecoveryPerson;
import com.training.hrm.recoveries.RecoveryPersonnel;
import com.training.hrm.repositories.RecoveryContractRepository;
import com.training.hrm.repositories.RecoveryEmployeeRepository;
import com.training.hrm.repositories.RecoveryPersonRepository;
import com.training.hrm.repositories.RecoveryPersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecoveryService {

    @Autowired
    private RecoveryPersonnelRepository recoveryPersonnelRepository;

    @Autowired
    private RecoveryPersonRepository recoveryPersonRepository;

    @Autowired
    private RecoveryContractRepository recoveryContractRepository;

    @Autowired
    private RecoveryEmployeeRepository recoveryEmployeeRepository;

    public List<RecoveryContract> getAllRecoveryContract() throws ServiceRuntimeException, InvalidException {
        try {
            List<RecoveryContract> listRecoveryContract = recoveryContractRepository.findAll();
            if (listRecoveryContract.isEmpty()) {
                throw new InvalidException("The contract recovery list is empty");
            }

            return listRecoveryContract;
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while reading this recovery list: " + e.getMessage());
        }
    }

    public List<RecoveryEmployee> getAllRecoveryEmployee() throws ServiceRuntimeException, InvalidException {
        try {
            List<RecoveryEmployee> listRecoveryEmployee = recoveryEmployeeRepository.findAll();
            if (listRecoveryEmployee.isEmpty()) {
                throw new InvalidException("The employee recovery list is empty");
            }

            return listRecoveryEmployee;
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while reading this recovery list: " + e.getMessage());
        }
    }

    public List<RecoveryPerson> getAllRecoveryPerson() throws ServiceRuntimeException, InvalidException {
        try {
            List<RecoveryPerson> listRecoveryPerson = recoveryPersonRepository.findAll();
            if (listRecoveryPerson.isEmpty()) {
                throw new InvalidException("The person recovery list is empty");
            }

            return listRecoveryPerson;
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while reading this recovery list: " + e.getMessage());
        }
    }

    public List<RecoveryPersonnel> getAllRecoveryPersonnel() throws ServiceRuntimeException, InvalidException {
        try {
            List<RecoveryPersonnel> listRecoveryPersonnel = recoveryPersonnelRepository.findAll();
            if (listRecoveryPersonnel.isEmpty()) {
                throw new InvalidException("The personnel recovery list is empty");
            }

            return listRecoveryPersonnel;
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while reading this recovery list: " + e.getMessage());
        }
    }
}
