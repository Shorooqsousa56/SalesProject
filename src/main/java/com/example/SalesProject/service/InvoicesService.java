package com.example.SalesProject.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SalesProject.dto.AddInvoice;
import com.example.SalesProject.entity.Invoices;
import com.example.SalesProject.repository.InvoicesRepository;

@Service
public class InvoicesService {

    @Autowired
    private InvoicesRepository invoicesRepository;

    public Optional<Invoices> getinvoicesById(long id) {
        return invoicesRepository.findById(id);
    }

    public List<Invoices> getAllInvoices() {
        return invoicesRepository.findAll();
    }

    public String CreateInvoice(AddInvoice newInvoice) {
        Invoices invoice = new Invoices();

        invoice.setAmount(newInvoice.getAmount());
        invoice.setStatus(newInvoice.getStatus());
        invoice.setNote(newInvoice.getNote());
        invoice.setDueDate(newInvoice.getDueDate());
        invoice.setInvoiceNumber(newInvoice.getInvoiceNumber());
        invoice.setUserId(newInvoice.getUserId());
        invoicesRepository.save(invoice);
        return "invoice Added Successful";

    }

    public void deleteInvoice(Long id) {
        Optional<Invoices> optionalInvoice = invoicesRepository.findById(id);
        if (optionalInvoice.isPresent()) {
            invoicesRepository.deleteById(id); // Delete the user by ID
        } else {
            throw new RuntimeException("Invoice not found with ID: " + id);
        }
    }

    public List<Invoices> getInvoicesByStatus(String status) {

        return invoicesRepository.findByStatus(status);

    }

    public List<Invoices> getInvoicesByDateRange(Date startDate, Date endDate) {
        return invoicesRepository.findByDueDateBetween(startDate, endDate);
    }

    public String updateInvoice(Long id, AddInvoice newInvoice) {
        Optional<Invoices> optionalInvoice = invoicesRepository.findById(id);
        if (optionalInvoice.isPresent()) {
            Invoices invoice = optionalInvoice.get();

            if (newInvoice.getUserId() != null) {
                invoice.setUserId(newInvoice.getUserId());
            }
            if (newInvoice.getInvoiceNumber() != null) {
                invoice.setInvoiceNumber(newInvoice.getInvoiceNumber());
            }
            if (newInvoice.getAmount() != null) {
                invoice.setAmount(newInvoice.getAmount());
            }
            if (newInvoice.getStatus() != null) {
                invoice.setStatus(newInvoice.getStatus());
            }
            if (newInvoice.getDueDate() != null) {
                invoice.setDueDate(newInvoice.getDueDate());
            }
            if (newInvoice.getNote() != null) {
                invoice.setNote(newInvoice.getNote());
            }

            invoicesRepository.save(invoice);
            return "Invoice updated successfully";
        } else {
            return "Invoice not found with ID: " + id;
        }
    }

}
