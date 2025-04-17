package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Emergencia;
import model.interfaces.IServicioEmergencia;
import model.observer.ObserverEmergencias;
import model.observer.SujetoEmergencias;
import model.services.Ambulancia;
import model.services.Bomberos;
import model.services.GestorRecursos;
import model.services.Policia;
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

    public void inicializarRecursos() {
    listaRecursos.add(new Ambulancia("AMB1", "Centro"));
    listaRecursos.add(new Bomberos("BOM1", "Zona Norte"));
    listaRecursos.add(new Policia("POL1", "Centro"));
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
        listaEmergencias = new ArrayList<>();
        listaRecursos = new ArrayList<>();
        observadores = new ArrayList<>();
        emergenciasAtendidas = 0;
        tiempoTotalAtencion = 0;
        inicializarRecursos(); // Agregar recursos al sistema
        }


    public static SistemaEmergencias getInstance() {
        if (instancia == null) {
            instancia = new SistemaEmergencias();
        }
        return instancia;
    }

    public void registrarEmergencia(String tipo, String ubicacion, NivelGravedad nivel, int tiempoRespuesta) {
        System.out.println("\nIniciando registro de emergencia...");

        Emergencia emergencia = FactoryEmergencias.crearEmergencia(tipo, ubicacion, nivel, tiempoRespuesta);
        if (emergencia == null) {
            System.out.println("\nError: No se pudo registrar la emergencia.");
            return;
        }

        listaEmergencias.add(emergencia);
        System.out.println("\nEmergencia registrada: " + tipo + " en zona " + ubicacion);

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

        System.out.println("Recursos requeridos: " + recursosRequeridos);

        // Asignar cada recurso requerido desde la estación correcta
        for (String recurso : recursosRequeridos) {
            String estacionTipo = obtenerTipoEstacion(recurso);
            String estacionAsignada = mapa.obtenerEstacionCercana(ubicacion, estacionTipo);

            if (estacionAsignada != null) {
                IServicioEmergencia unidadAsignada = gestorRecursos.asignarRecursoDesde(ubicacion,estacionAsignada, recurso);
                if (unidadAsignada == null) {
                    System.out.println(
                            "\nNo hay suficientes recursos de: " + recurso + " en la estación: " + estacionAsignada);
                }
            } else {
                System.out.println("\nNo se encontró una estación cercana para el recurso: " + recurso);
            }
        }
        emergencia.setAtendida(true);
        notificarObservers(emergencia);
    }

    public void listarEmergencias() {
        if (listaEmergencias.isEmpty()) {
            System.out.println("\nNo hay emergencias activas.");
        } else {
            System.out.println("\n--- Emergencias Activas ---");
            for (Emergencia emergencia : listaEmergencias) {
                System.out.println("\n- " + emergencia.getDescripcion() + " en zona " + emergencia.getUbicacion());
            }
        }
    }

    public void mostrarRecursos() {
        if (listaRecursos.isEmpty()) {
            System.out.println("\nNo hay recursos disponibles.");
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
                    "\nTiempo promedio de respuesta: " + (tiempoTotalAtencion / emergenciasAtendidas) + " minutos");
        } else {
            System.out.println("\nNo se han atendido emergencias aún.");
        }
        System.out.println("\nRecursos disponibles: " + listaRecursos.size());
    }

    public List<Emergencia> getListaEmergencias() {
        return this.listaEmergencias;
    }  
    
    public void mostrarRecursosDisponibles() {
        gestorRecursos.mostrarRecursosDisponibles();
    }

    public void reasignarRecursosEmergencia(Emergencia emergencia) {
        List<String> recursosNecesarios = determinarRecursosPorEmergencia(emergencia.getTipo(), mapa.obtenerZona(emergencia.getUbicacion())).keySet().stream().toList();
    
        for (String tipoRecurso : recursosNecesarios) {
            System.out.println("Intentando reasignar recurso de tipo: " + tipoRecurso);
    
            String estacionAlternativa = mapa.obtenerEstacionCercana(emergencia.getUbicacion(), tipoRecurso);
    
            if (estacionAlternativa != null) {
                IServicioEmergencia nuevoRecurso = gestorRecursos.asignarRecursoDesde(emergencia.getUbicacion(),estacionAlternativa, tipoRecurso);
    
                if (nuevoRecurso != null) {
                    System.out.println("Recurso " + nuevoRecurso.getId() + " reasignado desde " + estacionAlternativa);
                } else {
                    System.out.println("No fue posible reasignar recurso de tipo: " + tipoRecurso);
                }
            } else {
                System.out.println("No se encontró una estación alternativa para tipo: " + tipoRecurso);
            }
        }
    }
    

    private String obtenerEstacionCercana(CityMap mapa, String ubicacion, String tipoEstacion) {
        System.out.println("\nBuscando estación más cercana a: " + ubicacion);

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
