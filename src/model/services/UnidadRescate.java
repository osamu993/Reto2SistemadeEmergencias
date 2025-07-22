// Esta clase representa una unidad especializada en rescates dentro del sistema de emergencias.
// Hereda la lógica común desde ServicioBase, y sobrescribe los métodos que definen cómo evalúa y atiende una emergencia de rescate.
package model.services;

import model.Emergencia;

public class UnidadRescate extends ServicioBase {
    /**
     * Constructor de la Unidad de Rescate.
     *
     * @param id        Identificador único de la unidad (por ejemplo: R1).
     * @param ubicacion Ubicación inicial donde está disponible esta unidad.
     *                  Se configura con:
     *                  - 6 rescatistas disponibles
     *                  - 100 unidades de combustible
     */
    public UnidadRescate(String id, String ubicacion) {
        super(id, 6, 100, ubicacion); 
    }
    /**
     * Simula la evaluación de una situación de rescate al llegar a la zona.
     */
    @Override
    public void evaluarSituacion() {
        System.out.println("Evaluando situación en la zona de rescate.");
    }
      /**
     * Muestra en consola que la unidad está atendiendo la emergencia,
     * incluyendo el tipo o descripción de la situación.
     *
     * @param emergencia Emergencia que está siendo atendida.
     */
    @Override
    public void atenderEmergencia(Emergencia emergencia) {
        System.out.println("Unidad de Rescate " + getId() + " atendiendo emergencia: " + emergencia.getDescripcion());
    }
}
