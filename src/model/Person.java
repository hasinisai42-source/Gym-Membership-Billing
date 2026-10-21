package model;

// Common parent class demonstrates inheritance.
public abstract class Person {
    private final int id;
    private String name;

    protected Person(int id, String name) {
        if (id <= 0) throw new IllegalArgumentException("ID must be positive.");
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
        this.id = id;
        this.name = name.trim();
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
        this.name = name.trim();
    }

    public abstract String getRole();
}
