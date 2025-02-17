package controller;

public class Incendio extends Emergencia implements Responder{

    public Incendio (String tipo, String ubicacion, int nivelGravedad, int tiempoReespuesta){
        super(ubicacion, nivelGravedad, tiempoReespuesta);
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
