package com.ims.invoicesmanagementsystem.repository;

import com.ims.invoicesmanagementsystem.model.Invoice;
import com.ims.invoicesmanagementsystem.model.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByDueDateBetweenAndStatus(LocalDate startDate, LocalDate endDate, InvoiceStatus status);
}
