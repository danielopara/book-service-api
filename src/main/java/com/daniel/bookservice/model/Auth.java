package com.daniel.bookservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "authentications")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "token")
    private String token;
    @Column(name = "creation")
    private Date creation;

    @Column(name = "expiry")
    private Date expiry;
}
