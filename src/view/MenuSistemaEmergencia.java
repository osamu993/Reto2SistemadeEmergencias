package view;

import controller.SistemaEmergencias;
import model.Emergencia;
import utils.NivelGravedad;
import utils.TipoEmergencia;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MenuSistemaEmergencia {
    private Scanner scanner;
    private SistemaEmergencias sistemaEmergencias;

    public MenuSistemaEmergencia(SistemaEmergencias sistemaEmergencias) {
        this.scanner = new Scanner(System.in);
        this.sistemaEmergencias = sistemaEmergencias;
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n\n--- Sistema de Emergencias ---\n");
            System.out.println("1. Registrar emergencia");
            System.out.println("2. Gestionar emergencias");
            System.out.println("3. Mostrar recursos disponibles");
            System.out.println("4. Mostrar estadísticas del día");
            System.out.println("5. Salir");
            System.out.print("\nSeleccione una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Ingrese un número.");
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    registrarEmergencia();
                    break;
                case 2:
                    mostrarSubmenuGestionarEmergencias();
                    break;
                case 3:
                    sistemaEmergencias.mostrarRecursosDisponibles();
                    break;
                case 4:
                    mostrarEstadisticas();
                    break;
                case 5:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida, intente nuevamente.");
            }
        } while (opcion != 5);
    }

    private void mostrarSubmenuGestionarEmergencias() {
        int opcionSubmenu;
        do {
            System.out.println("\n\n--- Gestionar Emergencias ---");
            System.out.println("1. Mostrar emergencias activas");
            System.out.println("2. Reasignar recursos");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            opcionSubmenu = scanner.nextInt();
            scanner.nextLine();

            switch (opcionSubmenu) {
                case 1:
                    mostrarEmergencias();
                    break;
                case 2:
                    reasignarRecursos();
                    break;
                case 3:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcionSubmenu != 3);
    }

    private void registrarEmergencia() {
        String tipo, ubicacion = "", gravedad;
        boolean entradaUbicacionValida = false;
        List<String> zonasValidas = Arrays.asList("CENTRO", "NORTE", "SUR", "ESTE", "OESTE");
        int tiempo = -1;

        do {
            System.out.print("Ingrese el tipo de emergencia " + TipoEmergencia.getListaTipos() + ": ");
            tipo = scanner.nextLine().toUpperCase();
            TipoEmergencia.fromString(tipo);

        } while (TipoEmergencia.fromString(tipo) == null);

        System.out.println("\nZonas disponibles: Centro, Norte, Sur, Este, Oeste");

        while (!entradaUbicacionValida) {
            System.out.print("Ingrese la ubicación de la emergencia: ");
            ubicacion = scanner.nextLine().trim().toUpperCase();

            if (zonasValidas.contains(ubicacion)) {
                entradaUbicacionValida = true;
            } else {
                System.out.println("\nError: Ubicación no válida. Debe ser una de las siguientes opciones:");
                System.out.println("Centro, Norte, Sur, Este, Oeste");
            }
        }

        NivelGravedad nivelGravedad;
        do {
            System.out.print("\nIngrese el nivel de gravedad (BAJO, MEDIO, ALTO): ");
            gravedad = scanner.nextLine().toUpperCase();
            nivelGravedad = NivelGravedad.fromString(gravedad);
            if (nivelGravedad == null) {
                System.out.println("Nivel de gravedad inválido. Intente de nuevo.");
            }
        } while (nivelGravedad == null);

        do {
            System.out.print("\nIngrese el tiempo estimado de respuesta en minutos: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Ingrese un número válido.");
                scanner.next();
            }
            tiempo = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            if (tiempo <= 0) {
                System.out.println("El tiempo debe ser mayor a 0.");
            }
        } while (tiempo <= 0);

        sistemaEmergencias.registrarEmergencia(tipo, ubicacion, nivelGravedad, tiempo);
        System.out.println("\nEmergencia registrada correctamente.");
    }

    private void mostrarEmergencias() {
        System.out.println("\nListado de emergencias activas:");
        sistemaEmergencias.listarEmergencias();
    }

    private void mostrarRecursos() {
        System.out.println("\nRecursos disponibles:");
        sistemaEmergencias.mostrarRecursos();
    }

    private void reasignarRecursos() {
        System.out.println("\nReasignación de Recursos:");

        List<Emergencia> emergencias = sistemaEmergencias.getListaEmergencias()
                .stream()
                .filter(e -> !e.isAtendida())
                .toList();

        if (emergencias.isEmpty()) {
            System.out.println("\nNo hay emergencias activas para reasignar recursos.");
            return;
        }

        for (int i = 0; i < emergencias.size(); i++) {
            Emergencia e = emergencias.get(i);
            System.out.println((i + 1) + ". " + e.getTipo() + " en " + e.getUbicacion() + " (Gravedad: "
                    + e.getNivelGravedad() + ")");
        }

        System.out.print("Seleccione el número de la emergencia para reasignar recursos: ");
        int seleccion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        if (seleccion < 1 || seleccion > emergencias.size()) {
            System.out.println("Selección inválida.");
            return;
        }

        Emergencia seleccionada = emergencias.get(seleccion - 1);
        sistemaEmergencias.reasignarRecursosEmergencia(seleccionada);
    }

    private void mostrarEmergenciasAtendidas() {
        System.out.println("\nEmergencias Atendidas:");
        boolean hayAtendidas = false;
        for (Emergencia e : sistemaEmergencias.getListaEmergencias()) {
            if (e.isAtendida()) {
                System.out.println("\n" + e.getTipo() + " - Zona: " + e.getUbicacion() +
                        " - Gravedad: " + e.getNivelGravedad());
                hayAtendidas = true;
            }
        }
        if (!hayAtendidas) {
            System.out.println("No hay emergencias atendidas por el momento.");
        }
    }

    private void mostrarEstadisticas() {
        System.out.println("\nEstadísticas del día:");
        sistemaEmergencias.mostrarEstadisticas();
    }
}
