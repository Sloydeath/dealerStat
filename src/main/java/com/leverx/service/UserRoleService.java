package com.leverx.service;

import com.leverx.model.UserRole;
import com.leverx.model.enums.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRoleService {
    void save(UserRole userRole);
    boolean update(UserRole userRole);
    boolean deleteById(Long id);
    List<UserRole> findAll();
    UserRole findById(Long id);
    UserRole findRoleByName(Role role);
}
