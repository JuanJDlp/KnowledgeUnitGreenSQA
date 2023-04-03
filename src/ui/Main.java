package ui;

import java.util.Calendar;
import java.util.Scanner;

import model.KnowledgeSystem;

class Main {
    private Scanner input;
    KnowledgeSystem driver;

    // Constructor
    public Main() {
        input = new Scanner(System.in);
        driver = new KnowledgeSystem();
    }

    public static void main(String[] args) {
        Main view = new Main();
        int option = view.menu();

        while (option != 11) {
            view.executeOption(option);
            option = view.menu();
        }
        System.out.println("\nHAVE A GREAT DAY!!");
        view.input.close();
    }

    /**
     * This option is the one encharged of running the option inserted by the user
     * in the Menu
     * 
     * @param option -> It will be a number between 1 and 6 representing what the
     *               user want's to do with the software.
     * @return The program return nothing since it will only execute an option.
     */
    public void executeOption(int option) {
        switch (option) {

            case 1:
                if (driver.getFirtsValidPosition() == driver.getprojects().length) {
                    System.out.println("You can't craete more projects, the projects are full.");
                } else {
                    createProject();
                }
                break;

            case 2:
                if (driver.getprojects()[0] != null) {
                    endPhase();
                } else {
                    System.out.println("\nThere is no current projects.\n");
                }
                break;

            case 3:
                if (driver.getprojects()[0] != null) {
                    addCapsule();
                } else {
                    System.out.println("\nThere is no current projects.\n");
                }
                break;

            case 4:
                if (driver.getprojects()[0] != null) {
                    approveACapsule();
                } else {
                    System.out.println("\nThere is no current projects.\n");
                }
                break;

            case 5:
                if (driver.getprojects()[0] != null) {
                    publishCapsule();
                } else {
                    System.out.println("\nThere is no current projects.\n");
                }
                break;
            default:
                System.out.println("Option not recognized.");
        }

    }

    /**
     * 
     * Shows a menu with options related to project and capsule management, and
     * information retrieval.
     * 
     * @return the selected option by the user that will be later used in the method
     *         executeOption
     */
    public int menu() {
        int option = 0;
        do {
            System.out.println("----Welcome----"

                    + "\n---Capsules and projects---\n" +

                    "\n1. Create a project " +
                    "\n2. End a project phase" +
                    "\n3. Register a capsule " +
                    "\n4. Aprove a capsule " +
                    "\n5. Publish capsule\n " +

                    "\n---Information---\n" +

                    "\n6. List capsules of a type " +
                    "\n7. Lessons per stage " +
                    "\n8. Project with the most amount of capsules " +
                    "\n9. Capsules created by a colaborator " +
                    "\n10. Search lessons  "
                    +
                    "\n\n11. SALIR DEL PROGRAMA");
            System.out.print("\n>> ");
            option = validateIntegerInput();
            if (option < 1 || option > 11) {
                System.out.println("Please select a valid option \n");
            }
        } while (option < 1 || option > 11);
        return option;
    }

    /**
     * 
     * Validates the input from the user to make sure it is an integer.
     * If what the user is not an integer input it will display a message and the
     * option will be -1
     * 
     * @return an integer input from the user
     */
    public int validateIntegerInput() {
        int option = 0;
        if (input.hasNextInt()) {
            option = input.nextInt();
        } else {
            input.nextLine();// clean the Scanner
            option = -1;
            System.out.println("Insert a numeric value.");
        }
        return option;
    }

    /**
     * 
     * Validates the input from the user to make sure it is a double.
     * If what the user is not a daouble input it will display a message and the
     * option will be -1
     * 
     * @return a double input from the user
     */
    public double validatedoubleInput() {
        double option = 0;
        if (input.hasNextDouble()) {
            option = input.nextDouble();
        } else {
            input.nextLine();// clean the Scanner
            option = -1;
            System.out.println("Insert a numeric value.");
        }
        return option;
    }

