package model.services;

import java.util.ArrayList;
import java.util.List;
import model.Emergencia;
import model.interfaces.IServicioEmergencia;
import model.strategy.IEstrategyAsignacion;


public class GestorRecursos {
    private List<IServicioEmergencia> recursosDisponibles = new ArrayList<>();
    private IEstrategyAsignacion estrategiaAsignacion;
    

    public GestorRecursos() {
        inicializarRecursos();
    }

    public void agregarRecursos(IServicioEmergencia recurso) {
        recursosDisponibles.add(recurso);
    }

    public IServicioEmergencia asignarRecurso(int personalRequerido, int combustibleRequerido) {
        for (IServicioEmergencia recurso : recursosDisponibles) {
            if (recurso.estaDisponible() && recurso.getPersonalDisponible() >= personalRequerido
                    && recurso.getCombustible() >= combustibleRequerido) {
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

    public IServicioEmergencia asignarRecursoDesde(String ubicacion, String estacionAsignada, String tipoRecurso) {

        if (estacionAsignada == null) {
            System.out.println("No se puede asignar recurso.");
            return null;
        }
        

        for (IServicioEmergencia recurso : recursosDisponibles) {
            if (recurso.estaDisponible() &&
                    recurso.getUbicacion().equalsIgnoreCase(estacionAsignada) &&
                    recurso.getClass().getSimpleName().toUpperCase().contains(tipoRecurso.toUpperCase())) {

                recurso.desplegarUnidad(ubicacion);
                return recurso;
            }
        }

        System.out.println("No hay suficientes recursos de: " + tipoRecurso + " en la estación: " + estacionAsignada);
        return null;
    }

    public void liberarRecurso(IServicioEmergencia recurso, int personalLiberado) {
        recurso.liberarPersonal(personalLiberado);
        recurso.liberarRecurso(); // Se encarga de marcar el recurso como disponible
    }

    public void mostrarRecursos() {
        for (IServicioEmergencia recurso : recursosDisponibles) {
            System.out.println("ID: " + recurso.getId() + " (Personal: " + recurso.getPersonalDisponible()
                    + ", Combustible: " + recurso.getCombustible() + " galones)");
        }
    }

    public void mostrarRecursosDisponibles() {
        System.out.println("\nRecursos disponibles:");
    
        boolean hayDisponibles = false;
    
        for (IServicioEmergencia recurso : recursosDisponibles) {
            if (recurso.estaDisponible()) {
                System.out.println("ID: " + recurso.getId() + " | Tipo: " + recurso.getClass().getSimpleName() + " | Ubicación: " + recurso.getUbicacion());
                hayDisponibles = true;
            }
        }
    
        if (!hayDisponibles) {
            System.out.println("No hay recursos disponibles en este momento.");
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

    private void inicializarRecursos() {
        recursosDisponibles.add(new Bomberos("B1", "Bomberos"));
        recursosDisponibles.add(new Bomberos("B2", "Bomberos"));
    
        recursosDisponibles.add(new Policia("P1", "Policia"));
        recursosDisponibles.add(new Policia("P2", "Policia"));
    
        recursosDisponibles.add(new Ambulancia("A1", "Hospital"));
        recursosDisponibles.add(new Ambulancia("A2", "Hospital"));
    
        recursosDisponibles.add(new UnidadRescate("R1", "Rescate"));
        recursosDisponibles.add(new UnidadRescate("R2", "Rescate"));
    }
}
