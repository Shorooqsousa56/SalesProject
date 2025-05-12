package com.example.SalesProject.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AddInter {
    private Long clientId;
    private Long salesManId;
    private String type;
    private String notes;
    private LocalDateTime createdTime;

}
