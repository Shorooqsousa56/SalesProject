package com.example.SalesProject.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AddClients {

    private String email;
    private String name;
    private String phone;
    private String city;
    private String address;
    private Long salesManId;
    private LocalDateTime createdAt;

}
