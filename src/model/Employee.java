package model;

import java.util.ArrayList;

public class Employee {
    private String name;
    private String phone;
    private String role;
    private ArrayList<Capsule> capsules;

    public Employee(String name, String phone, String role) { // Contructor for when creating a Manager
        this.name = name;
        this.phone = phone;
        this.role = role;
    }

    public Employee(String name, String role) { // Contructor for when creating a Manager
        this.name = name;
        this.role = role;
        this.capsules = new ArrayList<Capsule>();
    }

    public ArrayList<Capsule> getCapsules() {
        return capsules;
    }

    /**
     * Adds the corresponding capsule that the employee created to the capsules
     * arraylist.
     * 
     * @param capsule the capsule to add
     */
    public void addCapsule(Capsule capsule) {
        capsules.add(capsule);
    }

    @Override
    public String toString() {
        String msj = "Name: " + this.name + "\n Phone number:  " + this.phone + "\n Role: " + this.role;
        if (this.phone == null) {
            msj = "Name: " + this.name + "\n Role: " + this.role;
        }
        return msj;
    }
}
