package utils;

import java.util.Arrays;
import java.util.List;

/**
 * Enumeración que representa los distintos tipos de emergencias
 * que pueden ser gestionadas por el sistema.
 */

public enum TipoEmergencia {
    ACCIDENTE_VEHICULAR,
    ROBO,
    INCENDIO;
    /**
     * Convierte una cadena de texto en un TipoEmergencia, manejando errores.
     * @param tipo Texto que representa el tipo de emergencia (puede ser en minúsculas o mayúsculas).
     * @return TipoEmergencia correspondiente o null si no se reconoce el valor.
     */
    public static TipoEmergencia fromString(String tipo) {
        if (tipo == null) {
            return null; // Si el valor es nulo, no se puede determinar un tipo de emergencia válido.
        }
        try {
            return TipoEmergencia.valueOf(tipo.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("Advertencia: Tipo de emergencia desconocido -> " + tipo);
            return null; // Retorna null si el tipo no es válido
        }
    }
    /**
     * Obtiene una lista de todos los tipos de emergencias disponibles.
     * @return Lista con los nombres de los tipos de emergencias.
     */
    public static List<String> getListaTipos() {
        return Arrays.asList(ACCIDENTE_VEHICULAR.name(), ROBO.name(), INCENDIO.name());
    }
}
