package model;

import java.util.ArrayList;

public class Employee {
    private String name;
    private String phone;
    private String role;
    private ArrayList<Capsule> capsules = new ArrayList<Capsule>();

    public Employee(String name, String phone, String role) { // Contructor for when creating a Manager
        this.name = name;
        this.phone = phone;
        this.role = role;
    }

    public Capsule creatCapsule(String capsuleType, String description, String learnings) {
        Capsule capsule = new Capsule(description, capsuleType, learnings);
        capsules.add(capsule);
        return capsule;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\n Phone number:  " + this.phone + "\n Role: " + this.role;
    }
}
