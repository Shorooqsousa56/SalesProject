package com.example.SalesProject.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.SalesProject.dto.AddInvoice;
import com.example.SalesProject.entity.Clients;
import com.example.SalesProject.entity.Invoices;
import com.example.SalesProject.service.InvoicesService;

@RestController
@RequestMapping("api/invoices")
public class InvoicesController {
    @Autowired
    private InvoicesService invoicesService;

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('admin')")
    public List<Invoices> getAllInvoices() {
        return invoicesService.getAllInvoices();
    }

    @GetMapping("/getInvoiceById/{id}")
    @PreAuthorize("hasAnyRole('admin')")
    public Invoices getInvoiceById(@PathVariable Long id) {

        Optional<Invoices> invoice = invoicesService.getinvoicesById(id);
        if (invoice.isPresent()) {

            return invoice.get();
        } else {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found with id " + id);

        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('admin')")
    public ResponseEntity<String> add(@RequestBody AddInvoice request) {
        return ResponseEntity.ok(invoicesService.CreateInvoice(request));
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyRole('admin')")
    public String deleteInvoice(@PathVariable Long id) {
        invoicesService.deleteInvoice(id);
        return "invoice with ID " + id + " has been deleted successfully.";
    }

    @GetMapping("/filterByStatus/{status}")
    @PreAuthorize("hasAnyRole('admin')")
    public List<Invoices> filterByStatus(@PathVariable String status) {
        return invoicesService.getInvoicesByStatus(status);
    }

    @GetMapping("/filterByDate")
    @PreAuthorize("hasAnyRole('admin')")
    public List<Invoices> filterByDateRange(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end) {

        return invoicesService.getInvoicesByDateRange(start, end);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('admin')")
    public ResponseEntity<String> updateInvoice(
            @PathVariable Long id,
            @RequestBody AddInvoice newInvoice) {
        String result = invoicesService.updateInvoice(id, newInvoice);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getSalesInvoice")
    @PreAuthorize("hasAnyRole('accountant')")
    public List<Invoices> getSalesClients() {
        return invoicesService.getAccInvoice();
    }

    @PostMapping("/addAccInvoice")
    @PreAuthorize("hasAnyRole('accountant')")
    public ResponseEntity<String> accAdd(@RequestBody AddInvoice request) {
        return ResponseEntity.ok(invoicesService.CreateAccInvoice(request));
    }

    @PutMapping("/Accupdate/{id}")
    @PreAuthorize("hasAnyRole('accountant')")
    public ResponseEntity<String> updateAccInvoice(
            @PathVariable Long id,
            @RequestBody AddInvoice newInvoice) {
        String result = invoicesService.updateAccInvoice(id, newInvoice);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("Accdelete/{id}")
    @PreAuthorize("hasAnyRole('accountant')")
    public String deleteAccInvoice(@PathVariable Long id) {
        invoicesService.deleteAccInvoice(id);
        return "invoice with ID " + id + " has been deleted successfully.";
    }

}
