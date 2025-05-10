package com.example.SalesProject.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String email;
    private String password;
    private String name;
    private String Role;
    private String phone;
    private String address;
}
