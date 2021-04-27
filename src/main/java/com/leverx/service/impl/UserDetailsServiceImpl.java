package com.leverx.service.impl;

import com.leverx.error.exception.UserIsNotActiveException;
import com.leverx.model.User;
import com.leverx.model.UserRole;
import com.leverx.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //use user's email like username
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByUserEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with email %s was not found", email));
        }
        else if (!user.isActive()) {
            throw new UserIsNotActiveException("User's email is not activated");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                mapRoleAuthority(user.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> mapRoleAuthority(Set<UserRole> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName().getRole())).collect(Collectors.toList());
    }
}
