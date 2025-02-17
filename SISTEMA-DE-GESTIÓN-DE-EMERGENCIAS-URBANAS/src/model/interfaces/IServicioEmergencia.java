package model.interfaces;
public interface IServicioEmergencia {
    String getId();
    int getPersonalDisponible();
    int getCombustible();
    boolean estaDisoponible();
    void asignarPersonal(int cantidad);
    void liberarPersonal(int cantidad);
    void gastarCombustible(int cantidad);
    void tanquearCombustible(int cantidad);

    //void atenderEmergencia(Emergencia emergencia);
}
