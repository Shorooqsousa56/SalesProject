
package com.example.SalesProject.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.example.SalesProject.entity.Clients;

@Repository
public interface ClientsRepository extends JpaRepository<Clients, Long> {

    List<Clients> findAll();

    Optional<Clients> findById(Long id);

    Optional<Clients> deleteById(long id);

    List<Clients> findBySalesManId(Long id);

}
