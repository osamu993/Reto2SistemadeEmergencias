package view;

import controller.SistemaEmergencias;
import utils.NivelGravedad;
<<<<<<< HEAD

import java.util.Arrays;
import java.util.List;
=======
import utils.TipoEmergencia;
>>>>>>> a643c021564c78432de695379ef6684efa502cec
import java.util.Scanner;

public class MenuSistemaEmergencia {
    private Scanner scanner;
    private SistemaEmergencias sistemaEmergencias;

    public MenuSistemaEmergencia(SistemaEmergencias sistemaEmergencias) {
        this.scanner = new Scanner(System.in);
<<<<<<< HEAD
        this.controller = SistemaEmergencias.getInstance();
=======
>>>>>>> a643c021564c78432de695379ef6684efa502cec
        this.sistemaEmergencias = sistemaEmergencias;
    }

    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n\n--- Sistema de Emergencias ---\n");
            System.out.println("1. Registrar emergencia");
            System.out.println("2. Mostrar emergencias activas");
            System.out.println("3. Mostrar recursos disponibles");
            System.out.println("4. Reasignar recursos");
            System.out.println("5. Mostrar estadísticas del día");
            System.out.println("6. Salir");
<<<<<<< HEAD
            System.out.print("\nSeleccione una opción: \n");
=======
            System.out.print("Seleccione una opción: ");

            while (!scanner.hasNextInt()) {
                System.out.println("⚠️ Entrada inválida. Ingrese un número.");
                scanner.next();
            }
>>>>>>> a643c021564c78432de695379ef6684efa502cec
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
<<<<<<< HEAD

        // Validar tipo de emergencia
        String tipo = "";
        boolean entradaValida = false;
        while (!entradaValida) {
            System.out.print("Ingrese el tipo de emergencia (ROBO, INCENDIO, ACCIDENTE_VEHICULAR): ");
            tipo = scanner.nextLine().toUpperCase();
            if (tipo.equals("ROBO") || tipo.equals("INCENDIO") || tipo.equals("ACCIDENTE_VEHICULAR")) {
                entradaValida = true;
            } else {
                System.out.println("⚠️ Error: Tipo de emergencia no válido. Intente nuevamente.");
            }
        }

        // Mostrar las zonas disponibles antes de pedir la ubicación de la emergencia
        System.out.println("\nZonas disponibles: Centro, Norte, Sur, Este, Oeste");
        List<String> zonasValidas = Arrays.asList("CENTRO", "NORTE", "SUR", "ESTE", "OESTE");

        // Validar la entrada de la ubicación
        String ubi = "";
        entradaValida = false;
        while (!entradaValida) {
            System.out.print("Ingrese la ubicación de la emergencia: ");
            ubi = scanner.nextLine().toUpperCase();

            // Verificar si la ubicación pertenece a una zona válida
            if (zonasValidas.contains(ubi)) {
                entradaValida = true;
            } else {
                System.out.println("⚠️ Error: Ubicación no válida. Debe ser una de las siguientes opciones:");
                System.out.println("   ➜ Centro, Norte, Sur, Este, Oeste");
            }
        }

        // Validar nivel de gravedad
        entradaValida = false;
        String gravedad = "";
        while (!entradaValida) {
            System.out.print("Ingrese el nivel de gravedad (BAJO, MEDIO, ALTO): ");
            gravedad = scanner.nextLine().toUpperCase();
            try {
                NivelGravedad.valueOf(gravedad);
                entradaValida = true;
            } catch (IllegalArgumentException e) {
                System.out.println("⚠️ Error: Nivel de gravedad no válido. Intente nuevamente.");
            }
        }

        // Validar el tiempo de respuesta
        int tiempo = -1;
        entradaValida = false;
        while (!entradaValida) {
            System.out.print("Ingrese el tiempo estimado de respuesta en minutos: ");
            if (scanner.hasNextInt()) {
                tiempo = scanner.nextInt();
                if (tiempo > 0) {
                    entradaValida = true;
                } else {
                    System.out.println("⚠️ Error: El tiempo debe ser un número positivo.");
                }
            } else {
                System.out.println("⚠️ Error: Debe ingresar un número entero.");
                scanner.next(); // Limpiar entrada inválida
            }
        }

        scanner.nextLine(); // Limpiar buffer

        System.out.println("🔍 Verificando ubicación de emergencia: " + ubi);
        controller.registrarEmergencia(tipo, ubi, NivelGravedad.valueOf(gravedad), tiempo);
=======
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
>>>>>>> a643c021564c78432de695379ef6684efa502cec
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
<<<<<<< HEAD
        boolean reasignado = sistemaEmergencias.reasignarRecursos();

        System.out.println("\nIntentando reasignar recursos...");

        if (reasignado) {
=======
        System.out.println("\n🔄 Intentando reasignar recursos...");
        if (sistemaEmergencias.reasignarRecursos()) {
>>>>>>> a643c021564c78432de695379ef6684efa502cec
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
