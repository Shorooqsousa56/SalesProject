package com.example.SalesProject.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.SalesProject.dto.AddClients;
import com.example.SalesProject.entity.Clients;
import com.example.SalesProject.entity.Users;
import com.example.SalesProject.repository.ClientsRepository;

@Service
public class ClientsService {

    @Autowired
    private ClientsRepository clientsRepository;

    public String CreateClient(AddClients newClient) {

        Clients client = new Clients();

        client.setAddress(newClient.getAddress());
        client.setCity(newClient.getCity());
        client.setEmail(newClient.getEmail());
        client.setName(newClient.getName());
        client.setPhone(newClient.getPhone());
        client.setSalesManId(newClient.getSalesManId());
        client.setCreatedAt(LocalDateTime.now());

        clientsRepository.save(client);
        return "client Added Successful";

    }

    public void deleteClient(Long id) {
        Optional<Clients> optionalClient = clientsRepository.findById(id);
        if (optionalClient.isPresent()) {
            clientsRepository.deleteById(id); // Delete the user by ID
        } else {
            throw new RuntimeException("client not found with ID: " + id);
        }
    }

    public String updateClient(Long id, AddClients newClient) {
        Optional<Clients> optionalClient = clientsRepository.findById(id);
        if (optionalClient.isPresent()) {
            Clients client = optionalClient.get();

            if (newClient.getAddress() != null) {
                client.setAddress((newClient.getAddress()));
            }

            if (newClient.getCity() != null) {
                client.setCity((newClient.getCity()));
            }
            if (newClient.getCreatedAt() != null) {
                client.setCreatedAt((newClient.getCreatedAt()));
            }
            if (newClient.getEmail() != null) {
                client.setEmail((newClient.getEmail()));
            }

            if (newClient.getName() != null) {
                client.setName((newClient.getName()));
            }

            if (newClient.getPhone() != null) {
                client.setPhone((newClient.getPhone()));
            }
            if (newClient.getSalesManId() != null) {
                client.setSalesManId((newClient.getSalesManId()));
            }

            clientsRepository.save(client);
            return "client updated successfully";
        } else {
            return "client not found with ID: " + id;
        }
    }

    public List<Clients> getAllClients() {
        return clientsRepository.findAll();
    }

    public List<Clients> getSalesClients() {
        Users user = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long salesManId = user.getId();

        return clientsRepository.findBySalesManId(salesManId);
    }

    public String updateSalesClient(Long clientId, AddClients newClient) {
        Users currentUser = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentUserId = currentUser.getId();

        Optional<Clients> optionalClient = clientsRepository.findById(clientId);
        if (optionalClient.isEmpty()) {
            return "Client not found with ID: " + clientId;
        }

        Clients client = optionalClient.get();

        if (!Long.valueOf(client.getSalesManId()).equals(currentUserId)) {
            return "You are not authorized to update this client.";
        }

        if (newClient.getAddress() != null)
            client.setAddress(newClient.getAddress());
        if (newClient.getCity() != null)
            client.setCity(newClient.getCity());
        if (newClient.getCreatedAt() != null)
            client.setCreatedAt(newClient.getCreatedAt());
        if (newClient.getEmail() != null)
            client.setEmail(newClient.getEmail());
        if (newClient.getName() != null)
            client.setName(newClient.getName());
        if (newClient.getPhone() != null)
            client.setPhone(newClient.getPhone());

        clientsRepository.save(client);
        return "Client updated successfully";
    }

}
