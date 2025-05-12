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

import com.example.SalesProject.dto.AddInter;
import com.example.SalesProject.entity.Interactions;
import com.example.SalesProject.service.InterService;

@RestController
@RequestMapping("api/interactions")
public class InterController {

    @Autowired
    private InterService interService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('salesman')")
    public ResponseEntity<String> signup(@RequestBody AddInter request) {
        return ResponseEntity.ok(interService.CreateInter(request));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('salesman')")
    public ResponseEntity<String> updateInter(
            @PathVariable Long id,
            @RequestBody AddInter newInter) {
        String result = interService.updateSalesInter(id, newInter);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/getSalesInter")
    @PreAuthorize("hasAnyRole('salesman')")
    public List<Interactions> getSalesInter() {
        return interService.getSalesInter();
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyRole('salesman')")
    public String deleteInter(@PathVariable Long id) {
        interService.deleteInter(id);
        return "interaction with ID " + id + " has been deleted successfully.";
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAnyRole('admin')")
    public List<Interactions> getAllClients() {
        return interService.getAllInter();
    }
}
