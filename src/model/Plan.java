package model;

public class Plan {
    private final int id;
    private final String name;
    private final int durationDays;
    private final double fee;
    private final double lateRenewalPenalty;

    public Plan(int id, String name, int durationDays, double fee, double lateRenewalPenalty) {
        if (id <= 0) throw new IllegalArgumentException("Plan ID must be positive.");
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Plan name cannot be empty.");
        if (durationDays <= 0) throw new IllegalArgumentException("Duration must be positive.");
        if (fee < 0 || lateRenewalPenalty < 0) throw new IllegalArgumentException("Fee and penalty cannot be negative.");
        this.id = id;
        this.name = name.trim();
        this.durationDays = durationDays;
        this.fee = fee;
        this.lateRenewalPenalty = lateRenewalPenalty;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getDurationDays() { return durationDays; }
    public double getFee() { return fee; }
    public double getLateRenewalPenalty() { return lateRenewalPenalty; }

    @Override
    public String toString() {
        return "Plan ID: " + id + " | " + name + " | " + durationDays + " days | Fee: ₹"
                + String.format("%.2f", fee) + " | Late penalty: ₹" + String.format("%.2f", lateRenewalPenalty);
    }
}
