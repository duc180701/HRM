package com.training.hrm.controllers;

import com.training.hrm.dto.PersonnelRequest;
import com.training.hrm.exceptions.BadRequestException;
import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.Personnel;
import com.training.hrm.repositories.PersonnelRepository;
import com.training.hrm.services.BackupService;
import com.training.hrm.services.PersonnelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personnel")
public class PersonnelController {

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private PersonnelService personnelService;

    @Autowired
    private BackupService backupService;

    @PostMapping("/create")
    public ResponseEntity<Object> createPersonnel(@Valid @RequestBody PersonnelRequest personnelRequest, BindingResult result) {
            try {
                if (result.hasErrors()) {
                    throw new BadRequestException(result.getAllErrors().get(0).getDefaultMessage());
                }
                if (personnelRepository.findPersonnelByInternalEmail(personnelRequest.getInternalEmail()) != null
                        || personnelRepository.findPersonnelByInternalPhoneNumber(personnelRequest.getInternalPhoneNumber()) != null) {
                    throw new BadRequestException("Personnel already exits");
                }
                Personnel newPersonnel = personnelService.createPersonnel(personnelRequest);
                return new ResponseEntity<>(newPersonnel, HttpStatus.CREATED);
            } catch (BadRequestException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            } catch (ServiceRuntimeException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
    }

    @GetMapping("/read/{personnelID}")
    public ResponseEntity<Object> readPersonnel(@PathVariable String personnelID) {
        try {
            Personnel personnel = personnelService.readPersonnel(Long.parseLong(personnelID));

            return new ResponseEntity<>(personnel, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid personnel ID format", HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update/{personnelID}")
    public ResponseEntity<Object> updatePersonnel(@PathVariable String personnelID, @Valid @RequestBody PersonnelRequest personnelRequest, BindingResult result) {
        try {
            Personnel exitsPersonnel = personnelRepository.findPersonnelByPersonnelID(Long.parseLong(personnelID));

            if (exitsPersonnel == null) {
                throw new InvalidException("Personnel not found");
            }
            if (result.hasErrors()) {
                throw new BadRequestException(result.getAllErrors().get(0).getDefaultMessage());
            }
            if (personnelRequest.getInternalPhoneNumber() != exitsPersonnel.getInternalPhoneNumber()) {
                Personnel findPersonnel = personnelRepository.findPersonnelByInternalPhoneNumber(personnelRequest.getInternalPhoneNumber());
                if (findPersonnel != null && findPersonnel.getPersonnelID() != Long.parseLong(personnelID)) {
                    throw new BadRequestException("Personnel already exits");
                }
            }
            if (personnelRequest.getInternalEmail() != exitsPersonnel.getInternalEmail()) {
                Personnel findPersonnel = personnelRepository.findPersonnelByInternalEmail(personnelRequest.getInternalEmail());
                if (findPersonnel != null && findPersonnel.getPersonnelID() != Long.parseLong(personnelID)) {
                    throw new BadRequestException("Personnel already exits");
                }
            }

            // Backup
            backupService.createBackupPersonnel(personnelRequest, Long.parseLong(personnelID));

            Personnel updatePersonnel = personnelService.updatePersonnel(exitsPersonnel, personnelRequest);
            return new ResponseEntity<>(updatePersonnel, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid personnel ID format", HttpStatus.BAD_REQUEST);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete/{personnelID}")
    public ResponseEntity<Object> deletePersonnel(@PathVariable String personnelID) {
        try {
            Personnel personnel = personnelRepository.findPersonnelByPersonnelID(Long.parseLong(personnelID));
            if (personnel == null) {
                throw new InvalidException("Personnel not found");
            }
            personnelService.deletePersonnel(Long.parseLong(personnelID));
            return new ResponseEntity<>("Delete personnel successful", HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid personnel ID format", HttpStatus.BAD_REQUEST);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
