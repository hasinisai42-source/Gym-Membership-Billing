package model;

public class Trainer {
    private final int id;
    private String name;
    private String specialty;

    public Trainer(int id, String name, String specialty) {
        if (id <= 0) throw new IllegalArgumentException("Trainer ID must be positive.");
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Trainer name cannot be empty.");
        if (specialty == null || specialty.trim().isEmpty()) throw new IllegalArgumentException("Specialty cannot be empty.");
        this.id = id;
        this.name = name.trim();
        this.specialty = specialty.trim();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSpecialty() { return specialty; }

    @Override
    public String toString() {
        return "Trainer ID: " + getId() + " | " + getName() + " | Specialty: " + specialty;
    }
}
