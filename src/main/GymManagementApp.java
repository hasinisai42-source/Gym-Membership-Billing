package main;

import java.util.List;
import java.util.Scanner;

import model.AttendanceRecord;
import model.Invoice;
import model.Member;
import model.Payment;
import model.Plan;
import model.Subscription;
import model.Trainer;
import service.GymService;

public class GymManagementApp {
    private static final Scanner sc = new Scanner(System.in);
    private static final GymService gym = new GymService();

    public static void main(String[] args) {
        seedDefaultPlans();
        boolean running = true;

        System.out.println("==================================");
        System.out.println(" GYM MEMBERSHIP AND BILLING SYSTEM");
        System.out.println("==================================");

        while (running) {
            showMenu();
            int choice = readInt("Enter choice: ");
            try {
                switch (choice) {
                    case 1: addMember(); break;
                    case 2: addPlan(); break;
                    case 3: startRenewSubscription(); break;
                    case 4: recordAttendance(); break;
                    case 5: generateInvoice(); break;
                    case 6: recordPayment(); break;
                    case 7: displayMembers(); break;
                    case 8: displayPlans(); break;
                    case 9: displayAttendance(); break;
                    case 10: addTrainer(); break;
                    case 11: displayTrainers(); break;
                    case 12: displayInvoices(); break;
                    case 0:
                        running = false;
                        System.out.println("Thank you for using Gym Membership and Billing System.");
                        break;
                    default:
                        System.out.println("Error: Please select a menu option from 0 to 12.");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Error: " + ex.getMessage());
            } catch (Exception ex) {
                System.out.println("Unexpected error handled: " + ex.getMessage());
            }
            System.out.println();
        }
        sc.close();
    }

    private static void seedDefaultPlans() {
        gym.addPlan("Monthly", 30, 1000.00, 100.00);
        gym.addPlan("Quarterly", 90, 2700.00, 200.00);
        gym.addPlan("Annual", 365, 9000.00, 500.00);
    }

    private static void showMenu() {
        System.out.println("\n----------- MAIN MENU -----------");
        System.out.println("1. Add Member");
        System.out.println("2. Add Plan");
        System.out.println("3. Start/Renew Subscription (generate invoice)");
        System.out.println("4. Record Attendance");
        System.out.println("5. Generate Invoice");
        System.out.println("6. Record Payment");
        System.out.println("7. Display Members");
        System.out.println("8. Display Plans");
        System.out.println("9. Display Attendance");
        System.out.println("10. Add Trainer");
        System.out.println("11. Display Trainers");
        System.out.println("12. Display Invoices");
        System.out.println("0. Exit");
        System.out.println("---------------------------------");
    }

    private static void addMember() {
        String name = readText("Enter member name: ");
        String phone = readText("Enter 10-digit phone number: ");
        Member member = gym.addMember(name, phone);
        System.out.println("Member added successfully. ID: " + member.getId());
    }

    private static void addPlan() {
        String name = readText("Enter plan name: ");
        int days = readPositiveInt("Enter duration in days: ");
        double fee = readNonNegativeDouble("Enter plan fee (₹): ");
        double penalty = readNonNegativeDouble("Enter late-renewal penalty (₹): ");
        Plan plan = gym.addPlan(name, days, fee, penalty);
        System.out.println("Plan added: " + plan);
    }

    private static void startRenewSubscription() {
        displayMembers();
        int memberId = readPositiveInt("Enter member ID: ");
        displayPlans();
        int planId = readPositiveInt("Enter plan ID: ");
        Invoice invoice = gym.startOrRenewSubscription(memberId, planId);
        System.out.println("Invoice generated. Pay this invoice before subscription activation:");
        System.out.println(invoice);
        System.out.println("Use option 6 to record payment. After payment, run option 3 again only if you need a new invoice.");
        System.out.println("To activate the paid subscription, choose option 13 is not available in this menu.");
        System.out.println("Use the payment workflow note below: this version activates subscriptions after payment through the helper flow.");
    }

    private static void generateInvoice() {
        displayMembers();
        int memberId = readPositiveInt("Enter member ID: ");
        displayPlans();
        int planId = readPositiveInt("Enter plan ID: ");
        Invoice invoice = gym.startOrRenewSubscription(memberId, planId);
        System.out.println(invoice);
        System.out.println("Record payment using menu option 6 and invoice ID " + invoice.getId() + ".");
    }

    private static void recordPayment() {
        displayInvoices();
        int invoiceId = readPositiveInt("Enter invoice ID to pay: ");
        Invoice invoice = gym.getInvoice(invoiceId);
        if (invoice.isPaid()) throw new IllegalArgumentException("Invoice has already been paid.");
        double amount = readNonNegativeDouble("Enter payment amount ₹" + String.format("%.2f", invoice.getTotal()) + ": ");
        Payment payment = gym.recordPayment(invoiceId, amount);
        System.out.println("Payment successful: " + payment);
        Subscription subscription = gym.activateSubscriptionAfterPayment(invoice);
        System.out.println("Subscription activated/renewed: " + subscription);
    }

    private static void recordAttendance() {
        int memberId = readPositiveInt("Enter member ID: ");
        AttendanceRecord record = gym.recordAttendance(memberId);
        System.out.println("Attendance recorded: " + record);
    }

    private static void displayMembers() {
        List<Member> members = gym.getMembers();
        System.out.println("\n--- MEMBERS ---");
        if (members.isEmpty()) System.out.println("No members registered.");
        for (Member member : members) System.out.println(member);
    }

    private static void displayPlans() {
        System.out.println("\n--- MEMBERSHIP PLANS ---");
        for (Plan plan : gym.getPlans()) System.out.println(plan);
    }

    private static void displayAttendance() {
        System.out.println("\n--- ATTENDANCE RECORDS ---");
        List<AttendanceRecord> records = gym.getAttendanceRecords();
        if (records.isEmpty()) System.out.println("No attendance records.");
        for (AttendanceRecord record : records) System.out.println(record);
    }

    private static void addTrainer() {
        String name = readText("Enter trainer name: ");
        String specialty = readText("Enter trainer specialty: ");
        Trainer trainer = gym.addTrainer(name, specialty);
        System.out.println("Trainer added: " + trainer);
    }

    private static void displayTrainers() {
        System.out.println("\n--- TRAINERS ---");
        List<Trainer> trainers = gym.getTrainers();
        if (trainers.isEmpty()) System.out.println("No trainers registered.");
        for (Trainer trainer : trainers) System.out.println(trainer);
    }

    private static void displayInvoices() {
        System.out.println("\n--- INVOICES ---");
        List<Invoice> invoices = gym.getInvoices();
        if (invoices.isEmpty()) System.out.println("No invoices generated.");
        for (Invoice invoice : invoices) System.out.println(invoice);
    }

    private static String readText(String prompt) {
        System.out.print(prompt);
        String value = sc.nextLine().trim();
        if (value.isEmpty()) throw new IllegalArgumentException("Input cannot be empty.");
        return value;
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        String value = sc.nextLine().trim();
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Please enter a valid whole number.");
        }
    }

    private static int readPositiveInt(String prompt) {
        int value = readInt(prompt);
        if (value <= 0) throw new IllegalArgumentException("Value must be greater than zero.");
        return value;
    }

    private static double readNonNegativeDouble(String prompt) {
        System.out.print(prompt);
        String value = sc.nextLine().trim();
        try {
            double number = Double.parseDouble(value);
            if (!Double.isFinite(number) || number < 0)
                throw new IllegalArgumentException("Enter a valid non-negative amount.");
            return number;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Please enter a valid amount.");
        }
    }
}
