package model.factory;

import model.AccidenteVehicular;
import model.Emergencia;
import model.Incendio;
import model.Robo;
import utils.NivelGravedad;

public class FactoryEmergencias {

    public static Emergencia crearEmergencia(String tipo, String ubicacion, NivelGravedad nivelGravedad, int tiempoReespuesta) {
        
        if (ubicacion == null || nivelGravedad == null) {
            System.err.println(" Error: La ubicación o el nivel de gravedad no pueden ser nulos.");
            return null;
        }

        switch (tipo.toUpperCase()) {
            case "ROBO":
                return new Robo(ubicacion, nivelGravedad, tiempoReespuesta);
            case "ACCIDENTE_VEHICULAR":
                return new AccidenteVehicular(ubicacion, nivelGravedad, tiempoReespuesta);
            case "INCENDIO":
                return new Incendio(ubicacion, nivelGravedad, tiempoReespuesta);
            default:
                System.err.println(" Error: Tipo de emergencia desconocido -> " + tipo);
                return null;
        }  
    }
}

