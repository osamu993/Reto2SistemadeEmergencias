// Clase abstracta que centraliza el comportamiento común de todos los servicios de emergencia.
// Implementa la interfaz IServicioEmergencia, lo que garantiza que todas las subclases
// tengan un comportamiento estándar. Esta clase evita duplicación de código.
package model.services;

import model.Emergencia;
import model.interfaces.IServicioEmergencia;

public abstract class ServicioBase implements IServicioEmergencia {

    private String id;// Identificador único de la unidad (ej: AMB1, BOM2)
    private int personalDisponible;// Cantidad de personal que aún no ha sido asignado
    private int combustible; // Nivel de combustible de la unidad
    private String ubicacion;// Ubicación actual de la unidad
    /**
     * Constructor base que inicializa los atributos comunes a cualquier recurso.
     * 
     * @param id                Identificador único de la unidad.
     * @param personalDisponible Cantidad de personal disponible inicialmente.
     * @param combustible        Nivel inicial de combustible.
     * @param ubicacion          Ubicación inicial del recurso.
     */
    public ServicioBase(String id, int personalDisponible, int combustible, String ubicacion) {
        this.id = id;
        this.personalDisponible = personalDisponible;
        this.combustible = combustible;
        this.ubicacion = ubicacion;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getPersonalDisponible() {
        return personalDisponible;
    }

    @Override
    public int getCombustible() {
        return combustible;
    }

    @Override
    public String getUbicacion() {
        return ubicacion;
    }
    /**
     * Establece una nueva ubicación para el recurso.
     * @param ubicacion Nueva ubicación.
     */
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
        System.out.println("📍 La unidad " + id + " ha cambiado de ubicación a " + ubicacion);
    }
    
    /**
     * Devuelve true si el recurso está disponible: tiene personal y combustible.
     */
    @Override
    public boolean estaDisponible() {
        return personalDisponible > 0 && combustible > 0;
    }
      /**
     * Asigna personal a una emergencia si hay suficiente disponible.
     */
    @Override
    public void asignarPersonal(int cantidad) {
        if (cantidad <= personalDisponible) {
            personalDisponible -= cantidad;
        } else {
            System.out.println("No hay suficiente personal disponible en " + id);
        }
    }
      /**
     * Libera personal después de haber sido asignado.
     */
    @Override
    public void liberarPersonal(int cantidad) {
        personalDisponible += cantidad;
    }
    /**
     * Consume combustible al atender una emergencia.
     */
    @Override
    public void gastarCombustible(int cantidad) {
        combustible = Math.max(0, combustible - cantidad);
    }
    /**
     * Añade combustible a la unidad (ej: después de recargar).
     */
    @Override
    public void tanquearCombustible(int cantidad) {
        combustible += cantidad;
    }
    /**
     * Método para liberar el recurso después de atender una emergencia.
     */
    @Override
    public void liberarRecurso() {
        System.out.println("La unidad " + id + " ha sido liberada y está disponible nuevamente.");
    }
        /**
     * Envía la unidad a una ubicación específica.
     */
    @Override
    public void desplegarUnidad(String ubicacion) {
        this.ubicacion = ubicacion;
        System.out.println("Unidad " + id + " en camino a la zona: " + ubicacion);
    }
     /**
     * Devuelve un resumen textual del estado del recurso.
     */
    @Override
    public String getEstado() {
        return personalDisponible > 0 && combustible > 0 ? "Disponible" : "Ocupado";
    }
    /**
     * Método que debe implementar cada tipo de recurso para definir cómo atender una emergencia.
     * @param emergencia Emergencia que debe ser atendida.
     */
    public abstract void atenderEmergencia(Emergencia emergencia);
}

