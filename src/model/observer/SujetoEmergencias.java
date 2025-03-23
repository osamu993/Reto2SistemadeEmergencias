package model.observer;

import model.Emergencia;

public interface SujetoEmergencias {

    /**
     * Agrega un observador al sistema.
     * @param observerEmergencias Observador a agregar.
     */
    void agregarObserver(ObserverEmergencias observerEmergencias);

    /**
     * Remueve un observador del sistema.
     * @param observerEmergencias Observador a remover.
     */
    void removerObserver(ObserverEmergencias observerEmergencias);

    /**
     * Notifica a los observadores cuando se registra una nueva emergencia.
     * @param emergencia Emergencia registrada.
     */
    void notificarObservers(Emergencia emergencia);

    /**
     * Notifica a los observadores cuando una emergencia ha sido resuelta.
     * @param emergencia Emergencia que ha sido resuelta.
     */
    void notificarEmergenciaResuelta(Emergencia emergencia);

    /**
     * Notifica a los observadores cuando los recursos han sido reasignados.
     */
    void notificarRecursosReasignados();

    
}
