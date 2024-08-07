package com.training.hrm.repositories;

import com.training.hrm.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(String name);
    Role findRoleByRoleID(Long id);

    @Query("SELECT n.name FROM Role n")
    List<String> findAllRoleNames();
}
