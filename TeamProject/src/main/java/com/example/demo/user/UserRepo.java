package com.example.demo.user;

import com.example.demo.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Extends the Sprint Security JpaRepository
 */
@Repository
public interface UserRepo extends JpaRepository<User, Long>, CustomUserRepo {
    /**
     * Find user by username
     * @param username user's username
     * @return User
     */
    User findByUsername(String username);
}