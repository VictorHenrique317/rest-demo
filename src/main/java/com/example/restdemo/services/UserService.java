package com.example.restdemo.services;

import com.example.restdemo.common.EntityNotFoundException;
import com.example.restdemo.domain.DomainModel;
import com.example.restdemo.domain.Post;
import com.example.restdemo.domain.User;
import com.example.restdemo.repository.UserRepository;
import com.example.restdemo.utils.Ansi;
import com.example.restdemo.utils.RepositoryMethods;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public User findById(int id) {
        User result = repositoryMethods.findById(id);
        if (result == null) throw new EntityNotFoundException();
        return result;
    }

    public User findByName(String name) {
        Optional<User> result = userRepository.findByName(name);
        return result.orElse(null);
    }

    public Page<User> findByEmailType(String emailType, Pageable pageable) {
        Page<User> result = userRepository.getByEmailType("%" + emailType + "%", pageable);
        if (result.getTotalElements() == 0) return null; // No results
        return result;
    }

    @Transactional
    public User updateUser(int id, User user) {
        User userToUpdate = userRepository.getOne(id);
        if (user.getName() != null) userToUpdate.setName(user.getName());
        if (user.getEmail() != null) userToUpdate.setEmail(user.getEmail());
        userRepository.save(userToUpdate);
        return userToUpdate;
    }

    @Transactional
    public User save(@NonNull User user) {
        Optional<User> possibleDuplicate = userRepository.findByNameAndEmail(user.getName(), user.getEmail());
        if (possibleDuplicate.isPresent()) {
            log.info("USER ALREADY EXISTS");
            return null;
        }
        userRepository.save(user);
        return user;
    }

    @Transactional
    public User delete(int id) {
        return repositoryMethods.delete(id);
    }

    public Page<User> findAll(Pageable pageable) {
        return repositoryMethods.findAll(pageable);
    }

    public Page<Post> getPostsForUser(int id, Pageable pageable) {
        Optional<User> possibleUser = userRepository.findById(id);

        List<Post> posts = null;
        if (possibleUser.isPresent()) {
            posts = possibleUser.get().getPosts();
            log.debug("{} Getting posts for user {}", Ansi.ANSI_BLUE, possibleUser.get().getName());
            log.debug("{} Found {} posts", Ansi.ANSI_BLUE, posts.size());
            return listToPage(posts, pageable);
        }
        return null;
    }

    private <T extends DomainModel> Page<T> listToPage(List<T> list, Pageable pageable) {
        long start = pageable.getOffset();
        long end = (pageable.getPageSize() > list.size()) ? list.size() : start + pageable.getPageSize();
        return new PageImpl<T>(list.subList((int) start, (int) end), pageable, list.size());
    }
}
