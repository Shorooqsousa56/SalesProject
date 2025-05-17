package com.example.SalesProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SalesProject.dto.UpdateUser;
import com.example.SalesProject.entity.Users;
import com.example.SalesProject.service.UsersService;

@RestController
@RequestMapping("users/")
public class UsersController {

    @Autowired
    private UsersService userService;

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAnyRole('admin','accountant','salesman')")
    public List<Users> getAllUsers() {
        return userService.getUsersByRoles();
    }

    @GetMapping("/getUserById/{id}")
    @PreAuthorize("hasAnyRole('admin','accountant','salesman')")
    public Users getUserById(@PathVariable Long id) {
        return userService.getUsersById(id).orElse(null);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasAnyRole('admin')")
    public Users updateUser(@PathVariable Long id, @RequestBody UpdateUser updatedUser) {
        return userService.updateUser(id, updatedUser);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyRole('admin')")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User with ID " + id + " has been deleted successfully.";
    }

    @PutMapping("/editCurrency/{id}")
    @PreAuthorize("hasAnyRole('admin','accountant','salesman','client')")
    public Users updateCurrency(@PathVariable Long id) {
        return userService.updateCurrency(id);
    }

}
