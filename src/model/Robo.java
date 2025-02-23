package model;

import controller.Responder;
import utils.NivelGravedad;

public class Robo extends Emergencia implements Responder{

    public Robo (String ubicacion, NivelGravedad nivelGravedad, int tiempoReespuesta){
        super("Robo", ubicacion, nivelGravedad, tiempoReespuesta);
    }

    @Override
    public void atenderEmergencia() {
        System.out.println("Policia en camino!!!");
    }

    @Override
    public void evaluarEstado() {
        System.out.println("Revisar el estado de los afectados");
    }

}
