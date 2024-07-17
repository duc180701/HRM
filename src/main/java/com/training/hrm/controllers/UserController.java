package com.training.hrm.controllers;

import com.training.hrm.components.JwtUtil;
import com.training.hrm.customservices.CustomUserDetailsService;
import com.training.hrm.dto.LoginRequest;
import com.training.hrm.dto.LoginResponse;
import com.training.hrm.exceptions.BadRequestException;
import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.User;
import com.training.hrm.repositories.EmployeeRepository;
import com.training.hrm.repositories.UserRepository;
import com.training.hrm.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new BadRequestException(result.getAllErrors().get(0).getDefaultMessage());
            }
            if (employeeRepository.findEmployeeByEmployeeID(user.getEmployeeID()) == null) {
                throw new InvalidException("Employee not found");
            }
            if (userRepository.findUserByEmployeeID(user.getEmployeeID()) != null) {
                throw new BadRequestException("This employee already has an account");
            }
            if (userRepository.findUserByUsername(user.getUsername()) != null) {
                throw new BadRequestException("User already exits");
            }
            userService.registerUser(user);
            User createUser = userService.createUser(user);

            return new ResponseEntity<>(createUser, HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/read/{userID}")
    public ResponseEntity<Object> readContract(@PathVariable String userID) {
        try {
            if (userRepository.findUserByUserID(Long.parseLong(userID)) == null) {
                throw new InvalidException("User not found");
            }
            User readUser = userService.readUser(Long.parseLong(userID));
            return new ResponseEntity<>(readUser, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid user ID format", HttpStatus.BAD_REQUEST);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update/{userID}")
    public ResponseEntity<Object> updateUser(@PathVariable String userID, @Valid @RequestBody User user, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new BadRequestException(result.getAllErrors().get(0).getDefaultMessage());
            }
            User exitsUser = userRepository.findUserByUserID(Long.parseLong(userID));
            if (exitsUser == null) {
                throw new InvalidException("User not found");
            }
            if (employeeRepository.findEmployeeByEmployeeID(user.getEmployeeID()) == null) {
                throw new InvalidException("Employee not found");
            }
            if (userRepository.findUserByEmployeeID(user.getEmployeeID()) != null) {
                throw new BadRequestException("This employee already has an account");
            }
            if (userRepository.findUserByUsername(user.getUsername()) != null) {
                throw new BadRequestException("User already exits");
            }
            User updateUser = userService.updateUser(exitsUser, user);
            return new ResponseEntity<>(updateUser, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid user ID format", HttpStatus.BAD_REQUEST);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/delete/{userID}")
    public ResponseEntity<Object> deleteUser(@PathVariable String userID) {
        try {
            if (userRepository.findUserByUserID(Long.parseLong(userID)) == null) {
                throw new InvalidException("User not found");
            }
            userService.deleteUser(Long.parseLong(userID));
            return new ResponseEntity<>("Delete user successful", HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Invalid user ID format", HttpStatus.BAD_REQUEST);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        try {
            if(result.hasErrors()) {
                throw new BadRequestException(result.getAllErrors().get(0).getDefaultMessage());
            }
            if (userRepository.findUserByUsername(loginRequest.getUsername()) == null) {
                throw new InvalidException("User not found");
            }
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
            String jwt = jwtUtil.generateToken(userDetails);

            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.BAD_REQUEST);
        }
    }

}
