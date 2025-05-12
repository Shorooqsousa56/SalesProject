package com.example.SalesProject.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.SalesProject.dto.AddInter;
import com.example.SalesProject.entity.Interactions;
import com.example.SalesProject.entity.Users;
import com.example.SalesProject.repository.InteractionsRepository;

@Service
public class InterService {

    @Autowired
    private InteractionsRepository interRepository;

    public String CreateInter(AddInter newInter) {

        Users currentUser = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentUserId = currentUser.getId();

        Interactions inter = new Interactions();

        inter.setClientId(newInter.getClientId());
        inter.setCreatedTime(LocalDateTime.now());
        inter.setNotes(newInter.getNotes());
        inter.setSalesManId(currentUserId);
        inter.setType(newInter.getType());

        interRepository.save(inter);
        return "interaction Added Successful";

    }

    public String updateSalesInter(Long InterId, AddInter newInter) {

        Users currentUser = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentUserId = currentUser.getId();

        Optional<Interactions> optionalInter = interRepository.findById(InterId);
        if (optionalInter.isEmpty()) {
            return "interaction not found with ID: " + InterId;
        }

        Interactions inter = optionalInter.get();

        if (!Long.valueOf(inter.getSalesManId()).equals(currentUserId)) {
            return "You are not authorized to update this client.";
        }

        if (newInter.getType() != null)
            inter.setType(newInter.getType());

        if (newInter.getNotes() != null)
            inter.setNotes(newInter.getNotes());

        inter.setCreatedTime(LocalDateTime.now());

        if (newInter.getClientId() != null)
            inter.setClientId(newInter.getClientId());

        interRepository.save(inter);
        return "interaction updated successfully";
    }

    public List<Interactions> getSalesInter() {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long salesManId = user.getId();

        return interRepository.findBySalesManId(salesManId);
    }

    public String deleteInter(Long id) {
        Optional<Interactions> optionalInter = interRepository.findById(id);
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long salesManId = user.getId();

        if (optionalInter.isEmpty()) {
            return "interaction not found with ID: " + id;
        }

        Interactions inter = optionalInter.get();

        if (!Long.valueOf(inter.getSalesManId()).equals(salesManId)) {
            return "You are not authorized to update this interaction.";
        }

        interRepository.deleteById(id);
        return "interaction deleted successful";

    }

    public List<Interactions> getAllInter() {
        return interRepository.findAll();
    }

}
