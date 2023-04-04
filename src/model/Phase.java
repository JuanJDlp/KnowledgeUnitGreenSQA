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
        this.CAPSULESIZE = 50;
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

    /**
     * 
     * Returns information about the capsules in the current phase of the project.
     * This method concatenates the details of the first valid capsule in the phase,
     * along with its hashtags, into a single string and returns it.
     * 
     * @return A string containing the details of the first valid capsule in the
     *         current phase of the project and its hashtags, separated by a newline
     *         character.
     */

    public String getCapsulesInfo() {
        return getCapsules()[getFirtsValidCapsule() - 1]
                + "\n" +
                getCapsules()[getFirtsValidCapsule() - 1].getHastags();
    }

    /**
     * Checks if there is room for another capsule in the current phase.
     * 
     * @return true if there is room for another capsule, false otherwise.
     */
    public boolean isThereRoomForAnotherCapsule() {
        boolean isThereRoomForAnotherCapsule = false;
        if (capsules.length != getFirtsValidCapsule()) {
            isThereRoomForAnotherCapsule = true;
        }
        return isThereRoomForAnotherCapsule;
    }

    /**
     * Adds a capsule to the capsules array if there is room.
     * 
     * @param capsule The capsule to add.
     */
    public void addCapsule(Capsule capsule) {
        if (isThereRoomForAnotherCapsule()) {
            capsules[getFirtsValidCapsule()] = capsule;
        }
    }

    /**
     * Returns the index of the first valid capsule. If there is not space for more
     * capsules
     * it will return the lenght of the capsules array times -1.
     * 
     * @return The index of the first valid capsule.
     */
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

    /**
     * Finds the capsule with the specified ID.
     * 
     * @param ID The ID of the capsule to find.
     * @return The capsule with the specified ID, or null if no capsule with that ID
     *         is found.
     */
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

    /**
     * Approves the capsule with the specified ID.
     * 
     * @param ID The ID of the capsule to approve.
     * @return true if the capsule was approved, false otherwise.
     */
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

    /**
     * Publishes the capsule with the specified ID.
     * 
     * @param ID The ID of the capsule to publish.
     * @return The URL of the published capsule, or an empty string if the capsule
     *         could not be published.
     */
    public String publishCapsule(String ID) {
        String URL = "";
        Capsule currentIDCapsule = findCapsule(ID);
        if (currentIDCapsule != null && !currentIDCapsule.getPublished()) {
            if (currentIDCapsule.getApproved()) {
                currentIDCapsule.setPublished(true);
                URL = currentIDCapsule.createCapsuleURL();
                currentIDCapsule.createCapsuleHTML();
            }
        }

        return URL;
    }

    public int amountCapsulesByType(String capsuleType) {
        int counter = 0;
        for (int i = 0; i < getFirtsValidCapsule(); i++) {
            if (capsules[i].getCapsuleType().equalsIgnoreCase(capsuleType)) {
                counter++;
            }
        }
        return counter;
    }

    public String learningsInAPhase() {
        String listOfLearnings = "";
        for (int i = 0; i < getFirtsValidCapsule(); i++) {
            listOfLearnings += "\n" + (i + 1) + ": " +
                    capsules[i].getID() +
                    "\nDescription: " +
                    capsules[i].getDescription() +
                    "\nLearnings: " +
                    capsules[i].getLearnings() + "\n";
        }
        return listOfLearnings;
    }

    public String InformLeaningsOfCapsulesByHastag(String query) {
        String listOfCapsules = "";
        for (int i = 0; i < getFirtsValidCapsule(); i++) {
            if (capsules[i].matchQueryWithHastag(query) && capsules[i].getApproved() && capsules[i].getPublished()) {
                listOfCapsules += "\n" + capsules[i].toString() + "\n";
            }
        }
        return listOfCapsules;

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
