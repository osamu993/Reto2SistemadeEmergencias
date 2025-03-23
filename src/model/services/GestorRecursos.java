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
        System.out.println("🔍 Verificando ubicación de emergencia: " + ubicacionEmergencia);
    
        // Determinar qué estación buscar según el recurso
        Map<String, String> estacionesPorRecurso = new HashMap<>();
        estacionesPorRecurso.put("BOMBEROS", "Bomberos");
        estacionesPorRecurso.put("AMBULANCIA", "Hospital");
        estacionesPorRecurso.put("POLICIA", "Policia");
        estacionesPorRecurso.put("RESCATE", "Rescate");
    
        String tipoEstacion = estacionesPorRecurso.get(tipoRecurso.toUpperCase());
    
        if (tipoEstacion == null) {
            System.out.println("⚠️ Tipo de recurso no reconocido.");
            return null;
        }
    
        String estacionCercana = cityMap.obtenerEstacionCercana(ubicacionEmergencia, tipoEstacion);
    
        if (estacionCercana == null) {
            System.out.println("⚠️ No se encontró una estación adecuada para el recurso: " + tipoRecurso);
            return null;
        }
    
        System.out.println("✅ Asignando recurso de tipo: " + tipoRecurso + " desde la estación: " + estacionCercana);
    
        for (IServicioEmergencia recurso : recursosDisponibles) {
            if (recurso.estaDisponible() && recurso.getUbicacion().equalsIgnoreCase(estacionCercana)
                    && recurso.getClass().getSimpleName().equalsIgnoreCase(tipoRecurso)) {
                recurso.desplegarUnidad(estacionCercana);
                return recurso;
            }
        }
    
        System.out.println("⚠️ No hay suficientes recursos de " + tipoRecurso + " en " + estacionCercana + ". Se buscarán en otra estación.");
        return null; // No hay recursos disponibles
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
        return cityMap.obtenerEstacionCercana(ubicacionEmergencia, estacionTipo);
    }
    

}
