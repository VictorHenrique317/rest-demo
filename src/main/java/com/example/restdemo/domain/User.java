package com.example.restdemo.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = {"name", "email"})
@Entity
public class User implements DomainModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String email;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<Post> posts;
}
