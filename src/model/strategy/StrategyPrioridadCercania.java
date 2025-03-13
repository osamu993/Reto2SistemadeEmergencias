package model.strategy;

import java.util.List;

import model.Emergencia;
import model.interfaces.IServicioEmergencia;
import utils.CityMap;

public class StrategyPrioridadCercania implements IEstrategyAsignacion {
    private CityMap cityMap;

    public StrategyPrioridadCercania(CityMap cityMap) {
        this.cityMap = cityMap;
    }

    @Override
    public IServicioEmergencia asignarRecurso(List<IServicioEmergencia> recursos, Emergencia emergencia) {
        IServicioEmergencia recursoCercano = null;
        double distanciaMinima = Double.MAX_VALUE;

        for (IServicioEmergencia recurso : recursos) {
            double distancia = cityMap.getDistancia(recurso.getUbicacion(), emergencia.getUbicacion());
            if (distancia < distanciaMinima) {
                distanciaMinima = distancia;
                recursoCercano = recurso;
            }
        }
        return recursoCercano;
    }

}
