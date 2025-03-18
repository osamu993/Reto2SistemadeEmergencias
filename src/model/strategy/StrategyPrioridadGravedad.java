package model.strategy;

import model.Emergencia;

/**
 * Estrategia de prioridad basada en el nivel de gravedad de la emergencia.
 */
public class StrategyPrioridadGravedad implements StrategyPrioridad {

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
}
