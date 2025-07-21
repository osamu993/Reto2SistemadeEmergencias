package model;

import controller.Responder;
import utils.NivelGravedad;

public class Incendio extends Emergencia implements Responder {

    public Incendio(String ubicacion, NivelGravedad nivelGravedad, int tiempoReespuesta) {
        super("Incendio", ubicacion, nivelGravedad, tiempoReespuesta);
    }

    @Override
    public void atenderEmergencia() {
        System.out.println(" Bomberos en camino al incendio en " + getUbicacion() + "!!!");
    }

    @Override
    public void evaluarEstado() {
        System.out.println(" Evaluando la situación del incendio y verificando personas afectadas.");
    }
}
