package com.websecurity.app01;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE id = :userId", nativeQuery = true)
    Optional<User> findUser(@Param("userId") Long userId);

    @Query(value = "SELECT * FROM users WHERE SAFE_USER_ID = :safeUserId", nativeQuery = true)
    Optional<User> findBySafeUserId(String safeUserId);
}