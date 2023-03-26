package model;

public class Employee {
    String name;
    String phone;
    String role;

    public Employee(String name, String phone, String role) { // Contructor for when creating a Manager
        this.name = name;
        this.phone = phone;
        this.role = role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + " Numero de telefono: " + this.phone + " Role: " + this.role;
    }
}
