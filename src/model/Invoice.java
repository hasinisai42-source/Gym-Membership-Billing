package model;

import java.time.LocalDate;

public class Invoice {
    private final int id;
    private final Member member;
    private final Plan plan;
    private final LocalDate issueDate;
    private final double subtotal;
    private final double penalty;
    private final double tax;
    private final double total;
    private boolean paid;

    public Invoice(int id, Member member, Plan plan, LocalDate issueDate, double penalty, double taxRate) {
        if (member == null || plan == null || issueDate == null) throw new IllegalArgumentException("Invoice details are required.");
        if (penalty < 0 || taxRate < 0) throw new IllegalArgumentException("Penalty and tax rate cannot be negative.");
        this.id = id;
        this.member = member;
        this.plan = plan;
        this.issueDate = issueDate;
        this.subtotal = plan.getFee();
        this.penalty = penalty;
        this.tax = (subtotal + penalty) * taxRate;
        this.total = subtotal + penalty + tax;
        this.paid = false;
    }

    public int getId() { return id; }
    public Member getMember() { return member; }
    public Plan getPlan() { return plan; }
    public LocalDate getIssueDate() { return issueDate; }
    public double getSubtotal() { return subtotal; }
    public double getPenalty() { return penalty; }
    public double getTax() { return tax; }
    public double getTotal() { return total; }
    public boolean isPaid() { return paid; }
    public void markPaid() { paid = true; }

    @Override
    public String toString() {
        return "\n----- INVOICE #" + id + " -----\nMember: " + member.getName() + " (ID " + member.getId()
                + ")\nPlan: " + plan.getName() + "\nDate: " + issueDate
                + "\nPlan fee: ₹" + String.format("%.2f", subtotal)
                + "\nLate penalty: ₹" + String.format("%.2f", penalty)
                + "\nTax (5%): ₹" + String.format("%.2f", tax)
                + "\nTOTAL: ₹" + String.format("%.2f", total)
                + "\nPayment status: " + (paid ? "PAID" : "UNPAID") + "\n----------------------";
    }
}
