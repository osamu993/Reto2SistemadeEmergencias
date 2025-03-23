package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Emergencia;
import model.interfaces.IServicioEmergencia;
import model.observer.ObserverEmergencias;
import model.observer.SujetoEmergencias;
import model.services.GestorRecursos;
import model.factory.FactoryEmergencias;
import utils.CityMap;
import utils.NivelGravedad;

public class SistemaEmergencias implements SujetoEmergencias {

    @Override
    public void notificarRecursosReasignados() {
        System.out.println("Recursos han sido reasignados.");
    }

    @Override
    public void notificarEmergenciaResuelta(Emergencia emergencia) {
        System.out.println("Emergencia resuelta: " + emergencia.getDescripcion());
    }

    private static SistemaEmergencias instancia;
    private List<Emergencia> listaEmergencias;
    private List<IServicioEmergencia> listaRecursos;
    private List<ObserverEmergencias> observadores;
    private CityMap mapa = new CityMap();
    private GestorRecursos gestorRecursos = new GestorRecursos(mapa);
    private int emergenciasAtendidas;
    private long tiempoTotalAtencion;

    public SistemaEmergencias() {
        listaEmergencias = new ArrayList<>();
        listaRecursos = new ArrayList<>();
        observadores = new ArrayList<>();
        emergenciasAtendidas = 0;
        tiempoTotalAtencion = 0;
    }

    public static SistemaEmergencias getInstance() {
        if (instancia == null) {
            instancia = new SistemaEmergencias();
        }
        return instancia;
    }

    public void registrarEmergencia(String tipo, String ubicacion, NivelGravedad nivel, int tiempoRespuesta) {
        System.out.println("🚨 Iniciando registro de emergencia...");
        
        Emergencia emergencia = FactoryEmergencias.crearEmergencia(tipo, ubicacion, nivel, tiempoRespuesta);
        if (emergencia == null) {
            System.out.println("❌ Error: No se pudo registrar la emergencia.");
            return;
        }
    
        listaEmergencias.add(emergencia);
        System.out.println("✅ Emergencia registrada: " + tipo + " en " + ubicacion);
    
        // Determinar qué recursos se requieren según el tipo de emergencia
        List<String> recursosRequeridos = new ArrayList<>();
        if (tipo.equalsIgnoreCase("INCENDIO")) {
            recursosRequeridos.add("BOMBEROS");
            recursosRequeridos.add("AMBULANCIA");
        } else if (tipo.equalsIgnoreCase("ROBO")) {
            recursosRequeridos.add("POLICIA");
            recursosRequeridos.add("RESCATE");
        } else if (tipo.equalsIgnoreCase("ACCIDENTE_VEHICULAR")) {
            recursosRequeridos.add("BOMBEROS");
            recursosRequeridos.add("AMBULANCIA");
            recursosRequeridos.add("POLICIA");
        }
    
        // Asignar cada recurso requerido desde la estación correcta
        for (String recurso : recursosRequeridos) {
            String estacionTipo = obtenerTipoEstacion(recurso);
            String estacionAsignada = mapa.obtenerEstacionCercana(ubicacion);
    
            System.out.println("🚒 Estación asignada para " + recurso + ": " + estacionAsignada);
    
            if (estacionAsignada != null) {
                IServicioEmergencia unidadAsignada = gestorRecursos.asignarRecursoDesde(estacionAsignada, recurso);
                if (unidadAsignada == null) {
                    System.out.println("⚠️ No hay suficientes recursos de: " + recurso + " en la estación: " + estacionAsignada);
                }
            } else {
                System.out.println("⚠️ No se encontró una estación cercana para el recurso: " + recurso);
            }
        }
    
        notificarObservers(emergencia);
    }
    

    public void listarEmergencias() {
        if (listaEmergencias.isEmpty()) {
            System.out.println(" No hay emergencias activas.");
        } else {
            System.out.println("\n--- Emergencias Activas ---");
            for (Emergencia emergencia : listaEmergencias) {
                System.out.println("- " + emergencia.getDescripcion() + " en " + emergencia.getUbicacion() + " ("
                        + emergencia.getNivelGravedad() + ")");
            }
        }
    }

