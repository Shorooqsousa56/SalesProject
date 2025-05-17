package com.example.SalesProject.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.SalesProject.dto.AddSnooze;
import com.example.SalesProject.entity.Snoozes;
import com.example.SalesProject.entity.Users;
import com.example.SalesProject.repository.SnoozesRepository;

@Service
public class SnoozesService {

    @Autowired
    private SnoozesRepository snoozesRepository;

    public String CreateSnooze(AddSnooze newSnooze) {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = user.getId();
        Snoozes snoozes = new Snoozes();

        snoozes.setInvoiceId(newSnooze.getInvoiceId());
        snoozes.setCreatedAt(LocalDateTime.now());
        snoozes.setSnoozeUntil(newSnooze.getSnoozeUntil());
        snoozes.setUserId(userId);

        snoozesRepository.save(snoozes);
        return "snooze Added Successful";

    }

    public List<Snoozes> getAllSnoozes() {
        return snoozesRepository.findAll();
    }

    public List<Snoozes> getSalesSnoozes() {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long salesId = user.getId();

        return snoozesRepository.findByUserId(salesId);
    }

    public void deleteSnooze(Long id) {
        Optional<Snoozes> optionalSnoozes = snoozesRepository.findById(id);
        if (optionalSnoozes.isPresent()) {
            snoozesRepository.deleteById(id); // Delete the user by ID
        } else {
            throw new RuntimeException(" snooze not found with ID: " + id);
        }
    }

}
