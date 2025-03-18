package view;

import controller.SistemaEmergencias;
import utils.NivelGravedad;
import utils.TipoEmergencia;
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
            System.out.println("\n--- Sistema de Emergencias ---");
            System.out.println("1. Registrar emergencia");
            System.out.println("2. Mostrar emergencias activas");
            System.out.println("3. Mostrar recursos disponibles");
            System.out.println("4. Reasignar recursos");
            System.out.println("5. Mostrar estadísticas del día");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.println("⚠️ Entrada inválida. Ingrese un número.");
                scanner.next();
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
                    System.out.println("👋 Saliendo del sistema...");
                    break;
                default:
                    System.out.println("⚠️ Opción no válida, intente nuevamente.");
            }
        } while (opcion != 6);
    }

    private void registrarEmergencia() {
        String tipo, ubicacion, gravedad;
        int tiempo = -1;

        do {
            System.out.print("Ingrese el tipo de emergencia " + TipoEmergencia.getListaTipos() + ": ");
            tipo = scanner.nextLine().toUpperCase();
            if (TipoEmergencia.fromString(tipo) == null) {
                System.out.println("⚠️ Tipo de emergencia inválido. Intente de nuevo.");
            }
        } while (TipoEmergencia.fromString(tipo) == null);

        System.out.print("Ingrese la ubicación: ");
        ubicacion = scanner.nextLine();

        NivelGravedad nivelGravedad;
        do {
            System.out.print("Ingrese el nivel de gravedad (BAJO, MEDIO, ALTO): ");
            gravedad = scanner.nextLine().toUpperCase();
            nivelGravedad = NivelGravedad.fromString(gravedad);
            if (nivelGravedad == null) {
                System.out.println("⚠️ Nivel de gravedad inválido. Intente de nuevo.");
            }
        } while (nivelGravedad == null);

        do {
            System.out.print("Ingrese el tiempo estimado de respuesta en minutos: ");
            while (!scanner.hasNextInt()) {
                System.out.println("⚠️ Entrada inválida. Ingrese un número válido.");
                scanner.next();
            }
            tiempo = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer
            if (tiempo <= 0) {
                System.out.println("⚠️ El tiempo debe ser mayor a 0.");
            }
        } while (tiempo <= 0);

        sistemaEmergencias.registrarEmergencia(tipo, ubicacion, nivelGravedad, tiempo);
        System.out.println("✅ Emergencia registrada correctamente.");
    }

    private void mostrarEmergencias() {
        System.out.println("\n📋 Listado de emergencias activas:");
        sistemaEmergencias.listarEmergencias();
    }

    private void mostrarRecursos() {
        System.out.println("\n🚑 Recursos disponibles:");
        sistemaEmergencias.mostrarRecursos();
    }

    private void reasignarRecursos() {
        System.out.println("\n🔄 Intentando reasignar recursos...");
        if (sistemaEmergencias.reasignarRecursos()) {
            System.out.println("✅ Recursos reasignados correctamente.");
        } else {
            System.out.println("⚠️ No hay recursos disponibles para reasignar.");
        }
    }

    private void mostrarEstadisticas() {
        System.out.println("\n📊 Estadísticas del día:");
        sistemaEmergencias.mostrarEstadisticas();
    }
}
