package model;

import utils.NivelGravedad;

public class Incendio extends Emergencia{

    public Incendio(String ubicacion, NivelGravedad nivelGravedad, int tiempoRespuesta) {
        super("Incendio", ubicacion, nivelGravedad, tiempoRespuesta);
    }

}
