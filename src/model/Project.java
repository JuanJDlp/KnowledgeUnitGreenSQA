package model;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Project {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
    private String projectName;
    private String clientName;
    private Calendar startDate;
    private Calendar endingDate;
    private double projectBudget;
    private int currentPhase = 0;
    private ArrayList<Employee> managers = new ArrayList<Employee>();
    private Phase[] phase = {
            new Phase("START", true),
            new Phase("ANALYSIS", false),
            new Phase("DESIGNING", false),
            new Phase("EXECUTION", false),
            new Phase("CLOSING AND CONTROL", false),
            new Phase("MAINTAINING THE PROJECT", false)
    };

    public Project(String projecName, String clientName, Calendar startDate, double projectBudget) {
        this.projectName = projecName;
        this.clientName = clientName;
        this.startDate = startDate;
        this.projectBudget = projectBudget;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getCurrentPhase() {
        return currentPhase;
    }

    public void setCurrentPhase(int currentPhase) {
        this.currentPhase = currentPhase;
    }

    public Phase[] getPhase() {
        return phase;
    }

    public String getProjectName() {
        return projectName;
    }

    public ArrayList<Employee> getmanagers() {
        return this.managers;
    }

    public void setEndingDate(Calendar endingDate) {
        this.endingDate = endingDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    @Override
    public String toString() {
        return "Project's name: " + this.projectName +
                "\nClient's name: " + this.clientName +
                "\nStarting planned date: " + sdf.format(this.startDate.getTime()) +
                "\nEnding planned date: " + sdf.format(this.endingDate.getTime()) +
                "\nProject budget: " + this.projectBudget + " dolars" +
                "\nManagers: " + managers.toString() + "\n";
    }
}
