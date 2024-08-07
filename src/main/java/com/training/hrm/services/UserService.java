package com.training.hrm.services;

import com.training.hrm.dto.ForgotPasswordRequest;
import com.training.hrm.dto.UserRequest;
import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.*;
import com.training.hrm.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonnelRepository personnelRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

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

    // Lưu thông tin người dùng
    public User createUser(UserRequest userRequest) throws ServiceRuntimeException {
        try {
            User user = new User();
            user.setUsername(userRequest.getUsername());
            user.setPassword(userRequest.getPassword());
            user.setEmployeeID(userRequest.getEmployeeID());
            user.setAvatar("");

            List<String> listRole = roleRepository.findAllRoleNames();
            if (listRole.contains(userRequest.getRole())) {
                user.setRole(userRequest.getRole());
            } else {
                throw new InvalidException("Please enter the role listed: " + listRole);
            }

            return userRepository.save(user);
        } catch (InvalidException e) {
            throw e;
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

//    public User updateUser(User exitsUser, User user) throws ServiceRuntimeException {
//        try {
//            exitsUser.setEmployeeID(user.getEmployeeID());
//            exitsUser.setRole(user.getRole());
//            exitsUser.setUsername(user.getUsername());
//            exitsUser.setPassword(user.getPassword());
//
//            return userRepository.save(exitsUser);
//        } catch (ServiceRuntimeException e) {
//            throw new ServiceRuntimeException("An error occurred while updating the user: " + e.getMessage());
//        }
//    }

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

    // Mã hóa mật khẩu trước khi lưu vào DB
    public void registerUser(UserRequest userRequest) throws ServiceRuntimeException {
        try {
            userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
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

    // Xử lý việc quên mật khẩu qua những tham số cần thiết
    public ForgotPassword forgotPassword(ForgotPasswordRequest forgotPasswordRequest) throws InvalidException, ServiceRuntimeException {
        try {
            //Kiểm tra dữ liệu đầu vào có khớp với nhau hay không
            String employeeID = forgotPasswordRequest.getEmployeeID();
            String username = forgotPasswordRequest.getUsername();
            String internalPhoneNumber = forgotPasswordRequest.getInternalPhoneNumber();
            String citizenIdentityID = forgotPasswordRequest.getCitizenIdentityID();

            Employee employee = employeeRepository.findEmployeeByEmployeeID(Long.parseLong(employeeID));
            if (employee == null) {
                throw new InvalidException("Employee not found");
            }
            User user = userRepository.findUserByUsername(username);
            if (user == null) {
                throw new InvalidException("User not found");
            }
            Personnel personnel = personnelRepository.findPersonnelByInternalPhoneNumber(internalPhoneNumber);
            if (personnel == null) {
                throw new InvalidException("Personnel not found");
            }
            Person person = personRepository.findPersonByCitizenIdentity_CitizenIdentityID(citizenIdentityID);
            if (person == null) {
                throw new InvalidException("Person not found");
            }

            User rootUser = userRepository.findUserByEmployeeID(employee.getEmployeeID());
            Personnel rootPersonnel = personnelRepository.findPersonnelByPersonnelID(employee.getPersonnelID());
            Person rootPerson = personRepository.findPersonByPersonID(employee.getPersonID());
            String rootCitizenIdentity = rootPerson.getCitizenIdentity().getCitizenIdentityID();

            if ((!username.equals(rootUser.getUsername())) || (!internalPhoneNumber.equals(rootPersonnel.getInternalPhoneNumber())) || (!citizenIdentityID.equals(rootCitizenIdentity))) {
                throw new InvalidException("Information does not match");
            }

            // Lưu dữ liệu cần thiết lại đợi duyệt
            ForgotPassword forgotPassword = new ForgotPassword();
            forgotPassword.setEmployeeID(Long.parseLong(employeeID));
            forgotPassword.setUsername(username);
            forgotPassword.setStatus(false);

            return forgotPasswordRepository.save(forgotPassword);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Please enter a valid ID");
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while sending forgot password request: " + e.getMessage());
        }
    }
}
