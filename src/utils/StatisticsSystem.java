package utils;

import java.util.List;

<<<<<<< HEAD
import model.Emergencia;

//Clase para calcular métricas del desempeño del sistema de emergencias.

public class StatisticsSystem {
=======
/**
 * Clase para calcular métricas del desempeño del sistema de emergencias.
 */
public class StatisticsSystem {

>>>>>>> a643c021564c78432de695379ef6684efa502cec
    /**
     * Calcula el tiempo promedio de respuesta a emergencias.
     * 
     * @param tiempos Lista de tiempos de respuesta en minutos.
     * @return Tiempo promedio o 0.0 si la lista está vacía.
     */
    public static double calcularTiempoPromedio(List<Double> tiempos) {
        if (tiempos.isEmpty()) {
            System.out.println("⚠️ Advertencia: No hay tiempos de respuesta registrados.");
            return 0.0;
        }
        double suma = 0;
        for (double tiempo : tiempos) {
            suma += tiempo;
        }
        return suma / tiempos.size();
    }

    /**
     * Calcula la eficiencia del uso de recursos en el sistema.
     * 
     * @param recursosUsados      Cantidad de recursos utilizados.
     * @param recursosDisponibles Cantidad total de recursos.
     * @return Porcentaje de uso de recursos o 0.0 si recursosDisponibles es 0.
     */
    public static double calcularEficienciaRecursos(int recursosUsados, int recursosDisponibles) {
        if (recursosDisponibles == 0) {
            System.out.println("⚠️ Advertencia: No hay recursos disponibles para calcular eficiencia.");
            return 0.0;
        }
        return (recursosUsados / (double) recursosDisponibles) * 100;
    }

<<<<<<< HEAD
    public static void calcularEstadisticas() {
        List<Emergencia> historial = SystemRegistration.obtenerHistorial();

        if (historial.isEmpty()) {
            System.out.println("No hay datos suficientes para calcular estadísticas.");
            return;
        }

        long totalEmergencias = historial.size();
        long emergenciasGraves = historial.stream()
                .filter(e -> e.getNivelGravedad() == NivelGravedad.ALTO)
                .count();

        System.out.println("=== Estadísticas del Sistema ===");
        System.out.println("Total de emergencias registradas: " + totalEmergencias);
        System.out.println("Emergencias graves atendidas: " + emergenciasGraves);
    }

=======
    /**
     * Calcula la tasa de éxito en la atención de emergencias.
     * @param emergenciasAtendidas Número de emergencias que fueron resueltas.
     * @param totalEmergencias Número total de emergencias registradas.
     * @return Porcentaje de emergencias atendidas con éxito o 0.0 si totalEmergencias es 0.
     */
    public static double calcularTasaExitoEmergencias(int emergenciasAtendidas, int totalEmergencias) {
        if (totalEmergencias == 0) {
            System.out.println("⚠️ Advertencia: No hay emergencias registradas para calcular la tasa de éxito.");
            return 0.0;
        }
        return (emergenciasAtendidas / (double) totalEmergencias) * 100;
    }
>>>>>>> a643c021564c78432de695379ef6684efa502cec
}
