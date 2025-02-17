package model;

import utils.NivelGravedad;

public class AccidenteVehicular extends Emergencia{

    public AccidenteVehicular(String ubicacion, NivelGravedad nivelGravedad, int tiempoRespuesta) {
        super("Accidente Vehicular", ubicacion, nivelGravedad, tiempoRespuesta);
    }
}
