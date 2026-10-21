package model;

import java.time.LocalDateTime;

public class Payment {
    private final int id;
    private final Invoice invoice;
    private final double amount;
    private final LocalDateTime paymentTime;

    public Payment(int id, Invoice invoice, double amount) {
        if (invoice == null) throw new IllegalArgumentException("Invoice is required.");
        if (amount <= 0) throw new IllegalArgumentException("Payment amount must be greater than zero.");
        if (invoice.isPaid()) throw new IllegalArgumentException("This invoice has already been paid.");
        if (Math.abs(amount - invoice.getTotal()) > 0.01)
            throw new IllegalArgumentException("Payment must equal the invoice total: ₹" + String.format("%.2f", invoice.getTotal()));
        this.id = id;
        this.invoice = invoice;
        this.amount = amount;
        this.paymentTime = LocalDateTime.now();
        invoice.markPaid();
    }

    public int getId() { return id; }
    public Invoice getInvoice() { return invoice; }
    public double getAmount() { return amount; }
    public LocalDateTime getPaymentTime() { return paymentTime; }

    @Override
    public String toString() {
        return "Payment #" + id + " | Invoice #" + invoice.getId() + " | Amount: ₹"
                + String.format("%.2f", amount) + " | " + paymentTime;
    }
}
