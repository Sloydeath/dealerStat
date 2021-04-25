package com.leverx.service.intefaces;

import java.util.List;

import com.leverx.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void save(User user);
    boolean update(User user);
    List<User> findAll();
    User findUserById(Long id);
    boolean deleteUserById(Long id);
}
