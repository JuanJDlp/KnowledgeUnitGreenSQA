package model;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is the controller class, the class is encharged of conecting the
 * frontend with the backend
 * 
 * @author Juan Jose De La Pava
 */
public class KnowledgeSystem {
    private final int SIZEPROJECTS;
    private Project[] projects;

    public KnowledgeSystem() {
        this.SIZEPROJECTS = 10;
        this.projects = new Project[SIZEPROJECTS];
    }

    public Project[] getProjects() {
        return projects;
    }

    public int getSIZEPROJECTS() {
        return SIZEPROJECTS;
    }

    public boolean isACapsuleApproved(int projectNumber, int i, int j) {
        return getProjects()[projectNumber].getPhase()[i].getCapsules()[j].getApproved();
    }

    /**
     * Checks if there is room for more projects
     * 
     * @return true if there is a position where a proejct can be added, otherwise
     *         false.
     */
    public boolean isProjectsFull() {
        boolean answer = false;
        if (getFirtsValidPosition() == projects.length) {
            answer = true;
        }
        return answer;
    }

    /**
     * Check if there is a projects created.
     * 
     * @return true if there has been a project created otherwise false.
     */
    public boolean hasThereBeenProjectsCreated() {
        boolean answer = false;
        if (projects[0] != null) {
            answer = true;
        }
        return answer;
    }

    /**
     * Saves all the names of the phases of a project
     * 
     * @param projectNumber index of the project whom names of the phases wants to
     *                      get.
     * @return Name of the phases of an specific project.
     */
    public String[] phaseNamesInAProject(int projectNumber) {
        return projects[projectNumber].phaseNamesInAProject();
    }

    /**
     * It checks if all the project phases are false and if they are it says that
     * the project has ended.
     * 
     * @param projectNumber the index of the project to check
     * @return true if the project has ended otherwise false
     */
    public boolean hasAProjectEndend(int projectNumber) {
        boolean hasItEnded = false;
        if (projects[projectNumber].getCurrentPhase(0) == null) {
            hasItEnded = true;
        }
        return hasItEnded;
    }

    /**
     * This method gets the information of the las phase of the project and it is
     * used
     * when a project has culminated.
     * 
     * @param projectNumber index of the project
     * @return a String containing the information of the last phase of the project
     */
    public String endProjectInformation(int projectNumber) {
        return projects[projectNumber].endProjectInformation();
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
     * @param actualDate                  The actual date
     * @param phaseIndex                  The index of the Phase
     * @param amountMonthsBetweenProjects The amount of months between phases
     * @param projectNumber               The number of the Project
     */
    public void initProjectPhases(Calendar actualDate, int phaseIndex, int amountMonthsBetweenProjects,
            int projectNumber) {
        Project currenProject = getProjects()[projectNumber];
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
        String msg = "\nFATAL: There needs to be hastags in the learnings and in the description.";
        if (hasHashWords(description) && hasHashWords(learnings)) {
            // Checks if there is room for another capsules.
            if (projects[projectNumber].addCapsule(capsuleType, description, employeeNumber, learnings)) {
                // ITs the to sting of the capsule
                msg = "\nCapsule created succesfully created \n\n"
                        + getCapsulesInfo(projectNumber);

            } else {
                msg = "\nYou have reached the maximum amount of capsules.\n";
            }

        }
        return msg;
    }

    /**
     * 
     * Returns information about the first valid capsule in the current phase of the
     * given project, including its description
     * and hashtags.
     * 
     * @param projectNumber the index of the project to get the capsule information
     *                      from
     * @return a string with the description and hashtags of the first valid capsule
     *         in the current phase of the project
     * @throws ArrayIndexOutOfBoundsException if the project number is invalid
     */
    public String getCapsulesInfo(int projectNumber) {
        return projects[projectNumber].getCurrentPhase(0).getCapsulesInfo();
    }

    /**
     * 
     * Checks if a given text contains at least one hashtag word.
     * 
     * @param text the text to check for hashtags
     * 
     * @return true if the text contains at least one hashtag word, false otherwise
     */
    public boolean hasHashWords(String text) {
        Pattern p = Pattern.compile("#([^#]+)#"); // Modify the regex to match the pattern between '#' characters
        Matcher m = p.matcher(text);

        return m.find();
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
        String msg = "FALTAL: there was an error publishing the capsule. Check the ID or if the capsule was alredy published";
        String URL = projects[projectNumber].publishCapsule(ID);
        if (!URL.equalsIgnoreCase("")) {
            msg = "\nCapsule was approved succesfully, it's HTML FILE its in the capsulesHTML folder \n"
                    + "URL: " + URL;
        }
        return msg;
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
        return pos = (pos == -1) ? getProjects().length
                : pos;
    }

    /**
     * Returns the amount of capsules of a specific type in a project.
     *
     * @param projectIndex The index of the project.
     * @param capsuleType  The type of capsule to count.
     * @return The amount of capsules of the specified type in the project.
     */
    public int amountCapsulesByType(int projectIndex, String capsuleType) {
        return projects[projectIndex].amountCapsulesByType(capsuleType);
    }

    /**
     * Retrieves the list of learnings in a specific phase of a project.
     *
     * @param projectIndex The index of the project.
     * @param phaseIndex   The index of the phase.
     * @return The list of learnings in the specified phase of the project, or a
     *         message indicating that there are no learnings.
     */
    public String learningsInAPhase(int projectIndex, int phaseIndex) {
        String listOfLearnings = projects[projectIndex].learningsInAPhase(phaseIndex);
        if (listOfLearnings.equalsIgnoreCase("")) {
            listOfLearnings = "There is no learnings in this phase.";
        }
        return listOfLearnings;
    }

    /**
     * Retrieves the name of the project with the most amount of capsules
     * registered.
     *
     * @return The name of the project with the most amount of capsules registered.
     */
    public String projectWithTheMostAmountOfCapsules() {
        int temp = projects[0].getAmountOfCapsulesRegistered();
        int position = 0;
        for (int i = 0; i < getFirtsValidPosition(); i++) {
            if (projects[i].getAmountOfCapsulesRegistered() > temp) {
                temp = projects[i].getAmountOfCapsulesRegistered();
                position = i;
            }
        }
        return projects[position].getProjectName();
    }

    /**
     * Retrieves the list of capsules written by an employee in all projects.
     *
     * @param employeeName The name of the employee.
     * @return The list of capsules written by the specified employee in all
     *         projects.
     */
    public String capsulesOfAnEmployee(String employeeName) {
        String listOfCapsules = "";
        for (int i = 0; i < getFirtsValidPosition(); i++) {
            listOfCapsules += "\n" + projects[i].capsulesOfAnEmployee(employeeName);
        }
        return listOfCapsules;
    }

    /**
     * Retrieves the list of learnings in all projects that match a given hashtag
     * query.
     *
     * @param query The hashtag query to search for.
     * @return The list of learnings in all projects that match the given hashtag
     *         query.
     */
    public String InformLearningsOfCapsulesByHastag(String query) {
        String searchResult = "";
        for (int i = 0; i < getFirtsValidPosition(); i++) {
            searchResult += "\n" + projects[i].InformLearningsOfCapsulesByHastag(query);
        }
        return searchResult;
    }
}
