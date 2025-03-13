package utils;

import java.util.List;

import model.Emergencia;


 //Clase para generar reportes sobre el desempeño del sistema de emergencias.
 
public class SystemReport {
    /**
     * Genera un reporte con el resumen de emergencias atendidas.
     * @param emergenciasAtendidas Lista de emergencias resueltas.
     */
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
    }
}

}
