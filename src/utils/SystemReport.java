package utils;

import java.io.*;
import java.util.List;

/**
 * Clase para generar y guardar reportes sobre el desempeño del sistema de emergencias.
 */
public class SystemReport {
    private static final String NOMBRE_ARCHIVO = "reporte_emergencias.txt";

    /**
     * Genera un reporte con el resumen de emergencias atendidas.
     * @param emergenciasAtendidas Lista de emergencias resueltas.
     */
    public static void generarReporte(List<String> emergenciasAtendidas) {
        System.out.println("\n--- Reporte del Sistema de Emergencias ---\n");
        System.out.println("Total de emergencias atendidas: " + emergenciasAtendidas.size());

        if (emergenciasAtendidas.isEmpty()) {
            System.out.println("No se atendieron emergencias en este período.");
        } else {
            for (String emergencia : emergenciasAtendidas) {
                System.out.println("- " + emergencia);
            }
        }

        // Guardar el reporte en un archivo
        guardarReporte(emergenciasAtendidas);
    }

    /**
     * Guarda el reporte en un archivo de texto.
     * @param emergenciasAtendidas Lista de emergencias resueltas.
     */
    private static void guardarReporte(List<String> emergenciasAtendidas) {
        try (FileWriter fw = new FileWriter(NOMBRE_ARCHIVO, true);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println("\n--- Reporte del Sistema de Emergencias ---\n");
            pw.println("Total de emergencias atendidas: " + emergenciasAtendidas.size());

            if (emergenciasAtendidas.isEmpty()) {
                pw.println("No se atendieron emergencias en este período.");
            } else {
                for (String emergencia : emergenciasAtendidas) {
                    pw.println("- " + emergencia);
                }
            }

            pw.println("=========================================");
            System.out.println("\nReporte guardado en " + NOMBRE_ARCHIVO);

        } catch (IOException e) {
            System.err.println("Error al guardar el reporte: " + e.getMessage());
        }
    }

    /**
     * Lee y muestra el contenido del último reporte guardado.
     */
    public static void leerReporte() {
        System.out.println("\n----- Último Reporte Guardado -----\n");
        try (BufferedReader br = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            String ultimoReporte = "";
            boolean enReporte = false;
            
            while ((linea = br.readLine()) != null) {
                if (linea.contains("--- Reporte del Sistema de Emergencias ---")) {
                    ultimoReporte = "";
                    enReporte = true;
                } else if (linea.contains("=========================================") && enReporte) {
                    ultimoReporte += linea + "\n";
                    break;
                }
                
                if (enReporte) {
                    ultimoReporte = linea + "\n";
                }
            }
            
            if (!ultimoReporte.isEmpty()) {
                System.out.println(ultimoReporte);
            } else {
                System.out.println("No hay reportes disponibles.");
            }
        } catch (IOException e) {
            System.err.println("Error al leer el reporte: " + e.getMessage());
        }
    }

    /**
     * Lee y muestra todo el historial de reportes guardados.
     */
    public static void leerHistorialCompleto() {
        System.out.println("\n--- Historial Completo de Reportes ---");
        try (BufferedReader br = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al leer el historial: " + e.getMessage());
        }
    }

    /**
     * Limpia el archivo de reportes.
     */
    public static void limpiarReportes() {
        try (FileWriter fw = new FileWriter(NOMBRE_ARCHIVO, false)) { // Modo sobrescritura
            System.out.println("Reportes de emergencias eliminados correctamente.");
        } catch (IOException e) {
            System.err.println("Error al limpiar los reportes: " + e.getMessage());
        }
    }


}


