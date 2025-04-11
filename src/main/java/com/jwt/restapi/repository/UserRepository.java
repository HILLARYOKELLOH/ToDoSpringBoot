package com.jwt.restapi.repository;

import com.jwt.restapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Check if a user exists by username
    boolean existsByUsername(String username);

    // Find a user by their username
    User findByUsername(String username);

    // Check if a user exists by email (new)
    boolean existsByEmail(String email);

    // Find a user by their email (new)
    Optional<User> findByEmail(String email);

    // Optionally, you can add methods for finding users by role
    // For example, find all users by role
    List<User> findByRole(User.Role role);  // Uncommented and fixed method

}
