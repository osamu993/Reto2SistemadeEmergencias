package utils;

import java.util.List;

/**
 * Clase utilitaria para calcular métricas clave sobre el desempeño del sistema de emergencias.
 * Permite obtener indicadores como tiempo promedio de respuesta, eficiencia de uso de recursos
 * y tasa de éxito en la atención de emergencias.
 */
public class StatisticsSystem {
    /**
     * Calcula el tiempo promedio de respuesta a las emergencias registradas.
     * Este valor permite medir la agilidad del sistema frente a eventos críticos.
     *
     * @param tiempos Lista con los tiempos de respuesta (en minutos) de cada emergencia.
     * @return Tiempo promedio de respuesta o 0.0 si no hay datos registrados.
     */
    public static double calcularTiempoPromedio(List<Double> tiempos) {
        if (tiempos.isEmpty()) {
            System.out.println("\nAdvertencia: No hay tiempos de respuesta registrados.");
            return 0.0;
        }
        double suma = 0;
        for (double tiempo : tiempos) {
            suma += tiempo;
        }
        return suma / tiempos.size();
    }
  /**
     * Calcula la eficiencia en el uso de los recursos del sistema.
     * Se interpreta como el porcentaje de recursos que han sido efectivamente utilizados.
     *
     * @param recursosUsados Cantidad de recursos que fueron utilizados en la jornada.
     * @param recursosDisponibles Cantidad total de recursos disponibles en el sistema.
     * @return Porcentaje de eficiencia o 0.0 si no hay recursos disponibles.
     */
    public static double calcularEficienciaRecursos(int recursosUsados, int recursosDisponibles) {
        if (recursosDisponibles == 0) {
            System.out.println("Advertencia: No hay recursos disponibles para calcular eficiencia.");
            return 0.0;
        }
        return (recursosUsados / (double) recursosDisponibles) * 100;
    }
     /**
     * Calcula el porcentaje de emergencias que fueron atendidas con éxito.
     * Esta métrica permite evaluar la cobertura y efectividad del sistema.
     *
     * @param emergenciasAtendidas Número de emergencias que fueron resueltas.
     * @param totalEmergencias Número total de emergencias que se registraron.
     * @return Porcentaje de éxito o 0.0 si no hubo emergencias.
     */
    public static double calcularTasaExitoEmergencias(int emergenciasAtendidas, int totalEmergencias) {
        if (totalEmergencias == 0) {
            System.out.println("Advertencia: No hay emergencias registradas para calcular la tasa de éxito.");
            return 0.0;
        }
        return (emergenciasAtendidas / (double) totalEmergencias) * 100;
    }
}
