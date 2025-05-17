package com.example.SalesProject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.SalesProject.dto.UpdateUser;
import com.example.SalesProject.entity.Users;
import com.example.SalesProject.repository.UserRepository;

@Service
public class UsersService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Users> getUsersByRoles() {
        List<String> roles = List.of("client", "accountant", "salesman");
        return userRepository.findByroleIn(roles);
    }

    public Optional<Users> getUsersById(long id) {
        return userRepository.findById(id);
    }

    public Users updateUser(Long id, UpdateUser updatedUser) {
        // تحقق من وجود المستخدم
        Optional<Users> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();

            if (updatedUser.getEmail() != null) {
                user.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getName() != null) {
                user.setName(updatedUser.getName());
            }
            if (updatedUser.getPhone() != null) {
                user.setPhone(updatedUser.getPhone());
            }
            if (updatedUser.getAddress() != null) {
                user.setAddress(updatedUser.getAddress());
            }
            if (updatedUser.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

            }
            if (updatedUser.getPreferredCurrencyId() != null) {
                user.setPreferredCurrencyId(updatedUser.getPreferredCurrencyId());
            }

            if (updatedUser.getRole() != null) {
                user.setRole(updatedUser.getRole());
            }

            userRepository.save(user);
            return user;
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void deleteUser(Long id) {
        Optional<Users> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found with ID: " + id);
        }
    }

    public Users updateCurrency(Long id) {

        Users currentUser = (Users) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentUserId = currentUser.getId();

        Optional<Users> optionalUser = userRepository.findById(currentUserId);
        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            user.setPreferredCurrencyId(id);

            userRepository.save(user);
            return user;
        } else {
            throw new RuntimeException("User not found");
        }
    }

}
