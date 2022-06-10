package com.techvify.question.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
public class Account {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "firstName", length = 50)
    private String firstName;

    @Column(name = "lastName", length = 50)
    private String lastName;

    @Column(name = "email", length = 50,unique = true)
    private String email;

    @Column(name = "username", length = 50, nullable = false, unique = true, updatable = false)
    private String username;

    @Column(name = "passwords", length = 800, nullable = false)
    private String password;

    @Column(name="role",length = 20)
    private String role;

    @Column(name="enabled")
    private boolean enabled = false;

    public Account() {
    }

    public Account(int id, String firstName, String lastName, String email, String username, String password, String role, boolean enabled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }
}
