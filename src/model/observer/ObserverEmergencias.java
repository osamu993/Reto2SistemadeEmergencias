package model.observer;

import model.Emergencia;

public interface ObserverEmergencias {
    void onNuevasEmergencias(Emergencia emergencia);
}
