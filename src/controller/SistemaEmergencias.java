package controller;

import java.util.ArrayList;
import java.util.List;
import model.Emergencia;
import model.interfaces.IServicioEmergencia;
import model.observer.ObserverEmergencias;
import model.observer.SujetoEmergencias;
import model.factory.FactoryEmergencias;
import utils.NivelGravedad;

public class SistemaEmergencias implements SujetoEmergencias {
    private static SistemaEmergencias instancia;
    private List<Emergencia> listaEmergencias;
    private List<IServicioEmergencia> listaRecursos;
    private List<ObserverEmergencias> observadores;
    
    private int emergenciasAtendidas;
    private long tiempoTotalAtencion;

    private SistemaEmergencias() {
        listaEmergencias = new ArrayList<>();
        listaRecursos = new ArrayList<>();
        observadores = new ArrayList<>();
        emergenciasAtendidas = 0;
        tiempoTotalAtencion = 0;
    }

    public static SistemaEmergencias getInstance() {
        if (instancia == null) {
            instancia = new SistemaEmergencias();
        }
        return instancia;
    }

    public void registrarEmergencia(String tipo, String ubicacion, NivelGravedad nivel, int tiempoRespuesta) {
        Emergencia emergencia = FactoryEmergencias.crearEmergencia(tipo, ubicacion, nivel, tiempoRespuesta);
        if (emergencia != null) {
            listaEmergencias.add(emergencia);
            notificarObservers(emergencia);
            System.out.println(" Emergencia registrada: " + tipo + " en " + ubicacion);
        } else {
            System.err.println(" Error: No se pudo registrar la emergencia.");
        }
    }


    public void listarEmergencias() {
        if (listaEmergencias.isEmpty()) {
            System.out.println(" No hay emergencias activas.");
        } else {
            System.out.println("\n--- Emergencias Activas ---");
            for (Emergencia emergencia : listaEmergencias) {
                System.out.println("- " + emergencia.getDescripcion() + " en " + emergencia.getUbicacion() + " (" + emergencia.getNivelGravedad() + ")");
            }
        }
    }

    public void mostrarRecursos() {
        if (listaRecursos.isEmpty()) {
            System.out.println(" No hay recursos disponibles.");
        } else {
            for (IServicioEmergencia recurso : listaRecursos) {
                System.out.println("- " + recurso.getId() + " (Disponible: " + recurso.estaDisponible() + ")");
            }
        }
    }

    public boolean reasignarRecursos() {
        for (IServicioEmergencia recurso : listaRecursos) {
            if (!recurso.estaDisponible()) {
                recurso.liberarRecurso();
                return true;
            }
        }
        return false;
    }

    public void mostrarEstadisticas() {
        System.out.println("\n--- Estadísticas del Día ---");
        System.out.println("Emergencias atendidas: " + emergenciasAtendidas);
        if (emergenciasAtendidas > 0) {
            System.out.println("Tiempo promedio de respuesta: " + (tiempoTotalAtencion / emergenciasAtendidas) + " minutos");
        } else {
            System.out.println("No se han atendido emergencias aún.");
        }
        System.out.println("Recursos disponibles: " + listaRecursos.size());
    }

    @Override
    public void agregarObserver(ObserverEmergencias observerEmergencias) {
        observadores.add(observerEmergencias);
    }

    @Override
    public void removerObserver(ObserverEmergencias observerEmergencias) {
        observadores.remove(observerEmergencias);
    }

    @Override
    public void notificarObservers(Emergencia emergencia) {
        for (ObserverEmergencias observerEmergencias : observadores) {
            observerEmergencias.onNuevasEmergencias(emergencia);
        }
    }
}
