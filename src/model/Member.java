package model;

public class Member {
    private final int id;
    private String name;
    private String phone;
    private Subscription subscription;

    public Member(int id, String name, String phone) {
        if (id <= 0) throw new IllegalArgumentException("Member ID must be positive.");
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
        if (phone == null || !phone.matches("[0-9]{10}"))
            throw new IllegalArgumentException("Phone number must contain exactly 10 digits.");
        this.id = id;
        this.name = name.trim();
        this.phone = phone;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public Subscription getSubscription() { return subscription; }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
        this.name = name.trim();
    }

    public void setPhone(String phone) {
        if (phone == null || !phone.matches("[0-9]{10}"))
            throw new IllegalArgumentException("Phone number must contain exactly 10 digits.");
        this.phone = phone;
    }

    public void setSubscription(Subscription subscription) { this.subscription = subscription; }

    @Override
    public String toString() {
        return "Member ID: " + getId() + " | Name: " + getName() + " | Phone: " + phone
                + " | Subscription: " + (subscription == null ? "None" : subscription.getStatus());
    }
}
