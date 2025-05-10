
package com.example.SalesProject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false) // Enforces unique values
    private String email;

    @Column(nullable = false) // Ensures 'password' cannot be null
    private String password;

    @Column(nullable = false) // Ensures 'name' cannot be null
    private String name;

    @Column(nullable = false) // Ensures 'name' cannot be null
    private String role;

    @Column() // Ensures 'name' cannot be null
    private String phone;

    @Column() // Ensures 'name' cannot be null
    private String address;
}