    public void mostrarRecursos() {
        if (listaRecursos.isEmpty()) {
            System.out.println(" No hay recursos disponibles.");
        } else {
            for (IServicioEmergencia recurso : listaRecursos) {
                System.out.println("- " + recurso.getId() + " (Disponible: " + recurso.estaDisponible() + ")");
            }
        }
    }

    public boolean reasignarRecursos() {

        Emergencia emergenciaGrave = null;
        Emergencia emergenciaLeve = null;

        // Busca la amergencia mas grande que no tenga recursos suficientes
        for (Emergencia emergencia : listaEmergencias) {
            if (emergencia.getNivelGravedad() == NivelGravedad.ALTO && emergencia.necesitaRecursos()) {
                emergenciaGrave = emergencia;
                break;
            }
        }

        for (Emergencia emergencia : listaEmergencias) {
            if (emergencia.getNivelGravedad() != NivelGravedad.ALTO && emergencia.tieneRecursosAsignados()) {
                emergenciaLeve = emergencia;
                break;
            }
        }

        if (emergenciaGrave != null && emergenciaLeve != null) {
            IServicioEmergencia recurso = emergenciaLeve.liberarRecurso();
            if (recurso != null) {
                emergenciaGrave.asignarRecurso(recurso);
                return true;
            }
        }
        return false; // no se pudo reasignar ningun recurso
    }

    public void mostrarEstadisticas() {
        System.out.println("\n--- Estadísticas del Día ---");
        System.out.println("Emergencias atendidas: " + emergenciasAtendidas);
        if (emergenciasAtendidas > 0) {
            System.out.println(
                    "Tiempo promedio de respuesta: " + (tiempoTotalAtencion / emergenciasAtendidas) + " minutos");
        } else {
            System.out.println("No se han atendido emergencias aún.");
        }
        System.out.println("Recursos disponibles: " + listaRecursos.size());
    }

    private String obtenerEstacionCercana(CityMap mapa, String ubicacion, String tipoEstacion) {
        System.out.println("🔍 Buscando estación más cercana a: " + ubicacion);

        String estacionCercana = null;
                for (String estacion : mapa.getUbicaciones()) {
                    double distancia = mapa.obtenerDistancia(estacion, ubicacion);
                    double menorDistancia = 0;
                                        if (distancia < menorDistancia) {
                        menorDistancia = distancia;
                        estacionCercana = estacion;
            }
        }

        return estacionCercana;
    }

    private Map<String, Integer> determinarRecursosPorEmergencia(String tipoEmergencia, String zona) {
        Map<String, Integer> recursosNecesarios = new HashMap<>();

        switch (tipoEmergencia.toUpperCase()) {
            case "INCENDIO":
                recursosNecesarios.put("BOMBEROS", 2);
                recursosNecesarios.put("AMBULANCIA", 1);
                break;
            case "ROBO":
                recursosNecesarios.put("POLICIA", 2);
                break;
            case "ACCIDENTE_VEHICULAR":
                recursosNecesarios.put("POLICIA", 1);
                recursosNecesarios.put("AMBULANCIA", 2);
                break;
            default:
                System.out.println("Tipo de emergencia desconocido, asignando valores predeterminados.");
                recursosNecesarios.put("POLICIA", 1);
                recursosNecesarios.put("AMBULANCIA", 1);
        }

        // Ajuste según la zona
        if ("RURAL".equalsIgnoreCase(zona)) {
            recursosNecesarios.replaceAll((k, v) -> v + 1); // Aumentar recursos en zonas rurales
        }

        return recursosNecesarios;
    }

    private String obtenerTipoEstacion(String recurso) {
        switch (recurso.toUpperCase()) {
            case "BOMBEROS":
                return "Bomberos";
            case "POLICIA":
                return "Policia";
            case "RESCATE":
                return "Rescate";
            case "AMBULANCIA":
                return "Hospital";
            default:
                return null;
        }
    }
    

    public CityMap getCityMap() {
        return mapa;
    }

    @Override
    public void agregarObserver(ObserverEmergencias observerEmergencias) {
        observadores.add(observerEmergencias);
    }

    @Override
    public void removerObserver(ObserverEmergencias observerEmergencias) {
        observadores.remove(observerEmergencias);
    }

    @Override
    public void notificarObservers(Emergencia emergencia) {
        for (ObserverEmergencias observerEmergencias : observadores) {
            observerEmergencias.onNuevasEmergencias(emergencia);
        }
    }
}
