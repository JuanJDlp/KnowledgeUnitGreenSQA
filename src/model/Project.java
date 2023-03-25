package model;

import java.util.Calendar;
import java.util.ArrayList;

public class Project {
    private String projectName;
    private String clientName;
    private Calendar startPlannedDate;
    private Calendar endingPlannedDate;
    private double projectBudget;
    private ArrayList<String> managersName = new ArrayList<String>();
    private ArrayList<String> phoneNumber = new ArrayList<String>();

    public Project(String projecName, String clientName, Calendar startPlannedDate, Calendar endingPlannedDate,
            double projectBudget) {
        this.projectName = projecName;
        this.clientName = clientName;
        this.startPlannedDate = startPlannedDate;
        this.endingPlannedDate = endingPlannedDate;
        this.projectBudget = projectBudget;
    }
}
