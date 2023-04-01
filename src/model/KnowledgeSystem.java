package model;

import java.util.Calendar;

public class KnowledgeSystem {
    private final int SIZEPROJECTS;
    private Project[] projects;

    public KnowledgeSystem() {
        this.SIZEPROJECTS = 3;
        this.projects = new Project[SIZEPROJECTS];
    }

    public Project[] getprojects() {
        return projects;
    }

    public int getSIZEPROJECTS() {
        return SIZEPROJECTS;
    }

    // Adds a manager to a project based on its position.
    public void addManager(String name, String phone, int projectNumber) {
        projects[projectNumber].addManager(name, phone);
    }

    public Employee[] employeesInAProject(int projectNumber) {
        return projects[projectNumber].getEmployee();
    }

    public void addProject(String projectName, String clientName, double projectBudge) {

        Calendar startPlannedDate = Calendar.getInstance();
        int projectPositionInArray = getFirtsValidPosition();

        projects[projectPositionInArray] = new Project(projectName, clientName, startPlannedDate, projectBudge);
        // Set the first phase real starting date and planned date.
        projects[projectPositionInArray].getPhase()[0].setRealStartingDate(startPlannedDate);
        projects[projectPositionInArray].getPhase()[0].setStartPlannedDate(startPlannedDate);
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

    public void initProjectPhases(Calendar actualDate, int phaseIndex, int amountMonthsBetweenProjects,
            int projectNumber) {
        Project currenProject = getprojects()[projectNumber];
        currenProject.initProjectPhases(actualDate, phaseIndex, amountMonthsBetweenProjects);
    }

    public void endPhase(int projectNumber) {
        projects[projectNumber].endPhase();
    }

    public String addCapsule(int projectNumber, String capsuleType, String description, int employeeNumber,
            String learnings) {
        String msj = "\nFATAL: There needs to be hastags in the learnings and in the description. OR\n you reached the maximum capsules available {50}";
        if (projects[projectNumber].addCapsule(capsuleType, description, employeeNumber, learnings)) {
            msj = "\nCapsule created succesfully created \n\n"
                    + projects[projectNumber].getCurrentPhase(0)
                            .getCapsules()[projects[projectNumber].getCurrentPhase(0).getFirtsValidCapsule() - 1]

                    + "\n" +

                    projects[projectNumber].getCurrentPhase(0)
                            .getCapsules()[projects[projectNumber].getCurrentPhase(0).getFirtsValidCapsule() - 1]
                            .getHastags();
        }
        return msj;
    }

    public String approveCapsule(String ID, int projectNumber) {
        String msg = "\nThere was an error approving the capsule\n";
        if (projects[projectNumber].approveCapsule(ID)) {
            msg = "\nCapsule approved correctly\n";
        }
        return msg;
    }

    public String publishCapsule(String ID, int projectNumber) {
        String URL = projects[projectNumber].publishCapsule(ID);
        return URL;
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
        return pos = (pos == -1) ? getprojects().length
                : pos;
    }
}
