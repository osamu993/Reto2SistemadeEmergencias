package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//Clase que representa un mapa de la ciudad con ubicaciones y distancias.

public class CityMap {
    private Map<String, Map<String, Double>> mapa;

    // Constructor que inicializa el mapa de la ciudad.

    public CityMap() {
        this.mapa = new HashMap<>();
        inicializarMapa();
    }

    // Método para inicializar el mapa con ubicaciones y distancias.

    private void inicializarMapa() {
        // Ejemplo de datos ficticios de distancias entre ubicaciones
        mapa.put("Estación Bomberos", new HashMap<>());
        mapa.get("Estación Bomberos").put("Centro", 5.0);
        mapa.get("Estación Bomberos").put("Zona Norte", 10.0);

        mapa.put("Hospital", new HashMap<>());
        mapa.get("Hospital").put("Centro", 3.0);
        mapa.get("Hospital").put("Zona Sur", 7.0);
    }

    public Set<String> getUbicaciones() {
        return mapa.keySet();
    }

    public double getDistancia(String origen, String destino) {
        if (mapa.containsKey(origen) && mapa.get(origen).containsKey(destino)) {
            return mapa.get(origen).get(destino);
        }
        return Double.MAX_VALUE; // Retorna una distancia muy grande si no hay conexión
    }
    
    public String obtenerZona(String ubicacion) {
        // Definir zonas por ubicación
        Map<String, String> zonas = new HashMap<>();
        zonas.put("Centro", "URBANA");
        zonas.put("Sur", "RURAL");
        zonas.put("Norte", "URBANA");
        zonas.put("Oeste", "INDUSTRIAL");
        zonas.put("Este", "URBANA");
    
        // Buscar la zona de la ubicación dada
        for (Map.Entry<String, String> entry : zonas.entrySet()) {
            if (ubicacion.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
    
        return "URBANA"; // Por defecto, se considera una zona urbana si no se encuentra otra
    }    

    /**
     * Método para obtener la distancia entre dos ubicaciones.
     * 
     * @param origen  Ubicación de origen.
     * @param destino Ubicación de destino.
     * @return Distancia en kilómetros o -1 si no existe el camino.
     */
    public double obtenerDistancia(String origen, String destino) {
        if (mapa.containsKey(origen) && mapa.get(origen).containsKey(destino)) {
            return mapa.get(origen).get(destino);
        }
        return -1; // Indica que no hay una ruta directa registrada
    }
}
