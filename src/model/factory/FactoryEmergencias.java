package model.factory;

import model.AccidenteVehicular;
import model.Emergencia;
import model.Incendio;
import model.Robo;
import utils.NivelGravedad;

public class FactoryEmergencias {

    public static Emergencia crearEmergencia(String tipo, String ubicacion, NivelGravedad nivelGravedad, int tiempoReespuesta) {
        
        switch (tipo) {
            case "ROBO":
                return new Robo(ubicacion, nivelGravedad, tiempoReespuesta);
            case "ACCIDENTE_VEHICULAR":
                return new AccidenteVehicular(ubicacion, nivelGravedad, tiempoReespuesta);
            case "INCENDIO":
                return new Incendio(ubicacion, nivelGravedad, tiempoReespuesta);
            default:
                return null;
        }  
    }
}
