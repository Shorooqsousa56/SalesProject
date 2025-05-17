package com.example.SalesProject.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "snoozes")
public class Snoozes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "invoice_id")
    private Long invoiceId;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @CreationTimestamp
    @Column(name = "snooze_until", updatable = false)
    private LocalDateTime snoozeUntil;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
