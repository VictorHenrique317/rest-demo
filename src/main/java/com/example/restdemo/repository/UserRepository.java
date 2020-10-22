package com.example.restdemo.repository;

import com.example.restdemo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByName(String name);
    Optional<User> findByNameAndEmail(String name, String email);

    @Query("SELECT u FROM User AS u WHERE u.email LIKE :emailType")
    Page<Collection<User>> getByEmailType(String emailType, Pageable pageable);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE User AS u SET u.name= :name, u.email= :email WHERE u.id=:id")
    void updateUser(int id, String name, String email);

    @Query("SELECT u.name FROM User AS u WHERE u.id= :id")
    String getNameOfUser(int id);

    @Query("SELECT u.email FROM User AS u WHERE u.id= :id")
    String getEmailOfUser(int id);
}
