package com.example.SalesProject.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AddSnooze {
    private Long invoiceId;
    private LocalDateTime snoozeUntil;

}
