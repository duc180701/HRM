package com.training.hrm.controllers;

import com.training.hrm.components.JwtUtil;
import com.training.hrm.customservices.CustomUserDetailsService;
import com.training.hrm.dto.ChangePasswordRequest;
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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

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

    private static final int MAX_FAILED_ATTEMPTS = 3;
    // Lưu số lần đăng nhập sai cho từng người dùng
    private static final Map<String, Integer> failedAttempts = new HashMap<>();
    // Lưu mã được tạo ra cho từng người dùng khi đăng nhập sai tối đa
    private static final Map<String, String> verificationCodes = new HashMap<>();

    private String generateVerificationCode() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    @Operation(summary = "Login with the created account and password")
    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new BadRequestException(result.getAllErrors().get(0).getDefaultMessage());
            }
            User user = userRepository.findUserByUsername(loginRequest.getUsername());
            if (user == null) {
                throw new InvalidException("User not found");
            }

            // Kiểm ra số lần đăng nhập sai và mã xác thực
            if (failedAttempts.getOrDefault(loginRequest.getUsername(), 0) >= MAX_FAILED_ATTEMPTS) {
                if (loginRequest.getVerificationCode() == null || loginRequest.getVerificationCode().isEmpty()) {
                    throw new BadRequestException("Please enter a valid verification code");
                }
                if (!loginRequest.getVerificationCode().equals(verificationCodes.get(loginRequest.getUsername()))) {
                    throw new BadRequestException("Invalid verification code");
                }
            }

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            // Reset failed attempts on successful login
            failedAttempts.put(loginRequest.getUsername(), 0);

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
            String jwt = jwtUtil.generateToken(userDetails);

            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            int attempts = failedAttempts.getOrDefault(loginRequest.getUsername(), 0);
            failedAttempts.put(loginRequest.getUsername(), attempts + 1);

            if (failedAttempts.get(loginRequest.getUsername()) >= MAX_FAILED_ATTEMPTS) {
                String verificationCode = generateVerificationCode();
                verificationCodes.put(loginRequest.getUsername(), verificationCode);
                return new ResponseEntity<>(Map.of("verificationCode", verificationCode), HttpStatus.UNAUTHORIZED);
            }

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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

    @Operation(summary = "Change user password")
    @PostMapping("/change-password")
    public ResponseEntity<Object> changePasswordUser(@RequestParam String userID, @Valid @RequestBody ChangePasswordRequest changePasswordRequest, BindingResult result) {
        try {
            if (result.hasErrors()) {
                throw new InvalidException(result.getAllErrors().get(0).getDefaultMessage());
            }
            Long id = Long.parseLong(userID);
            userService.changePassword(id, changePasswordRequest);

            return new ResponseEntity<>("Change password successful", HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>("Please enter a valid user ID", HttpStatus.BAD_REQUEST);
        } catch (InvalidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ServiceRuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
