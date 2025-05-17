package com.example.SalesProject.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.SalesProject.dto.AddClients;
import com.example.SalesProject.dto.AddInvoice;
import com.example.SalesProject.entity.Clients;
import com.example.SalesProject.entity.Invoices;
import com.example.SalesProject.entity.Users;
import com.example.SalesProject.repository.ClientsRepository;
import com.example.SalesProject.repository.InvoicesRepository;

@Service
public class InvoicesService {

    @Autowired
    private InvoicesRepository invoicesRepository;

    @Autowired
    private ClientsRepository clientsRepository;

    public Optional<Invoices> getinvoicesById(long id) {
        return invoicesRepository.findById(id);
    }

    public List<Invoices> getAllInvoices() {
        return invoicesRepository.findAll();
    }

    public String CreateInvoice(AddInvoice newInvoice) {
        Invoices invoice = new Invoices();

        invoice.setAmount(newInvoice.getAmount());
        invoice.setClientId(newInvoice.getClientId());
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
            if (newInvoice.getClientId() != null) {
                invoice.setClientId(newInvoice.getClientId());
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

    /* here is for accountant */
    public List<Invoices> getAccInvoice() {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long AccId = user.getId();

        return invoicesRepository.findByUserId(AccId);
    }

    public String CreateAccInvoice(AddInvoice newInvoice) {
        Invoices invoice = new Invoices();
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long AccId = user.getId();

        invoice.setAmount(newInvoice.getAmount());
        invoice.setClientId(newInvoice.getClientId());
        invoice.setStatus(newInvoice.getStatus());
        invoice.setNote(newInvoice.getNote());
        invoice.setDueDate(newInvoice.getDueDate());
        invoice.setInvoiceNumber(newInvoice.getInvoiceNumber());
        invoice.setUserId(AccId);
        invoicesRepository.save(invoice);
        return "invoice Added Successful";

    }

    public String updateAccInvoice(Long invoiceId, AddInvoice newInvoice) {
        Users currentUser = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentUserId = currentUser.getId();

        Optional<Invoices> optionalInvoice = invoicesRepository.findById(invoiceId);
        if (optionalInvoice.isEmpty()) {
            return "Cinvoice not found with ID: " + invoiceId;
        }

        Invoices invoices = optionalInvoice.get();

        if (!Long.valueOf(invoices.getUserId()).equals(currentUserId)) {
            return "You are not authorized to update this client.";
        }

        if (newInvoice.getInvoiceNumber() != null)
            invoices.setInvoiceNumber(newInvoice.getInvoiceNumber());

        if (newInvoice.getAmount() != null)
            invoices.setAmount(newInvoice.getAmount());

        if (newInvoice.getStatus() != null)
            invoices.setStatus(newInvoice.getStatus());

        if (newInvoice.getClientId() != null)
            invoices.setClientId(newInvoice.getClientId());

        if (newInvoice.getNote() != null)
            invoices.setNote(newInvoice.getNote());

        if (newInvoice.getDueDate() != null)
            invoices.setDueDate(newInvoice.getDueDate());

        invoicesRepository.save(invoices);
        return "Client updated successfully";
    }

    public void deleteAccInvoice(Long id) {
        Optional<Invoices> optionalInvoice = invoicesRepository.findById(id);
        Invoices invoices = optionalInvoice.get();

        Users currentUser = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentUserId = currentUser.getId();

        if (optionalInvoice.isPresent() && Long.valueOf(invoices.getUserId()).equals(currentUserId)) {
            invoicesRepository.deleteById(id); // Delete the user by ID
        } else {
            throw new RuntimeException("Invoice not found with ID: " + id);
        }
    }

    public List<Invoices> getAccInvoicesByStatus(String status) {
        Users currentUser = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentUserId = currentUser.getId();
        List<Invoices> optionalInvoice = invoicesRepository.findByStatus(status);

        List<Invoices> filteredInvoices = optionalInvoice.stream()
                .filter(invoice -> invoice.getUserId().equals(currentUserId))
                .collect(Collectors.toList());

        return filteredInvoices;
    }

    public List<Clients> getLateClientsManual() {
        List<Invoices> unpaidInvoices = invoicesRepository.findByStatus("unpaid");

        List<Long> lateClientIds = unpaidInvoices.stream()
                .filter(invoice -> invoice.getDueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                        .plusDays(14).isBefore(LocalDate.now()))
                .map(Invoices::getClientId)
                .distinct()
                .collect(Collectors.toList());

        if (lateClientIds.isEmpty()) {
            return new ArrayList<>();
        }

        System.out.println("Late Client IDs: " + lateClientIds);

        return clientsRepository.findAllById(lateClientIds);
    }

    public List<Invoices> getAllLateInvoices() {
        Date today = new Date();
        List<Invoices> all = invoicesRepository.findAll();
        return all.stream()
                .filter(invoice -> invoice.getDueDate() != null &&
                        invoice.getDueDate().before(today))
                .collect(Collectors.toList());

    }

    public List<Invoices> getAllLateSalesInvoices() {
        Date today = new Date();
        Users currentUser = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long SalesId = currentUser.getId();
        List<Invoices> all = invoicesRepository.findAll();
        return all.stream()
                .filter(invoice -> invoice.getSalesManId() != null &&
                        invoice.getSalesManId().equals(SalesId) &&
                        invoice.getDueDate().before(today))
                .collect(Collectors.toList());

    }

}
