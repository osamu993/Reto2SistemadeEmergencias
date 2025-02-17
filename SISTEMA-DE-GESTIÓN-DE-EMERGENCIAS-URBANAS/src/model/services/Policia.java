package model.services;
import model.Emergencia;

public class Policia extends ServivioBase{

    public Policia(String id, int personalDisponible, int combustible) {
            super(id, personalDisponible, combustible);
        }
    
        @Override
    public void atenderEmergencia(Emergencia emergencia) {
        System.out.println("Policia en camino!!!");
        System.out.println("-> [Policia] " + getId() + "]:" + emergencia.getDescripcion());

        asignarPersonal(2);
        gastarCombustible(5);
    }

}
