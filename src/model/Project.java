package model;

import java.util.Calendar;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Project {
    private SimpleDateFormat sdf;
    private String projectName;
    private String clientName;
    private Calendar startDate;
    private Calendar endingDate;
    private double projectBudget;
    private ArrayList<Employee> managers;
    private Phase[] phase;
    private Employee[] employee;

    public Project(String projecName, String clientName, Calendar startDate, double projectBudget) {
        this.sdf = new SimpleDateFormat("dd/MMM/yyyy");

        this.projectName = projecName;
        this.clientName = clientName;
        this.startDate = startDate;
        this.projectBudget = projectBudget;

        this.employee = new Employee[] {
                new Employee("Vanessa", "COLLABORATOR"),
                new Employee("Rony", "COLLABORATOR"),
                new Employee("Mariana", "COLLABORATOR"),
                new Employee("Natalia", "COLLABORATOR")
        };

        this.phase = new Phase[] {
                new Phase("START", true),
                new Phase("ANALYSIS", false),
                new Phase("DESIGNING", false),
                new Phase("EXECUTION", false),
                new Phase("CLOSING AND CONTROL", false),
                new Phase("MAINTAINING THE PROJECT", false)
        };

        this.managers = new ArrayList<Employee>();
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

    public Employee[] getEmployee() {
        return employee;
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

        Phase currentPhase = getCurrentPhase(0);

        if (currentPhase != null && currentPhase != phase[5]) {
            Phase nextPhase = getCurrentPhase(1);

            currentPhase.setRealEndingDate(actualTime);
            currentPhase.setActive(false);

            nextPhase.setRealStartingDate(actualTime);
            nextPhase.setActive(true);
        } else if (currentPhase == phase[5]) {
            currentPhase.setActive(false);
            currentPhase.setRealEndingDate(actualTime);
        }

    }

    public boolean addCapsule(String capsuleType, String description, int employeeNumber,
            String learnings) {
        boolean added = false;
        if (hasHashWords(learnings) && hasHashWords(description)) {
            String ID = "CC" + this.projectName + getCurrentPhase(0).getPhaseType()
                    + String.valueOf(getCurrentPhase(0).getFirtsValidCapsule());
            Capsule capsule = new Capsule(ID, description, capsuleType, learnings, employee[employeeNumber]);
            employee[employeeNumber].addCapsule(capsule);
            getCurrentPhase(0).addCapsule(capsule);
            added = true; // Flag
        }
        return added;
    }

    public boolean hasHashWords(String text) {
        Pattern p = Pattern.compile("#([^#]+)#"); // Modify the regex to match the pattern between '#' characters
        Matcher m = p.matcher(text);

        return m.find();
    }

    public Phase getCurrentPhase(int PositoinsToMoveToGetTheNextPhase) {
        Phase currentStage = null;
        boolean found = false;
        int position = -1;
        for (int i = 0; i < phase.length && !found; i++) {
            if (phase[i].getActive() == true) {
                found = true;
                position = i;
            }
        }
        if (position != -1) {
            currentStage = phase[position + PositoinsToMoveToGetTheNextPhase];
        }

        return currentStage;
    }

    public boolean approveCapsule(String ID) {
        boolean found = false;
        for (int i = 0; i < phase.length && !found; i++) {
            if (phase[i].getFirtsValidCapsule() > 0) {
                found = phase[i].approveCapsule(ID);
            }
        }
        return found;
    }

    public String publishCapsule(String ID) {
        boolean found = false;
        String URL = "";
        for (int i = 0; i < phase.length && !found; i++) {
            if (phase[i].getFirtsValidCapsule() > 0) {
                found = true;
                URL = phase[i].publishCapsule(ID);
            }
        }
        return URL;
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
