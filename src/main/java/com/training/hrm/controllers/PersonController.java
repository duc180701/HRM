package com.training.hrm.controllers;

import com.training.hrm.exceptions.BadRequestException;
import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.Person;
import com.training.hrm.repositories.PersonRepository;
import com.training.hrm.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    public PersonService personService;

    @Autowired
    public PersonRepository personRepository;

    @PostMapping("/create")
    public ResponseEntity<Object> createPerson (@Valid @RequestBody Person person, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new BadRequestException(result.getAllErrors().get(0).getDefaultMessage());
            }
            if (personRepository.findPersonByEmail(person.getEmail()) != null
                    || personRepository.findPersonByCitizenIdentity_CitizenIdentityID(person.getCitizenIdentity().getCitizenIdentityID()) != null
                    || personRepository.findPersonByPassPort_PassPortID(person.getPassPort().getPassPortID()) != null
                    || personRepository.findPersonByPhoneNumber(person.getPhoneNumber()) != null) {
                throw new BadRequestException("Person already exits");
            }
            Person createPerson = personService.createPerson(person);
            return new ResponseEntity<>(createPerson, HttpStatus.CREATED);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read/{personID}")
    public ResponseEntity<Object> readPerson(@PathVariable String personID) throws Exception {
        try {
            Person person = personService.readPerson(Long.parseLong(personID));

            return new ResponseEntity<>(person, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid person ID format", HttpStatus.BAD_REQUEST);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/update/{personID}")
    public ResponseEntity<Object> updatePerson(@PathVariable String personID, @Valid @RequestBody Person person, BindingResult result) throws Exception {
        try {
            Person updatePerson = personRepository.findPersonByPersonID(Long.parseLong(personID));
            if (person == null) {
                throw new InvalidException("Person not found");
            }
            if (result.hasErrors()) {
                throw new BadRequestException(result.getAllErrors().get(0).getDefaultMessage());
            }
            if (personRepository.findPersonByEmail(person.getEmail()) != null
                    || personRepository.findPersonByCitizenIdentity_CitizenIdentityID(person.getCitizenIdentity().getCitizenIdentityID()) != null
                    || personRepository.findPersonByPassPort_PassPortID(person.getPassPort().getPassPortID()) != null
                    || personRepository.findPersonByPhoneNumber(person.getPhoneNumber()) != null) {
                throw new BadRequestException("Person already exits");
            }
            Person afterUpdatePerson = personService.updatePerson(updatePerson, person);
            return new ResponseEntity<>(afterUpdatePerson, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid person ID format", HttpStatus.BAD_REQUEST);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete/{personID}")
    public ResponseEntity<Object> deletePerson(@PathVariable String personID) {
        try {
            Person person = personRepository.findPersonByPersonID(Long.parseLong(personID));
            if (person == null) {
                throw new InvalidException("Person not found");
            }
            personService.deletePerson(person.getPersonID());
            return new ResponseEntity<>("Delete person successful", HttpStatus.OK);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
