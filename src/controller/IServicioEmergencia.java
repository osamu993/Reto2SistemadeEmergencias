package controller;

public interface IServicioEmergencia {

    String getId();
    int getPersonalDisponible();
    int getCombustible();
    boolean estaDisponible();
    void asignarPersonal(int cantidad);
    void liberarPersonal(int cantidad);
    void gastarCombustible(int cantidad);
    void tanquearCombustible(int cantidad);
    //void atenderEmergencia(Emergencia emergencia); 


}
