package model;

import model.interfaces.IServicioEmergencia;
import utils.NivelGravedad;
import java.util.ArrayList;
import java.util.List;

public abstract class Emergencia {
    private String tipo;
    private String ubicacion;
    private NivelGravedad nivelGravedad;
    private int tiempoReespuesta;
    private boolean atendida = false;
    private long tiempoInicioAtencion;
    private long tiempoFinAtencion;
    private List<IServicioEmergencia> recursosAsignados = new ArrayList<>();

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
        this.atendida = true;
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
        return tipo + " (Gravedad: " + nivelGravedad + ")";
    }

    /**
     * Asigna un recurso a la emergencia y lo marca como desplegado.
     * @param recurso Recurso de emergencia a asignar.
     */
    public void asignarRecurso(IServicioEmergencia recurso) {
        if (recurso != null && recurso.estaDisponible()) {
            recurso.desplegarUnidad(this.ubicacion);
            recursosAsignados.add(recurso);
            System.out.println("🚨 Se asignó " + recurso.getId() + " a la emergencia en " + ubicacion);
        } else {
            System.out.println("No se pudo asignar el recurso a la emergencia en " + ubicacion);
        }
    }

    /**
     * Libera el recurso asignado más recientemente.
     * @return Recurso liberado o null si no hay recursos.
     */
    public IServicioEmergencia liberarRecurso() {
        if (!recursosAsignados.isEmpty()) {
            IServicioEmergencia recurso = recursosAsignados.remove(recursosAsignados.size() - 1);
            recurso.liberarRecurso();
            System.out.println("✅ Se liberó el recurso " + recurso.getId() + " de la emergencia en " + ubicacion);
            return recurso;
        }
        return null; // No hay recursos para liberar
    }

    /**
     * Verifica si la emergencia necesita recursos adicionales.
     * @return true si no tiene recursos asignados, false en caso contrario.
     */
    public boolean necesitaRecursos() {
        return recursosAsignados.isEmpty();
    }

    /**
     * Indica si la emergencia tiene recursos asignados.
     * @return true si tiene al menos un recurso asignado, false en caso contrario.
     */
    public boolean tieneRecursosAsignados() {
        return !recursosAsignados.isEmpty();
    }

    /**
     * Finaliza la atención de la emergencia y registra el tiempo de finalización.
     */
    public void finalizarAtencion() {
        this.atendida = true;
        this.tiempoFinAtencion = System.currentTimeMillis();
        System.out.println("🏁 Emergencia resuelta: " + this.getDescripcion() + " en " + ubicacion);
    }
    

    @Override
    public String toString() {
        return "Emergencia [tipo=" + tipo + ", ubicacion=" + ubicacion + ", nivelGravedad=" + nivelGravedad
                + ", tiempoReespuesta=" + tiempoReespuesta + ", atendida=" + atendida + ", tiempoInicioAtencion="
                + tiempoInicioAtencion + ", tiempoFinAtencion=" + tiempoFinAtencion + "]";
    }

    
}
