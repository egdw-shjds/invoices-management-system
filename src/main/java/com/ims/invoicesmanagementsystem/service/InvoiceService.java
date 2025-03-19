package com.ims.invoicesmanagementsystem.service;

import com.ims.invoicesmanagementsystem.dto.InvoiceDto;
import com.ims.invoicesmanagementsystem.model.Invoice;
import com.ims.invoicesmanagementsystem.model.InvoiceBuilder;
import com.ims.invoicesmanagementsystem.repository.InvoiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ims.invoicesmanagementsystem.model.InvoiceBuilder.anInvoice;
import static com.ims.invoicesmanagementsystem.model.InvoiceStatus.*;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public Invoice createInvoice(Invoice invoice) {
        InvoiceBuilder invoiceBuilder = anInvoice()
                .withAmount(invoice.getAmount())
                .withDueDate(invoice.getDueDate())
                .withStatus(invoice.getStatus());

        return invoiceRepository.save(invoiceBuilder.build());
    }

    public List<InvoiceDto> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoices.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private InvoiceDto convertToDto(Invoice invoice) {
        long overdueDays = 0;
        if (invoice.getDueDate().isBefore(LocalDate.now()) && (invoice.getStatus() == PENDING) || (invoice.getStatus() == PARTIALLY_PAID) || (invoice.getStatus() == VOID)) {
            overdueDays = LocalDate.now().toEpochDay() - invoice.getDueDate().toEpochDay();
        }
        return new InvoiceDto(
                invoice.getId(),
                invoice.getAmount(),
                invoice.getPaidAmount(),
                invoice.getDueDate(),
                invoice.getStatus(),
                overdueDays
        );
    }

    @Transactional
    public String payInvoice(Long id, InvoiceDto invoiceDto) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);

        if (invoice.isEmpty()) {
            throw new RuntimeException("Invoice not found!");
        }

        Invoice invoiceToPay = invoice.get();

        if (invoiceToPay.getStatus() == PAID) {
            return "Invoice already paid!";
        }

        if (invoiceToPay.getStatus() == VOID) {
            return "Invoice is void!";
        }

        double newPaidAmount = invoiceToPay.getPaidAmount() + invoiceDto.getAmount();

        if (newPaidAmount > invoiceToPay.getAmount()) {
            return "Payment exceeds the invoice amount!";
        }

        invoiceToPay.setPaidAmount(newPaidAmount);

        if (newPaidAmount == invoiceToPay.getAmount()) {
            invoiceToPay.setStatus(PAID);
            return "Invoice paid successfully!";
        }

        return "PENDING Amount is " + (invoiceToPay.getAmount() - invoiceToPay.getPaidAmount());
    }

    public void processOverdueInvoices(double lateFee, int overdueDays) {
        LocalDate startDate = LocalDate.now().minusDays(overdueDays);
        LocalDate endDate = LocalDate.now().minusDays(1);
        List<Invoice> overdueInvoices = invoiceRepository.findByDueDateBetweenAndStatus(startDate, endDate, PENDING);

        if (overdueInvoices.isEmpty()) {
            throw new RuntimeException("Overdue invoices not found!");
        }

        for (Invoice invoice : overdueInvoices) {
            if (invoice.getPaidAmount() > 0.0) {
                invoice.setStatus(PARTIALLY_PAID);
            } else {
                invoice.setStatus(VOID);
            }
            invoiceRepository.save(invoice);

            createNewInvoiceForOverDues(invoice, lateFee);
        }
    }

    private void createNewInvoiceForOverDues(Invoice oldInvoice, double lateFee) {
        InvoiceBuilder newInvoiceBuilder = anInvoice()
                .withAmount(oldInvoice.getPaidAmount() > 0.0
                        ? oldInvoice.getAmount() - oldInvoice.getPaidAmount() + lateFee
                        : oldInvoice.getAmount() + lateFee)
                .withDueDate(LocalDate.now().plusDays(30))
                .withStatus(PENDING);

        invoiceRepository.save(newInvoiceBuilder.build());
    }
}
