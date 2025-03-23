package model.observer;

import model.Emergencia;

public interface ObserverEmergencias {

    /**
     * Se activa cuando se registra una nueva emergencia.
     * @param emergencia La emergencia registrada.
     */
    void onNuevasEmergencias(Emergencia emergencia);

    /**
     * Se activa cuando una emergencia ha sido resuelta.
     * @param emergencia La emergencia que ha sido resuelta.
     */
    void onEmergenciaResuelta(Emergencia emergencia);

    /**
     * Se activa cuando se reasignan recursos en el sistema.
     */
    void onRecursosReasignados();
}
