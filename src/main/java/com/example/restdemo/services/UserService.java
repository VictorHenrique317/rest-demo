package com.example.restdemo.services;

import com.example.restdemo.domain.User;
import com.example.restdemo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
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

    public Page<Collection<User>> findByEmailType(String emailType, int page, int pageSize){
        Page<Collection<User>> result = userRepository.getByEmailType("%" + emailType + "%",
                PageRequest.of(page, pageSize));
        if (result.getTotalElements() == 0) return null; // No results
        return result;
    }

    @Transactional
    public User updateUser(int id, String name, String email){
        if (!userRepository.existsById(id)) return null; // id doesn't exist
        if (name == null) name = userRepository.getNameOfUser(id); // No changes
        if (email == null) email = userRepository.getEmailOfUser(id); // No changes

        userRepository.updateUser(id, name, email);
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public User save(String name, String email){
        User user = new User();
        user.setName(name);
        user.setEmail(email);

        Optional<User> possibleDuplicate = userRepository.findByNameAndEmail(name, email);
        if (possibleDuplicate.isPresent()){
            log.info("USER ALREADY EXISTS");
            return null;
        }
        userRepository.save(user);
        return userRepository.findById(user.getId()).orElse(null);
    }

    @Transactional
    public User delete(int id){
        Optional<User> possibleEntry = userRepository.findById(id);
        if (possibleEntry.isPresent()) {
            userRepository.deleteById(id);
        }
        return possibleEntry.orElse(null);
    }

}
