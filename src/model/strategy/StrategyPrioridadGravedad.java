package model.strategy;

import java.util.List;

<<<<<<< HEAD
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

=======
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
>>>>>>> a643c021564c78432de695379ef6684efa502cec
        }

        return recursoPrioritario;
    }
}
