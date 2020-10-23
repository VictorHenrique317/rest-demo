package com.example.restdemo.controller;

import com.example.restdemo.common.EntityConflictException;
import com.example.restdemo.domain.User;
import com.example.restdemo.services.UserService;
import com.example.restdemo.utils.Validation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Object insertUser(@RequestParam String name,
                             @RequestParam String email) {
        User user = userService.save(name, email);
        if (user == null) throw new EntityConflictException();
        return user;
    }

    @GetMapping
    public Object getAllUsers(@RequestParam(required = false, defaultValue = "0")int page,
                              @RequestParam(required = false, defaultValue = "3")int size){
        return Validation.defineResponse(userService.findAll(page, size));
    }

    @GetMapping("{id}")
    public Object getById(@PathVariable int id) {
        return Validation.defineResponse(userService.findById(id));
    }

    @GetMapping("{id}/posts")
    public Object getPostsForUser(int id,
                                  @RequestParam(required = false, defaultValue = "0") int page,
                                  @RequestParam(required = false, defaultValue = "3") int size){
        return Validation.defineResponse(userService.getPostsForUser(id, page, size));
    }

    @GetMapping("name/{name}")
    public Object getByName(@PathVariable String name) {
        return Validation.defineResponse(userService.findByName(name));
    }

    @GetMapping("emailType/{emailType}")
    public Object getByEmailType(@PathVariable String emailType,
                                 @RequestParam(required = false, defaultValue = "0") int page,
                                 @RequestParam(required = false, defaultValue = "3") int size) {
        return Validation.defineResponse(userService.findByEmailType(emailType, page, size));
    }

    @PutMapping("{id}")
    public Object updateUser(@PathVariable int id,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) String email) {
        log.info("Updating user {} with name {} and email {}", id, name, email);
        return Validation.defineResponse(userService.updateUser(id, name, email));
    }

    @DeleteMapping("{id}")
    public Object deleteUser(@PathVariable int id) {
        return Validation.defineResponse(userService.delete(id));
    }


}
