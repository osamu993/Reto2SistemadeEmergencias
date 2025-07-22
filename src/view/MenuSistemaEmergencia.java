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
// Esta clase representa el menú principal del sistema de emergencias.
// Se encarga de interactuar con el usuario, capturar sus decisiones y delegar tareas al controlador del sistema.

public class MenuSistemaEmergencia {
    private Scanner scanner;
    private SistemaEmergencias sistemaEmergencias;

 // Constructor que recibe el sistema de emergencias y prepara el escáner de consola.
    public MenuSistemaEmergencia(SistemaEmergencias sistemaEmergencias) {
        this.scanner = new Scanner(System.in);
        this.sistemaEmergencias = sistemaEmergencias;
    }
 // Método principal del menú. Muestra las opciones y espera la interacción del usuario.
    public void mostrarMenu() {
        int opcion;
        do {
// Despliegue del menú principal
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
// Validación de entrada (solo números)
            while (!scanner.hasNextInt()) {
                System.out.println("\nEntrada inválida. Ingrese un número.");
                scanner.next();
            }
            opcion = scanner.nextInt();
            scanner.nextLine();
// Ejecución de la opción seleccionada
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
        } while (opcion != 8); // El sistema se repite hasta que el usuario elige salir.
    }
 // --- Submenú para acciones específicas sobre emergencias ---
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

    // --- Registro de una nueva emergencia ---
    private void registrarEmergencia() {
        // Captura del tipo, ubicación y gravedad de la emergencia
        String tipo, ubicacion = "", gravedad;
        boolean entradaUbicacionValida = false;
        List<String> zonasValidas = Arrays.asList("CENTRO", "NORTE", "SUR", "ESTE", "OESTE");
 // Validación del tipo de emergencia
        do {
            System.out.print("Ingrese el tipo de emergencia " + TipoEmergencia.getListaTipos() + ": ");
            tipo = scanner.nextLine().toUpperCase();
            if (TipoEmergencia.fromString(tipo) == null) {
                System.out.println("Tipo de emergencia inválido. Intente de nuevo.");
            }
        } while (TipoEmergencia.fromString(tipo) == null);
// Validación de la ubicación ingresada
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
// Validación del nivel de gravedad
        NivelGravedad nivelGravedad;
        do {
            System.out.print("\nIngrese el nivel de gravedad (BAJO, MEDIO, ALTO): ");
            gravedad = scanner.nextLine().toUpperCase();
            nivelGravedad = NivelGravedad.fromString(gravedad);
            if (nivelGravedad == null) {
                System.out.println("Nivel de gravedad inválido. Debe ser BAJO, MEDIO o ALTO. Intente de nuevo.");
            }
        } while (nivelGravedad == null);

// Se calcula automáticamente el tiempo estimado de atención basado en la zona y el tipo
        int tiempoCalculado = sistemaEmergencias.getCityMap().calcularTiempoAtencion(ubicacion, tipo);
        
        System.out.println("\nTiempo calculado automáticamente: " + tiempoCalculado + " minutos");
        System.out.println("(Basado en ubicación: " + ubicacion + " y tipo: " + tipo + ")");

        sistemaEmergencias.registrarEmergencia(tipo, ubicacion, nivelGravedad, tiempoCalculado);
        System.out.println("\nEmergencia registrada correctamente.");
    }
 // Muestra en consola todas las emergencias que aún están activas (no atendidas)
    private void mostrarEmergencias() {
        System.out.println("\nListado de emergencias activas:");
        sistemaEmergencias.listarEmergencias();
    }
// Permite reasignar recursos de una emergencia activa a otra más urgente
    private void reasignarRecursos() {
        System.out.println("\nReasignación de Recursos:");
 // Filtra solo emergencias que no han sido atendidas
        List<Emergencia> emergencias = sistemaEmergencias.getListaEmergencias()
                .stream()
                .filter(e -> !e.isAtendida())
                .toList();

        if (emergencias.isEmpty()) {
            System.out.println("\nNo hay emergencias activas para reasignar recursos.");
            return;
        }
// Se listan las emergencias activas para que el usuario elija
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
// Muestra únicamente las emergencias que ya fueron atendidas
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
// Muestra las estadísticas acumuladas del día actual
    private void mostrarEstadisticas() {
        sistemaEmergencias.mostrarEstadisticasDelDia();
    }
 // Permite elegir cómo se asignarán los recursos: por gravedad o por cercanía
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
 // Permite marcar una emergencia como finalizada
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
  // Permite al usuario consultar reportes y registros de días anteriores
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
   // Muestra todos los registros históricos de emergencias guardados en archivos  
    private void mostrarHistorialEmergencias() {
        System.out.println("\n--- Historial de Emergencias ---");
        List<String> historial = SystemRegistration.leerRegistros();
        
        if (historial.isEmpty()) {
            System.out.println("No hay registros históricos disponibles.");
        } else {
// Elimina duplicados para mostrar solo entradas únicas
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
  // Finaliza la jornada actual, guarda estadísticas y prepara todo para el siguiente ciclo   
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




