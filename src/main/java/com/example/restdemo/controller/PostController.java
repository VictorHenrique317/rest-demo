package com.example.restdemo.controller;

import com.example.restdemo.domain.Post;
import com.example.restdemo.services.PostService;
import com.example.restdemo.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("posts")
@RestController
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public Object savePost(String user, String message){
        return Validation.defineResponse(postService.save(user, message));
    }

    @GetMapping Object getAllPosts(Pageable pageable){
        return Validation.defineResponse(postService.findAll(pageable));
    }

    @GetMapping("{id}")
    public Object getPostById(@PathVariable int id){
       return Validation.defineResponse(postService.findById(id));
    }

    @PutMapping("{id}")
    public Object updatePost(@PathVariable int id,
                             @RequestBody Post post){
        return Validation.defineResponse(postService.update(id, post));
    }

    @DeleteMapping("{id}")
    public Object deletePost(@PathVariable int id){
        return Validation.defineResponse(postService.delete(id));
    }
}
