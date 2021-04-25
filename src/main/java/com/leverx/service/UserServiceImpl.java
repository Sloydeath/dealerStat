package com.leverx.service;

import com.leverx.model.User;
import com.leverx.model.UserRole;
import com.leverx.model.enums.Role;
import com.leverx.repository.UserRepository;
import com.leverx.service.intefaces.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger log = Logger.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        if (userRepository.findByUserEmail(user.getEmail()) != null) {
            return;
        }
        user.setRoles(Collections.singleton(new UserRole(Role.TRADER)));
        user.setPassword(user.getPassword());
        userRepository.save(user);
        log.info("--- New user was added ---");
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
    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(new User());
    }

    @Override
    public boolean deleteUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //use user's email like username
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByUserEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with email %s was not found", email));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getFirstName(),
                user.getPassword(),
                mapRoleAuthority(user.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> mapRoleAuthority(Set<UserRole> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName().getRole())).collect(Collectors.toList());
    }
}
