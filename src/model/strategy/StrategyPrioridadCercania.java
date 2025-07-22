// Esta clase implementa una estrategia dual:
// 1. Calcula la prioridad de atención de una emergencia según qué tan cerca esté de los recursos.
// 2. Asigna el recurso más cercano disponible utilizando esa prioridad como criterio.
// Integra el patrón de diseño Strategy, permitiendo intercambiar esta lógica fácilmente
// con otras formas de evaluación de emergencias (por gravedad, tipo, etc.).
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
 // Simula un mapa urbano con distancias predefinidas desde estaciones a zonas de emergencia
    private MapaUrbano mapaUrbano = new MapaUrbano();
    /**
     * Calcula la prioridad de una emergencia según su distancia.
     * Cuanto más cerca esté, mayor será la prioridad.
     * 
     * @param emergencia Emergencia a evaluar.
     * @return Un número de prioridad donde 20 es la más alta y nunca baja de 1.
     */
    @Override
    public int calcularPrioridad(Emergencia emergencia) {
        int distancia = mapaUrbano.calcularDistancia(emergencia.getUbicacion());
        return Math.max(1, 20 - distancia); // Asegura que la prioridad nunca sea menor a 1
    }
    /**
     * Asigna el recurso disponible más cercano a la ubicación de la emergencia.
     * 
     * @param recursos   Lista de recursos disponibles.
     * @param emergencia Emergencia que requiere atención.
     * @return Recurso asignado o null si no hay recursos disponibles.
     */
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
 * Clase interna que simula un mapa urbano con distancias entre zonas.
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
     * Devuelve la distancia estimada desde una estación hasta la ubicación dada.
     * Si no se encuentra, asume una distancia genérica de 12 unidades.
     *
     * @param ubicacion Nombre textual de la zona.
     * @return Distancia numérica estimada.
     */
    public int calcularDistancia(String ubicacion) {
        return distancias.getOrDefault(ubicacion.toLowerCase(), 12); // Distancia por defecto
    }
}
