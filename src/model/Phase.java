package model;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class Phase {
    private SimpleDateFormat sdf;
    private Calendar startPlannedDate;
    private Calendar endingPlannedDate;
    private Calendar realStartingDate;
    private Calendar realEndingDate;
    private boolean active;
    private String phaseType;
    private final int CAPSULESIZE;
    private Capsule[] capsules;

    public Phase(String phaseType, boolean active) {
        this.sdf = new SimpleDateFormat("dd/MMM/yyyy");
        this.phaseType = phaseType;
        this.active = active;
        this.CAPSULESIZE = 2;
        this.capsules = new Capsule[CAPSULESIZE];
    }

    // Getters and setters
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

    public Capsule[] getCapsules() {
        return capsules;
    }

    // Methods-----

    public boolean isThereRoomForAnotherCapsule() {
        boolean isThereRoomForAnotherCapsule = false;
        if (capsules.length != getFirtsValidCapsule()) {
            isThereRoomForAnotherCapsule = true;
        }
        return isThereRoomForAnotherCapsule;
    }

    public void addCapsule(Capsule capsule) {
        if (isThereRoomForAnotherCapsule()) {
            capsules[getFirtsValidCapsule()] = capsule;
        }
    }

    public int getFirtsValidCapsule() {
        boolean isFound = false; // flag
        int pos = capsules.length * -1;
        for (int i = 0; i < CAPSULESIZE && !isFound; i++) {
            if (capsules[i] == null) {
                isFound = true;
                pos = i;
            }
        }
        return pos = (pos == capsules.length * -1) ? capsules.length : pos;
    }

    public Capsule findCapsule(String ID) {
        boolean found = false;
        Capsule capsule = null;
        for (int i = 0; i < getFirtsValidCapsule() && !found; i++) {
            if (capsules[i].getID().equalsIgnoreCase(ID)) {
                found = true;
                capsule = capsules[i];
            }
        }
        return capsule;
    }

    public boolean approveCapsule(String ID) {
        boolean isApproved = false;
        Capsule currentIDCapsule = findCapsule(ID);
        if (currentIDCapsule != null) {
            if (!currentIDCapsule.getApproved()) {
                isApproved = true;
                currentIDCapsule.setApproved(true);
                currentIDCapsule.setApprovalDate(Calendar.getInstance());
            }
        }

        return isApproved;
    }

    public String publishCapsule(String ID) {
        String URL = "";
        Capsule currentIDCapsule = findCapsule(ID);
        if (currentIDCapsule != null) {
            if (currentIDCapsule.getApproved()) {
                URL = currentIDCapsule.createCapsuleURL();
                currentIDCapsule.createCapsuleHTML();
            }
        }

        return URL;
    }

    @Override
    public String toString() {
        String result = "Phase: " + this.phaseType +
                "\n Active: " + this.active +
                "\n Start planned date: " + sdf.format(this.startPlannedDate.getTime()) +
                "\n Ending Planned date: " + sdf.format(this.endingPlannedDate.getTime());

        if (this.active) {
            result += "\n The project started: " + sdf.format(this.realStartingDate.getTime());
        } else {
            result += "\n Starting date: " + sdf.format(this.realStartingDate.getTime()) +
                    "\n Ending date: " + sdf.format(this.realEndingDate.getTime());
        }

        result += "\n";

        return result;
    }

}
