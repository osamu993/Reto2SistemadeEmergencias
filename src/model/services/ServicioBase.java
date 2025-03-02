package model.services;

import model.Emergencia;
import model.interfaces.IServicioEmergencia;

public abstract class ServicioBase implements IServicioEmergencia{

    private String id;
    private int personalDisponible;
    private int combustible;
    private String ubicacion;

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

    @Override
    public boolean estaDisponible() {
        return personalDisponible > 0 && combustible > 0;
    }

    @Override
    public void asignarPersonal(int cantidad) {
       if (cantidad <= personalDisponible) {
           personalDisponible -= cantidad;        
       }else{
           System.out.println("No hay suficiente personal disponible " + id);
       }
    }

    @Override
    public void liberarPersonal(int cantidad) {
        personalDisponible += cantidad;
    }

    @Override
    public void gastarCombustible(int cantidad) {
        combustible = Math.max(0, combustible - cantidad); 
    }

    @Override
    public void tanquearCombustible(int cantidad) {
        combustible += cantidad;
    }
    public abstract void atenderEmergencia(Emergencia emergencia);

}
