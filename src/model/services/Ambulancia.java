package model.services;

import model.Emergencia;

public class Ambulancia extends ServicioBase {
    public Ambulancia(String id, String ubicacion) {
        super(id, 6, 100, ubicacion); // Ejemplo: 6 paramédicos, 100 de combustible
    }

    @Override
    public void evaluarSituacion() {
        System.out.println("📋 Evaluando la situación en el lugar del incidente.");
    }

    @Override
    public void atenderEmergencia(Emergencia emergencia) {
        System.out.println("Ambulancia " + getId() + " atendiendo emergencia: " + emergencia.getDescripcion());
    }
}
