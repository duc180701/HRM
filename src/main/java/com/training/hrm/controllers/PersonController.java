package com.training.hrm.controllers;

import com.training.hrm.models.Person;
import com.training.hrm.repositories.PersonRepository;
import com.training.hrm.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.spec.EdDSAParameterSpec;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    public PersonService personService;

    @Autowired
    public PersonRepository personRepository;

    @PostMapping("/create")
    public ResponseEntity<Object> createPerson (@Valid @RequestBody Person person, BindingResult result) throws Exception {
        try {
            if (result.hasErrors()) {
                return new ResponseEntity<>(result.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
            if (personRepository.findPersonByEmail(person.getEmail()) != null
            || personRepository.findPersonByCitizenIdentity_CitizenIdentityID(person.getCitizenIdentity().getCitizenIdentityID()) != null
            || personRepository.findPersonByPassPort_PassPortID(person.getPassPort().getPassPortID()) != null
            || personRepository.findPersonByPhoneNumber(person.getPhoneNumber()) != null) {
                return new ResponseEntity<>("Person already exits", HttpStatus.BAD_REQUEST);
            }
            Person createPerson = personService.createPerson(person);
            return new ResponseEntity<>(createPerson, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read/{personID}")
    public ResponseEntity<Object> readPerson(@PathVariable String personID) throws Exception {
        try {
            Person person = personRepository.findPersonByPersonID(Long.parseLong(personID));
            if (person == null) {
                return new ResponseEntity<>("Person not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(person, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update/{personID}")
    public ResponseEntity<Object> updatePerson(@PathVariable String personID, @Valid @RequestBody Person person, BindingResult result) throws Exception {
        try {
            Person updatePerson = personRepository.findPersonByPersonID(Long.parseLong(personID));
            if (person == null) {
                return new ResponseEntity<>("Person not found", HttpStatus.NOT_FOUND);
            }
            if (result.hasErrors()) {
                return new ResponseEntity<>(result.getAllErrors().get(0).getDefaultMessage(), HttpStatus.BAD_REQUEST);
            }
            if (personRepository.findPersonByEmail(person.getEmail()) != null
                    || personRepository.findPersonByCitizenIdentity_CitizenIdentityID(person.getCitizenIdentity().getCitizenIdentityID()) != null
                    || personRepository.findPersonByPassPort_PassPortID(person.getPassPort().getPassPortID()) != null
                    || personRepository.findPersonByPhoneNumber(person.getPhoneNumber()) != null) {
                return new ResponseEntity<>("Person already exits", HttpStatus.BAD_REQUEST);
            }
            Person afterUpdatePerson = personService.updatePerson(updatePerson, person);
            return new ResponseEntity<>(afterUpdatePerson, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete/{personID}")
    public ResponseEntity<Object> deletePerson(@PathVariable String personID) throws Exception {
        try {
            Person person = personRepository.findPersonByPersonID(Long.parseLong(personID));
            if (person == null) {
                return new ResponseEntity<>("Person not found", HttpStatus.NOT_FOUND);
            }
            personService.deletePerson(person.getPersonID());
            return new ResponseEntity<>("Delete person successful", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
