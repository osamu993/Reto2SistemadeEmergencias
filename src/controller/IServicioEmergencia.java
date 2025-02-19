package controller;

public interface IServicioEmergencia {

    String getId();
    int getPersonalDisponible();
    int getCombustible();
    boolean estaDisponible();
    void asignarPersonal(int cantidad);
    void liberarPersonal(int cantidad);
    void asignarCombustible(int cantidad);
    void liberarCombustible(int cantidad);
    void atenderEmergencia(Emergencia emergencia);


}
