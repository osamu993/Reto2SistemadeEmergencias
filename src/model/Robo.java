package model;

import controller.Responder;
import utils.NivelGravedad;

public class Robo extends Emergencia implements Responder {

    public Robo(String ubicacion, NivelGravedad nivelGravedad, int tiempoReespuesta) {
        super("Robo", ubicacion, nivelGravedad, tiempoReespuesta);
    }

    @Override
    public void atenderEmergencia() {
        System.out.println(" Policía en camino al robo en " + getUbicacion() + "!!!");
    }

    @Override
    public void evaluarEstado() {
        System.out.println(" Evaluando la escena del crimen y verificando el estado de los afectados.");
    }
}

