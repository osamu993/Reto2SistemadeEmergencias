package model;

import controller.Responder;
import utils.NivelGravedad;

public class AccidenteVehicular extends Emergencia implements Responder {

    public AccidenteVehicular(String ubicacion, NivelGravedad nivelGravedad, int tiempoReespuesta) {
        super("Accidente Vehicular", ubicacion, nivelGravedad, tiempoReespuesta);
    }

    @Override
    public void atenderEmergencia() {
        System.out.println("🚑 Paramedicos en camino al accidente vehicular en " + getUbicacion() + "!!!");
    }

    @Override
    public void evaluarEstado() {
        System.out.println("📋 Evaluando signos vitales de los involucrados en el accidente.");
    }
}

