package controller;

public class Robo extends Emergencia implements Responder{

    public Robo (String tipo, String ubicacion, int nivelGravedad, int tiempoReespuesta){
        super(ubicacion, nivelGravedad, tiempoReespuesta);
    }

    @Override
    public void atenderEmergencia() {
        System.out.println("Policia en camino!!!");
    }

    @Override
    public void evaluarEstado() {
        System.out.println("Revisar el estado de los afectados");
    }

}
