package com.ims.invoicesmanagementsystem.model;

import java.time.LocalDate;

public class InvoiceBuilder {

    private InvoiceBuilder() {}

    private Invoice invoice;

    public static InvoiceBuilder anInvoice() {
        InvoiceBuilder builder = new InvoiceBuilder();
        builder.invoice = new Invoice();
        return builder;
    }

    public InvoiceBuilder withId(Long id) {
        invoice.setId(id);
        return this;
    }

    public InvoiceBuilder withAmount(Double amount) {
        invoice.setAmount(amount);
        return this;
    }

    public InvoiceBuilder withPaidAmount(Double paidAmount) {
        invoice.setPaidAmount(paidAmount);
        return this;
    }

    public InvoiceBuilder withDueDate(LocalDate dueDate) {
        invoice.setDueDate(dueDate);
        return this;
    }

    public InvoiceBuilder withStatus(InvoiceStatus status) {
        invoice.setStatus(status);
        return this;
    }

    public Invoice build() {
        return invoice;
    }

}
