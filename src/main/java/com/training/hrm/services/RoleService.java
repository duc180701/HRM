package com.training.hrm.services;

import com.training.hrm.dto.RoleRequest;
import com.training.hrm.exceptions.InvalidException;
import com.training.hrm.exceptions.ServiceRuntimeException;
import com.training.hrm.models.Role;
import com.training.hrm.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(RoleRequest roleRequest) throws ServiceRuntimeException, InvalidException {
        try {
            if (roleRepository.findRoleByName(roleRequest.getName()) != null) {
                throw new InvalidException("Role already exits");
            }

            Role role = new Role();
            role.setName(roleRequest.getName());
            role.setDescription(roleRequest.getDescription());

            return roleRepository.save(role);
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while creating the role: " + e.getMessage());
        }
    }

    public Role readRole(Long roleID) throws ServiceRuntimeException, InvalidException {
        try {
            Role role = roleRepository.findRoleByRoleID(roleID);
            if (role == null) {
                throw new InvalidException("Role not found");
            }
            return role;
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while reading the role: " + e.getMessage());
        }
    }

    public Role updateRole(Long roleID, RoleRequest roleRequest) throws ServiceRuntimeException, InvalidException {
        try {
            Role exitsRole = roleRepository.findRoleByRoleID(roleID);
            if (exitsRole == null) {
                throw new InvalidException("Role not found");
            }
            Role checkRole = roleRepository.findRoleByName(roleRequest.getName());
            if (checkRole != null && roleID != checkRole.getRoleID()) {
                throw new InvalidException("Role already exits");
            }
            exitsRole.setName(roleRequest.getName());
            exitsRole.setDescription(roleRequest.getDescription());

            return roleRepository.save(exitsRole);
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while updating the role: " + e.getMessage());
        }
    }

    public void deleteRole(Long roleID) throws ServiceRuntimeException, InvalidException {
        try {
            if (roleRepository.findRoleByRoleID(roleID) == null) {
                throw new InvalidException("Role not found");
            }
            roleRepository.deleteById(roleID);
        } catch (InvalidException e) {
            throw e;
        } catch (ServiceRuntimeException e) {
            throw new ServiceRuntimeException("An error occurred while reading the role: " + e.getMessage());
        }
    }
}
