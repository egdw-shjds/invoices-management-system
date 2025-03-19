package com.ims.invoicesmanagementsystem.controller;

import com.ims.invoicesmanagementsystem.dto.InvoiceDto;
import com.ims.invoicesmanagementsystem.model.Invoice;
import com.ims.invoicesmanagementsystem.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(
            @Valid @RequestBody Invoice invoice
    ) {
        return new ResponseEntity<>(invoiceService.createInvoice(invoice), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDto>> getAllInvoices() {
        return new ResponseEntity<>(invoiceService.getAllInvoices(), HttpStatus.OK);
    }

    @PostMapping("/{id}/payments")
    public ResponseEntity<String> payInvoice(
            @PathVariable Long id,
            @Valid @RequestBody InvoiceDto invoiceDto
    ) {
        return new ResponseEntity<>(invoiceService.payInvoice(id, invoiceDto), HttpStatus.OK);
    }

    @PostMapping("/processoverdue")
    public ResponseEntity<String> processOverdue(
            @RequestParam double lateFee,
            @RequestParam int overdueDays
    ) {
        invoiceService.processOverdueInvoices(lateFee, overdueDays);
        return new ResponseEntity<>("Overdue invoices processed successfully!", HttpStatus.OK);
    }
}
