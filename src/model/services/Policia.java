package model.services;

import model.Emergencia;

public class Policia extends ServicioBase {
    public Policia(String id, String ubicacion) {
        super(id, 8, 100, ubicacion); // Ejemplo: 8 policías, 100 de combustible
    }

    @Override
    public void evaluarSituacion() {
        System.out.println("Evaluando la situación del incidente.");
    }

    @Override
    public void atenderEmergencia(Emergencia emergencia) {
        System.out.println("Policía " + getId() + " atendiendo emergencia: " + emergencia.getDescripcion());
    }
}
