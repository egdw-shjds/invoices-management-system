package com.ims.invoicesmanagementsystem.dto;

import com.ims.invoicesmanagementsystem.model.InvoiceStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDto {

    private Long id;

    @NotNull(message = "Amount cannot be null")
    @Min(value = 1, message = "Paid amount must be positive")
    private Double amount;

    private Double paidAmount;

    private LocalDate dueDate;

    private InvoiceStatus status;

    private long overdueDays;
}
