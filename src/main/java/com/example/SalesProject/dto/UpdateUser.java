package com.example.SalesProject.dto;

import lombok.Data;

@Data
public class UpdateUser {

    private String email;
    private String name;
    private String phone;
    private String address;
    private String password;
    private String role;
    private Long preferredCurrencyId;

}