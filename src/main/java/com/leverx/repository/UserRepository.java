package com.leverx.repository;

import com.leverx.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.active = true")
    User findByUserEmailAndActive(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByUserEmail(@Param("email") String email);
}
