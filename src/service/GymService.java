package service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import model.AttendanceRecord;
import model.Invoice;
import model.Member;
import model.Payment;
import model.Plan;
import model.Subscription;
import model.Trainer;

public class GymService {
    private final Map<Integer, Member> members = new LinkedHashMap<>();
    private final Map<Integer, Plan> plans = new LinkedHashMap<>();
    private final Map<Integer, Trainer> trainers = new LinkedHashMap<>();
    private final List<AttendanceRecord> attendanceRecords = new ArrayList<>();
    private final Map<Integer, Invoice> invoices = new LinkedHashMap<>();
    private final List<Payment> payments = new ArrayList<>();

    private int nextMemberId = 1;
    private int nextPlanId = 1;
    private int nextTrainerId = 1;
    private int nextInvoiceId = 1;
    private int nextPaymentId = 1;

    public Member addMember(String name, String phone) {
        Member member = new Member(nextMemberId++, name, phone);
        members.put(member.getId(), member);
        return member;
    }

    public Plan addPlan(String name, int days, double fee, double latePenalty) {
        Plan plan = new Plan(nextPlanId++, name, days, fee, latePenalty);
        plans.put(plan.getId(), plan);
        return plan;
    }

    public Trainer addTrainer(String name, String specialty) {
        Trainer trainer = new Trainer(nextTrainerId++, name, specialty);
        trainers.put(trainer.getId(), trainer);
        return trainer;
    }

    public Member getMember(int id) {
        Member member = members.get(id);
        if (member == null) throw new IllegalArgumentException("Member not found.");
        return member;
    }

    public Plan getPlan(int id) {
        Plan plan = plans.get(id);
        if (plan == null) throw new IllegalArgumentException("Plan not found.");
        return plan;
    }

    public Invoice getInvoice(int id) {
        Invoice invoice = invoices.get(id);
        if (invoice == null) throw new IllegalArgumentException("Invoice not found.");
        return invoice;
    }

    public List<Member> getMembers() { return new ArrayList<>(members.values()); }
    public List<Plan> getPlans() { return new ArrayList<>(plans.values()); }
    public List<Trainer> getTrainers() { return new ArrayList<>(trainers.values()); }
    public List<AttendanceRecord> getAttendanceRecords() { return new ArrayList<>(attendanceRecords); }
    public List<Invoice> getInvoices() { return new ArrayList<>(invoices.values()); }
    public List<Payment> getPayments() { return new ArrayList<>(payments); }

    public Invoice startOrRenewSubscription(int memberId, int planId) {
        Member member = getMember(memberId);
        Plan plan = getPlan(planId);
        LocalDate today = LocalDate.now();
        Subscription previous = member.getSubscription();
        boolean lateRenewal = previous != null && today.isAfter(previous.getEndDate());
        double penalty = lateRenewal ? plan.getLateRenewalPenalty() : 0.0;

        // Generate an invoice before accepting payment; activation occurs after payment.
        Invoice invoice = new Invoice(nextInvoiceId++, member, plan, today, penalty, 0.05);
        invoices.put(invoice.getId(), invoice);
        return invoice;
    }

    public Subscription activateSubscriptionAfterPayment(Invoice invoice) {
        if (invoice == null) throw new IllegalArgumentException("Invoice is required.");
        if (!invoice.isPaid()) throw new IllegalArgumentException("Pay the invoice before activating the subscription.");
        Member member = invoice.getMember();
        LocalDate startDate = LocalDate.now();
        Subscription old = member.getSubscription();
        if (old != null && startDate.isAfter(old.getEndDate()) == false && old.getStatus().equals("ACTIVE")) {
            startDate = old.getEndDate().plusDays(1);
        }
        Subscription subscription = new Subscription(member, invoice.getPlan(), startDate);
        member.setSubscription(subscription);
        return subscription;
    }

    public AttendanceRecord recordAttendance(int memberId) {
        Member member = getMember(memberId);
        Subscription subscription = member.getSubscription();
        LocalDate today = LocalDate.now();
        if (subscription == null || !subscription.isActiveOn(today))
            throw new IllegalArgumentException("Attendance denied: member has no active subscription for today.");
        AttendanceRecord record = new AttendanceRecord(member, LocalDateTime.now());
        attendanceRecords.add(record);
        return record;
    }

    public Payment recordPayment(int invoiceId, double amount) {
        Invoice invoice = getInvoice(invoiceId);
        Payment payment = new Payment(nextPaymentId++, invoice, amount);
        payments.add(payment);
        return payment;
    }

    public List<Invoice> getUnpaidInvoices() {
        List<Invoice> result = new ArrayList<>();
        for (Invoice invoice : invoices.values()) if (!invoice.isPaid()) result.add(invoice);
        return result;
    }
}
