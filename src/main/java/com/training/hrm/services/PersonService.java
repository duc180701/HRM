package com.training.hrm.services;

import com.training.hrm.dto.PersonRequest;
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

    public Person createPerson(PersonRequest personRequest) throws ServiceRuntimeException {
        try {
            Person person = new Person();

            person.setFullName(personRequest.getFullName());
            person.setGender(personRequest.getGender());
            person.setBirthOfDate(personRequest.getBirthOfDate());

            Person.CitizenIdentity personCiti = new Person.CitizenIdentity();
            personCiti.setCitizenIdentityID(personRequest.getCitizenIdentity().getCitizenIdentityID());
            personCiti.setCitizenIdentityDate(personRequest.getCitizenIdentity().getCitizenIdentityDate());
            personCiti.setCitizenIdentityWhere(personRequest.getCitizenIdentity().getCitizenIdentityWhere());
            personCiti.setCitizenIdentityOutDate(personRequest.getCitizenIdentity().getCitizenIdentityOutDate());
            person.setCitizenIdentity(personCiti);

            Person.PassPort personPass = new Person.PassPort();
            personPass.setPassPortID(personRequest.getPassPort().getPassPortID());
            personPass.setPassPortDate(personRequest.getPassPort().getPassPortDate());
            personPass.setPassPortWhere(personRequest.getPassPort().getPassPortWhere());
            personPass.setPassPortOutDate(personRequest.getPassPort().getPassPortOutDate());
            person.setPassPort(personPass);

            person.setPermanentAddress(personRequest.getPermanentAddress());
            person.setCurrentAddress(personRequest.getCurrentAddress());
            person.setPhoneNumber(personRequest.getPhoneNumber());
            person.setEmail(personRequest.getEmail());

            return personRepository.save(person);
        } catch (ServiceRuntimeException e) {
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

    public Person updatePerson(Person exitsPerson, PersonRequest personRequest) throws ServiceRuntimeException {
        try {
            exitsPerson.setFullName(personRequest.getFullName());
            exitsPerson.setGender(personRequest.getGender());
            exitsPerson.setBirthOfDate(personRequest.getBirthOfDate());
            exitsPerson.getCitizenIdentity().setCitizenIdentityID(personRequest.getCitizenIdentity().getCitizenIdentityID());
            exitsPerson.getCitizenIdentity().setCitizenIdentityDate(personRequest.getCitizenIdentity().getCitizenIdentityDate());
            exitsPerson.getCitizenIdentity().setCitizenIdentityWhere(personRequest.getCitizenIdentity().getCitizenIdentityWhere());
            exitsPerson.getCitizenIdentity().setCitizenIdentityOutDate(personRequest.getCitizenIdentity().getCitizenIdentityOutDate());
            exitsPerson.getPassPort().setPassPortID(personRequest.getPassPort().getPassPortID());
            exitsPerson.getPassPort().setPassPortDate(personRequest.getPassPort().getPassPortDate());
            exitsPerson.getPassPort().setPassPortWhere(personRequest.getPassPort().getPassPortWhere());
            exitsPerson.getPassPort().setPassPortOutDate(personRequest.getPassPort().getPassPortOutDate());
            exitsPerson.setPermanentAddress(personRequest.getPermanentAddress());
            exitsPerson.setCurrentAddress(personRequest.getCurrentAddress());
            exitsPerson.setPhoneNumber(personRequest.getPhoneNumber());
            exitsPerson.setEmail(personRequest.getEmail());

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
