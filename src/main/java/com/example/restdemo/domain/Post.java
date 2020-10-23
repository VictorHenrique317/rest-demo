package com.example.restdemo.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Entity;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Post implements DomainModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne   @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    private String message;
}
