package com.example.restdemo.controller;

import com.example.restdemo.common.UserConflictException;
import com.example.restdemo.common.UserNotFoundException;
import com.example.restdemo.domain.User;
import com.example.restdemo.repository.UserRepository;
import com.example.restdemo.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping(value = "users")
public class UserController {
    private UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private Object defineResponse(User user) {
        if (user == null) throw new UserNotFoundException();
        return user;
    }

    private Object defineResponse(Page<Collection<User>> usersPage) {
        if (usersPage == null) throw new UserNotFoundException();
        return usersPage;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Object insertUser(@RequestParam String name,
                             @RequestParam String email) {
        User user = userService.save(name, email);
        if (user == null) throw new UserConflictException();
        return user;
    }

    @GetMapping("{id}")
    public Object getById(@PathVariable int id) {
        return defineResponse(userService.findById(id));
    }

    @GetMapping("name/{name}")
    public Object getByName(@PathVariable String name) {
        return defineResponse(userService.findByName(name));
    }

    @GetMapping("emailType/{emailType}")
    public Object getByEmailType(@PathVariable String emailType,
                                 @RequestParam(required = false, defaultValue = "0") int page,
                                 @RequestParam(required = false, defaultValue = "3") int size) {
        return defineResponse(userService.findByEmailType(emailType, page, size));
    }

    @PutMapping("{id}")
    public Object updateUser(@PathVariable int id,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) String email) {
        log.info("Updating user {} with name {} and email {}", id, name, email);
        return defineResponse(userService.updateUser(id, name, email));
    }

    @DeleteMapping("{id}")
    public Object deleteUser(@PathVariable int id) {
        return defineResponse(userService.delete(id));
    }


}
