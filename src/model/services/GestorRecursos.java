package model.services;

import java.util.ArrayList;
import java.util.List;

import model.Emergencia;
import model.interfaces.IServicioEmergencia;
import model.strategy.IEstrategyAsignacion;
import utils.CityMap;

public class GestorRecursos {
    private List<ServicioBase> recursosDisponibles = new ArrayList<>();
    private CityMap cityMap;
    private IEstrategyAsignacion estrategiaAsignacion;

    public GestorRecursos(CityMap cityMap) {
        this.cityMap = cityMap;
    }

    public void agregarRecursos(ServicioBase recurso) {
        recursosDisponibles.add(recurso);
    }

    public IServicioEmergencia asignarRecruso(int personalRequerido, int combustibleRequerido) {
        for (IServicioEmergencia recurso : recursosDisponibles) {
            if (recurso.estaDisponible() && recurso.getPersonalDisponible() >= personalRequerido
                    && recurso.getCombustible() >= combustibleRequerido) {
                recurso.asignarPersonal(personalRequerido);
                recurso.gastarCombustible(combustibleRequerido);
                recurso.setDisponible(false);
                return recurso;
            }
        }
        return null; // No hay recursos disponibles
    }

    public IServicioEmergencia obtenerRecursoDisponible() {
        for (IServicioEmergencia recurso : recursosDisponibles) {
            if (!recurso.estaDisponible()) { // Busca un recurso ocupado para liberarlo
                return recurso;
            }
        }
        return null; // No hay recursos disponibles
    }

    public IServicioEmergencia asignarRecursoDesde(String ubicacionEmergencia, String tipoRecurso) {

        String estacionCercana = cityMap.obtenerEstacionCercana(ubicacionEmergencia);

        if (estacionCercana == null) {
            System.out.println("No se encontro una estación cercana a la ubicación de emergencia.");
            return null; // No se pudo obtener la estación cercana a la ubicación de emergencia
        }

        for (IServicioEmergencia recurso : recursosDisponibles) {
            if (recurso.estaDisponible() && recurso.getUbicacion().equalsIgnoreCase(estacionCercana)
                    && recurso.getClass().getSimpleName().equalsIgnoreCase(tipoRecurso)) {
                recurso.desplegarUnidad(estacionCercana);
                return recurso;
            }
        }
        return null; // No hay recursos disponibles de ese tipo en la estación indicada
    }

    public void liberarRecurso(IServicioEmergencia recurso, int personalLiberado) {
        recurso.liberarPersonal(personalLiberado);
        recurso.setDisponible(true);
    }

    public void mostrarRecursos() {
        for (IServicioEmergencia recurso : recursosDisponibles) {
            System.out.println("ID: " + recurso.getId() + " (Personal Disponible: " + recurso.getPersonalDisponible()
                    + ", Combustible Disponible: " + recurso.getCombustible() + "Galones");
        }
    }

    public void setEstrategiaAsignacion(IEstrategyAsignacion estrategia) {
        this.estrategiaAsignacion = estrategia;
    }

    // Aplicar la estrategia en la asignación de recursos
    public IServicioEmergencia asignarRecurso(Emergencia emergencia) {
        if (estrategiaAsignacion != null) {
            List<IServicioEmergencia> recursos = new ArrayList<>(recursosDisponibles);
            return estrategiaAsignacion.asignarRecurso(recursos, emergencia);
        }
        return null; // No se asigna recurso
    }
}