    /**
     * 
     * This method allows the user to create a project. The user inputs
     * - the name of the project,
     * -the client's name,
     * -the budget,
     * -the duration of each phase in months,
     * -the managers in charge of the project.
     * 
     * The method validates the inputs and adds the project to the system. calling
     * the driver.
     */
    public void createProject() {
        int projectNumber = driver.getFirtsValidPosition();
        System.out.println("\n\n\tCREATING A PROJECT\n");

        System.out.print("Project's name: ");
        input.nextLine(); // Grab the enter
        String projectName = input.nextLine();

        while (driver.findProject(projectName) != -1) {
            System.out.println("\nProject name alredy exists, please pick another name" + "\n>> ");
            projectName = input.nextLine();
        }

        System.out.print("Client's name: ");
        String clientName = input.nextLine();

        System.out.print("Budget for the project: ");
        double budget = -1;
        do {
            System.out.println("Please insert a positive budget");
            budget = validatedoubleInput();
        } while (budget < 0);

        driver.addProject(projectName, clientName, budget);

        System.out.println("\nInsert how long in MONTHS each phase will take. \n");

        Calendar actualDate = Calendar.getInstance();
        int months = -1;
        for (int i = 0; i < 6; i++) {
            do {
                System.out.print("Duration in months of '"
                        + driver.getprojects()[projectNumber].getPhase()[i].getPhaseType()
                        + "' : ");
                months = validateIntegerInput();
            } while (months < 0);
            driver.initProjectPhases(actualDate, i, months, projectNumber);

        }

        driver.getprojects()[projectNumber].getPhase()[5].setendingPlannedDate(actualDate);
        driver.getprojects()[projectNumber].setEndingDate(actualDate);
        int amountOfManagers = -1;
        do {
            System.out.print("\nHow many managers are you going to introduce?: ");
            amountOfManagers = validateIntegerInput();
        } while (amountOfManagers < 0);

        input.nextLine(); // Grab the enter

        for (int i = 0; i < amountOfManagers; i++) {
            System.out.println("Name of the manager: ");
            String managerName = input.nextLine();
            System.out.println("Manager's phone: ");
            String managersPhone = input.nextLine();
            driver.addManager(managerName, managersPhone, projectNumber);
            System.out.println("\n");
        }

        System.out.println("\n\tThe project was created succesfully \n");

        showProjects();
        System.out.println("\n");
    }

    /**
     * 
     * Allows to end the current phase of a project, after displaying the current
     * phase information and
     * asking for confirmation.
     * <p>
     * It asks the user which project they want to end their phase by displaying a
     * list of all active projects.
     * 
     * If the selected project has no active phases, it informs the user that the
     * project has already ended.
     * 
     * If the selected project has an active phase, it displays the current phase
     * information and asks the user
     * for confirmation to end it.
     * </p>
     */
    public void endPhase() {
        int projectNumber = -2;

        System.out.println("\t Wich project do you want to end their phase? :");

        do {
            System.out.println("\n");
            // Printing all the projects name's
            showProjectStatus();
            System.out.println("\n-1 : to exit the menu");
            // Picking a project
            projectNumber = validateIntegerInput();
        } while ((projectNumber <= 0
                || projectNumber > driver.getFirtsValidPosition()) && projectNumber != -1);

        if (projectNumber >= 0) {
            if (driver.getprojects()[projectNumber - 1].getCurrentPhase(0) == null) {
                System.out.println(
                        "\nYou can't end the phase of this project. This project has ended");
                driver.endPhase(projectNumber - 1);
                System.out.println(driver.getprojects()[projectNumber - 1]
                        .getPhase()[driver.getprojects()[projectNumber - 1]
                                .getPhase().length - 1]
                        .toString());
            } else {

                // Displaying the stage information
                System.out.println(
                        "-------------" + "\nPROJECT -> " + driver.getprojects()[projectNumber - 1].getProjectName());
                System.out.println("\nCURRENT STAGE -> " +
                        driver.getprojects()[projectNumber - 1].getCurrentPhase(0));
                // Confirmation
                System.out.println("\nAre you sure you want to end this phase? Y/n ");
                input.nextLine();// Grab the enter
                String confirmation = input.nextLine();

                if (confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("")) {
                    driver.endPhase(projectNumber - 1);
                    System.out.println("\nPhase endending complete");
                } else {
                    System.out.println("\nCancelling task..\n");
                }
            }

        }

    }

    /**
     * 
     * Allows the user to write a new capsule for a project.
     * The method displays the list of active projects, then prompts the user to
     * select a project.
     * 
     * After the user selects a project, the method displays a list of employees
     * working on that project,
     * 
     * then prompts the user to select an employee.
     * The user is then asked to write a short description of the capsule and to
     * choose a capsule type (TECHNIQUE, MANAGEMENT, DOMAIN or EXPERIENCES).
     * 
     * Finally, the user is asked to write their learnings, and the method adds
     * the new capsule to the project.
     * 
     * If the project has already ended, it is not possible to add a new capsule.
     */
    public void addCapsule() {
        String capsuleType = "";
        int Typeoption = -1;
        int employee = -1;

        System.out.println("Which project would you like to write a capsule to?: \n");
        showProjectStatus();
        int projectNumber = validateIntegerInput() - 1;

        if (driver.getprojects()[projectNumber].getCurrentPhase(0) != null) {

            System.out.println("\nChoose an employee profile.\n");
            for (int i = 0; i < driver.employeesInAProject(projectNumber).length; i++) {
                System.out.println((i + 1) + ": " + driver.employeesInAProject(projectNumber)[i] + "\n");
            }
            do {
                employee = validateIntegerInput() - 1;
            } while (employee < 0 || employee > driver.employeesInAProject(projectNumber).length);

            System.out.println("\nWrite a short description of the capsule: ");
            input.nextLine();
            String description = input.nextLine();

            System.out.println("\nPlease select a type: " +
                    "\n1.-TECHNIQUE " +
                    "\n2.-MANAGMENT" +
                    "\n3.-DOMAIN" +
                    "\n4.-EXPERIENCES");
            do {
                Typeoption = validateIntegerInput();
            } while (Typeoption < 1 || Typeoption > 4);
            switch (Typeoption) {
                case 1:
                    capsuleType = "TECHNIQUE";
                    break;
                case 2:
                    capsuleType = "MANAGMENT";
                    break;
                case 3:
                    capsuleType = "DOMAIN";
                    break;
                case 4:
                    capsuleType = "EXPERIENCES";
                    break;
            }
            System.out.println("\nWrite your learnings: ");
            input.nextLine();
            String learnings = input.nextLine();

            System.out.println(driver.addCapsule(projectNumber, capsuleType, description, employee, learnings));
        } else {
            System.out.println("\nThe project has enden, it is not possible to add a capsule.\n");
        }

    }

