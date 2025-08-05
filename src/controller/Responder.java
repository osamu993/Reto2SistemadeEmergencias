// Esta interfaz define el comportamiento esencial que deben tener
// todos los recursos que pueden responder a una emergencia (como bomberos, policías, ambulancias).
package controller;

public interface Responder {
// Método obligatorio: cada recurso debe saber cómo atender una emergencia.
// Su implementación concreta dependerá del tipo de recurso (bombero, ambulancia, etc.).
    void atenderEmergencia();
// Método obligatorio: cada recurso también debe poder evaluar su estado
// (por ejemplo, si ya está ocupado, si está dañado, si necesita mantenimiento, etc.).
    void evaluarEstado();
}
