package com.example.restdemo.services;

import com.example.restdemo.domain.Post;
import com.example.restdemo.domain.User;
import com.example.restdemo.repository.PostRepository;
import com.example.restdemo.repository.UserRepository;
import com.example.restdemo.utils.Ansi;
import com.example.restdemo.utils.RepositoryMethods;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PostService {
    private UserService userService;
    private PostRepository postRepository;
//    private UserRepository userRepository;
    private RepositoryMethods<Post> repositoryMethods;

    @Autowired
    public PostService(PostRepository postRepository, UserService userService) {
        this.userService = userService;
        this.postRepository = postRepository;
        this.repositoryMethods = new RepositoryMethods<>(postRepository);
    }

    public Post findById(int id){
        return repositoryMethods.findById(id);
    }

    public Page<Post> findAll(Pageable pageable){
        return repositoryMethods.findAll(
                pageable.getPageNumber(), pageable.getPageSize());
    }

    @Transactional
    public Post delete(int id){
        return repositoryMethods.delete(id);
    }

    @Transactional
    public Post save(@NonNull String userName,@NonNull String message){
        User user = userService.findByName(userName);
        log.debug("{} Found user {}", Ansi.ANSI_BLUE, user);
        if (user == null) return null; // No user with this name

        Post post = new Post();
        post.setUser(user);
        post.setMessage(message);
        postRepository.save(post);
        return post;
    }

    @Transactional
    public Post update(int id, @NonNull Post post){
//        Optional<Post> possiblePost = postRepository.findById(id);
//        if (possiblePost.isPresent()){
//            Post post = possiblePost.get();
//            if (message != null) post.setMessage(message);
//            postRepository.update(id, message);
//            return post;
//        }
//        return null;
        Post postToUpdate = postRepository.getOne(id);

        if (post.getUser() != null) postToUpdate.setUser(post.getUser());
        if (post.getMessage() != null) postToUpdate.setMessage(post.getMessage());
        postRepository.save(postToUpdate);
        return postToUpdate;
    }
}
