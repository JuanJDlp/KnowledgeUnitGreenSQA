package model;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;;

public class Capsule {
    private String ID;
    private String description;
    private String capsuleType;
    private Employee collaborator;
    private String learnings;
    private boolean approved = false;
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
        return "ID: " + this.ID +
                "\nDescription: " + this.description +
                "\nCapsule Type: " + this.capsuleType +
                "\nEmployee: " + this.collaborator.toStringEmployee() +
                "\nLearnings: " + this.learnings;
    }
}
