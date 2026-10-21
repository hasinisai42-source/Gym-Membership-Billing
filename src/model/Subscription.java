package model;

import java.time.LocalDate;

public class Subscription {
    private final Member member;
    private final Plan plan;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

    public Subscription(Member member, Plan plan, LocalDate startDate) {
        if (member == null || plan == null || startDate == null)
            throw new IllegalArgumentException("Member, plan and start date are required.");
        this.member = member;
        this.plan = plan;
        activateFrom(startDate);
    }

    public void activateFrom(LocalDate date) {
        if (date == null) throw new IllegalArgumentException("Date is required.");
        this.startDate = date;
        this.endDate = date.plusDays(plan.getDurationDays() - 1L);
        this.active = true;
    }

    public Member getMember() { return member; }
    public Plan getPlan() { return plan; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }

    public String getStatus() {
        return active && !LocalDate.now().isAfter(endDate) ? "ACTIVE" : "EXPIRED";
    }

    public boolean isActiveOn(LocalDate date) {
        return active && date != null && !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    public void deactivate() { active = false; }

    @Override
    public String toString() {
        return plan.getName() + " | Start: " + startDate + " | End: " + endDate + " | Status: " + getStatus();
    }
}
