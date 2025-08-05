// Esta clase representa una estrategia que evalúa la prioridad de una emergencia
// basándose exclusivamente en su nivel de gravedad: ALTO, MEDIO o BAJO.
// Es parte del patrón Strategy, lo que permite intercambiar este enfoque
// por otros más complejos (como distancia, disponibilidad, tipo de emergencia, etc.)
package model.strategy;

import java.util.List;

import model.Emergencia;
import model.interfaces.IServicioEmergencia;

/**
 * Estrategia de prioridad basada en el nivel de gravedad de la emergencia.
 * Una emergencia con nivel ALTO tendrá más prioridad que una con nivel BAJO.
 */
public class StrategyPrioridadGravedad implements StrategyPrioridad, IEstrategyAsignacion {
        /**
     * Asigna un valor numérico a la prioridad de una emergencia según su gravedad.
     *
     * ALTO  → prioridad 10  
     * MEDIO → prioridad 5  
     * BAJO  → prioridad 1  
     * 
     * Si no se define el nivel de gravedad, se usa un valor mínimo por defecto.
     *
     * @param emergencia Emergencia a evaluar.
     * @return Valor numérico de prioridad.
     */

    @Override
    public int calcularPrioridad(Emergencia emergencia) {
        if (emergencia.getNivelGravedad() == null) {
            System.out.println("Advertencia: La emergencia no tiene nivel de gravedad definido. Se asigna prioridad mínima.");
            return 1; // Valor por defecto si no hay información de gravedad
        }

        // Se asigna la prioridad según el nivel de gravedad
        switch (emergencia.getNivelGravedad()) {
            case ALTO:
                return 10;
            case MEDIO:
                return 5;
            case BAJO:
                return 1;
            default:
                return 1;
        }
    }
    /**
     * Asigna el mejor recurso disponible considerando únicamente la gravedad de la emergencia.
     * No toma en cuenta distancia ni tipo de recurso.
     *
     * @param recursos Lista de recursos disponibles en el sistema.
     * @param emergencia Emergencia que requiere atención.
     * @return Recurso asignado o null si no hay recursos disponibles.
     */
    @Override
    public IServicioEmergencia asignarRecurso(List<IServicioEmergencia> recursos, Emergencia emergencia) {
        // Asignar el recurso más cercano a la ubicación de la emergencia
        IServicioEmergencia mejorRecurso = null;
        int mejorPrioridad = -1;

        for (IServicioEmergencia recurso : recursos) {
            if (recurso.estaDisponible()) {
                int prioridad = calcularPrioridad(emergencia);
                if (prioridad > mejorPrioridad) {
                    mejorPrioridad = prioridad;
                    mejorRecurso = recurso;
                }
            }
        }

        if (mejorRecurso != null) {
            mejorRecurso.desplegarUnidad(emergencia.getUbicacion());
            System.out.println("Asignado recurso " + mejorRecurso.getId() + " con prioridad " + mejorPrioridad);
        }

        return mejorRecurso;
    }
}
