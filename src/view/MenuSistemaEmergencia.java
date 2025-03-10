package view;

import controller.SistemaEmergencias;
import utils.NivelGravedad;
import java.util.Scanner;

public class MenuSistemaEmergencia {
    private Scanner scanner;
    private SistemaEmergencias controller;

    public MenuSistemaEmergencia() {
        this.scanner = new Scanner(System.in);
        this.controller = SistemaEmergencias.getInstance();
        }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n--- Sistema de Emergencias ---");
            System.out.println("1. Registrar emergencia");
            System.out.println("2. Mostrar emergencias activas");
            System.out.println("3. Mostrar recursos disponibles");
            System.out.println("4. Reasignar recursos");
            System.out.println("5. Mostrar estadísticas del día");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.println(" Entrada no válida. Ingrese un número del 1 al 6.");
                scanner.next(); // Limpiar entrada incorrecta
            }

            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    registrarEmergencia();
                    break;
                case 2:
                    mostrarEmergencias();
                    break;
                case 3:
                    mostrarRecursos();
                    break;
                case 4:
                    reasignarRecursos();
                    break;
                case 5:
                    mostrarEstadisticas();
                    break;
                case 6:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida, intente nuevamente.");
            }
        } while (opcion != 6);
    }

    private void registrarEmergencia() {
        System.out.print("Ingrese el tipo de emergencia (ROBO, INCENDIO, ACCIDENTE_VEHICULAR): ");
        String tipo = scanner.nextLine().toUpperCase();
        System.out.print("Ingrese la ubicación: ");
        String ubicacion = scanner.nextLine();

        System.out.print("Ingrese el nivel de gravedad (BAJO, MEDIO, ALTO): ");
        String gravedad = scanner.nextLine().toUpperCase();

        while (!gravedad.equals("BAJO") && !gravedad.equals("MEDIO") && !gravedad.equals("ALTO")) {
            System.out.print(" Nivel de gravedad inválido. Ingrese BAJO, MEDIO o ALTO: ");
            gravedad = scanner.nextLine().toUpperCase();
        }

        System.out.print("Ingrese el tiempo estimado de respuesta en minutos: ");
        while (!scanner.hasNextInt()) {
            System.out.println(" Entrada no válida. Ingrese un número entero para el tiempo estimado de respuesta.");
            scanner.next(); // Limpiar entrada incorrecta
        }
        int tiempo = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        controller.registrarEmergencia(tipo, ubicacion, NivelGravedad.valueOf(gravedad), tiempo);
        System.out.println(" Emergencia registrada correctamente.");
    }

    private void mostrarEmergencias() {
        System.out.println("\nListado de emergencias activas:");
        controller.listarEmergencias();
    }

    private void mostrarRecursos() {
        System.out.println("\nRecursos disponibles:");
        controller.mostrarRecursos();
    }

    private void reasignarRecursos() {
        System.out.println("\nIntentando reasignar recursos...");
        if (controller.reasignarRecursos()) {
            System.out.println(" Recursos reasignados correctamente.");
        } else {
            System.out.println(" No hay recursos disponibles para reasignar.");
        }
    }

    private void mostrarEstadisticas() {
        System.out.println("\nEstadísticas del día:");
        controller.mostrarEstadisticas();
    }
}
