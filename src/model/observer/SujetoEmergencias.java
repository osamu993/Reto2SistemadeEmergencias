package model.observer;

import model.Emergencia;

public interface SujetoEmergencias {
    void agregarObserver(ObserverEmergencias observerEmergencias);
    void removerObserver(ObserverEmergencias observerEmergencias);
    void notificarObservers(Emergencia emergencia);
}
