package view;

import controller.SistemaEmergencias;
import model.Emergencia;
import model.strategy.StrategyPrioridadGravedad;
import model.strategy.StrategyPrioridadCercania;
import utils.NivelGravedad;
import utils.TipoEmergencia;
import utils.SystemReport;
import utils.SystemRegistration;

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
            System.out.println("5. Configurar estrategia de asignación");
            System.out.println("6. Ver reportes históricos");
            System.out.println("7. Cerrar jornada");
            System.out.println("8. Salir");
            System.out.print("\n\nSeleccione una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.println("\nEntrada inválida. Ingrese un número.");
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine();

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
                    configurarEstrategiaAsignacion();
                    break;
                case 6:
                    mostrarReportesHistoricos();
                    break;
                case 7:
                    cerrarJornada();
                    break;
                case 8:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida, intente nuevamente.");
            }
        } while (opcion != 8);
    }

    private void mostrarSubmenuGestionarEmergencias() {
        int opcionSubmenu;
        do {
            System.out.println("\n\n--- Gestionar Emergencias ---\n");
            System.out.println("1. Mostrar emergencias activas");
            System.out.println("2. Mostrar emergencias atendidas y cerradas");
            System.out.println("3. Reasignar recursos");
            System.out.println("4. Finalizar emergencia");
            System.out.println("5. Volver al menú principal");
            System.out.print("\nSeleccione una opción: ");
            opcionSubmenu = scanner.nextInt();
            scanner.nextLine();

            switch (opcionSubmenu) {
                case 1:
                    mostrarEmergencias();
                    break;
                case 2:
                    mostrarEmergenciasAtendidas();
                    break;
                case 3:
                    reasignarRecursos();
                    break;
                case 4:
                    finalizarEmergencia();
                    break;
                case 5:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcionSubmenu != 5);
    }

    private void registrarEmergencia() {
        String tipo, ubicacion = "", gravedad;
        boolean entradaUbicacionValida = false;
        List<String> zonasValidas = Arrays.asList("CENTRO", "NORTE", "SUR", "ESTE", "OESTE");

        do {
            System.out.print("Ingrese el tipo de emergencia " + TipoEmergencia.getListaTipos() + ": ");
            tipo = scanner.nextLine().toUpperCase();
            if (TipoEmergencia.fromString(tipo) == null) {
                System.out.println("Tipo de emergencia inválido. Intente de nuevo.");
            }
        } while (TipoEmergencia.fromString(tipo) == null);

        System.out.println("\nZonas disponibles: Centro, Norte, Sur, Este, Oeste");

        while (!entradaUbicacionValida) {
            System.out.print("Ingrese la ubicación de la emergencia: ");
            ubicacion = scanner.nextLine().trim().toUpperCase();

            if (zonasValidas.contains(ubicacion)) {
                entradaUbicacionValida = true;
            } else {
                System.out.println("\nError: Ubicación no válida. Debe ser una de las siguientes opciones:");
                System.out.println("Centro, Norte, Sur, Este, Oeste\n");
            }
        }

        NivelGravedad nivelGravedad;
        do {
            System.out.print("\nIngrese el nivel de gravedad (BAJO, MEDIO, ALTO): ");
            gravedad = scanner.nextLine().toUpperCase();
            nivelGravedad = NivelGravedad.fromString(gravedad);
            if (nivelGravedad == null) {
                System.out.println("Nivel de gravedad inválido. Debe ser BAJO, MEDIO o ALTO. Intente de nuevo.");
            }
        } while (nivelGravedad == null);

        // Calcular tiempo automáticamente
        int tiempoCalculado = sistemaEmergencias.getCityMap().calcularTiempoAtencion(ubicacion, tipo);
        
        System.out.println("\nTiempo calculado automáticamente: " + tiempoCalculado + " minutos");
        System.out.println("(Basado en ubicación: " + ubicacion + " y tipo: " + tipo + ")");

        sistemaEmergencias.registrarEmergencia(tipo, ubicacion, nivelGravedad, tiempoCalculado);
        System.out.println("\nEmergencia registrada correctamente.");
    }

    private void mostrarEmergencias() {
        System.out.println("\nListado de emergencias activas:");
        sistemaEmergencias.listarEmergencias();
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
        sistemaEmergencias.mostrarEstadisticasDelDia();
    }

    private void configurarEstrategiaAsignacion() {
        System.out.println("\nConfiguración de Estrategia de Asignación:");
        System.out.println("1. Asignar por gravedad");
        System.out.println("2. Asignar por cercanía");
        System.out.println("3. Volver al menú principal");
        System.out.print("\nSeleccione una opción: ");
        
        while (!scanner.hasNextInt()) {
            System.out.println("\nEntrada inválida. Ingrese un número.");
            scanner.next();
        }
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        switch (opcion) {
            case 1:
                sistemaEmergencias.setEstrategiaAsignacion(new StrategyPrioridadGravedad());
                System.out.println("\nEstrategia de asignación configurada a 'Asignar por gravedad'.");
                break;
            case 2:
                sistemaEmergencias.setEstrategiaAsignacion(new StrategyPrioridadCercania());
                System.out.println("\nEstrategia de asignación configurada a 'Asignar por cercanía'.");
                break;
            case 3:
                System.out.println("\nVolviendo al menú principal...");
                break;
            default:
                System.out.println("\nOpción no válida.");
        }
    }

    private void finalizarEmergencia() {
        System.out.println("\nFinalizar Emergencia:");

        List<Emergencia> emergencias = sistemaEmergencias.getListaEmergencias()
                .stream()
                .filter(e -> !e.isAtendida())
                .toList();

        if (emergencias.isEmpty()) {
            System.out.println("\nNo hay emergencias activas para finalizar.");
            return;
        }

        for (int i = 0; i < emergencias.size(); i++) {
            Emergencia e = emergencias.get(i);
            System.out.println((i + 1) + ". " + e.getTipo() + " en " + e.getUbicacion() + " (Gravedad: "
                    + e.getNivelGravedad() + ")");
        }

        System.out.print("Seleccione el número de la emergencia para finalizar: ");
        int seleccion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        if (seleccion < 1 || seleccion > emergencias.size()) {
            System.out.println("Selección inválida.");
            return;
        }

        Emergencia seleccionada = emergencias.get(seleccion - 1);
        sistemaEmergencias.finalizarEmergencia(seleccionada);
    }

    private void mostrarReportesHistoricos() {
        System.out.println("\n    --- Reportes Históricos ---\n");
        System.out.println("1. Ver último reporte del sistema");
        System.out.println("2. Ver historial de emergencias");
        System.out.println("3. Volver al menú principal");
        System.out.print("\nSeleccione una opción: ");
        
        while (!scanner.hasNextInt()) {
            System.out.println("\nEntrada inválida. Ingrese un número.");
            scanner.next();
        }
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer
        
        switch (opcion) {
            case 1:
                SystemReport.leerReporte();
                break;
            case 2:
                mostrarHistorialEmergencias();
                break;
            case 3:
                System.out.println("\nVolviendo al menú principal...");
                break;
            default:
                System.out.println("\nOpción no válida.");
        }
    }
    
    private void mostrarHistorialEmergencias() {
        System.out.println("\n--- Historial de Emergencias ---");
        List<String> historial = SystemRegistration.leerRegistros();
        
        if (historial.isEmpty()) {
            System.out.println("No hay registros históricos disponibles.");
        } else {
            // Eliminar duplicados usando un Set
            List<String> historialUnico = historial.stream()
                    .distinct()
                    .toList();
            
            System.out.println("Total de registros únicos: " + historialUnico.size());
            System.out.println("\nTodos los registros:");
            
            for (int i = 0; i < historialUnico.size(); i++) {
                System.out.println((i + 1) + ". " + historialUnico.get(i));
            }
        }
    }
    
    private void cerrarJornada() {
        System.out.println("\n¿Está seguro de que desea cerrar la jornada?");
        System.out.println("Esto guardará las estadísticas y preparará el sistema para el siguiente ciclo.");
        System.out.print("Ingrese 'SI' para confirmar: ");
        
        String confirmacion = scanner.nextLine().trim().toUpperCase();
        
        if ("SI".equals(confirmacion)) {
            sistemaEmergencias.cerrarJornada();
        } else {
            System.out.println("Cierre de jornada cancelado.");
        }
    }

}




