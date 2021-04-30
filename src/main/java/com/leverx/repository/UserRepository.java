package com.leverx.repository;

import com.leverx.model.User;
import com.leverx.model.custom.IRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.active = false ")
    List<User> findAllNotActive();

    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByUserEmail(@Param("email") String email);

    @Query("SELECT u.id AS id, u.firstName AS firstName, u.lastName AS lastName, u.email AS email, COUNT(c.id) " +
            "AS points FROM User u JOIN Comment c ON c.user.id = u.id GROUP BY u.id ORDER BY points DESC")
    List<IRating> getTradersRating();
}
