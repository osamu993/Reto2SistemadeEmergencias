package model.strategy;

import java.util.List;

import model.Emergencia;
import model.interfaces.IServicioEmergencia;
import java.util.HashMap;
import java.util.Map;

/**
 * Estrategia de prioridad basada en la cercanía de la emergencia a una zona específica.
 */
public class StrategyPrioridadCercania implements StrategyPrioridad, IEstrategyAsignacion {

    private MapaUrbano mapaUrbano = new MapaUrbano();

    @Override
    public int calcularPrioridad(Emergencia emergencia) {
        int distancia = mapaUrbano.calcularDistancia(emergencia.getUbicacion());
        return Math.max(1, 20 - distancia); // Asegura que la prioridad nunca sea menor a 1
    }

    @Override
    public IServicioEmergencia asignarRecurso(List<IServicioEmergencia> recursos, Emergencia emergencia) {
        // Asignar el recurso más cercano a la ubicación de la emergencia
        IServicioEmergencia mejorRecurso = null;
        int mejorPrioridad = -1;

        for (IServicioEmergencia recurso : recursos) {
            if (recurso.estaDisponible()) {
                int prioridad = calcularPrioridad(emergencia);
                if (prioridad > mejorPrioridad) {
                    mejorPrioridad = prioridad;
                    mejorRecurso = recurso;
                }
            }
        }

        if (mejorRecurso != null) {
            mejorRecurso.desplegarUnidad(emergencia.getUbicacion());
            System.out.println("Asignado recurso " + mejorRecurso.getId() + " con prioridad " + mejorPrioridad);
        }

        return mejorRecurso;
    }
}

/**
 * Clase para calcular distancias entre ubicaciones en la ciudad.
 */
class MapaUrbano {
    private final Map<String, Integer> distancias;

    public MapaUrbano() {
        distancias = new HashMap<>();
        distancias.put("zona-norte", 8);
        distancias.put("zona-sur", 10);
        distancias.put("centro", 2);
        distancias.put("zona-oriente", 5);
        distancias.put("zona-occidente", 6);
    }

    /**
     * Calcula la distancia de una ubicación a la emergencia.
     * @param ubicacion Nombre de la ubicación.
     * @return Distancia en unidades arbitrarias.
     */
    public int calcularDistancia(String ubicacion) {
        return distancias.getOrDefault(ubicacion.toLowerCase(), 12); // Distancia por defecto
    }
}
