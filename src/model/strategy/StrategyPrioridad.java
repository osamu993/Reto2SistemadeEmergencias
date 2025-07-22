// Esta interfaz define una estrategia para calcular la prioridad de una emergencia.
// Se puede extender con múltiples criterios: por nivel de gravedad, tiempo de respuesta, ubicación, etc.
// Forma parte del patrón de diseño Strategy, ya que permite intercambiar fácilmente
// la forma en que se evalúan las prioridades de atención.
package model.strategy;

import model.Emergencia;

/**
 * Interfaz que define la estrategia para calcular la prioridad de una emergencia.
 */
public interface StrategyPrioridad {
    /**
     * Calcula la prioridad de una emergencia en base a su gravedad, tiempo de respuesta, etc.
     * @param emergencia Emergencia para la cual se calcula la prioridad.
     * @return Un valor numérico que representa la prioridad de la emergencia.
     */
    int calcularPrioridad(Emergencia emergencia);
    /**
     * Método por defecto para evitar errores si no se implementa una estrategia específica.
     * @param emergencia Emergencia para la cual se calcula la prioridad.
     * @return Prioridad mínima por defecto.
     */
    default int calcularPrioridadPorDefecto(Emergencia emergencia) {
        return 1; // La prioridad mínima por defecto
    }
}


    
