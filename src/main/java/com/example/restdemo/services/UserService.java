package com.example.restdemo.services;

import com.example.restdemo.domain.Post;
import com.example.restdemo.domain.User;
import com.example.restdemo.repository.UserRepository;
import com.example.restdemo.utils.RepositoryMethods;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.awt.print.PageFormat;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {
    private UserRepository userRepository;
    private RepositoryMethods<User> repositoryMethods;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.repositoryMethods = new RepositoryMethods<>(userRepository);
    }

    public User findById(int id){
        return repositoryMethods.findById(id);
    }

    public User findByName(String name){
        Optional<User> result =  userRepository.findByName(name);
        return result.orElse(null);
    }

    public Page<User> findByEmailType(String emailType, int page, int pageSize){
        Page<User> result = userRepository.getByEmailType("%" + emailType + "%",
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
    public User save(@NonNull String name, @NonNull String email){
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
      return repositoryMethods.delete(id);
    }

    public Page<User> findAll(int page, int size) {
        return repositoryMethods.findAll(page, size);
    }

    public Page<Post> getPostsForUser(int id, int page, int size) {
        Optional<User> possibleUser = userRepository.findById(id);
        List<Post> posts = null;
        if (possibleUser.isPresent()){
            posts = possibleUser.get().getPosts();
            return new PageImpl<>(posts, PageRequest.of(page, size), posts.size());
        }
        return null;
    }
}
