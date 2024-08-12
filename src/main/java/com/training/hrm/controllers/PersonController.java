package com.training.hrm.controllers;

import com.training.hrm.dto.PersonRequest;
import com.training.hrm.dto.PersonnelRequest;
import com.training.hrm.exceptions.BadRequestException;
import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.Person;
import com.training.hrm.models.Personnel;
import com.training.hrm.repositories.PersonRepository;
import com.training.hrm.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Create a new person")
    @PostMapping("/create")
    public ResponseEntity<Object> createPerson (@Valid @RequestBody PersonRequest personRequest, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new BadRequestException(result.getAllErrors().get(0).getDefaultMessage());
            }
            if (personRepository.findPersonByEmail(personRequest.getEmail()) != null
                    || personRepository.findPersonByCitizenIdentity_CitizenIdentityID(personRequest.getCitizenIdentity().getCitizenIdentityID()) != null
                    || personRepository.findPersonByPassPort_PassPortID(personRequest.getPassPort().getPassPortID()) != null
                    || personRepository.findPersonByPhoneNumber(personRequest.getPhoneNumber()) != null) {
                throw new BadRequestException("Person already exits");
            }
            Person createPerson = personService.createPerson(personRequest);
            return new ResponseEntity<>(createPerson, HttpStatus.CREATED);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Read all information of a person")
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

    @Operation(summary = "Update a person by person ID")
    @PostMapping("/update/{personID}")
    public ResponseEntity<Object> updatePerson(@PathVariable String personID, @Valid @RequestBody PersonRequest personRequest, BindingResult result) throws Exception {
        try {
            Person exitsPerson = personRepository.findPersonByPersonID(Long.parseLong(personID));
            if (exitsPerson == null) {
                throw new InvalidException("Person not found");
            }
            if (result.hasErrors()) {
                throw new BadRequestException(result.getAllErrors().get(0).getDefaultMessage());
            }
            if (personRequest.getEmail() != exitsPerson.getEmail()) {
                Person findPerson = personRepository.findPersonByEmail(personRequest.getEmail());
                if (findPerson != null && findPerson.getPersonID() != Long.parseLong(personID)) {
                    throw new BadRequestException("Person already exits");
                }
            }
            if (personRequest.getCitizenIdentity().getCitizenIdentityID() != exitsPerson.getCitizenIdentity().getCitizenIdentityID()) {
                Person findPerson = personRepository.findPersonByCitizenIdentity_CitizenIdentityID(personRequest.getCitizenIdentity().getCitizenIdentityID());
                if (findPerson != null && findPerson.getPersonID() != Long.parseLong(personID)) {
                    throw new BadRequestException("Person already exits");
                }
            }
            if (personRequest.getPassPort().getPassPortID() != exitsPerson.getPassPort().getPassPortID()) {
                Person findPerson = personRepository.findPersonByPassPort_PassPortID(personRequest.getPassPort().getPassPortID());
                if (findPerson != null && findPerson.getPersonID() != Long.parseLong(personID)) {
                    throw new BadRequestException("Person already exits");
                }
            }
            if (personRequest.getPhoneNumber() != exitsPerson.getPhoneNumber()) {
                Person findPerson = personRepository.findPersonByPhoneNumber(personRequest.getPhoneNumber());
                if (findPerson != null && findPerson.getPersonID() != Long.parseLong(personID)) {
                    throw new BadRequestException("Person already exits");
                }
            }

            Person afterUpdatePerson = personService.updatePerson(exitsPerson, personRequest);
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

    @Operation(summary = "Delete a person by person ID")
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
