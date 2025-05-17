package com.example.SalesProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.SalesProject.entity.Snoozes;

@Repository
public interface SnoozesRepository extends JpaRepository<Snoozes, Long> {

    List<Snoozes> findAll();

    Optional<Snoozes> findById(long id);

    Optional<Snoozes> deleteById(long id);

    List<Snoozes> findByUserId(Long id);

}
