package utils;

import java.util.List;


 //Clase para generar reportes sobre el desempeño del sistema de emergencias.
 
public class SystemReport {
    /**
     * Genera un reporte con el resumen de emergencias atendidas.
     * @param emergenciasAtendidas Lista de emergencias resueltas.
     */
    public static void generarReporte(List<String> emergenciasAtendidas) {
        System.out.println("\n--- Reporte del Sistema de Emergencias ---");
        System.out.println("Total de emergencias atendidas: " + emergenciasAtendidas.size());
        
        for (String emergencia : emergenciasAtendidas) {
            System.out.println("- " + emergencia);
        }
    }
}
