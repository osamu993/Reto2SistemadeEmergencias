// Esta clase aplica el patrón de diseño Factory para crear objetos de tipo Emergencia.
// Permite instanciar dinámicamente diferentes subtipos (Robo, Incendio, AccidenteVehicular)
// sin necesidad de usar 'new' directamente en el código del sistema.
// Esto facilita el mantenimiento, escalabilidad y legibilidad del código.
package model.factory;

import model.AccidenteVehicular;
import model.Emergencia;
import model.Incendio;
import model.Robo;
import utils.NivelGravedad;

public class FactoryEmergencias {
    
    /**
     * Método estático que crea una emergencia según el tipo indicado.
     * 
     * @param tipo             El tipo de emergencia: ROBO, INCENDIO, ACCIDENTE_VEHICULAR
     * @param ubicacion        La zona o lugar donde ocurre la emergencia
     * @param nivelGravedad    Nivel de gravedad: BAJO, MEDIO, ALTO
     * @param tiempoReespuesta Tiempo estimado de atención en minutos
     * @return Un objeto del tipo Emergencia correspondiente, o null si el tipo es inválido
     */

    public static Emergencia crearEmergencia(String tipo, String ubicacion, NivelGravedad nivelGravedad, int tiempoReespuesta) {
// Validación básica: no se puede crear una emergencia sin ubicación o gravedad
        if (ubicacion == null || nivelGravedad == null) {
            System.err.println(" Error: La ubicación o el nivel de gravedad no pueden ser nulos.");
            return null;
        }
// Según el tipo ingresado, se instancia la clase correspondiente
        switch (tipo.toUpperCase()) {
            case "ROBO":
                return new Robo(ubicacion, nivelGravedad, tiempoReespuesta);
            case "ACCIDENTE_VEHICULAR":
                return new AccidenteVehicular(ubicacion, nivelGravedad, tiempoReespuesta);
            case "INCENDIO":
                return new Incendio(ubicacion, nivelGravedad, tiempoReespuesta);
            default:
             // Si el tipo no coincide con ninguno, se informa y retorna null
                System.err.println(" Error: Tipo de emergencia desconocido -> " + tipo);
                return null;
        }  
    }
}

