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

    // Getters And Setters
    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    // Methods--------------------

    public void addManager(String name, String phone) {
        managers.add(new Employee(name, phone, "MANAGER"));
    }

    public void initProjectPhases(Calendar actualDate, int phaseIndex, int amountMonthsBetweenProjects) {

        actualDate.add(Calendar.MONTH, amountMonthsBetweenProjects);

        getPhase()[phaseIndex].setendingPlannedDate(actualDate);

        Calendar nextStartDate = (Calendar) actualDate.clone();
        if (phaseIndex != 5) {
            getPhase()[phaseIndex + 1]
                    .setStartPlannedDate(nextStartDate);
        }

    }

    public void endPhase() {

        Calendar actualTime = Calendar.getInstance();

        int phaseIndex = getCurrentPhase();

        if (phaseIndex != -5 && phaseIndex != 5) {
            getPhase()[phaseIndex].setActive(false);
            getPhase()[phaseIndex].setRealEndingDate(actualTime);
            getPhase()[phaseIndex + 1].setRealStartingDate(actualTime);
            getPhase()[phaseIndex + 1].setActive(true);
        } else if (phaseIndex == 5) {
            getPhase()[phaseIndex].setActive(false);
            getPhase()[phaseIndex].setRealEndingDate(actualTime);
        }

    }

    public void addCapsule(Capsule capsule) {
        phase[getCurrentPhase()].addCapsule(capsule);
    }

    public int getCurrentPhase() {
        boolean found = false;
        int position = -5;
        for (int i = 0; i < phase.length && !found; i++) {
            if (phase[i].getActive() == true) {
                found = true;
                position = i;
            }
        }
        return position;
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
