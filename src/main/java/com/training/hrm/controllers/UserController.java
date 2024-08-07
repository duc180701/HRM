package com.training.hrm.controllers;

import com.training.hrm.components.JwtUtil;
import com.training.hrm.customservices.CustomUserDetailsService;
import com.training.hrm.dto.ForgotPasswordRequest;
import com.training.hrm.dto.LoginRequest;
import com.training.hrm.dto.UserRequest;
import com.training.hrm.exceptions.BadRequestException;
import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.ForgotPassword;
import com.training.hrm.models.User;
import com.training.hrm.repositories.EmployeeRepository;
import com.training.hrm.repositories.UserRepository;
import com.training.hrm.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @Operation(summary = "Create a user account")
    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserRequest userRequest, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new BadRequestException(result.getAllErrors().get(0).getDefaultMessage());
            }
            if (employeeRepository.findEmployeeByEmployeeID(userRequest.getEmployeeID()) == null) {
                throw new InvalidException("Employee not found");
            }
            if (userRepository.findUserByEmployeeID(userRequest.getEmployeeID()) != null) {
                throw new BadRequestException("This employee already has an account");
            }
            if (userRepository.findUserByUsername(userRequest.getUsername()) != null) {
                throw new BadRequestException("User already exits");
            }

            // Mã hóa mật khẩu tài khoản
            userService.registerUser(userRequest);

            User createUser = userService.createUser(userRequest);

            return new ResponseEntity<>(createUser, HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Read all user information")
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

//    @Operation(summary = "Update user by ID")
//    @PostMapping("/update/{userID}")
//    public ResponseEntity<Object> updateUser(@PathVariable String userID, @Valid @RequestBody User user, BindingResult result) {
//        try {
//            if (result.hasErrors()) {
//                throw new BadRequestException(result.getAllErrors().get(0).getDefaultMessage());
//            }
//            User exitsUser = userRepository.findUserByUserID(Long.parseLong(userID));
//            if (exitsUser == null) {
//                throw new InvalidException("User not found");
//            }
//            if (employeeRepository.findEmployeeByEmployeeID(user.getEmployeeID()) == null) {
//                throw new InvalidException("Employee not found");
//            }
//            if (userRepository.findUserByEmployeeID(user.getEmployeeID()) != null) {
//                throw new BadRequestException("This employee already has an account");
//            }
//            if (userRepository.findUserByUsername(user.getUsername()) != null) {
//                throw new BadRequestException("User already exits");
//            }
//            User updateUser = userService.updateUser(exitsUser, user);
//            return new ResponseEntity<>(updateUser, HttpStatus.OK);
//        } catch (NumberFormatException e) {
//            return new ResponseEntity<>("Invalid user ID format", HttpStatus.BAD_REQUEST);
//        } catch (InvalidException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        } catch (ServiceRuntimeException e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }

    @Operation(summary = "Delete a user by ID")
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

    @PostMapping("/avatar/{userID}")
    public ResponseEntity<Object> uploadAvatar(@PathVariable String userID, @PathVariable String avatarUrl, @RequestParam("file") MultipartFile file) {
        try {
            userService.setAvatar(Long.parseLong(userID), file);

            return new ResponseEntity<>("Avatar uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("Invalid avatar URL", HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Login with the created account and password")
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

    @Operation(summary = "Retrieve password if forget")
    @PostMapping("/forgot-password")
    public ResponseEntity<Object> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new InvalidException(result.getAllErrors().get(0).getDefaultMessage());
            }

            ForgotPassword forgotPassword = userService.forgotPassword(forgotPasswordRequest);

            return new ResponseEntity<>(forgotPassword, HttpStatus.OK);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
