package com.example.SalesProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.SalesProject.entity.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    List<Currency> findAll();

    Optional<Currency> findById(Long id);

    Optional<Currency> deleteById(long id);

}