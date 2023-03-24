package ui;

import java.util.Scanner;

class Main {
    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int option = menu();

        while (option != 12) {
            option = menu();
        }
        System.out.println("\nHAVE A GREAT DAY!!");
        input.close();
    }

    public static int menu() {
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
            System.out.print("\n>>");
            option = validateIntegerInput();
            if (option < 1 || option > 12) {
                System.out.println("Please select a valid option \n");
            }
        } while (option < 1 || option > 12);
        return option;
    }

    public static int validateIntegerInput() {
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

}