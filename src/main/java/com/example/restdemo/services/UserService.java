package com.example.restdemo.services;

import com.example.restdemo.domain.User;
import com.example.restdemo.repository.UserRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(int id){
        Optional<User> result = userRepository.findById(id);
        return result.orElse(null);
    }

    public User findByName(String name){
        Optional<User> result =  userRepository.findByName(name);
        return result.orElse(null);
    }

    public User findByEmailType(String emailType){
        Optional<User> result = userRepository.findByEmailType("%" + emailType + "%");
        return result.orElse(null);
    }
}
