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
import utils.StatisticsSystem;
import utils.SystemReport;
import utils.SystemRegistration;

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
    private GestorRecursos gestorRecursos = new GestorRecursos();
 

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
            emergencia.setAtendida(true); // Marcar como atendida
            emergencia.setTiempoInicioAtencion(System.currentTimeMillis()); // Registrar tiempo de inicio
            System.out.println("Recurso asignado: " + recursoAsignado.getId());
            System.out.println("Emergencia marcada como atendida.");
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
        List<Double> tiemposRespuesta = new ArrayList<>();
        List<String> emergenciasAtendidas = new ArrayList<>();
    
        for (Emergencia emergencia : listaEmergencias) {
            if (emergencia.isAtendida()) {
                atendidas++;
                emergenciasAtendidas.add(emergencia.getDescripcion() + " en " + emergencia.getUbicacion());
                
                // Calcular tiempo de respuesta real
                if (emergencia.getTiempoFinAtencion() > 0 && emergencia.getTiempoInicioAtencion() > 0) {
                    double tiempoReal = (emergencia.getTiempoFinAtencion() - emergencia.getTiempoInicioAtencion()) / 1000.0 / 60.0; // en minutos
                    tiemposRespuesta.add(tiempoReal);
                }
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
    
        // Calcular métricas usando StatisticsSystem
        double tiempoPromedio = StatisticsSystem.calcularTiempoPromedio(tiemposRespuesta);
        double tasaExito = StatisticsSystem.calcularTasaExitoEmergencias(atendidas, totalEmergencias);
        
        // Calcular eficiencia de recursos
        int totalRecursosDisponibles = listaRecursos.size();
        int recursosUtilizados = recursosUsados.values().stream().mapToInt(Integer::intValue).sum();
        double eficienciaRecursos = StatisticsSystem.calcularEficienciaRecursos(recursosUtilizados, totalRecursosDisponibles);
    
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ESTADÍSTICAS DEL DÍA");
        System.out.println("=".repeat(60));
        
        System.out.println("\nEMERGENCIAS:");
        System.out.println("   • Total registradas: " + totalEmergencias);
        System.out.println("   • Atendidas: " + atendidas);
        System.out.println("   • Pendientes: " + pendientes);
        System.out.println("   • Tasa de éxito: " + String.format("%.1f", tasaExito) + "%");
        
        System.out.println("\nTIEMPOS DE RESPUESTA:");
        System.out.println("   • Tiempo promedio: " + String.format("%.1f", tiempoPromedio) + " minutos");
        System.out.println("   • Emergencias con tiempo registrado: " + tiemposRespuesta.size());
        
        System.out.println("\nRECURSOS:");
        System.out.println("   • Total disponibles: " + totalRecursosDisponibles);
        System.out.println("   • Utilizados: " + recursosUtilizados);
        System.out.println("   • Eficiencia: " + String.format("%.1f", eficienciaRecursos) + "%");
        
        if (!recursosUsados.isEmpty()) {
            System.out.println("   • Desglose por tipo:");
            for (Map.Entry<String, Integer> entry : recursosUsados.entrySet()) {
                System.out.println("     - " + entry.getKey() + ": " + entry.getValue());
            }
        }
        
        System.out.println("\n" + "=".repeat(60));
        
        // Generar y guardar reporte
        if (!emergenciasAtendidas.isEmpty()) {
            SystemReport.generarReporte(emergenciasAtendidas);
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

    public void cerrarJornada() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🏁 CERRANDO JORNADA DEL SISTEMA DE EMERGENCIAS");
        System.out.println("=".repeat(60));
        
        // Mostrar estadísticas finales
        mostrarEstadisticasDelDia();
        
        // Registrar todas las emergencias del día
        List<String> registrosEmergencias = new ArrayList<>();
        for (Emergencia emergencia : listaEmergencias) {
            String registro = String.format("Emergencia: %s | Ubicación: %s | Gravedad: %s | Estado: %s | Tiempo: %d min",
                emergencia.getTipo(),
                emergencia.getUbicacion(),
                emergencia.getNivelGravedad(),
                emergencia.isAtendida() ? "Atendida" : "Pendiente",
                emergencia.getTiempoReespuesta());
            registrosEmergencias.add(registro);
        }
        
        if (!registrosEmergencias.isEmpty()) {
            SystemRegistration.registrarEmergencias(registrosEmergencias);
            System.out.println("\n📝 Registros guardados en el archivo de historial.");
        }
        
        // Preparar sistema para siguiente ciclo
        prepararSiguienteCiclo();
        
        System.out.println("\n✅ Jornada cerrada exitosamente.");
        System.out.println("🔄 Sistema preparado para el siguiente ciclo.");
        System.out.println("=".repeat(60));
    }
    
    private void prepararSiguienteCiclo() {
        // Liberar todos los recursos
        for (IServicioEmergencia recurso : listaRecursos) {
            if (!recurso.estaDisponible()) {
                recurso.liberarRecurso();
            }
        }
        
        // Marcar emergencias pendientes como no atendidas para el siguiente ciclo
        for (Emergencia emergencia : listaEmergencias) {
            if (!emergencia.isAtendida()) {
                emergencia.setAtendida(false);
            }
        }
        
        System.out.println("🔄 Recursos liberados y sistema reinicializado.");
    }
}
