package ui;

import java.util.Calendar;
import java.util.Scanner;

import model.KnowledgeSystem;

class Main {
    private Scanner input;
    KnowledgeSystem driver;

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
                createProject();
                break;

            case 2:
                if (driver.getFirtsValidPosition() >= 1) {
                    endPhase();
                } else {
                    System.out.println("\nThere is no current projects.\n");
                }
                break;

            case 3:
                if (driver.getFirtsValidPosition() >= 1) {
                    addCapsule();
                } else {
                    System.out.println("\nThere is no current projects.\n");
                }
                break;

            case 4:
                break;

            case 5:
                break;
            default:
                System.out.println("Option not recognized.");
        }

    }

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

    public void createProject() {
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
                        + driver.getprojects()[driver.getFirtsValidPosition() - 1].getPhase()[i].getPhaseType()
                        + "' : ");
                months = validateIntegerInput();
            } while (months < 0);
            driver.initProjectPhases(actualDate, i, months);

        }

        driver.getprojects()[driver.getFirtsValidPosition() - 1].getPhase()[5].setendingPlannedDate(actualDate);
        driver.getprojects()[driver.getFirtsValidPosition() - 1].setEndingDate(actualDate);
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
            driver.addManager(managerName, managersPhone);
            System.out.println("\n");
        }

        System.out.println("\n\tThe project was crated succesfully \n");

        showProjects();
        System.out.println("\n");
    }

    public void endPhase() {
        int projectNumber = -2;
        System.out.println("\t Wich project do you want to end their phase? :");

        do {
            System.out.println("\n");
            // Printing all the projects name's
            for (int i = 0; i < driver.getFirtsValidPosition(); i++) {
                if (driver.getprojects()[i].getCurrentPhase() != -5) {
                    System.out.println((i + 1) + ": " + driver.getprojects()[i].getProjectName()
                            + " | " + driver.getprojects()[i].getPhase()[driver.getprojects()[i]
                                    .getCurrentPhase()].getPhaseType());
                } else {
                    System.out.println((i + 1) + ": " + driver.getprojects()[i].getProjectName()
                            + " | " + "THE PROJECT HAS ENDEND");
                }
            }
            System.out.println("\n-1 : to exit the menu");
            // Picking a project
            projectNumber = validateIntegerInput();
        } while ((projectNumber <= 0
                || projectNumber > driver.getFirtsValidPosition()) && projectNumber != -1);

        if (projectNumber >= 0) {
            if (driver.getprojects()[projectNumber - 1].getCurrentPhase() == -5) {
                System.out.println(
                        "\nYou can't end the phase of this project. This project has ended");
                driver.endPhase(projectNumber - 1);
                System.out.println(driver.getprojects()[projectNumber - 1]
                        .getPhase()[driver.getprojects()[projectNumber - 1]
                                .getPhase().length - 1]
                        .finishedPhase());
            } else {

                // Displaying the stage information
                System.out.println(
                        "-------------" + "\nPROJECT -> " + driver.getprojects()[projectNumber - 1].getProjectName());
                System.out.println("\nCURRENT STAGE -> " +
                        driver.getprojects()[projectNumber - 1].getPhase()[driver.getprojects()[projectNumber - 1]
                                .getCurrentPhase()]);
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

    public void addCapsule() {
        String capsuleType = "";
        int Typeoption = -1;
        int employee = -1;
        System.out.println("Choose an employee profile.\n");
        for (int i = 0; i < driver.getEmployees().length; i++) {
            System.out.println((i + 1) + ": " + driver.getEmployees()[i] + "\n");
        }
        do {
            employee = validateIntegerInput() - 1;
        } while (employee < 0 || employee > driver.getEmployees().length);

        System.out.println("Which project would you like to write a capsule to?: \n");
        showProjects();
        int projectNumber = validateIntegerInput() - 1;

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

    }

    public void showProjects() {
        for (int i = 0; i < driver.getFirtsValidPosition(); i++) {
            System.out.println((i + 1) + ": " + driver.getprojects()[i]);
        }
    }

}