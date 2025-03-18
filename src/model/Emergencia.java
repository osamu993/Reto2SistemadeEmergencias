package model;

import model.interfaces.IServicioEmergencia;
import model.services.GestorRecursos;
import utils.NivelGravedad;

public abstract class Emergencia {
    private String tipo;
    private String ubicacion;
    private NivelGravedad nivelGravedad;
    private int tiempoReespuesta;
    private boolean atendida;
    private long tiempoInicioAtencion;
    private long tiempoFinAtencion;
    private int recursosAsignados = 0;

    public Emergencia(String tipo, String ubicacion, NivelGravedad nivelGravedad, int tiempoReespuesta) {
        this.tipo = tipo;
        this.ubicacion = ubicacion;
        this.nivelGravedad = nivelGravedad;
        this.tiempoReespuesta = tiempoReespuesta;
        this.atendida = false;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public NivelGravedad getNivelGravedad() {
        return nivelGravedad;
    }

    public void setNivelGravedad(NivelGravedad nivelGravedad) {
        this.nivelGravedad = nivelGravedad;
    }

    public int getTiempoReespuesta() {
        return tiempoReespuesta;
    }

    public void setTiempoReespuesta(int tiempoReespuesta) {
        this.tiempoReespuesta = tiempoReespuesta;
    }

    public boolean isAtendida() {
        return atendida;
    }

    public void setAtendida(boolean atendida) {
        this.atendida = atendida;
    }

    public long getTiempoInicioAtencion() {
        return tiempoInicioAtencion;
    }

    public void setTiempoInicioAtencion(long tiempoInicioAtencion) {
        this.tiempoInicioAtencion = tiempoInicioAtencion;
    }

    public long getTiempoFinAtencion() {
        return tiempoFinAtencion;
    }

    public void setTiempoFinAtencion(long tiempoFinAtencion) {
        this.tiempoFinAtencion = tiempoFinAtencion;
    }

    public String getDescripcion() {
        return tipo + " en " + ubicacion + " (Nivel: " + nivelGravedad + ")";
    }

    public void asignarRecurso(IServicioEmergencia recurso) {
        this.recursosAsignados++;
        recurso.setDisponible(false);
    }

    public IServicioEmergencia liberarRecurso(GestorRecursos gestorRecursos) {
    if (this.recursosAsignados > 0) {
        this.recursosAsignados--;

        // Usar GestorRecursos para obtener un recurso asignado
        IServicioEmergencia recurso = gestorRecursos.obtenerRecursoDisponible();
        if (recurso != null) {
            recurso.setDisponible(true);
            return recurso; // Retorna el recurso liberado para reasignarlo
        }
    }
    return null; // No hay recursos para liberar
}


    public boolean necesitaRecursos() {
        return this.recursosAsignados == 0; // Si no tiene recursos asignados, necesita recursos
    }

    public boolean tieneRecursosAsignados() {
        return this.recursosAsignados > 0; // Si tiene al menos un recurso asignado
    }

    @Override
    public String toString() {
        return "Emergencia [tipo=" + tipo + ", ubicacion=" + ubicacion + ", nivelGravedad=" + nivelGravedad
                + ", tiempoReespuesta=" + tiempoReespuesta + ", atendida=" + atendida + ", tiempoInicioAtencion="
                + tiempoInicioAtencion + ", tiempoFinAtencion=" + tiempoFinAtencion + "]";
    }

}
