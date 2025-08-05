package utils;

/**
 * Enumeración que representa los niveles de gravedad de una emergencia.
 * Cada nivel está asociado a un valor numérico de prioridad para facilitar la toma de decisiones en el sistema.
 */
public enum NivelGravedad {
    ALTO(10),// Emergencia de alta prioridad
    MEDIO(5),// Emergencia de prioridad media
    BAJO(1);// Emergencia de baja prioridad

    private final int prioridad;
     /**
     * Constructor privado que asigna un valor numérico a cada nivel de gravedad.
     * @param prioridad Valor entero que representa la urgencia relativa del nivel.
     */
    NivelGravedad(int prioridad) {
        this.prioridad = prioridad;
    }
 /**
     * Devuelve el valor de prioridad asociado a este nivel de gravedad.
     * Es usado para cálculos y decisiones automáticas dentro del sistema.
     * @return Prioridad numérica.
     */
    public int getPrioridad() {
        return prioridad;
    }
    /**
     * Convierte un texto recibido (por ejemplo desde consola o interfaz) a un valor de la enumeración.
     * Permite manejar entradas en distintos formatos (mayúsculas, minúsculas, espacios).
     * @param nivel Cadena que representa un nivel de gravedad (ej: "alto", "medio").
     * @return El objeto NivelGravedad correspondiente, o MEDIO si no se reconoce el texto.
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
