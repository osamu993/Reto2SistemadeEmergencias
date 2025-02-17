package model.strategy;

import model.Emergencia;

public interface StrategyPrioridad {
    int calcularPrioridad(Emergencia emergencia);
}
