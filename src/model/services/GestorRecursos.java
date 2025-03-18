package model.services;

import java.util.ArrayList;
import java.util.List;
import model.interfaces.IServicioEmergencia;
import utils.CityMap;

public class GestorRecursos {
    private List<IServicioEmergencia> recursosDisponibles = new ArrayList<>();
    private CityMap cityMap;

    public GestorRecursos(CityMap cityMap) {
        this.cityMap = cityMap;
    }

    public void agregarRecursos(IServicioEmergencia recurso) {
        recursosDisponibles.add(recurso);
    }

    public IServicioEmergencia asignarRecurso(int personalRequerido, int combustibleRequerido) {
        for (IServicioEmergencia recurso : recursosDisponibles) {
            if (recurso.estaDisponible() && recurso.getPersonalDisponible() >= personalRequerido && recurso.getCombustible() >= combustibleRequerido) {
                recurso.asignarPersonal(personalRequerido);
                recurso.gastarCombustible(combustibleRequerido);
                recurso.desplegarUnidad(recurso.getUbicacion()); // Enviar recurso correctamente
                return recurso;
            }
        }
        return null; // No hay recursos disponibles
    }

    public IServicioEmergencia obtenerRecursoDisponible() {
        for (IServicioEmergencia recurso : recursosDisponibles) {
            if (recurso.estaDisponible()) { // Busca primero un recurso disponible
                return recurso;
            }
        }
        // Si no hay disponibles, busca uno ocupado para liberar
        for (IServicioEmergencia recurso : recursosDisponibles) {
            if (!recurso.estaDisponible()) {
                return recurso;
            }
        }
        return null; // No hay recursos disponibles
    }

    public IServicioEmergencia asignarRecursoDesde(String ubicacionEmergencia, String tipoRecurso) {
        String estacionCercana = cityMap.obtenerEstacionCercana(ubicacionEmergencia);
    
        if (estacionCercana == null || estacionCercana.isEmpty()) {
            System.out.println("⚠️ No se encontró una estación cercana a " + ubicacionEmergencia);
            return null;
        }
    
        for (IServicioEmergencia recurso : recursosDisponibles) {
            if (recurso.estaDisponible() && recurso.getUbicacion().equalsIgnoreCase(estacionCercana)
                    && recurso.getClass().getSimpleName().equalsIgnoreCase(tipoRecurso)) {
                recurso.desplegarUnidad(estacionCercana);
                return recurso;
            }
        }
    
        System.out.println("⚠️ No hay recursos disponibles en " + estacionCercana);
        return null;
    }

    public void liberarRecurso(IServicioEmergencia recurso, int personalLiberado) {
        recurso.liberarPersonal(personalLiberado);
        recurso.liberarRecurso(); // Se encarga de marcar el recurso como disponible
    }

    public void mostrarRecursos() {
        for (IServicioEmergencia recurso : recursosDisponibles) {
            System.out.println("ID: " + recurso.getId() + " (Personal: " + recurso.getPersonalDisponible() + ", Combustible: " + recurso.getCombustible() + " galones)");
        }
    }
}
