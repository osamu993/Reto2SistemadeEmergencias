package model;

import controller.Responder;

public class Incendio extends Emergencia implements Responder{

    public Incendio (String ubicacion, int nivelGravedad, int tiempoReespuesta){
        super("Incendio", ubicacion, nivelGravedad, tiempoReespuesta);
    }

    @Override
    public void atenderEmergencia() {
        System.out.println("Bomberos en camino!!!");
    }

    @Override
    public void evaluarEstado() {
        System.out.println("Revisar personas afectadas");   
    }

}
