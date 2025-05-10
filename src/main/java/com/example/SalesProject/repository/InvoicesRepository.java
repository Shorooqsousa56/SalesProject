
package com.example.SalesProject.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import com.example.SalesProject.entity.Invoices;

@Repository
public interface InvoicesRepository extends JpaRepository<Invoices, Long> {

    List<Invoices> findAll();

    Optional<Invoices> findById(long id);

    Optional<Invoices> deleteById(long id);

    List<Invoices> findByStatus(String status);

    List<Invoices> findByDueDateBetween(Date startDate, Date endDate);

}
