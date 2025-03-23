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

        mapa.put("Policia", new HashMap<>());
        mapa.get("Policia").put("Centro", 3.0);
        mapa.get("Policia").put("Norte", 5.0);
        mapa.get("Policia").put("Sur", 7.0);
        mapa.get("Policia").put("Este", 4.0);
        mapa.get("Policia").put("Oeste", 6.0);

        mapa.put("Rescate", new HashMap<>());
        mapa.get("Rescate").put("Centro", 4.0);
        mapa.get("Rescate").put("Norte", 6.0);
        mapa.get("Rescate").put("Sur", 5.0);
        mapa.get("Rescate").put("Este", 3.0);
        mapa.get("Rescate").put("Oeste", 4.5);

        mapa.put("Bomberos", new HashMap<>());
        mapa.get("Bomberos").put("Centro", 5.0);
        mapa.get("Bomberos").put("Norte", 10.0);
        mapa.get("Bomberos").put("Sur", 6.0);
        mapa.get("Bomberos").put("Este", 8.0);
        mapa.get("Bomberos").put("Oeste", 7.0);

        mapa.put("Hospital", new HashMap<>());
        mapa.get("Hospital").put("Centro", 2.0);
        mapa.get("Hospital").put("Norte", 7.0);
        mapa.get("Hospital").put("Sur", 5.0);
        mapa.get("Hospital").put("Este", 6.5);
        mapa.get("Hospital").put("Oeste", 8.0);

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

    public String obtenerEstacionCercana(String ubicacionEmergencia, String tipoEstacion) {
        double distanciaMinima = Double.MAX_VALUE;
        String estacionMasCercana = null;
    
        System.out.println("🔍 Buscando estación de tipo " + tipoEstacion + " más cercana a: " + ubicacionEmergencia);
    
        if (!mapa.containsKey(tipoEstacion)) {
            System.out.println("⚠️ No hay estaciones de tipo " + tipoEstacion + " registradas en el mapa.");
            return null;
        }
    
        for (Map.Entry<String, Map<String, Double>> entry : mapa.entrySet()) {
            String estacion = entry.getKey();
            Map<String, Double> destinos = entry.getValue();
    
            // 📌 🔍 Filtrar solo estaciones del tipo correcto
            if (!estacion.equalsIgnoreCase(tipoEstacion)) {
                continue; // Ignorar estaciones que no sean del tipo correcto
            }
    
            System.out.println("   ➜ Revisando estación: " + estacion);
    
            if (destinos.containsKey(ubicacionEmergencia)) {
                double distancia = destinos.get(ubicacionEmergencia);
                System.out.println("     ✅ Estación " + estacion + " tiene una distancia de: " + distancia);
    
                if (distancia < distanciaMinima) {
                    distanciaMinima = distancia;
                    estacionMasCercana = estacion;
                }
            } else {
                System.out.println("     ❌ La estación " + estacion + " NO tiene conexión con " + ubicacionEmergencia);
            }
        }
    
        if (estacionMasCercana == null) {
            System.out.println("⚠️ No se encontró una estación adecuada para el recurso.");
        } else {
            System.out.println("✅ Estación más cercana encontrada: " + estacionMasCercana);
        }
    
        return estacionMasCercana;
    }
        

    public String normalizarUbicacion(String ubicacion) {
        switch (ubicacion.toUpperCase()) {
            case "NORTE":
                return "Norte";
            case "SUR":
                return "Sur";
            case "ESTE":
                return "Este";
            case "OESTE":
                return "Oeste";
            case "CENTRO":
                return "Centro";
            default:
                return null;
        }
    }
    

}
