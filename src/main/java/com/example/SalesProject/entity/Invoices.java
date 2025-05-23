package com.example.SalesProject.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invoices")
public class Invoices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "invoice_number", nullable = false)
    private String invoiceNumber;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private String status;

    @Column(name = "due_date", nullable = false)
    private Date dueDate;

    @Column(name = "sales_man_id", nullable = false)
    private Long salesManId;

    @Column()
    private String note;

}
