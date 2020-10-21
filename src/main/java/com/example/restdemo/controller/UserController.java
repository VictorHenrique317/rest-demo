package com.example.restdemo.controller;

import com.example.restdemo.domain.User;
import com.example.restdemo.repository.UserRepository;
import com.example.restdemo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("id/{id}")
    public User getById(@PathVariable int id){
        return userService.findById(id);
    }

    @GetMapping("name/{name}")
    public User getByName(@PathVariable String name){
        return userService.findByName(name);
    }

    @GetMapping("emailType/{emailType}")
    public User getByEmailType(@PathVariable String emailType){
        return userService.findByEmailType(emailType);
    }
}
