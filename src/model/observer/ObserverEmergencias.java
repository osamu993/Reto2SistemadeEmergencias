// Esta interfaz define el comportamiento de un observador dentro del sistema de emergencias.
// Forma parte del patrón de diseño Observer, donde los observadores reciben notificaciones
// cuando ocurren eventos importantes, como nuevas emergencias, emergencias resueltas o reasignaciones.
package model.observer;

import model.Emergencia;

public interface ObserverEmergencias {
    /**
     * Método que se ejecuta automáticamente cuando se registra una nueva emergencia.
     * Permite que el observador reaccione al evento (por ejemplo, mostrar una alerta).
     *
     * @param emergencia La emergencia que fue registrada.
     */
    void onNuevasEmergencias(Emergencia emergencia);
     /**
     * Método que se activa cuando una emergencia ha sido resuelta.
     * Puede usarse para mostrar el resultado, actualizar estadísticas o liberar recursos.
     *
     * @param emergencia La emergencia que fue atendida y finalizada.
     */
    void onEmergenciaResuelta(Emergencia emergencia);
/**
     * Método que se llama cuando el sistema realiza una reasignación de recursos.
     * Es útil para informar al usuario o actualizar el estado de los recursos en pantalla.
     */
    void onRecursosReasignados();
}
