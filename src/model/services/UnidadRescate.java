package model.services;

import model.Emergencia;

public class UnidadRescate extends ServicioBase {
    public UnidadRescate(String id, String ubicacion) {
        super(id, 6, 100, ubicacion); // Ejemplo: 6 rescatistas, 100 de combustible
    }

    @Override
    public void evaluarSituacion() {
        System.out.println("Evaluando situación en la zona de rescate.");
    }

    @Override
    public void atenderEmergencia(Emergencia emergencia) {
        System.out.println("Unidad de Rescate " + getId() + " atendiendo emergencia: " + emergencia.getDescripcion());
    }
}