    /**
     * 
     * Allows a the user to approve a specific capsule from a project that is
     * currently in review.
     * Shows the list of capsules for each phase of the project and prompts the
     * user to input the ID of the
     * capsule they want to approve.
     */
    public void approveACapsule() {
        int ProjectNumber = -1;
        showProjectStatus();
        ProjectNumber = input.nextInt() - 1;
        for (int i = 0; i < driver.getprojects()[ProjectNumber].getPhase().length; i++) {

            System.out.println("\n" + driver.getprojects()[ProjectNumber].getPhase()[i].getPhaseType() + "\n");

            for (int j = 0; j < driver.getprojects()[ProjectNumber].getPhase()[i].getFirtsValidCapsule(); j++) {
                System.out.println(driver.getprojects()[ProjectNumber].getPhase()[i].getCapsules()[j]);
            }
        }
        System.out.println("\nInsert the ID of the capsule you want to APPROVE:  ");
        input.nextLine();
        String ID = input.nextLine();
        System.out.println(driver.approveCapsule(ID, ProjectNumber));
    }

    /**
     * It displays all the projects ifnromation
     */
    public void showProjects() {

        for (int i = 0; i < driver.getFirtsValidPosition(); i++) {
            System.out.println((i + 1) + ": " + driver.getprojects()[i]);
        }
    }

    /**
     * 
     * Displays a list of projects and prompts the user to select a project to
     * publish a capsule to.
     * 
     * Displays a list of approved capsules in the selected project and prompts the
     * user to select a capsule to publish.
     * 
     * Publishes the selected capsule by generating an HTML file and returns the URL
     * of the published capsule.
     * 
     * If there was an error publishing the capsule, an error message is displayed.
     */
    public void publishCapsule() {
        int ProjectNumber = -1;
        showProjectStatus();
        ProjectNumber = input.nextInt() - 1;

        for (int i = 0; i < driver.getprojects()[ProjectNumber].getPhase().length; i++) {

            System.out.println("\n" + driver.getprojects()[ProjectNumber].getPhase()[i].getPhaseType() + "\n");

            for (int j = 0; j < driver.getprojects()[ProjectNumber].getPhase()[i].getFirtsValidCapsule(); j++) {
                if (driver.getprojects()[ProjectNumber].getPhase()[i].getCapsules()[j].getApproved()) {
                    System.out.println(driver.getprojects()[ProjectNumber].getPhase()[i].getCapsules()[j]);
                }
            }
        }
        System.out.println("\nInsert the ID of the capsule you want to PUBLISH: ");
        input.nextLine();
        String ID = input.nextLine();
        if (!driver.publishCapsule(ID, ProjectNumber).equalsIgnoreCase("")) {
            System.out.println("\nCapsule was approved succesfully, it's HTML FILE its in the capsulesHTML folder \n"
                    + "URL: " + driver.publishCapsule(ID, ProjectNumber));
        } else {
            System.out.println("FALTAL: there was an error publishing the capsule");
        }

    }

    /**
     * 
     * Displays the current status of all projects by iterating over the
     * projects array and printing their name and current phase,
     * If the project has endend, the current phase will be changed for a message
     * indicating that the project has ended.
     */
    public void showProjectStatus() {
        for (int i = 0; i < driver.getFirtsValidPosition(); i++) {
            if (driver.getprojects()[i].getCurrentPhase(0) != null) {
                System.out.println((i + 1) + ": " + driver.getprojects()[i].getProjectName()
                        + " | " + driver.getprojects()[i].getCurrentPhase(0).getPhaseType());
            } else {
                System.out.println((i + 1) + ": " + driver.getprojects()[i].getProjectName()
                        + " | " + "THE PROJECT HAS ENDEND");
            }
        }
    }

}