package com.leverx.service;

import java.util.List;

import com.leverx.model.User;

public interface UserService {
    boolean update(User user);
    List<User> findAll();
    User findUserByEmail(String email);
    User findUserById(Long id);
    boolean deleteUserById(Long id);
    User registerNewUserAccount(User user);
    boolean isExists(String email);
}
