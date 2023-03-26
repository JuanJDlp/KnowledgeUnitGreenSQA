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
                    "\n5. Pubolish capsule\n " +

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

    public void createProject() {
        System.out.println("\n\n\tCREATING A PROJECT\n");

        System.out.print("Project's name: ");
        input.nextLine();
        String projectName = input.nextLine();

        System.out.print("Client's name: ");
        String clientName = input.nextLine();

        System.out.print("Budget for the project: ");
        double budget = input.nextDouble();

        driver.addProject(projectName, clientName, budget);

        System.out.println("\nInsert how long in MONTHS each phase will take. \n");

        Calendar actualDate = Calendar.getInstance();

        for (int i = 0; i < 5; i++) {
            System.out.print("Duration in months of '"
                    + driver.getprojects()[driver.getFirtsValidPosition() - 1].getPhase()[i].getPhaseType() + "' : ");
            int months = input.nextInt();

            driver.initProjectPhases(actualDate, i, months);
        }

        driver.getprojects()[driver.getFirtsValidPosition() - 1].getPhase()[5].setendingPlannedDate(actualDate);
        driver.getprojects()[driver.getFirtsValidPosition() - 1].setEndingDate(actualDate);

        System.out.print("How many managers are you going to introduce?: ");
        int amountOfManagers = validateIntegerInput();
        input.nextLine();

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
        System.out.println("\t Which from wich project do you want to end their phase? : \n");

        for (int i = 0; i < driver.getFirtsValidPosition(); i++) {
            System.out.println((i + 1) + ": " + driver.getprojects()[i].getProjectName());
        }

        int projectNumber = validateIntegerInput();

        System.out.println("\nCURRENT STAGE -> " +
                driver.getprojects()[projectNumber - 1].getPhase()[driver.getprojects()[projectNumber - 1]
                        .getCurrentPhase()]);

        System.out.println("\nAre you sure you want to end this phase? Y/n ");
        input.nextLine();
        String confirmation = input.nextLine();
        if (confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("")) {
            driver.endPhase(projectNumber);
        } else {
            System.out.println("\nCancelling task..\n");
        }

    }

    public void showProjects() {
        for (int i = 0; i < driver.getFirtsValidPosition(); i++) {
            System.out.println(driver.getprojects()[i]);
        }
    }

}