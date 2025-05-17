package com.example.SalesProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SalesProject.dto.AddClients;
import com.example.SalesProject.dto.AddSnooze;
import com.example.SalesProject.entity.Clients;
import com.example.SalesProject.entity.Snoozes;
import com.example.SalesProject.service.SnoozesService;

@RestController
@RequestMapping("api/snoozes")
public class SnoozesController {
    @Autowired
    private SnoozesService snoozesService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('admin','salesman')")
    public ResponseEntity<String> signup(@RequestBody AddSnooze request) {
        return ResponseEntity.ok(snoozesService.CreateSnooze(request));
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('admin')")
    public List<Snoozes> getAllClients() {
        return snoozesService.getAllSnoozes();
    }

    @GetMapping("/getSalesSnooze")
    @PreAuthorize("hasAnyRole('salesman')")
    public List<Snoozes> getSalessnooze() {
        return snoozesService.getSalesSnoozes();

    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyRole('admin')")
    public String deleteSnooze(@PathVariable Long id) {
        snoozesService.deleteSnooze(id);
        return "snooze with ID " + id + " has been deleted successfully.";
    }

}
