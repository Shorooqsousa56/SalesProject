package com.example.SalesProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.SalesProject.entity.Interactions;

@Repository
public interface InteractionsRepository extends JpaRepository<Interactions, Long> {

    List<Interactions> findAll();

    Optional<Interactions> findById(long id);

    Optional<Interactions> deleteById(long id);

    List<Interactions> findBySalesManId(Long id);

}
