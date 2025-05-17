package com.example.SalesProject.dto;

import java.util.Date;

import lombok.Data;

@Data
public class AddInvoice {

    private Long userId;
    private Long clientId;
    private String invoiceNumber;
    private Integer amount;
    private String status;
    private Date dueDate;
    private String note;
    private Long salesManId;

}
