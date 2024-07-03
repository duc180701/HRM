package com.training.hrm.services;

import com.training.hrm.models.Person;
import com.training.hrm.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person createPerson(Person person) throws Exception {
        return personRepository.save(person);
    }

    public Person updatePerson(Person exitsPerson, Person person) throws Exception {
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
    }

    public void deletePerson(Long id) throws Exception {
        personRepository.deleteById(id);
    }
}
