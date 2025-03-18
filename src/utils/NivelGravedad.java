package utils;

/**
 * Enumeración que representa los niveles de gravedad de una emergencia.
 */
public enum NivelGravedad {
    ALTO(10),
    MEDIO(5),
    BAJO(1);

    private final int prioridad;

    NivelGravedad(int prioridad) {
        this.prioridad = prioridad;
    }

    /**
     * Obtiene el valor de prioridad asociado al nivel de gravedad.
     * @return Valor de prioridad numérico.
     */
    public int getPrioridad() {
        return prioridad;
    }

    /**
     * Convierte una cadena de texto en un nivel de gravedad, manejando errores.
     * @param nivel Texto que representa el nivel de gravedad (puede ser en minúsculas o mayúsculas).
     * @return Nivel de gravedad correspondiente o MEDIO si no se reconoce el valor.
     */
    public static NivelGravedad fromString(String nivel) {
        if (nivel == null) {
            return MEDIO; // Valor por defecto
        }
        switch (nivel.trim().toUpperCase()) {
            case "ALTO":
                return ALTO;
            case "BAJO":
                return BAJO;
            case "MEDIO":
            default:
                return MEDIO;
        }
    }
}
