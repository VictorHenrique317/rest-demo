package com.example.restdemo.controller;

import com.example.restdemo.common.EntityConflictException;
import com.example.restdemo.domain.User;
import com.example.restdemo.services.UserService;
import com.example.restdemo.utils.Validation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Object insertUser(@RequestBody User user) {
        if (userService.save(user) == null) throw new EntityConflictException();
        return user;
    }

    @GetMapping
    public Object getAllUsers(Pageable pageable){
        return Validation.defineResponse(userService.findAll(pageable));
    }

    @GetMapping("{id}")
    public Object getById(@PathVariable int id) {
        return Validation.defineResponse(userService.findById(id));
    }

    @GetMapping("{id}/posts")
    public Object getPostsForUser(@PathVariable int id, Pageable pageable){
        return Validation.defineResponse(userService.getPostsForUser(id, pageable));
    }

    @GetMapping("name/{name}")
    public Object getByName(@PathVariable String name) {
        return Validation.defineResponse(userService.findByName(name));
    }

    @GetMapping("emailType/{emailType}")
    public Object getByEmailType(@PathVariable String emailType, Pageable pageable) {
        return Validation.defineResponse(userService.findByEmailType(emailType, pageable));
    }

    @PutMapping("{id}")
    public Object updateUser(@PathVariable int id,
            @RequestBody User user) {
        return Validation.defineResponse(userService.updateUser(id, user));
    }

    @DeleteMapping("{id}")
    public Object deleteUser(@PathVariable int id) {
        return Validation.defineResponse(userService.delete(id));
    }


}
