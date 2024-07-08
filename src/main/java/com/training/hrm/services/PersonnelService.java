package com.training.hrm.services;

import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.Personnel;
import com.training.hrm.repositories.PersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonnelService {

    @Autowired
    private PersonnelRepository personnelRepository;

    public Personnel createPersonnel(Personnel personnel) throws ServiceRuntimeException {
        try {
            return personnelRepository.save(personnel);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while creating the personnel: " + e.getMessage());
        }
    }

    public Personnel readPersonnel(Long id) throws InvalidException, ServiceRuntimeException {
        try {
            Personnel findPersonnel = personnelRepository.findPersonnelByPersonnelID(id);
            if (findPersonnel == null) {
                throw new InvalidException("Personnel not found");
            }
            return findPersonnel;
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while reading the personnel: " + e.getMessage());
        }
    }

    public Personnel updatePersonnel(Personnel exitsPersonnel, Personnel personnel) {
        try {
            exitsPersonnel.setLevel(personnel.getLevel());
            exitsPersonnel.setEducation(personnel.getEducation());
            exitsPersonnel.setGraduationMajor(personnel.getGraduationMajor());
            exitsPersonnel.setGraduationSchool(personnel.getGraduationSchool());
            exitsPersonnel.setGraduationYear(personnel.getGraduationYear());
            exitsPersonnel.setInternalPhoneNumber(personnel.getInternalPhoneNumber());
            exitsPersonnel.setInternalEmail(personnel.getInternalEmail());
            exitsPersonnel.setEmployeeID(personnel.getEmployeeID());
            exitsPersonnel.setEmployeeAccount(personnel.getEmployeeAccount());
            exitsPersonnel.setDepartment(personnel.getDepartment());
            exitsPersonnel.setPosition(personnel.getPosition());
            exitsPersonnel.setDirectManagementStaff(personnel.getDirectManagementStaff());
            exitsPersonnel.setStatus(personnel.getStatus());

            return personnelRepository.save(exitsPersonnel);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while reading the personnel: " + e.getMessage());
        }
    }

    public void deletePersonnel(Long id) {
        try {
            personnelRepository.deleteById(id);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while reading the personnel: " + e.getMessage());
        }
    }

}
