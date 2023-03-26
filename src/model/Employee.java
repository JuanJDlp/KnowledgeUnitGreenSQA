package model;

public class Employee {
    String name;
    String info;

    public Employee(String name, String info) { // Contructor for when creating a Manager
        this.name = name;
        this.info = info;
    }

    @Override
    public String toString() {
        return "Name: " + this.name + " info: " + this.info;
    }
}
