package com.example.SalesProject.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.SalesProject.dto.AddCurrency;
import com.example.SalesProject.entity.Currency;

import com.example.SalesProject.service.CurrencyService;

@RestController
@RequestMapping("api/currency")
public class CurrencyController {

    @Autowired
    private CurrencyService currencyService;

    @GetMapping("/getAllCurrency")
    @PreAuthorize("hasAnyRole('admin','accountant','salesman','client')")
    public List<Currency> getAll() {
        return currencyService.getAllcurrency();
    }

    @GetMapping("/getAllById/{id}")
    @PreAuthorize("hasAnyRole('admin','accountant','salesman','client')")
    public Currency getById(@PathVariable Long id) {

        Optional<Currency> currency = currencyService.getCurrencyById(id);
        if (currency.isPresent()) {

            return currency.get();
        } else {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "currency not found with id " + id);

        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('admin')")
    public ResponseEntity<String> add(@RequestBody AddCurrency request) {
        return ResponseEntity.ok(currencyService.CreateCurrency(request));
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("hasAnyRole('admin')")
    public String delete(@PathVariable Long id) {
        currencyService.deleteCurrency(id);
        return "currency with ID " + id + " has been deleted successfully.";
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('admin')")
    public ResponseEntity<String> updateClient(
            @PathVariable Long id,
            @RequestBody AddCurrency Newcurrency) {
        String result = currencyService.updateCurrency(id, Newcurrency);
        return ResponseEntity.ok(result);
    }

}
