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

    public Employee(String name, String role) { // Contructor for when creating a Manager
        this.name = name;
        this.role = role;
    }

    public ArrayList<Capsule> getCapsules() {
        return capsules;
    }

    public void addCapsule(Capsule capsule) {
        capsules.add(capsule);
    }

    @Override
    public String toString() {
        return "Name: " + this.name + "\n Phone number:  " + this.phone + "\n Role: " + this.role;
    }

    public String toStringEmployee() {
        return "Name: " + this.name + "\nRole: " + this.role;
    }
}
