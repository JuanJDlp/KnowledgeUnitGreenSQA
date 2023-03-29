package model;

import java.util.ArrayList;;

public class Capsule {
    private String ID;
    private String description;
    private String capsuleType;
    private Employee collaborator;
    private String learnings;
    private boolean approved = false;
    private ArrayList<String> hastags = new ArrayList<String>();

    public Capsule(String description, String capsuleType, String learnings) {
        this.description = description;
        this.capsuleType = capsuleType;
        this.learnings = learnings;
    }

    public void setID(int iD) {
        ID = "CC" + iD;
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

    public void addHastag(String hastag) {
        hastags.add(hastag);
    }

    public ArrayList<String> getHastags() {
        return hastags;
    }

    @Override
    public String toString() {
        return "ID: " + this.ID +
                "\nDescription: " + this.description +
                "\nCapsule Type: " + this.capsuleType +
                "\nEmployee: " + this.collaborator.toString() +
                "\nLearnings: " + this.learnings;
    }
}
