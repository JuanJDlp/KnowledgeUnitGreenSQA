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
        projects[getFirtsValidPosition() - 1].getmanagers().add(new Employee(name, phone, "MANAGER"));
    }

    public void addProject(String projectName, String clientName, double projectBudge) {

        Calendar startPlannedDate = Calendar.getInstance();
        int newProjectPosition = getFirtsValidPosition();

        projects[newProjectPosition] = new Project(projectName, clientName, startPlannedDate, projectBudge);
        // Set the first phase real starting date and planned date.
        projects[newProjectPosition - 1].getPhase()[0].setRealStartingDate(startPlannedDate);
        projects[newProjectPosition - 1].getPhase()[0].setStartPlannedDate(startPlannedDate);
    }

    public void initProjectPhases(Calendar actualDate, int phaseIndex, int amountMonthsBetweenProjects) {

        actualDate.add(Calendar.MONTH, amountMonthsBetweenProjects);
        Project currenProject = getprojects()[getFirtsValidPosition() - 1];

        currenProject.getPhase()[phaseIndex].setendingPlannedDate(actualDate);

        Calendar nextStartDate = (Calendar) actualDate.clone();

        currenProject.getPhase()[phaseIndex + 1]
                .setStartPlannedDate(nextStartDate);
    }

    public void endPhase(int projectNumber) {

        Project currenProject = projects[projectNumber - 1];
        int phaseIndex = currenProject.getCurrentPhase();

        currenProject.getPhase()[phaseIndex].setActive(false);
        currenProject.getPhase()[phaseIndex].setRealEndingDate(Calendar.getInstance());

        currenProject.getPhase()[phaseIndex + 1].setRealStartingDate(Calendar.getInstance());
        currenProject.getPhase()[phaseIndex + 1].setActive(true);
        currenProject.setCurrentPhase(phaseIndex + 1);

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
