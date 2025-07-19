package model.services;

import model.Emergencia;

public class Bomberos extends ServicioBase {
    public Bomberos(String id, String ubicacion) {
        super(id, 10, 100, ubicacion); // Ejemplo: 10 bomberos, 100 de combustible
    }

    @Override
    public void evaluarSituacion() {
        System.out.println("🔥 Evaluando la situación del incendio.");
    }

    @Override
    public void atenderEmergencia(Emergencia emergencia) {
        System.out.println("Bomberos " + getId() + " atendiendo emergencia: " + emergencia.getDescripcion());
    }

    @Override
    public void desplegarUnidad(String ubicacion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'desplegarUnidad'");
    }

    @Override
    public String getEstado() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEstado'");
    }
}
