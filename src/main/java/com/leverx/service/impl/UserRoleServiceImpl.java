package com.leverx.service.impl;

import com.leverx.model.UserRole;
import com.leverx.repository.UserRoleRepository;
import com.leverx.service.UserRoleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private static final Logger log = Logger.getLogger(UserRoleServiceImpl.class);

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public void save(UserRole userRole) {
        userRoleRepository.save(userRole);
    }

    @Override
    public boolean update(UserRole userRole) {
        if (userRoleRepository.findById(userRole.getId()).isPresent()) {
            userRoleRepository.save(userRole);
            return true;
        }
        else {
            log.info("--- No such role in database ---");
            return false;
        }
    }

    @Override
    public boolean deleteById(Long id) {
        if (userRoleRepository.findById(id).isPresent()) {
            userRoleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<UserRole> findAll() {
        return userRoleRepository.findAll();
    }

    @Override
    public UserRole findById(Long id) {
        return userRoleRepository.findById(id).orElse(new UserRole());
    }
}
