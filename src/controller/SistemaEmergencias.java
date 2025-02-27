package controller;

import java.util.ArrayList;
import java.util.List;

import model.Emergencia;
import model.interfaces.IServicioEmergencia;
import model.observer.ObserverEmergencias;
import model.observer.SujetoEmergencias;
import model.strategy.StrategyPrioridad;
import model.strategy.StrategyPrioridadGravedad;

public class SistemaEmergencias implements SujetoEmergencias{

    private static SistemaEmergencias instancia;
    private List<Emergencia> listaEmergencias;
    private List<IServicioEmergencia> listaRecursos;
    private List <ObserverEmergencias> observadores;

    private StrategyPrioridad strategyPrioridad;

    private int emergenciasAtendidas;  
    private long tiempoTotalAtencion;

    private SistemaEmergencias(){
        strategyPrioridad = new StrategyPrioridadGravedad();
        listaEmergencias = new ArrayList<>();
        listaRecursos = new ArrayList<>();
        observadores = new ArrayList<>();
        emergenciasAtendidas = 0;
        tiempoTotalAtencion = 0;
    }

    public static SistemaEmergencias getInstance(){
        if(instancia == null){
            instancia = new SistemaEmergencias();
        }
        return instancia;
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
