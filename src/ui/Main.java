package ui;

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

        while (option != 12) {
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

                    + "\n---Capulas y proyectos---\n" +

                    "\n1. Crear un proyecto " +
                    "\n2. Culminar la etapa de un proyecto" +
                    "\n3. Regitrar capsula " +
                    "\n4. Aprobar capsula " +
                    "\n5. Publicar capsula\n " +

                    "\n---Informacion---\n" +

                    "\n7. Informar al usuario cuantas de las cápsulas registradas hay por cada tipo de cápsula (técnico, gestión, dominio y experiencias) "
                    +
                    "\n8. Informar al usuario un listado de lecciones aprendidas correspondientes a las cápsulas registradas en los proyectos para una etapa en particular "
                    +
                    "\n9. Informar al usuario el nombre del proyecto con más cápsulas registradas " +
                    "\n10. Informar al usuario si un colaborador (por el nombre) ha registrado cápsulas en algún proyecto. "
                    +
                    "\n11. Informar al usuario las situaciones y lecciones aprendidas de las cápsulas aprobadas y publicadas, de acuerdo a una cadena de búsqueda dada por él mismo.  Esta cadena de búsqueda deberá ser encontrada en los hashtag.  "
                    + "\n\n12. SALIR DEL PROGRAMA");
            System.out.print("\n>> ");
            option = validateIntegerInput();
            if (option < 1 || option > 12) {
                System.out.println("Please select a valid option \n");
            }
        } while (option < 1 || option > 12);
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
        System.out.println("\n\n\n\n\n\n\n\n\n\tCREATING A PROJECT\n");

        System.out.print("Project's name: ");
        input.nextLine();

        String projectName = input.nextLine(); // This is not storing all the name.
        System.out.print("Client's name: ");

        String clientName = input.nextLine();
        System.out.print("When is the project supposed to start(dd-mm-yyyy): ");
        String startPlannedDateString = input.nextLine();

        System.out.print("When is the project supposed to end(dd-mm-yyyy): ");
        String endingPlannedDateString = input.nextLine();

        System.out.print("Budget for the project: ");
        double budget = input.nextDouble();

        driver.addProject(projectName, clientName, startPlannedDateString, endingPlannedDateString,
                budget);

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

    public void showProjects() {
        for (int i = 0; i < driver.getFirtsValidPosition(); i++) {
            System.out.println(driver.getprojects()[i]);
        }
    }

}