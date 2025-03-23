package utils;

import java.io.*;
import java.util.List;

<<<<<<< HEAD
import model.Emergencia;


 //Clase para generar reportes sobre el desempeño del sistema de emergencias.
 
=======
/**
 * Clase para generar y guardar reportes sobre el desempeño del sistema de emergencias.
 */
>>>>>>> a643c021564c78432de695379ef6684efa502cec
public class SystemReport {
    private static final String NOMBRE_ARCHIVO = "reporte_emergencias.txt";

    /**
     * Genera un reporte con el resumen de emergencias atendidas.
     * @param emergenciasAtendidas Lista de emergencias resueltas.
     */
<<<<<<< HEAD
   public static void generarReporte() {
    List<Emergencia> historial = SystemRegistration.obtenerHistorial();

    if (historial.isEmpty()) {
        System.out.println("No hay emergencias registradas en el historial.");
        return;
    }

    System.out.println("=== Reporte de Emergencias ===");
    for (Emergencia e : historial) {
        System.out.println("Tipo: " + e.getTipo() +
                           ", Ubicación: " + e.getUbicacion() +
                           ", Gravedad: " + e.getNivelGravedad());
=======
    public static void generarReporte(List<String> emergenciasAtendidas) {
        System.out.println("\n--- Reporte del Sistema de Emergencias ---");
        System.out.println("Total de emergencias atendidas: " + emergenciasAtendidas.size());

        if (emergenciasAtendidas.isEmpty()) {
            System.out.println("⚠️ No se atendieron emergencias en este período.");
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

            pw.println("\n--- Reporte del Sistema de Emergencias ---");
            pw.println("Total de emergencias atendidas: " + emergenciasAtendidas.size());

            if (emergenciasAtendidas.isEmpty()) {
                pw.println("⚠️ No se atendieron emergencias en este período.");
            } else {
                for (String emergencia : emergenciasAtendidas) {
                    pw.println("- " + emergencia);
                }
            }

            pw.println("=========================================");
            System.out.println("📄 Reporte guardado en " + NOMBRE_ARCHIVO);

        } catch (IOException e) {
            System.err.println("❌ Error al guardar el reporte: " + e.getMessage());
        }
    }

    /**
     * Lee y muestra el contenido del último reporte guardado.
     */
    public static void leerReporte() {
        System.out.println("\n--- Último Reporte Guardado ---");
        try (BufferedReader br = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.err.println("❌ Error al leer el reporte: " + e.getMessage());
        }
    }

    /**
     * Limpia el archivo de reportes.
     */
    public static void limpiarReportes() {
        try (FileWriter fw = new FileWriter(NOMBRE_ARCHIVO, false)) { // Modo sobrescritura
            System.out.println("🗑️ Reportes de emergencias eliminados correctamente.");
        } catch (IOException e) {
            System.err.println("❌ Error al limpiar los reportes: " + e.getMessage());
        }
>>>>>>> a643c021564c78432de695379ef6684efa502cec
    }
}

}
