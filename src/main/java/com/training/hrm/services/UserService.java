package com.training.hrm.services;

import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.User;
import com.training.hrm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void setAvatar(Long userID, MultipartFile file) throws IOException, ServiceRuntimeException {
        try {
            User user = userRepository.findUserByUserID(userID);
            if (user == null) {
                throw new InvalidException("User not found");
            }

            String avatarUrl = saveFile(file);

            user.setAvatar(avatarUrl);
            userRepository.save(user);
        } catch (IOException e) {
            throw new IOException("Failed to set avatar");
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while uploading the avatar: " + e.getMessage());
        }
    }

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

    public void registerUser(User user) throws ServiceRuntimeException {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while registering a user: " + e.getMessage());
        }
    }

    private String saveFile(MultipartFile file) throws IOException {
        String uploadDir = "src/main/resources/static/images/avatars/";
        String filename = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        Path uploadPath = Paths.get(uploadDir);

        if(!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(filename);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        } catch (IOException e) {
            throw new IOException("Could not save image file: " + filename, e);
        }
    }
}
