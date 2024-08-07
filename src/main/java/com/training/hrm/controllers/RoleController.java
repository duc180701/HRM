package com.training.hrm.controllers;

import com.training.hrm.dto.RoleRequest;
import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.Role;
import com.training.hrm.repositories.RoleRepository;
import com.training.hrm.services.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.apache.poi.xwpf.usermodel.IBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @Operation(summary = "Create a new role")
    @PostMapping("/create")
    public ResponseEntity<Object> createRole(@Valid @RequestBody RoleRequest roleRequest, BindingResult result) {
        try {
            if(result.hasErrors()) {
                throw new InvalidException(result.getAllErrors().get(0).getDefaultMessage());
            }

            Role role = roleService.createRole(roleRequest);

            return new ResponseEntity<>(role, HttpStatus.OK);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Read a role by ID")
    @GetMapping("/read/{roleID}")
    public ResponseEntity<Object> readRole(@PathVariable String roleID) {
        try {
            Long id = Long.parseLong(roleID);
            Role role = roleService.readRole(id);

            return new ResponseEntity<>(role, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Please enter a valid role ID", HttpStatus.BAD_REQUEST);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update a role by ID")
    @PostMapping("/update/{roleID}")
    public ResponseEntity<Object> updateRole(@PathVariable String roleID, @Valid @RequestBody RoleRequest roleRequest, BindingResult result) {
        try {
            if(result.hasErrors()) {
                throw new InvalidException(result.getAllErrors().get(0).getDefaultMessage());
            }
            Long id = Long.parseLong(roleID);
            Role role = roleService.updateRole(id, roleRequest);

            return new ResponseEntity<>(role, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Please enter a valid role ID", HttpStatus.BAD_REQUEST);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Delete a role by ID")
    @PostMapping("delete/{roleID}")
    public ResponseEntity<Object> deleteRole(@PathVariable String roleID) {
        try {
            Long id = Long.parseLong(roleID);
            roleService.deleteRole(id);

            return new ResponseEntity<>("Delete role successful", HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Please enter a valid role ID", HttpStatus.BAD_REQUEST);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
