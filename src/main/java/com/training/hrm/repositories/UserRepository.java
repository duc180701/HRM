package com.training.hrm.repositories;

import com.training.hrm.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUserID(Long id);
    User findUserByEmployeeID(Long id);
    User findUserByUsername(String username);
}
