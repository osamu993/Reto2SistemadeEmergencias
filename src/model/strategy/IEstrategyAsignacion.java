package model.strategy;

import java.util.List;

import model.Emergencia;
import model.interfaces.IServicioEmergencia;

public interface IEstrategyAsignacion {
    IServicioEmergencia asignarRecurso(List<IServicioEmergencia> recursos, Emergencia emergencia);
}
