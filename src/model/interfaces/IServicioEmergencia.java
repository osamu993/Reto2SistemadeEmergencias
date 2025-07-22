// Esta interfaz define el contrato que deben cumplir todos los servicios de emergencia del sistema.
// Incluye ambulancias, policías, bomberos, unidades de rescate, etc.
// Gracias a esta interfaz, se puede trabajar de forma polimórfica con diferentes servicios,
// sin importar su implementación concreta.

package model.interfaces;

public interface IServicioEmergencia {
/**
     * Devuelve el identificador único del recurso (por ejemplo: AMB1, BOM2, etc.).
     * @return ID del servicio.
     */
    String getId();
     /**
     * Indica cuántos miembros del personal están actualmente disponibles.
     * @return Cantidad de personal disponible.
     */
    int getPersonalDisponible();
     /**
     * Devuelve la cantidad de combustible disponible en la unidad.
     * @return Nivel de combustible.
     */
    int getCombustible();
     /**
     * Indica si la unidad está disponible para ser asignada a una emergencia.
     * @return true si está disponible, false si está ocupada o fuera de servicio.
     */
    boolean estaDisponible();
    /**
     * Asigna personal a una emergencia.
     * @param cantidad Número de personas a asignar.
     */
    void asignarPersonal(int cantidad);
    /**
     * Libera personal después de atender una emergencia.
     * @param cantidad Número de personas a liberar.
     */
    void liberarPersonal(int cantidad);
    /**
     * Reduce el nivel de combustible por uso durante la emergencia.
     * @param cantidad Cantidad de combustible consumido.
     */
    void gastarCombustible(int cantidad);
     /**
     * Recarga el combustible de la unidad.
     * @param cantidad Cantidad de combustible a añadir.
     */
    void tanquearCombustible(int cantidad);
    /**
     * Envía la unidad a la ubicación de una emergencia.
     * @param ubicacion Ubicación del incidente.
     */
    void desplegarUnidad(String ubicacion);
    /**
     * Realiza una evaluación de la situación en el lugar del incidente.
     * Esto puede usarse para simular la toma de decisiones.
     */
    void evaluarSituacion();
   /**
     * Devuelve una descripción textual del estado actual del recurso.
     * @return Estado del servicio (Ej: "En camino", "Disponible", "Sin combustible").
     */
    String getEstado();
     /**
     * Libera completamente el recurso, dejándolo listo para una nueva asignación.
     */
    void liberarRecurso();
    /**
     * Obtiene la ubicación actual de la unidad.
     * @return Ubicación actual (Ej: "Centro", "Norte", etc.).
     */
    String getUbicacion();
    /**
     * Actualiza la ubicación de la unidad después de un desplazamiento.
     * @param ubicacion Nueva zona donde se encuentra el recurso.
     */
    void setUbicacion(String ubicacion);
}


