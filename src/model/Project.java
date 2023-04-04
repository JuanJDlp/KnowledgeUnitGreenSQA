package model;

import java.util.Calendar;

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
    private Employee[] employees;
    private int amountOfCapsulesRegistered;

    public Project(String projecName, String clientName, Calendar startDate, double projectBudget) {
        this.sdf = new SimpleDateFormat("dd/MMM/yyyy");

        this.projectName = projecName;
        this.clientName = clientName;
        this.startDate = startDate;
        this.projectBudget = projectBudget;

        this.employees = new Employee[] {
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

    public Employee[] getEmployees() {
        return employees;
    }

    public int getAmountOfCapsulesRegistered() {
        return amountOfCapsulesRegistered;
    }

    // Methods--------------------
    /**
     * This method gets the information of the last phase.
     * 
     * @return the information of the last phase
     */
    // I created this method so when a user ends all the phases and he tries to end
    // the project again
    // he can see the information about the last phases ended.
    public String endProjectInformation() {
        return phase[phase.length - 1].toString();
    }

    /**
     * Create an array containing the names of all the phases in a project
     * 
     * @return Names of all the phases in a project
     */
    public String[] phaseNamesInAProject() {
        String[] phaseNames = new String[phase.length];

        for (int i = 0; i < phase.length; i++) {
            phaseNames[i] = phase[i].getPhaseType();
        }
        return phaseNames;
    }

    /**
     * 
     * Adds a manager to the project with the specified name and phone number.
     * 
     * @param name  the name of the manager.
     * @param phone the phone number of the manager.
     */
    public void addManager(String name, String phone) {
        managers.add(new Employee(name, phone, "MANAGER"));
    }

    /**
     * 
     * Initializes the project phases with the specified parameters.
     * 
     * @param actualDate                  the current date for the project.
     * @param phaseIndex                  the index of the current phase.
     * @param amountMonthsBetweenProjects the amount of months between each project
     *                                    phase.
     */
    public void initProjectPhases(Calendar actualDate, int phaseIndex, int amountMonthsBetweenProjects) {

        actualDate.add(Calendar.MONTH, amountMonthsBetweenProjects);

        getPhase()[phaseIndex].setendingPlannedDate(actualDate);

        Calendar nextStartDate = (Calendar) actualDate.clone();
        if (phaseIndex != 5) {
            getPhase()[phaseIndex + 1]
                    .setStartPlannedDate(nextStartDate);
        } else {
            // I had to make this way since it is an special case when the last phase is
            // initialized.
            phase[5].setendingPlannedDate(actualDate);
            setEndingDate(actualDate);
        }

    }

    /**
     * 
     * Ends the current project phase and starts the next one.
     */
    public void endPhase() {

        Calendar actualTime = Calendar.getInstance();

        Phase currentPhase = getCurrentPhase(0);
        // validates if it is the last phase or if there are no more phases to end.
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

    /**
     * 
     * Returns an array of strings representing the employees involved in the
     * project.
     * Each element of the array corresponds to the string representation of an
     * Employee object.
     * 
     * @return an array of strings representing the employees involved in the
     *         project
     */
    public String[] employeesInAProject() {
        String[] employeesString = new String[employees.length];
        for (int i = 0; i < employees.length; i++) {
            employeesString[i] = employees[i].toString();
        }
        return employeesString;
    }

    /**
     * Adds a new capsule to the project.
     * 
     * @param capsuleType    the type of the capsule being added.
     * @param description    the description of the capsule being added.
     * @param employeeNumber the number of the employee that created the capsule.
     * @param learnings      the learnings associated with the capsule being added.
     * @return a boolean value indicating whether or not the capsule was
     *         successfully added.
     */
    public boolean addCapsule(String capsuleType, String description, int employeeNumber,
            String learnings) {
        boolean added = false;
        if (getCurrentPhase(0).isThereRoomForAnotherCapsule()) {
            String ID = "CC" +
                    this.projectName.replace(" ", "_") +
                    getCurrentPhase(0).getPhaseType() +
                    String.valueOf(getCurrentPhase(0).getFirtsValidCapsule());

            Capsule capsule = new Capsule(ID, description, capsuleType, learnings, employees[employeeNumber]);
            amountOfCapsulesRegistered++;

            employees[employeeNumber].addCapsule(capsule);
            getCurrentPhase(0).addCapsule(capsule);
            added = true; // Flag
        }
        return added;
    }

    /**
     * Returns the current phase of the project. The user can pick which phase from
     * the actual one he wants to get
     * if the this param is 0, the method will return the current phase.
     * 
     * @param PositionsToMoveToGetTheNextPhase the number of positions to move to
     *                                         get the next phase. if its needed.
     * @return the current phase of the project.
     */
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

    /**
     * Approves a capsule in the project.
     * 
     * @param ID the ID of the capsule being approved.
     * @return a boolean value indicating whether or not the capsule was
     *         successfully approved.
     */
    public boolean approveCapsule(String ID) {
        boolean found = false;
        for (int i = 0; i < phase.length && !found; i++) {
            if (phase[i].getFirtsValidCapsule() > 0) {
                found = phase[i].approveCapsule(ID);
            }
        }
        return found;
    }

    /**
     * Publishes a capsule in the project.
     * 
     * @param ID the ID of the capsule being published.
     * @return the URL of the capsule that was published.
     */
    public String publishCapsule(String ID) {
        boolean found = false;
        String URL = "";
        for (int i = 0; i < phase.length && !found; i++) {
            URL = phase[i].publishCapsule(ID);
            if (!URL.equals("")) {
                found = true;
            }
        }
        return URL;
    }

    public int amountCapsulesByType(String capsuleType) {
        int counter = 0;
        for (int i = 0; i < phase.length; i++) {
            counter += phase[i].amountCapsulesByType(capsuleType);
        }
        return counter;
    }

    public String learningsInAPhase(int phaseIndex) {
        return phase[phaseIndex].learningsInAPhase();
    }

    public String capsulesOfAnEmployee(String employeeName) {
        String ListOfCapsules = "";
        for (int i = 0; i < employees.length; i++) {
            if (employees[i].getName().equalsIgnoreCase(employeeName)) {
                ListOfCapsules += "\n" + employees[i].capsulesOfAnEmployee() + "\n";
            }
        }
        return ListOfCapsules;
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
