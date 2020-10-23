package com.example.restdemo.controller;

import com.example.restdemo.domain.Post;
import com.example.restdemo.services.PostService;
import com.example.restdemo.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @GetMapping("{id}")
    public Object getPostById(@PathVariable int id){
       return Validation.defineResponse(postService.findById(id));
    }

//    @GetMapping("user/{id}")
//    public Object getPostsForUser(@PathVariable int id,
//                                      @RequestParam(required = false, defaultValue = "0") int page,
//                                      @RequestParam(required = false, defaultValue = "3") int size){
//        return Validation.defineResponse(postService.findByUser(id, page, size));
//    }

}
