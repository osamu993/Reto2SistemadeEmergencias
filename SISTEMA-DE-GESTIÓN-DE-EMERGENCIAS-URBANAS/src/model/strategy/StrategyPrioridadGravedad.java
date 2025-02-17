package model.strategy;

import model.Emergencia;

public class StrategyPrioridadGravedad implements StrategyPrioridad {

    @Override
    public int calcularPrioridad(Emergencia emergencia) {
        switch (emergencia.getNivelGravedad()) {
            case BAJO:
                return 1;
            case MEDIO:
                return 2;
            case ALTO:
                return 3;

            default:
                return 1;
        }
    }

}
