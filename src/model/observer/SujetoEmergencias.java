// Esta interfaz representa al sujeto (observable) del patrón Observer dentro del sistema de emergencias.
// Define los métodos que permiten agregar o quitar observadores, así como notificarles cuando
// ocurren eventos importantes como nuevas emergencias, emergencias resueltas o reasignación de recursos.
package model.observer;

import model.Emergencia;

public interface SujetoEmergencias {
    /**
     * Registra un nuevo observador al sistema.
     * Los observadores recibirán notificaciones sobre eventos importantes.
     *
     * @param observerEmergencias Observador que se desea agregar.
     */
    void agregarObserver(ObserverEmergencias observerEmergencias);
    /**
     * Elimina un observador previamente registrado.
     *
     * @param observerEmergencias Observador que se desea remover.
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
