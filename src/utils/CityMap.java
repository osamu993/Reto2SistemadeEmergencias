package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Clase que representa un mapa de la ciudad con ubicaciones y distancias.
 */
public class CityMap {
    private Map<String, Map<String, Double>> mapa;
    private Map<String, String> zonas;
    private Map<String, Integer> tiemposRespuesta; // Tiempos en minutos por zona

    /**
     * Constructor que inicializa el mapa de la ciudad.
     */
    public CityMap() {
        this.mapa = new HashMap<>();
        inicializarMapa();
        inicializarZonas();
        inicializarTiemposRespuesta();
    }

    /**
     * Método para inicializar el mapa con ubicaciones y distancias.
     */
    private void inicializarMapa() {
        // Ejemplo de datos ficticios de distancias entre ubicaciones
        agregarUbicacion("Bomberos", "CENTRO", 5.0);
        agregarUbicacion("Bomberos", "NORTE", 10.0);
        agregarUbicacion("Bomberos", "SUR", 15.0); 
        agregarUbicacion("Bomberos", "ESTE", 12.0);        
        agregarUbicacion("Bomberos", "OESTE", 20.0);
        agregarUbicacion("Hospital", "CENTRO", 2.0);
        agregarUbicacion("Hospital", "NORTE", 9.0);        
        agregarUbicacion("Hospital", "SUR", 16.0);
        agregarUbicacion("Hospital", "ESTE", 16.0);
        agregarUbicacion("Hospital", "OESTE", 16.0);
        agregarUbicacion("Policia", "CENTRO", 4.0);
        agregarUbicacion("Policia", "ESTE", 8.0);
        agregarUbicacion("Policia", "OESTE", 18.0);
        agregarUbicacion("Rescate", "CENTRO", 2.0);
        agregarUbicacion("Rescate", "Norte", 6.0);
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
    public String obtenerEstacionCercana(String zona, String tipoEstacion) {
        System.out.println("\nBuscando estación de tipo " + tipoEstacion + " más cercana a la zona: " + zona);
        
        Map<String, Double> posiblesEstaciones = mapa.get(tipoEstacion);
    
        if (posiblesEstaciones == null || posiblesEstaciones.isEmpty()) {
            System.out.println("No hay estaciones de tipo " + tipoEstacion + " registradas en el mapa.");
            return null;
        }
    
        // 1. Verificar si hay conexión directa con la zona
        if (posiblesEstaciones.containsKey(zona)) {
            double distancia = posiblesEstaciones.get(zona);
            System.out.println("   -->Estación de: " + tipoEstacion + " más cercana tiene una distancia de: " + distancia + " km.");
            return tipoEstacion;
        }

        System.out.println("\nNo se encontro una estacion en la zona: " + zona + ", buscando la mas cercana de otra zona.");
    
        // 2. Si no hay conexión directa, buscar la estación más cercana
        double menorDistancia = Double.MAX_VALUE;
        String estacionCercana = null;
    
        for (Map.Entry<String, Double> entry : posiblesEstaciones.entrySet()) {
            double distancia = entry.getValue();
            if (distancia < menorDistancia) {
                menorDistancia = distancia;
                estacionCercana = tipoEstacion;
            }
        }
    
        if (estacionCercana != null) {
            System.out.println("   -->Estación alternativa de: " + tipoEstacion + " con distancia: " + menorDistancia + " km.");
        } else {
            System.out.println("No se encontró una estación cercana a " + zona);
        }
    
        return estacionCercana;
    }
    

    /**
     * Método para determinar la zona de una ubicación.
     * @param ubicacion Nombre de la ubicación.
     * @return Tipo de zona (URBANA, RURAL, INDUSTRIAL).
     */
    public String obtenerZona(String ubicacion) {
        return zonas.getOrDefault(ubicacion.toUpperCase(), "URBANA");
    }

    private void inicializarZonas() {
        zonas = new HashMap<>();
        zonas.put("CENTRO", "URBANA");
        zonas.put("NORTE", "URBANA");
        zonas.put("SUR", "RURAL");
        zonas.put("ESTE", "URBANA");
        zonas.put("OESTE", "RURAL");
    }

    private void inicializarTiemposRespuesta() {
        tiemposRespuesta = new HashMap<>();
        tiemposRespuesta.put("CENTRO", 5);  // 5 minutos
        tiemposRespuesta.put("NORTE", 8);   // 8 minutos
        tiemposRespuesta.put("SUR", 15);    // 15 minutos (rural)
        tiemposRespuesta.put("ESTE", 7);    // 7 minutos
        tiemposRespuesta.put("OESTE", 12);  // 12 minutos (rural)
    }

    public int obtenerTiempoRespuesta(String ubicacion) {
        return tiemposRespuesta.getOrDefault(ubicacion.toUpperCase(), 10);
    }

    /**
     * Calcula el tiempo total de atención basado en la ubicación y tipo de emergencia
     */
    public int calcularTiempoAtencion(String ubicacion, String tipoEmergencia) {
        int tiempoBase = obtenerTiempoRespuesta(ubicacion);
        
        // Ajustar según tipo de emergencia
        switch (tipoEmergencia.toUpperCase()) {
            case "INCENDIO":
                return tiempoBase + 10; // Incendios requieren más tiempo
            case "ACCIDENTE_VEHICULAR":
                return tiempoBase + 8;  // Accidentes requieren tiempo adicional
            case "ROBO":
                return tiempoBase + 5;  // Robos requieren tiempo moderado
            default:
                return tiempoBase + 5;
        }
    }
}
