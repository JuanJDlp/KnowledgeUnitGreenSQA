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

    /**
     * 
     * It gets the infotmation fo the name,phone and the index of the project and
     * sends it to the respective project in order to crate a manager.
     * 
     * @param name          Manager's name
     * @param phone         Manager's phone number
     * @param projectNumber The number of the Project
     */
    public void addManager(String name, String phone, int projectNumber) {
        projects[projectNumber].addManager(name, phone);
    }

    /**
     * 
     * Returns an array containing information about the employees working in a
     * specific project.
     * 
     * @param projectNumber the number of the project to get the employees from.
     * @return an array containing information about the employees working in the
     *         project, or an empty string if there are no employees in the project.
     */
    public String[] employeesInAProject(int projectNumber) {
        String[] employeesString = projects[projectNumber].employeesInAProject();
        return employeesString;
    }

    /**
     * 
     * Adds a new Project to the Projects array
     * 
     * @param projectName   The name of the Project
     * @param clientName    The name of the Client
     * @param projectBudget The budget allocated for the Project
     */
    public void addProject(String projectName, String clientName, double projectBudget) {

        Calendar startPlannedDate = Calendar.getInstance();
        int projectPositionInArray = getFirtsValidPosition();

        projects[projectPositionInArray] = new Project(projectName, clientName, startPlannedDate, projectBudget);
        // Set the first phase real starting date and planned date.
        projects[projectPositionInArray].getPhase()[0].setRealStartingDate(startPlannedDate);
        projects[projectPositionInArray].getPhase()[0].setStartPlannedDate(startPlannedDate);
    }

    /**
     * 
     * Searches for a Project with a given name
     * 
     * @param name The name of the Project to search for
     * @return The index of the Project in the Projects array, or -1 if not found
     */
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

    /**
     * 
     * Initializes the phases of a Project
     * 
     * @param actualDate                The actual date
     * @param phaseIndex                The index of the Phase
     * @param amountMonthsBetweenPhases The amount of months between phases
     * @param projectNumber             The number of the Project
     */
    public void initProjectPhases(Calendar actualDate, int phaseIndex, int amountMonthsBetweenProjects,
            int projectNumber) {
        Project currenProject = getprojects()[projectNumber];
        currenProject.initProjectPhases(actualDate, phaseIndex, amountMonthsBetweenProjects);
    }

    /**
     * 
     * Ends the current phase of a Project calling the endPhase in the project Class
     * 
     * @param projectNumber The number of the Project
     */
    public void endPhase(int projectNumber) {
        projects[projectNumber].endPhase();
    }

    /**
     * 
     * Adds a new Capsule to a Phase in a Project
     * 
     * @param projectNumber  The index of the Project
     * @param capsuleType    The type of the Capsule
     * @param description    The description of the Capsule
     * @param employeeNumber The index of the Employee that created the Capsule
     * @param learnings      The learnings from the Capsule
     * @return A message about the status of the Capsule creation
     */
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

    /**
     * 
     * Approves a Capsule from a Phase of a Project calling the approveCapsule
     * method in projects
     * 
     * @param ID            The ID of the Capsule to approve
     * @param projectNumber The index of the Project
     * @return A message about the status of the Capsule approval
     */
    public String approveCapsule(String ID, int projectNumber) {
        String msg = "\nThere was an error approving the capsule\n";
        if (projects[projectNumber].approveCapsule(ID)) {
            msg = "\nCapsule approved correctly\n";
        }
        return msg;
    }

    /**
     * 
     * Publishes a Capsule from a Phase of a Project calling the publishCapsule
     * Method in projects
     * 
     * @param ID            The ID of the Capsule to publish
     * @param projectNumber The index of the Project
     * @return The URL of the published Capsule
     */
    public String publishCapsule(String ID, int projectNumber) {
        String URL = projects[projectNumber].publishCapsule(ID);
        return URL;
    }

    /**
     * 
     * Returns the first valid position in the Projects array
     * 
     * @return The first valid position in the Projects array
     */
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
