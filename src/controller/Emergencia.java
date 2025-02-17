package controller;

public class Emergencia implements Responder {
    private String ubicacion;
    private int nivelGravedad;
    private int tiempoReespuesta;
    
    public Emergencia(String ubicacion, int nivelGravedad, int tiempoReespuesta) {
        this.ubicacion = ubicacion;
        this.nivelGravedad = nivelGravedad;
        this.tiempoReespuesta = tiempoReespuesta;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getNivelGravedad() {
        return nivelGravedad;
    }

    public void setNivelGravedad(int nivelGravedad) {
        this.nivelGravedad = nivelGravedad;
    }

    public int getTiempoReespuesta() {
        return tiempoReespuesta;
    }

    public void setTiempoReespuesta(int tiempoReespuesta) {
        this.tiempoReespuesta = tiempoReespuesta;
    }

    @Override
    public String toString() {
        return "Emergencia [ubicacion=" + ubicacion + ", nivelGravedad=" + nivelGravedad + ", tiempoReespuesta="
                + tiempoReespuesta + "]";
    }

    @Override
    public void atenderEmergencia() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atenderEmergencia'");
    }

    @Override
    public void evaluarEstado() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'evaluarEstado'");
    }

    
}
