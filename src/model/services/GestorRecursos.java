package model.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Emergencia;
import model.interfaces.IServicioEmergencia;
import model.strategy.IEstrategyAsignacion;
import utils.CityMap;

public class GestorRecursos {
    private List<IServicioEmergencia> recursosDisponibles = new ArrayList<>();
    private CityMap cityMap;
    private IEstrategyAsignacion estrategiaAsignacion;

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

        if (estacionCercana == null) {
            System.out.println("⚠️ No se encontró una estación cercana a " + ubicacionEmergencia);
            return obtenerRecursoDisponible(); // Busca un recurso global si no encuentra en la estación
        }
    
                 estacionCercana = cityMap.obtenerEstacionCercana(ubicacionEmergencia);
    
        if (estacionCercana == null) {
            System.out.println("⚠️ No se encontró una estación adecuada para el recurso: " + tipoRecurso);
            return null;
        }
    
        System.out.println("✅ Asignando recurso de tipo: " + tipoRecurso + " desde la estación: " + estacionCercana);
    
        for (IServicioEmergencia recurso : recursosDisponibles) {
            if (recurso.estaDisponible() && recurso.getUbicacion().equalsIgnoreCase(estacionCercana) && recurso.getClass().getSimpleName().equalsIgnoreCase(tipoRecurso)) {
                recurso.desplegarUnidad(ubicacionEmergencia);
                return recurso;
            }
        }

        // Si no hay recursos en la estación, intenta encontrar uno disponible en cualquier ubicación
        System.out.println("⚠️ No hay recursos de tipo " + tipoRecurso + " en " + estacionCercana + ", buscando en otra estación.");
        return obtenerRecursoDisponible();
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

    private String obtenerEstacionPorTipoRecurso(String ubicacionEmergencia, String tipoRecurso) {
        
        Map<String, String> estacionesPorRecurso = new HashMap<>();
        estacionesPorRecurso.put("BOMBEROS", "Bomberos");
        estacionesPorRecurso.put("AMBULANCIA", "Hospital");
        estacionesPorRecurso.put("POLICIA", "Policia");
        estacionesPorRecurso.put("RESCATE", "Rescate");
    
        String estacionTipo = estacionesPorRecurso.get(tipoRecurso.toUpperCase());
    
        if (estacionTipo == null) {
            return null; // Si el recurso no existe, no hay estación
        }
    
        // Usamos la versión del método que solo recibe `ubicacionEmergencia`
        return cityMap.obtenerEstacionCercana(ubicacionEmergencia);
    }

}
