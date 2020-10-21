package com.example.restdemo.repository;

import com.example.restdemo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByName(String name);

    @Query("SELECT u FROM User AS u WHERE u.email LIKE :emailType")
    Optional<User> findByEmailType(String emailType);
}
