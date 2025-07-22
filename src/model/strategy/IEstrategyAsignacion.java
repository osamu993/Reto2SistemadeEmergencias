// Esta interfaz define una estrategia para asignar recursos a emergencias.
// Forma parte del patrón de diseño Strategy, permitiendo que el sistema pueda
// cambiar la forma de asignar recursos (por gravedad, cercanía, etc.) sin alterar el resto del código.
package model.strategy;

import java.util.List;

import model.Emergencia;
import model.interfaces.IServicioEmergencia;

public interface IEstrategyAsignacion {
      /**
     * Método que define cómo se asigna un recurso a una emergencia,
     * según la estrategia implementada.
     *
     * @param recursos   Lista de recursos disponibles o en uso.
     * @param emergencia Emergencia que necesita ser atendida.
     * @return Recurso que será asignado a la emergencia, o null si no hay ninguno disponible.
     */
    IServicioEmergencia asignarRecurso(List<IServicioEmergencia> recursos, Emergencia emergencia);
}
