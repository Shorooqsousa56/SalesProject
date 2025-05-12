package com.example.SalesProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SalesProject.dto.AddClients;
import com.example.SalesProject.entity.Clients;
import com.example.SalesProject.service.ClientsService;

@RestController
@RequestMapping("api/clients")
public class ClientsController {

    @Autowired
    private ClientsService clientService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('admin')")
    public ResponseEntity<String> signup(@RequestBody AddClients request) {
        return ResponseEntity.ok(clientService.CreateClient(request));
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyRole('admin')")
    public String deleteInvoice(@PathVariable Long id) {
        clientService.deleteClient(id);
        return "Client with ID " + id + " has been deleted successfully.";
    }

    @PutMapping("/adminupdate/{id}")
    @PreAuthorize("hasAnyRole('admin')")
    public ResponseEntity<String> updateClient(
            @PathVariable Long id,
            @RequestBody AddClients newClient) {
        String result = clientService.updateClient(id, newClient);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAllClients")
    @PreAuthorize("hasAnyRole('admin')")
    public List<Clients> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/getSalesClients")
    @PreAuthorize("hasAnyRole('salesman')")
    public List<Clients> getSalesClients() {
        return clientService.getSalesClients();
    }

    @PutMapping("/salesupdate/{id}")
    @PreAuthorize("hasAnyRole('salesman')")
    public ResponseEntity<String> updateSalesClient(
            @PathVariable Long id,
            @RequestBody AddClients newClient) {
        String result = clientService.updateSalesClient(id, newClient);
        return ResponseEntity.ok(result);
    }

}
