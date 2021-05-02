package com.leverx.repository;

import com.leverx.model.UserRole;
import com.leverx.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Andrew Panas
 */

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Query("SELECT ur FROM UserRole ur WHERE ur.name = :role")
    UserRole findRoleByName(@Param("role") Role role);
}
