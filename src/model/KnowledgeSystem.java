package model;

import java.util.Calendar;

public class KnowledgeSystem {
    private final int SIZEPROJECTS = 10;
    private Project[] projects = new Project[SIZEPROJECTS];

    public KnowledgeSystem() {
    }

    public void addProject(String projectName, String clientName, Calendar startPlannedDate, Calendar endingPlannedDate,
            double projectBudge) {
        projects[getFirtsValidPosition()] = new Project(projectName, clientName, startPlannedDate, endingPlannedDate,
                projectBudge);
    }

    public int getFirtsValidPosition() {
        boolean isFound = false; // flag
        int pos = -1;
        for (int i = 0; i < SIZEPROJECTS && !isFound; i++) {
            if (projects[i].equals(null)) {
                isFound = true;
                pos = i;
            }
        }
        return pos;
    }
}
