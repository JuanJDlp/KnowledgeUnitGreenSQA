package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class KnowledgeSystem {
    private final int SIZEPROJECTS = 10;
    private Project[] projects = new Project[SIZEPROJECTS];
    private Employee[] employee = {
            new Employee("Vanessa", null, "COLLABORATOR"),
            new Employee("Rony", null, "COLLABORATOR"),
            new Employee("Mariana", null, "COLLABORATOR"),
            new Employee("Natalia", null, "COLLABORATOR")
    };

    public KnowledgeSystem() {
        /* Empty constructor */}

    public Project[] getprojects() {
        return projects;
    }

    public int getSIZEPROJECTS() {
        return SIZEPROJECTS;
    }

    // Adds a manager to a project based on its position.
    public void addManager(String name, String phone) {
        projects[getFirtsValidPosition() - 1].addManager(name, phone);
    }

    public void addProject(String projectName, String clientName, double projectBudge) {

        Calendar startPlannedDate = Calendar.getInstance();

        projects[getFirtsValidPosition()] = new Project(projectName, clientName, startPlannedDate, projectBudge);
        // Set the first phase real starting date and planned date.
        projects[getFirtsValidPosition() - 1].getPhase()[0].setRealStartingDate(startPlannedDate);
        projects[getFirtsValidPosition() - 1].getPhase()[0].setStartPlannedDate(startPlannedDate);
    }

    public int findProject(String name) {
        boolean found = false;
        int position = -1;
        for (int i = 0; i < getFirtsValidPosition() && !found; i++) {
            if (name.equalsIgnoreCase(projects[i].getProjectName())) {
                found = true;
                position = i;
            }
        }
        return position;
    }

    public void initProjectPhases(Calendar actualDate, int phaseIndex, int amountMonthsBetweenProjects) {
        Project currenProject = getprojects()[getFirtsValidPosition() - 1];
        currenProject.initProjectPhases(actualDate, phaseIndex, amountMonthsBetweenProjects);
    }

    public void endPhase(int projectNumber) {

        Calendar actualTime = Calendar.getInstance();

        Project currentProject = projects[projectNumber];
        int phaseIndex = currentProject.getCurrentPhase();

        if (phaseIndex != -5 && phaseIndex != 5) {
            currentProject.getPhase()[phaseIndex].setActive(false);
            currentProject.getPhase()[phaseIndex].setRealEndingDate(actualTime);
            currentProject.getPhase()[phaseIndex + 1].setRealStartingDate(actualTime);
            currentProject.getPhase()[phaseIndex + 1].setActive(true);
        } else if (phaseIndex == 5) {
            currentProject.getPhase()[phaseIndex].setActive(false);
            currentProject.getPhase()[phaseIndex].setRealEndingDate(actualTime);
        }

    }

    public Calendar stringToCalendar(String string) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
        try {
            calendar.setTime(sdf.parse(string));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }

    public int getFirtsValidPosition() {
        boolean isFound = false; // flag
        int pos = -1;
        for (int i = 0; i < SIZEPROJECTS && !isFound; i++) {
            if (projects[i] == null) {
                isFound = true;
                pos = i;
            }
        }
        return pos;
    }
}
