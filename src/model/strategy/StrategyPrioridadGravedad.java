package model.strategy;

import java.util.List;

import model.Emergencia;
import model.interfaces.IServicioEmergencia;

/**
 * Estrategia de prioridad basada en el nivel de gravedad de la emergencia.
 */
public class StrategyPrioridadGravedad implements StrategyPrioridad, IEstrategyAsignacion {

    @Override
    public int calcularPrioridad(Emergencia emergencia) {
        if (emergencia.getNivelGravedad() == null) {
            System.out.println("⚠️ Advertencia: La emergencia no tiene nivel de gravedad definido. Se asigna prioridad mínima.");
            return 1; // Valor por defecto si no hay información de gravedad
        }

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
