package model.services;

import model.interfaces.IServicioEmergencia;

public class UnidadRescate implements IServicioEmergencia {
    private String id;
    private boolean disponible;
    private int combustible;
    private int personalDisponible;
    private String ubicacion;

    public UnidadRescate(String id, String ubicacion) {
        this.id = id;
        this.disponible = true;
        this.ubicacion = ubicacion;
        this.combustible = 100;
        this.personalDisponible = 6;
    }

    @Override
    public void desplegarUnidad(String ubicacion) {
        this.disponible = false;
        this.ubicacion = ubicacion;
        System.out.println("Unidad de Rescate " + id + " en camino a la zona: " + ubicacion);
    }

    @Override
    public void evaluarSituacion() {
        System.out.println("Evaluando situación en la zona de rescate.");
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
        System.out.println("✅ La unidad de rescate " + id + " ha sido liberada.");
    }

    @Override
    public void liberarPersonal(int cantidad) {
        System.out.println("🚑 Unidad de rescate " + id + " ha liberado " + cantidad + " miembros del equipo.");
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
        System.out.println("⛽ Unidad de rescate " + id + " ha gastado " + cantidad + ". Restante: " + this.combustible);
    }

    @Override
    public void tanquearCombustible(int cantidad) {
        this.combustible = Math.min(100, this.combustible + cantidad);
        System.out.println("⛽ Unidad de rescate " + id + " ha recargado " + cantidad + ". Total: " + this.combustible);
    }

    @Override
    public int getPersonalDisponible() {
        return this.personalDisponible;
    }

    @Override
    public void asignarPersonal(int cantidad) {
        if (personalDisponible >= cantidad) {
            personalDisponible -= cantidad;
            System.out.println("🧑‍🚒 Se han asignado " + cantidad + " rescatistas. Restantes: " + personalDisponible);
        } else {
            System.out.println("⚠️ No hay suficiente personal disponible en la unidad de rescate.");
        }
    }

    @Override
    public String getUbicacion() {
        return ubicacion;
    }

    @Override
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
        System.out.println("📍 La unidad de rescate " + id + " ahora está en " + ubicacion);
    }
}
