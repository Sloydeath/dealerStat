package com.leverx.service.impl;

import com.leverx.error.exception.UserAlreadyExistException;
import com.leverx.model.User;
import com.leverx.model.custom.IRating;
import com.leverx.model.enums.Role;
import com.leverx.repository.UserRepository;
import com.leverx.service.UserRoleService;
import com.leverx.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger log = Logger.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
    }

    @Override
    public boolean update(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            userRepository.save(user);
            return true;
        }
        else {
            log.info("--- No such user in database ---");
            return false;
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByUserEmail(email);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(new User());
    }

    @Override
    public boolean deleteUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            User user;
            if ((user = userRepository.findById(id).get()).getRoles().stream().noneMatch(r -> r.getName().equals(Role.ADMIN))) {
                user.setRoles(null);
                userRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }

    @Override
    public User registerNewUserAccount(User user) throws UserAlreadyExistException {
        if (isExists(user.getEmail())) {
            throw new UserAlreadyExistException();
        }
        user.setActive(false);
        user.setCreatedAt(LocalDateTime.now());
        user.addRole(userRoleService.findRoleByName(Role.TRADER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        log.info("--- New user was added ---");
        return user;
    }

    @Override
    public boolean isExists(String email) {
        return userRepository.findByUserEmail(email) != null;
    }

    @Override
    public List<IRating> getTradersRating() {
        return userRepository.getTradersRating();
    }

    @Override
    public List<User> getAllNotActive() {
        return userRepository.findAllNotActive();
    }
}
