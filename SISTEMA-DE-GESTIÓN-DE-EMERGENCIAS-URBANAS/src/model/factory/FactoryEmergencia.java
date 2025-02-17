package model.factory;
import model.AccidenteVehicular;
import model.Emergencia;
import model.Incendio;
import model.Robo;
import utils.NivelGravedad;
import utils.TipoEmergencia;

public class FactoryEmergencia {

    public static Emergencia crearEmergencia(TipoEmergencia tipo, String ubicacion, NivelGravedad nivelGravedad, int tiempoRespuesta) {

        switch (tipo) {
            case ACCIDENTE_VEHICULAR:
                return new AccidenteVehicular(ubicacion, nivelGravedad, tiempoRespuesta);
            case ROBO:
                return new Robo(ubicacion, nivelGravedad, tiempoRespuesta);
            case INCENDIO:
                return new Incendio(ubicacion, nivelGravedad, tiempoRespuesta);
            default:
                return null;
        }
    }
}
