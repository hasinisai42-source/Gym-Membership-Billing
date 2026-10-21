package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AttendanceRecord {
    private final Member member;
    private final LocalDateTime checkInTime;

    public AttendanceRecord(Member member, LocalDateTime checkInTime) {
        if (member == null || checkInTime == null) throw new IllegalArgumentException("Member and check-in time are required.");
        this.member = member;
        this.checkInTime = checkInTime;
    }

    public Member getMember() { return member; }
    public LocalDateTime getCheckInTime() { return checkInTime; }
    public LocalDate getDate() { return checkInTime.toLocalDate(); }

    @Override
    public String toString() {
        return "Member: " + member.getName() + " (ID " + member.getId() + ") | Check-in: " + checkInTime;
    }
}
