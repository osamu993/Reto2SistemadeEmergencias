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
        Emergencia emergencia = FactoryEmergencias.crearEmergencia(tipo, ubicacion, nivel, tiempoRespuesta);
       
        if (emergencia != null) {

            listaEmergencias.add(emergencia);

            //usamos el mapa para obtener la estacion mas cercana
            CityMap mapa = new CityMap();
            String zona = mapa.obtenerZona(ubicacion);
            String estacionCercana = obtenerEstacionCercana(mapa,ubicacion);

            //asignamos los recursos en la ubicacion mas cercana
            Map<String, Integer> recursosRequeridos = determinarRecursosPorEmergencia(tipo, zona);

            for (Map.Entry<String, Integer> entry : recursosRequeridos.entrySet()) {

                String tipoRecurso = entry.getKey();
                int cantidad = entry.getValue();

                for(int i = 0; i < cantidad; i++) {
                    IServicioEmergencia recurso = gestorRecursos.asignarRecursoDesde(estacionCercana, tipoRecurso);
                    if(recurso != null) {
                      System.out.println("Recurso asignado: " + recurso.getId() + "Desde: " + estacionCercana);
                    }else{
                        System.out.println("No hay suficientes recursos de: " + tipoRecurso + " en la estacionmás cercana, se procedera a enviar de otra estacion." + estacionCercana);
                    }
                }

            notificarObservers(emergencia);
            System.out.println(" Emergencia registrada: " + tipo + " en " + ubicacion);
            }
        } else {
            System.err.println(" Error: No se pudo registrar la emergencia.");
        }
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

        //Busca la amergencia mas grande que no tenga recursos suficientes
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

    private String obtenerEstacionCercana(CityMap mapa, String ubicacion) {
        String estacionCercana = null;
        double menorDistancia = Double.MAX_VALUE;

        for (String estacion : mapa.getUbicaciones()) {
            double distancia = mapa.obtenerDistancia(estacion, ubicacion);
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
