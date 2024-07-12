package com.training.hrm.services;

import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.User;
import com.training.hrm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user) throws ServiceRuntimeException {
        try {
            return userRepository.save(user);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while creating the user: " + e.getMessage());
        }
    }

    public User readUser(Long id) throws ServiceRuntimeException, InvalidException {
        try {
            User user = userRepository.findUserByUserID(id);
            return user;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while reading the user: " + e.getMessage());
        }
    }

    public User updateUser(User exitsUser, User user) throws ServiceRuntimeException {
        try {
            exitsUser.setEmployeeID(user.getEmployeeID());
            exitsUser.setRole(user.getRole());
            exitsUser.setUsername(user.getUsername());
            exitsUser.setPassword(user.getPassword());

            return userRepository.save(exitsUser);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while updating the user: " + e.getMessage());
        }
    }

    public void deleteUser(Long id) throws ServiceRuntimeException, InvalidException {
        try {
            if (userRepository.findUserByUserID(id) == null) {
                throw new InvalidException("User not found");
            }
            userRepository.deleteById(id);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while deleting the user: " + e.getMessage());
        }
    }

    public User registerUser(User user) throws ServiceRuntimeException {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while registering a user: " + e.getMessage());
        }
    }
}
