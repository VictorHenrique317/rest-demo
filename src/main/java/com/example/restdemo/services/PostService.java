package com.example.restdemo.services;

import com.example.restdemo.domain.Post;
import com.example.restdemo.domain.User;
import com.example.restdemo.repository.PostRepository;
import com.example.restdemo.repository.UserRepository;
import com.example.restdemo.utils.RepositoryMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private PostRepository postRepository;
//    private UserRepository userRepository;
    private RepositoryMethods<Post> repositoryMethods;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
//        this.userRepository = userRepository;
        this.repositoryMethods = new RepositoryMethods<>(postRepository);
    }

//    public Page<Post> findByUser(int id, int page, int size) {
//        if (!userRepository.existsById(id)) return null; // User doesnt exist
//        return postRepository.findByUser(id, PageRequest.of(page, size));
//    }

    public Post findById(int id){
        return repositoryMethods.findById(id);
    }

    public Page<Post> findAll(int page, int size){
        return repositoryMethods.findAll(page, size);
    }

    public Post delete(int id){
        return repositoryMethods.delete(id);
    }

//    public Post update(int id, int userId, String message){
//        if (!postRepository.existsById(id)) return null;
//        if (userId == 0) userId =
//    }
}
