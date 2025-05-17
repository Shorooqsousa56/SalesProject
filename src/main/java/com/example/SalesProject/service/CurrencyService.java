package com.example.SalesProject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SalesProject.dto.AddCurrency;
import com.example.SalesProject.dto.AddInvoice;
import com.example.SalesProject.entity.Invoices;
import com.example.SalesProject.repository.CurrencyRepository;
import com.example.SalesProject.entity.Currency;

@Service
public class CurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    public List<Currency> getAllcurrency() {
        return currencyRepository.findAll();
    }

    public Optional<Currency> getCurrencyById(long id) {
        return currencyRepository.findById(id);
    }

    public String CreateCurrency(AddCurrency newCurrency) {
        Currency currency = new Currency();

        currency.setCode(newCurrency.getCode());
        currency.setExchangeRate(newCurrency.getExchangeRate());
        currency.setName(newCurrency.getName());
        currency.setSymbol(newCurrency.getSymbol());

        currencyRepository.save(currency);
        return "currency Added Successful";

    }

    public String updateCurrency(Long id, AddCurrency newCurrency) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (optionalCurrency.isPresent()) {
            Currency currency = optionalCurrency.get();

            if (newCurrency.getName() != null) {
                currency.setName(newCurrency.getName());
            }

            if (newCurrency.getCode() != null) {
                currency.setCode(newCurrency.getCode());
            }
            if (newCurrency.getExchangeRate() != null) {
                currency.setExchangeRate(newCurrency.getExchangeRate());
            }
            if (newCurrency.getSymbol() != null) {
                currency.setSymbol(newCurrency.getSymbol());
            }
            currencyRepository.save(currency);
            return "Invoice updated successfully";
        } else {
            return "Invoice not found with ID: " + id;
        }
    }

    public void deleteCurrency(Long id) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(id);
        if (optionalCurrency.isPresent()) {
            currencyRepository.deleteById(id); // Delete the user by ID
        } else {
            throw new RuntimeException(" Currency not found with ID: " + id);
        }
    }

}
