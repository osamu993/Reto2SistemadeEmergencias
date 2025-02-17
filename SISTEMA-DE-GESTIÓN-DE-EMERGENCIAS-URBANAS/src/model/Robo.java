package model;

import utils.NivelGravedad;

public class Robo extends Emergencia{

    public Robo(String ubicacion, NivelGravedad nivelGravedad, int tiempoRespuesta) {
        super("Robo", ubicacion, nivelGravedad, tiempoRespuesta);   
    }

}
