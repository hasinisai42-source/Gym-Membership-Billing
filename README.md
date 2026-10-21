# Gym Membership and Billing System
Java console OOP mini project for Assignment Specification 17.

## Compile and run
From the project root:
```bash
javac -d out src/model/*.java src/service/*.java src/main/*.java
java -cp out main.GymManagementApp
```
In Eclipse, create a Java project, create packages `model`, `service`, and `main` under `src`, paste the source files, then run `main.GymManagementApp`.

## Default plans
Monthly (30 days, ₹1000), Quarterly (90 days, ₹2700), Annual (365 days, ₹9000).
Tax is set to 5%; late-renewal penalties are defined per plan.

## OOP concepts
- Encapsulation: private fields and controlled methods.
- Inheritance: `Member` and `Trainer` extend abstract `Person`.
- Aggregation: subscriptions link to members and plans; invoices link to members and plans; payments link to invoices; attendance links to members.
- Polymorphism: `Person.getRole()` is overridden by `Member` and `Trainer`.
