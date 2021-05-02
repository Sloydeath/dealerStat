package com.leverx.service;

import java.util.List;

import com.leverx.model.User;
import com.leverx.model.custom.IRating;
import com.leverx.model.custom.Rating;

/**
 * Interface of user service
 *
 * @author Andrew Panas
 */

public interface UserService {
    boolean update(User user);
    List<User> findAll();
    User findUserByEmail(String email);
    User findUserById(Long id);
    boolean deleteUserById(Long id);
    User registerNewUserAccount(User user);
    boolean isExists(String email);
    List<IRating> getTradersRating();
    List<User> getAllNotActive();
}
