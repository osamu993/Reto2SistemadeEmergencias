package model.strategy;

import java.util.List;

import model.Emergencia;
import model.interfaces.IServicioEmergencia;
import utils.NivelGravedad;

public class StrategyPrioridadGravedad implements IEstrategyAsignacion {

    @Override
    public IServicioEmergencia asignarRecurso(List<IServicioEmergencia> recursos, Emergencia emergencia) {
        IServicioEmergencia recursoPrioritario = null;

        for (IServicioEmergencia recurso : recursos) {
            if (recursoPrioritario == null || emergencia.getNivelGravedad().compareTo(NivelGravedad.ALTO) >= 0) {
                recursoPrioritario = recurso;
            }

        }

        return recursoPrioritario;
    }

}
