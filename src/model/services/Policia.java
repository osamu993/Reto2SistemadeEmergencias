package model.services;

import model.interfaces.IServicioEmergencia;

public class Policia implements IServicioEmergencia {
    private String id;
    private boolean disponible;
    private int combustible;
    private int personalDisponible;
    private String ubicacion;

    public Policia(String id, String ubicacion) {
        this.id = id;
        this.disponible = true;
        this.ubicacion = ubicacion;
        this.combustible = 100; // Inicializamos el combustible
    }

    @Override
    public void desplegarUnidad(String ubicacion) {
        this.disponible = false;
        this.ubicacion = ubicacion; // Actualizamos la ubicación
        System.out.println("Policía " + id + " en camino a la zona: " + ubicacion);
    }

    @Override
    public void evaluarSituacion() {
        System.out.println("📋 Evaluando la situación del incidente.");
    }

    @Override
    public String getEstado() {
        return disponible ? "Disponible" : "Ocupado";
    }

    @Override
    public boolean estaDisponible() {
        return disponible;
    }

    @Override
    public void liberarRecurso() {
        this.disponible = true;
        System.out.println("✅ La unidad de policía " + id + " ha sido liberada y está disponible nuevamente.");
    }

    @Override
    public void liberarPersonal(int cantidad) {
        System.out.println("🚔 Policía " + id + " ha liberado " + cantidad + " oficiales.");
    }

    @Override
    public int getCombustible() {
        return this.combustible;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void gastarCombustible(int cantidad) {
        this.combustible = Math.max(0, this.combustible - cantidad);
        System.out.println("⛽ Policía " + id + " ha gastado " + cantidad + " de combustible. Restante: " + this.combustible);
    }

    @Override
    public void tanquearCombustible(int cantidad) {
        this.combustible = Math.min(100, this.combustible + cantidad);
        System.out.println("⛽ Policía " + id + " ha tanqueado " + cantidad + " de combustible. Total: " + this.combustible);
    }

    @Override
    public int getPersonalDisponible() {
        return this.personalDisponible;
    }

    @Override
    public void asignarPersonal(int cantidad) {
        if (personalDisponible >= cantidad) {
            personalDisponible -= cantidad;
            System.out.println("👮‍♂️ Se han asignado " + cantidad + " oficiales. Restantes: " + personalDisponible);
        } else {
            System.out.println("⚠️ No hay suficiente personal disponible en la policía.");
        }
    }

    @Override
    public String getUbicacion() {
        return ubicacion;
    }

    /**
     * Método para actualizar la ubicación de la unidad de policía.
     * @param ubicacion Nueva ubicación.
     */
    @Override
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
        System.out.println("📍 La unidad de policía " + id + " ahora está en " + ubicacion);
    }
}
