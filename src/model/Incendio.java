package model;

import controller.Responder;
import utils.NivelGravedad;
/**
 * Clase que representa una emergencia de tipo Incendio.
 * Extiende la clase abstracta Emergencia e implementa la interfaz Responder,
 * lo que permite definir un comportamiento específico al atender y evaluar la situación.
 */
public class Incendio extends Emergencia implements Responder {
     /**
     * Constructor del incendio.
     * @param ubicacion Lugar donde ocurre el incendio.
     * @param nivelGravedad Nivel de gravedad del evento (ALTO, MEDIO, BAJO).
     * @param tiempoReespuesta Tiempo estimado de llegada de los recursos.
     */
    public Incendio(String ubicacion, NivelGravedad nivelGravedad, int tiempoReespuesta) {
        super("Incendio", ubicacion, nivelGravedad, tiempoReespuesta);
    }
    /**
     * Acción específica al momento de atender un incendio.
     * En este caso, notifica que los bomberos han sido desplegados a la ubicación indicada.
     */
    @Override
    public void atenderEmergencia() {
        System.out.println("Bomberos en camino al incendio en " + getUbicacion() + "!!!");
    }
     /**
     * Acción específica para evaluar el estado de la emergencia tipo incendio.
     * Aquí se simula la verificación del entorno y posibles personas afectadas.
     */
    @Override
    public void evaluarEstado() {
        System.out.println("Evaluando la situación del incendio y verificando personas afectadas.");
    }
}
