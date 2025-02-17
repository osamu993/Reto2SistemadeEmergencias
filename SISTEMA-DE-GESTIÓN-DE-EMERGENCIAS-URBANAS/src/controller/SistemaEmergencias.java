package controller;

import java.util.ArrayList;
import java.util.List;

import model.Emergencia;
import model.observer.ObserverEmergencias;
import model.observer.SujetoEmergencias;
import model.strategy.StrategyPrioridad;
import model.strategy.StrategyPrioridadCercania;

public class SistemaEmergencias implements SujetoEmergencias {

    private static SistemaEmergencias instancia;
    private List <Emergencia> listaEmergencias;
    private List <Emergencia> listaRecursos;
    private List <ObserverEmergencias> observadores;

    private StrategyPrioridad strategyPrioridad;
    private int emergenciasAtendidas;
    private int tiempoTotalRespuesta;


    private SistemaEmergencias() {
        strategyPrioridad = new StrategyPrioridadCercania();
        listaEmergencias = new ArrayList<>();
        listaRecursos = new ArrayList<>();;
        observadores = new ArrayList<>();;
        emergenciasAtendidas = 0;
        tiempoTotalRespuesta = 0;
    }

    public static SistemaEmergencias getInstance() {
        if (instancia == null) {
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
    public void notificarEmergencias(Emergencia emergencia) {
        for (ObserverEmergencias observerEmergencias : observadores) {
            observerEmergencias.onNuevaEmergencia(emergencia);
        }
    }

    //TODO: gestion de recursos (registrar recurso, agregar recurso, mostrar recurso, eliminar recurso, filtrar por recurso, etc)
    //TODO: gestion de emergencias (registrar emergencia, asigne los recursos, gestione emergencia, gregar emergencia, mostrar emergencia, eliminar emergencia, filtrar por emergencia, etc)
    //TODO: mostrar estadisticas, mostrar balance

}
