package model;

import controller.Responder;

public class AccidenteVehicular extends Emergencia implements Responder{

    public AccidenteVehicular(String tipo, String ubicacion, int nivelGravedad, int tiempoReespuesta){
        super(ubicacion, nivelGravedad, tiempoReespuesta);
    }

    @Override
    public void atenderEmergencia() {
        System.out.println("Paramedicos en camino!!!");
    }

    @Override
    public void evaluarEstado() {
        System.out.println("Revisar signos vitales de los involucrados");
    }

}
