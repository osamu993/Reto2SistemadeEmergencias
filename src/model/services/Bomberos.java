// Esta clase representa una unidad de bomberos dentro del sistema de emergencias.
// Hereda de ServicioBase y define su comportamiento específico para incendios.
// Esta estructura permite reutilizar código común y especializar solo lo necesario.
package model.services;

import model.Emergencia;

public class Bomberos extends ServicioBase {
    /**
     * Constructor de la unidad de bomberos.
     * 
     * @param id        Identificador único del recurso (ejemplo: BOM1).
     * @param ubicacion Ubicación inicial del camión de bomberos.
     *                  Se configura con:
     *                  - 10 bomberos disponibles
     *                  - 100 unidades de combustible
     */
    public Bomberos(String id, String ubicacion) {
        super(id, 10, 100, ubicacion); 
    }
    /**
     * Simula la evaluación de un incendio al llegar al sitio del incidente.
     */
    @Override
    public void evaluarSituacion() {
        System.out.println("Evaluando la situación del incendio.");
    }
        /**
     * Indica que los bomberos están atendiendo una emergencia específica.
     * Muestra en consola la acción junto con una descripción de la emergencia.
     *
     * @param emergencia Emergencia que están atendiendo.
     */
    @Override
    public void atenderEmergencia(Emergencia emergencia) {
        System.out.println("Bomberos " + getId() + " atendiendo emergencia: " + emergencia.getDescripcion());
    }
}
