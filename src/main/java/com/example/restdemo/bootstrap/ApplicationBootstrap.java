package com.example.restdemo.bootstrap;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.example.restdemo.domain.User;
import com.example.restdemo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ApplicationBootstrap implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    private static final Logger log = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    @Override
    public void run(String... args) throws Exception {
        log.setLevel(Level.DEBUG);
        log.info("Bootstrapping");
        User first = new User();
        first.setName("Mathew");
        first.setEmail("mathew@yyahoo.com");
        userRepository.save(first);
//
//        User second = new User();
//        second.setName("Dawkins");
//        second.setEmail("dawkins@gmail.com");
//        userRepository.save(second);
    }
}
