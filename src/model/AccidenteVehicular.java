package model;

import controller.Responder;
import utils.NivelGravedad;
/**
 * Representa una emergencia de tipo Accidente Vehicular.
 * Hereda las propiedades básicas de cualquier emergencia y además define
 * cómo se debe actuar en este tipo específico.
 *
 * También implementa la interfaz Responder para definir su comportamiento al ser atendida.
 */
public class AccidenteVehicular extends Emergencia implements Responder {
    /**
     * Constructor del accidente vehicular.
     * Inicializa la emergencia con un tipo predefinido, una ubicación,
     * un nivel de gravedad y un tiempo estimado de respuesta.
     *
     * @param ubicacion Lugar donde ocurre el accidente.
     * @param nivelGravedad Nivel de gravedad de la emergencia.
     * @param tiempoReespuesta Tiempo estimado en minutos para responder.
     */
    public AccidenteVehicular(String ubicacion, NivelGravedad nivelGravedad, int tiempoReespuesta) {
        super("Accidente Vehicular", ubicacion, nivelGravedad, tiempoReespuesta);
    }
     /**
     * Método que define la acción inmediata cuando se atiende la emergencia.
     * Simula el envío de paramédicos al lugar del accidente.
     */
    @Override
    public void atenderEmergencia() {
        System.out.println("Paramedicos en camino al accidente vehicular en " + getUbicacion() + "!!!");
    }
      /**
     * Método que simula la evaluación del estado de los involucrados en el accidente.
     */
    @Override
    public void evaluarEstado() {
        System.out.println("Evaluando signos vitales de los involucrados en el accidente.");
    }
}

