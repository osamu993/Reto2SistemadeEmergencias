package model.strategy;

import model.Emergencia;
import java.util.HashMap;
import java.util.Map;

/**
 * Estrategia de prioridad basada en la cercanía de la emergencia a una zona específica.
 */
public class StrategyPrioridadCercania implements StrategyPrioridad {

    private MapaUrbano mapaUrbano = new MapaUrbano();

    @Override
    public int calcularPrioridad(Emergencia emergencia) {
        int distancia = mapaUrbano.calcularDistancia(emergencia.getUbicacion());
        return Math.max(1, 20 - distancia); // Asegura que la prioridad nunca sea menor a 1
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
