package com.jobis.jobis.szs;

import com.jobis.jobis.szs.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Integer> {


    Optional<User> findByUserId(String userId);
}