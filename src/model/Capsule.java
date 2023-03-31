package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;

public class Capsule {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
    private String ID;
    private String description;
    private String capsuleType;
    private Employee collaborator;
    private String learnings;
    private boolean approved = false;
    private Calendar approvalDate;
    private ArrayList<String> hastags = new ArrayList<String>();

    public Capsule(String id, String description, String capsuleType, String learnings, Employee collaborator) {
        this.ID = id;
        this.description = description;
        this.capsuleType = capsuleType;
        this.learnings = learnings;
        this.collaborator = collaborator;
        addHasTags(description);
        addHasTags(learnings);
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getID() {
        return ID;
    }

    public void setCollaborator(Employee collaborator) {
        this.collaborator = collaborator;
    }

    public Employee getCollaborator() {
        return collaborator;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean getApproved() {
        return this.approved;
    }

    public Calendar getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Calendar approvalDate) {
        this.approvalDate = approvalDate;
    }

    public ArrayList<String> getHastags() {
        return hastags;
    }

    // -----METHODS-----
    public void addHasTags(String text) {
        Pattern p = Pattern.compile("#([^#]+)#");
        Matcher m = p.matcher(text);
        while (m.find()) {
            hastags.add(m.group(1).replaceAll("#", ""));
        }
    }

    @Override
    public String toString() {
        String result = "ID: " + this.ID +
                "\nDescription: " + this.description +
                "\nCapsule Type: " + this.capsuleType +
                "\nEmployee: " + this.collaborator.toString() +
                "\nLearnings: " + this.learnings +
                "\nAPPROVED: " + this.approved;

        if (this.approved) {
            result += "\nApproval Date: " + sdf.format(this.approvalDate.getTime()) + "\n";
        }

        return result;
    }
}
