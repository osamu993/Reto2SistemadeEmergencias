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
import model.strategy.StrategyPrioridadGravedad;
import model.strategy.IEstrategyAsignacion;
import utils.CityMap;
import utils.NivelGravedad;
import model.observer.NotificadorEmergencias;

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
 

    public SistemaEmergencias() {
        listaEmergencias = new ArrayList<>();
        listaRecursos = new ArrayList<>();
        observadores = new ArrayList<>();
        listaEmergencias = new ArrayList<>();
        listaRecursos = new ArrayList<>();
        observadores = new ArrayList<>();
        inicializarRecursos(); // Agregar recursos al sistema
        
        // Configurar estrategia de asignación por defecto
        gestorRecursos.setEstrategiaAsignacion(new StrategyPrioridadGravedad());
        
        // Registrar observador para notificaciones
        agregarObserver(new NotificadorEmergencias());
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

        // Usar la estrategia de asignación configurada
        IServicioEmergencia recursoAsignado = gestorRecursos.asignarRecurso(emergencia);
        if (recursoAsignado != null) {
            emergencia.asignarRecurso(recursoAsignado);
            System.out.println("Recurso asignado: " + recursoAsignado.getId());
        } else {
            System.out.println("No se pudo asignar ningún recurso a la emergencia.");
        }

        notificarObservers(emergencia);
    }

    public void listarEmergencias() {
        if (listaEmergencias.isEmpty()) {
            System.out.println("\nNo hay emergencias activas.");
        } else {
            System.out.println("\n--- Emergencias Activas ---");
            for (Emergencia emergencia : listaEmergencias) {
                if (!emergencia.isAtendida()) {
                    System.out.println("\n- " + emergencia.getDescripcion() + " en zona " + emergencia.getUbicacion());
                }
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

    // Archivo: SistemaEmergencias.java
    // Clase: SistemaEmergencias

    public void mostrarEstadisticasDelDia() {
        int totalEmergencias = listaEmergencias.size();
        int atendidas = 0;
        int pendientes = 0;
        Map<String, Integer> recursosUsados = new HashMap<>();
    
        for (Emergencia emergencia : listaEmergencias) {
            if (emergencia.isAtendida()) {
                atendidas++;
            } else {
                pendientes++;
            }
    
            // Obtener recursos según tipo de emergencia y zona
            Map<String, Integer> recursos = determinarRecursosPorEmergencia(
                    emergencia.getTipo(),
                    mapa.obtenerZona(emergencia.getUbicacion()));
    
            for (Map.Entry<String, Integer> entry : recursos.entrySet()) {
                String recurso = entry.getKey();
                int cantidad = entry.getValue();
    
                recursosUsados.put(recurso, recursosUsados.getOrDefault(recurso, 0) + cantidad);
            }
        }
    
        System.out.println("\n---Estadísticas del Día ---\n\n");
        System.out.println("Total emergencias registradas: " + totalEmergencias);
        System.out.println("-Atendidas: " + atendidas);
        System.out.println("-Pendientes: " + pendientes);
    
        System.out.println("\nRecursos desplegados:");
        for (Map.Entry<String, Integer> entry : recursosUsados.entrySet()) {
            System.out.println("- " + entry.getKey() + ": " + entry.getValue());
        }
    }
    

    public List<Emergencia> getListaEmergencias() {
        return this.listaEmergencias;
    }

    public void mostrarRecursosDisponibles() {
        gestorRecursos.mostrarRecursosDisponibles();
    }

    public void reasignarRecursosEmergencia(Emergencia emergencia) {
        System.out.println("\nReasignando recursos para emergencia: " + emergencia.getDescripcion());
        
        // Liberar recursos actuales si los hay
        if (emergencia.tieneRecursosAsignados()) {
            emergencia.liberarRecurso();
        }
        
        // Asignar nuevo recurso usando la estrategia configurada
        IServicioEmergencia nuevoRecurso = gestorRecursos.asignarRecurso(emergencia);
        if (nuevoRecurso != null) {
            emergencia.asignarRecurso(nuevoRecurso);
            System.out.println("Recurso reasignado: " + nuevoRecurso.getId());
        } else {
            System.out.println("No se pudo reasignar ningún recurso.");
        }
        
        // Notificar a los observadores sobre la reasignación
        notificarRecursosReasignados();
    }

    public void setEstrategiaAsignacion(IEstrategyAsignacion estrategia) {
        gestorRecursos.setEstrategiaAsignacion(estrategia);
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

    public void finalizarEmergencia(Emergencia emergencia) {
        if (emergencia != null && !emergencia.isAtendida()) {
            emergencia.setTiempoInicioAtencion(System.currentTimeMillis());
            emergencia.finalizarAtencion();
            System.out.println("Emergencia finalizada: " + emergencia.getDescripcion());
            
            // Notificar a los observadores sobre la emergencia resuelta
            notificarEmergenciaResuelta(emergencia);
        }
    }
}
