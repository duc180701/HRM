package com.training.hrm.customservices;

import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.User;
import com.training.hrm.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException, ServiceRuntimeException {
        try {
            User user = userRepository.findUserByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while loading user by username: " + e.getMessage());
        }
    }
}
