package model;

import controller.Responder;
import utils.NivelGravedad;
/**
 * Clase que representa una emergencia de tipo Robo.
 * Extiende la clase base Emergencia e implementa la interfaz Responder,
 * permitiendo definir acciones específicas que realiza la policía al atender esta situación.
 */
public class Robo extends Emergencia implements Responder {
      /**
     * Constructor para inicializar un objeto de tipo Robo.
     * @param ubicacion Lugar donde ocurre el robo.
     * @param nivelGravedad Nivel de gravedad asociado (ALTO, MEDIO, BAJO).
     * @param tiempoReespuesta Tiempo estimado de llegada de las unidades al sitio.
     */
    public Robo(String ubicacion, NivelGravedad nivelGravedad, int tiempoReespuesta) {
        super("Robo", ubicacion, nivelGravedad, tiempoReespuesta);
    }
      /**
     * Constructor para inicializar un objeto de tipo Robo.
     * @param ubicacion Lugar donde ocurre el robo.
     * @param nivelGravedad Nivel de gravedad asociado (ALTO, MEDIO, BAJO).
     * @param tiempoReespuesta Tiempo estimado de llegada de las unidades al sitio.
     */
    @Override
    public void atenderEmergencia() {
        System.out.println("Policía en camino al robo en " + getUbicacion() + "!!!");
    }
       /**
     * Acción específica para evaluar la escena del robo.
     * Se simula la inspección de la escena del crimen y la verificación del estado de las víctimas.
     */
    @Override
    public void evaluarEstado() {
        System.out.println("Evaluando la escena del crimen y verificando el estado de los afectados.");
    }
}

