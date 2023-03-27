package model;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Phase {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
    private Calendar startPlannedDate;
    private Calendar endingPlannedDate;
    private Calendar realStartingDate;
    private Calendar realEndingDate;
    private boolean approved;
    private boolean active;
    private String phaseType;

    public Phase(String phaseType, boolean active) {
        this.phaseType = phaseType;
        this.active = active;
    }

    public void setStartPlannedDate(Calendar date) {
        this.startPlannedDate = date;
    }

    public void setendingPlannedDate(Calendar date) {
        Calendar dateClone = (Calendar) date.clone();
        this.endingPlannedDate = dateClone;
    }

    public void setRealStartingDate(Calendar realStartingDate) {
        this.realStartingDate = realStartingDate;
    }

    public void setRealEndingDate(Calendar realEndingDate) {
        this.realEndingDate = realEndingDate;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setPhaseType(String phaseType) {
        this.phaseType = phaseType;
    }

    public String getPhaseType() {
        return phaseType;
    }

    public boolean getActive() {
        return active;
    }

    @Override
    public String toString() {
        return "Phase: " + this.phaseType +
                "\n Active: " + this.active +
                "\n The project started: " + sdf.format(this.realStartingDate.getTime()) + "\n" +
                "\n Start planned date: " + sdf.format(this.startPlannedDate.getTime()) +
                "\n Ending Planned date: " + sdf.format(this.endingPlannedDate.getTime());
    }

    public String finishedPhase() {
        return "Phase: " + this.phaseType +
                "\n Active: " + this.active +
                "\n Start planned date: " + sdf.format(this.startPlannedDate.getTime()) +
                "\n Ending Planned date: " + sdf.format(this.endingPlannedDate.getTime()) + "\n" +
                "\n Starting date: " + sdf.format(this.realStartingDate.getTime()) +
                "\n Ending date: " + sdf.format(this.realEndingDate.getTime());
    }

}
