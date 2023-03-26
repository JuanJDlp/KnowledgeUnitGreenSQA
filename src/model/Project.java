package model;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Project {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private String projectName;
    private String clientName;
    private Calendar startPlannedDate;
    private Calendar endingPlannedDate;
    private double projectBudget;
    private ArrayList<Employee> managers = new ArrayList<Employee>();

    public ArrayList<Employee> getmanagers() {
        return this.managers;
    }

    public Project(String projecName, String clientName, Calendar startPlannedDate, Calendar endingPlannedDate,
            double projectBudget) {
        this.projectName = projecName;
        this.clientName = clientName;
        this.startPlannedDate = startPlannedDate;
        this.endingPlannedDate = endingPlannedDate;
        this.projectBudget = projectBudget;
    }

    @Override
    public String toString() {
        return "Project's name: " + this.projectName +
                "\nClient's name: " + this.clientName +
                "\nStarting planned date: " + sdf.format(this.startPlannedDate.getTime()) +
                "\nEnding planned date: " + sdf.format(this.endingPlannedDate.getTime()) +
                "\nProject budget: " + this.projectBudget + " dolars" +
                "\nManagers: " + managers.toString() + "\n";
    }
}
