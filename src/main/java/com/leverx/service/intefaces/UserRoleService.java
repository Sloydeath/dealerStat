package com.leverx.service.intefaces;

import com.leverx.model.UserRole;

import java.util.List;

public interface UserRoleService {
    void save(UserRole userRole);
    boolean update(UserRole userRole);
    boolean deleteById(Long id);
    List<UserRole> findAll();
    UserRole findById(Long id);
}
