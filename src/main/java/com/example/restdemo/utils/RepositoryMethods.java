package com.example.restdemo.utils;

import com.example.restdemo.domain.DomainModel;
import com.example.restdemo.domain.User;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.Optional;

//@NoArgsConstructor
@Slf4j
public final class RepositoryMethods<T extends DomainModel> {
    private JpaRepository<T, Integer> repository;

    public RepositoryMethods(JpaRepository<T, Integer> repository) {
        this.repository = repository;
    }

    public T findById(int id){
        Optional<T> result = repository.findById(id);
        log.info("================================= {}", result.get());
        return result.orElse(null);
    }

    public T delete(int id){
        Optional<T> possibleEntry = repository.findById(id);
        if (possibleEntry.isPresent()) {
            repository.deleteById(id);
        }
        return possibleEntry.orElse(null);
    }

    public Page<T> findAll(int page, int size) {
        return repository.findAll(PageRequest.of(page, size));
    }

}
