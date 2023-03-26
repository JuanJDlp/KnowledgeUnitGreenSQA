package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class KnowledgeSystem {
    private final int SIZEPROJECTS = 10;
    private Project[] projects = new Project[SIZEPROJECTS];

    public KnowledgeSystem() {
        /* Empty constructor */}

    public Project[] getprojects() {
        return projects;
    }

    public int getSIZEPROJECTS() {
        return SIZEPROJECTS;
    }

    public void addProject(String projectName, String clientName, String startPlannedDateString,
            String endingPlannedDateString,
            double projectBudge) {
        Calendar startPlannedDate = stringToCalendar(startPlannedDateString);
        Calendar endingPlannedDate = stringToCalendar(endingPlannedDateString);
        projects[getFirtsValidPosition()] = new Project(projectName, clientName, startPlannedDate, endingPlannedDate,
                projectBudge);
    }

    public Calendar stringToCalendar(String string) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
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
