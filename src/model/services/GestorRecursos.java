package model.services;

import java.util.ArrayList;
import java.util.List;

import model.interfaces.IServicioEmergencia;

public class GestorRecursos {
    private List <ServicioBase> recursosDisponibles = new ArrayList<>();

    public void agregarRecursos(ServicioBase recurso) {
        recursosDisponibles.add(recurso);
    }

    public IServicioEmergencia asignarRecruso (int personalRequerido, int combustibleRequerido) {
        for (IServicioEmergencia recurso : recursosDisponibles) {
            if(recurso.estaDisponible() && recurso.getPersonalDisponible() >= personalRequerido && recurso.getCombustible() >= combustibleRequerido) {
                recurso.asignarPersonal(personalRequerido);
                recurso.gastarCombustible(combustibleRequerido);
                return recurso;
            }
        }
                return null; // No hay recursos disponibles
    }

    public void liberarRecurso(IServicioEmergencia recurso, int personalLiberado) {
        recurso.liberarPersonal(personalLiberado);
    }

    public void mostrarRecursos() {
        for (IServicioEmergencia recurso : recursosDisponibles) {
            System.out.println("ID: " + recurso.getId() + " (Personal Disponible: " + recurso.getPersonalDisponible() + ", Combustible Disponible: " + recurso.getCombustible() + "Galones");
        }
    }
}
