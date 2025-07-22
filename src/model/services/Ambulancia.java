// Esta clase representa una unidad de ambulancia dentro del sistema de emergencias.
// Hereda de ServicioBase, lo que significa que comparte comportamiento común con otros servicios,
// pero personaliza ciertas acciones como evaluar la situación o atender una emergencia médica.
package model.services;

import model.Emergencia;

public class Ambulancia extends ServicioBase {
       /**
     * Constructor de Ambulancia.
     * 
     * @param id        Identificador único de la unidad (ejemplo: AMB1).
     * @param ubicacion Zona donde se encuentra ubicada inicialmente la ambulancia.
     *                  El constructor también establece una configuración inicial:
     *                  - 6 paramédicos disponibles
     *                  - 100 unidades de combustible
     */
    public Ambulancia(String id, String ubicacion) {
        super(id, 6, 100, ubicacion); 
    }
      /**
     * Acción específica que realiza la ambulancia al llegar al lugar del incidente.
     * En este caso, simula una evaluación médica de la situación.
     */
    @Override
    public void evaluarSituacion() {
        System.out.println("Evaluando la situación en el lugar del incidente.");
    }
      /**
     * Método que indica que esta ambulancia está atendiendo una emergencia médica.
     * Se muestra en consola una descripción de la acción.
     *
     * @param emergencia Emergencia que está siendo atendida por esta ambulancia.
     */
    @Override
    public void atenderEmergencia(Emergencia emergencia) {
        System.out.println("Ambulancia " + getId() + " atendiendo emergencia: " + emergencia.getDescripcion());
    }
}
