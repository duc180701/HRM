package com.training.hrm.services;

import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.Person;
import com.training.hrm.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person createPerson(Person person) throws ServiceRuntimeException {
        try {
            return personRepository.save(person);
        } catch (Exception e) {
            throw new ServiceRuntimeException("An error occurred while creating the person: " + e.getMessage());
        }
    }

    public Person readPerson(Long id) throws ServiceRuntimeException, InvalidException {
        try {
            Person person = personRepository.findPersonByPersonID(id);
            if(person == null) {
                throw new InvalidException("Person not found");
            }
            return person;
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while reading the person: " + e.getMessage());
        }

    }

    public Person updatePerson(Person exitsPerson, Person person) throws ServiceRuntimeException {
        try {
            exitsPerson.setFullName(person.getFullName());
            exitsPerson.setGender(person.getGender());
            exitsPerson.setBirthOfDate(person.getBirthOfDate());
            exitsPerson.getCitizenIdentity().setCitizenIdentityID(person.getCitizenIdentity().getCitizenIdentityID());
            exitsPerson.getCitizenIdentity().setCitizenIdentityDate(person.getCitizenIdentity().getCitizenIdentityDate());
            exitsPerson.getCitizenIdentity().setCitizenIdentityWhere(person.getCitizenIdentity().getCitizenIdentityWhere());
            exitsPerson.getCitizenIdentity().setCitizenIdentityOutDate(person.getCitizenIdentity().getCitizenIdentityOutDate());
            exitsPerson.getPassPort().setPassPortID(person.getPassPort().getPassPortID());
            exitsPerson.getPassPort().setPassPortDate(person.getPassPort().getPassPortDate());
            exitsPerson.getPassPort().setPassPortWhere(person.getPassPort().getPassPortWhere());
            exitsPerson.getPassPort().setPassPortOutDate(person.getPassPort().getPassPortOutDate());
            exitsPerson.setPermanentAddress(person.getPermanentAddress());
            exitsPerson.setCurrentAddress(person.getCurrentAddress());
            exitsPerson.setPhoneNumber(person.getPhoneNumber());
            exitsPerson.setEmail(person.getEmail());

            return personRepository.save(exitsPerson);
        } catch (Exception e) {
            throw new ServiceRuntimeException("An error occurred while updating the person: " + e.getMessage());
        }
    }

    public void deletePerson(Long id) throws ServiceRuntimeException, InvalidException {
        try {
            Person person = personRepository.findPersonByPersonID(id);
            if (person == null) {
                throw new InvalidException("Person not found");
            }
        } catch (InvalidException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceRuntimeException("An error occurred while deleting the person: " + e.getMessage());
        }
    }
}
