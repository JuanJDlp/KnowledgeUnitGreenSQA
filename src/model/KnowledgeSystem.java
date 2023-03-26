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

        projects[getFirtsValidPosition()] = new Project(projectName, clientName, startPlannedDate, projectBudge);
        // Set the first phase real starting date and planned date.
        projects[getFirtsValidPosition() - 1].getPhase()[0].setRealStartingDate(startPlannedDate);
        projects[getFirtsValidPosition() - 1].getPhase()[0].setStartPlannedDate(startPlannedDate);
    }

    public void initProjectPhases(Calendar actualDate, int position, int amountMonthsBetweenProjects) {
        actualDate.add(Calendar.MONTH, amountMonthsBetweenProjects);
        getprojects()[getFirtsValidPosition() - 1].getPhase()[position].setendingPlannedDate(actualDate);

        Calendar nextStartDate = (Calendar) actualDate.clone();

        getprojects()[getFirtsValidPosition() - 1].getPhase()[position + 1]
                .setStartPlannedDate(nextStartDate);
    }

    public void endPhase(int projectNumber) {
        Project currenProject = projects[projectNumber - 1];

        currenProject.getPhase()[currenProject.getCurrentPhase()].setActive(false);
        currenProject.getPhase()[currenProject.getCurrentPhase()].setRealEndingDate(Calendar.getInstance());

        currenProject.getPhase()[currenProject.getCurrentPhase() + 1].setRealStartingDate(Calendar.getInstance());
        currenProject.getPhase()[currenProject.getCurrentPhase() + 1].setActive(true);
        currenProject.setCurrentPhase(currenProject.getCurrentPhase() + 1);

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
