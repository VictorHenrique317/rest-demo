package com.example.restdemo.repository;

import com.example.restdemo.domain.Post;
import com.example.restdemo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    @Query("SELECT p FROM Post AS p WHERE p.user=:id")
    Page<Post> findByUser(int id, Pageable pageable);

//    String getUserIdBy

}
