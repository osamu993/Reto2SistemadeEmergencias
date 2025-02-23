package utils;

import java.util.List;


 //Clase para calcular métricas del desempeño del sistema de emergencias.
 
public class StatisticsSystem{
    /**
     * Calcula el tiempo promedio de respuesta a emergencias.
     * @param tiempos Lista de tiempos de respuesta en minutos.
     * @return Tiempo promedio o -1 si la lista está vacía.
     */
    public static double calcularTiempoPromedio(List<Double> tiempos) {
        if (tiempos.isEmpty()) {
            return -1;
        }
        double suma = 0;
        for (double tiempo : tiempos) {
            suma += tiempo;
        }
        return suma / tiempos.size();
    }

    /**
     * Calcula la eficiencia del uso de recursos en el sistema.
     * @param recursosUsados Cantidad de recursos utilizados.
     * @param recursosDisponibles Cantidad total de recursos.
     * @return Porcentaje de uso de recursos o -1 si recursosDisponibles es 0.
     */
    public static double calcularEficienciaRecursos(int recursosUsados, int recursosDisponibles) {
        if (recursosDisponibles == 0) {
            return -1;
        }
        return (recursosUsados / (double) recursosDisponibles) * 100;
    }
}
