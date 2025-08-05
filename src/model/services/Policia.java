// Esta clase representa una unidad policial dentro del sistema de emergencias.
// Hereda de ServicioBase, por lo que comparte toda la lógica común de los recursos,
// y solo sobrescribe los métodos que definen su comportamiento específico.
package model.services;

import model.Emergencia;

public class Policia extends ServicioBase {
      /**
     * Constructor de la unidad de policía.
     *
     * @param id        Identificador único de la unidad (por ejemplo: POL1).
     * @param ubicacion Zona inicial donde se encuentra disponible esta unidad.
     *                  Se configura con:
     *                  - 8 policías disponibles
     *                  - 100 unidades de combustible
     */
    public Policia(String id, String ubicacion) {
        super(id, 8, 100, ubicacion);
    } 
    /**
     * Simula la evaluación de una situación al llegar al lugar del incidente.
     */
    @Override
    public void evaluarSituacion() {
        System.out.println("Evaluando la situación del incidente.");
    }
    /**
     * Acción que realiza esta unidad cuando es asignada a una emergencia.
     * Se muestra en consola una descripción textual de la operación.
     *
     * @param emergencia Emergencia que está siendo atendida por esta unidad.
     */
    @Override
    public void atenderEmergencia(Emergencia emergencia) {
        System.out.println("Policía " + getId() + " atendiendo emergencia: " + emergencia.getDescripcion());
    }
}
