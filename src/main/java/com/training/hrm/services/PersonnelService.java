package com.training.hrm.services;

import com.training.hrm.dto.PersonnelRequest;
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

    public Personnel createPersonnel(PersonnelRequest personnelRequest) throws ServiceRuntimeException {
        try {
            Personnel personnel = new Personnel();

            personnel.setLevel(personnelRequest.getLevel());
            personnel.setEducation(personnelRequest.getEducation());
            personnel.setGraduationMajor(personnelRequest.getGraduationMajor());
            personnel.setGraduationSchool(personnelRequest.getGraduationSchool());
            personnel.setGraduationYear(personnelRequest.getGraduationYear());
            personnel.setInternalPhoneNumber(personnelRequest.getInternalPhoneNumber());
            personnel.setInternalEmail(personnelRequest.getInternalEmail());
            personnel.setDepartment(personnelRequest.getDepartment());
            personnel.setPosition(personnelRequest.getPosition());
            personnel.setDirectManagementStaff(personnelRequest.getDirectManagementStaff());
            personnel.setStatus(personnelRequest.getStatus().toString());

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

    public Personnel updatePersonnel(Personnel exitsPersonnel, PersonnelRequest personnelRequest) {
        try {
            exitsPersonnel.setLevel(personnelRequest.getLevel());
            exitsPersonnel.setEducation(personnelRequest.getEducation());
            exitsPersonnel.setGraduationMajor(personnelRequest.getGraduationMajor());
            exitsPersonnel.setGraduationSchool(personnelRequest.getGraduationSchool());
            exitsPersonnel.setGraduationYear(personnelRequest.getGraduationYear());
            exitsPersonnel.setInternalPhoneNumber(personnelRequest.getInternalPhoneNumber());
            exitsPersonnel.setInternalEmail(personnelRequest.getInternalEmail());
            exitsPersonnel.setDepartment(personnelRequest.getDepartment());
            exitsPersonnel.setPosition(personnelRequest.getPosition());
            exitsPersonnel.setDirectManagementStaff(personnelRequest.getDirectManagementStaff());
            exitsPersonnel.setStatus(personnelRequest.getStatus().toString());

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
