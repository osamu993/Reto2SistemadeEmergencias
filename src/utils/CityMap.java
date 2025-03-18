package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Clase que representa un mapa de la ciudad con ubicaciones y distancias.
 */
public class CityMap {
    private Map<String, Map<String, Double>> mapa;

    /**
     * Constructor que inicializa el mapa de la ciudad.
     */
    public CityMap() {
        this.mapa = new HashMap<>();
        inicializarMapa();
    }

    /**
     * Método para inicializar el mapa con ubicaciones y distancias.
     */
    private void inicializarMapa() {
        // Ejemplo de datos ficticios de distancias entre ubicaciones
        agregarUbicacion("Estación Bomberos", "Centro", 5.0);
        agregarUbicacion("Estación Bomberos", "Zona Norte", 10.0);
        agregarUbicacion("Hospital", "Centro", 3.0);
        agregarUbicacion("Hospital", "Zona Sur", 7.0);
        agregarUbicacion("Estación Policía", "Centro", 4.0);
        agregarUbicacion("Estación Policía", "Zona Oeste", 8.0);
        agregarUbicacion("Base de Ambulancias", "Centro", 2.0);
        agregarUbicacion("Base de Ambulancias", "Zona Este", 6.0);
    }

    /**
     * Método para agregar nuevas ubicaciones con sus distancias.
     */
    private void agregarUbicacion(String origen, String destino, double distancia) {
        mapa.putIfAbsent(origen, new HashMap<>());
        mapa.get(origen).put(destino, distancia);
    }

    /**
     * Obtiene la lista de ubicaciones registradas.
     */
    public Set<String> getUbicaciones() {
        return mapa.keySet();
    }

    /**
     * Obtiene la distancia entre dos ubicaciones.
     * @param origen  Ubicación de origen.
     * @param destino Ubicación de destino.
     * @return Distancia en kilómetros o -1 si no existe el camino.
     */
    public double obtenerDistancia(String origen, String destino) {
        return mapa.getOrDefault(origen, new HashMap<>()).getOrDefault(destino, -1.0);
    }

    /**
     * Método para obtener la estación más cercana a la ubicación de emergencia.
     * @param ubicacionEmergencia Ubicación de la emergencia.
     * @return Nombre de la estación más cercana o null si no hay datos.
     */
    public String obtenerEstacionCercana(String ubicacionEmergencia) {
        String estacionCercana = null;
        double distanciaMinima = Double.MAX_VALUE;

        for (String estacion : mapa.keySet()) {
            double distancia = obtenerDistancia(estacion, ubicacionEmergencia);
            if (distancia >= 0 && distancia < distanciaMinima) {
                distanciaMinima = distancia;
                estacionCercana = estacion;
            }
        }
        return estacionCercana;
    }

    /**
     * Método para determinar la zona de una ubicación.
     * @param ubicacion Nombre de la ubicación.
     * @return Tipo de zona (URBANA, RURAL, INDUSTRIAL).
     */
    public String obtenerZona(String ubicacion) {
        // Definir zonas por ubicación de forma flexible
        Map<String, String> zonas = new HashMap<>();
        zonas.put("Centro", "URBANA");
        zonas.put("Sur", "RURAL");
        zonas.put("Norte", "URBANA");
        zonas.put("Oeste", "INDUSTRIAL");
        zonas.put("Este", "URBANA");

        return zonas.getOrDefault(ubicacion, "URBANA"); // Retorna URBANA por defecto
    }
}
