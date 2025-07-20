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
        System.out.println("\n\n--- Sistema de Emergencias ---\n");
        System.out.println("1. Registrar emergencia");
        System.out.println("2. Gestionar emergencias");
        System.out.println("3. Mostrar recursos disponibles");
        System.out.println("4. Mostrar estadísticas del día");
        System.out.println("5. Configurar estrategia de asignación");
        System.out.println("6. Ver reportes históricos");
        System.out.println("7. Cerrar jornada");
        System.out.println("8. Salir");
        System.out.print("\n\nSeleccione una opción: ");
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

        // Asegurar que la emergencia esté marcada como pendiente desde el inicio
        emergencia.setAtendida(false);
        emergencia.setTiempoInicioAtencion(System.currentTimeMillis());

        listaEmergencias.add(emergencia);

        // Notificar a los observadores ANTES de asignar recursos
        notificarObservers(emergencia);

        System.out.println("\n" + "=".repeat(60));
        System.out.println("EMERGENCIA REGISTRADA EXITOSAMENTE");
        System.out.println("=".repeat(60));
        System.out.println("Tipo: " + tipo);
        System.out.println("Ubicación: " + ubicacion);
        System.out.println("Gravedad: " + nivel);
        System.out.println("Tiempo estimado de respuesta: " + tiempoRespuesta + " minutos");
        System.out.println("Estado: PENDIENTE");

        // Asignar múltiples recursos según el tipo de emergencia
        List<IServicioEmergencia> recursosAsignados = asignarRecursosSegunTipo(tipo, emergencia);
        if (!recursosAsignados.isEmpty()) {
            for (IServicioEmergencia recurso : recursosAsignados) {
                emergencia.asignarRecurso(recurso);
            }

            System.out.println("\nRECURSOS ASIGNADOS:\n");
            for (IServicioEmergencia recurso : recursosAsignados) {
                System.out.println(recurso.getId() + " (" + recurso.getClass().getSimpleName() + ")");
                System.out.println("  Estado: EN CAMINO");
                System.out.println("  Tiempo estimado de llegada: " + tiempoRespuesta + " minutos");
            }
            System.out.println("\nLa emergencia está siendo atendida...");

            // Simular el tiempo de atención en un hilo separado
            simularAtencionEmergencia(emergencia, recursosAsignados);
        } else {
            System.out.println("\nERROR: No se pudo asignar ningún recurso a la emergencia.");
            System.out.println("La emergencia quedará en espera hasta que haya recursos disponibles.");
        }
        System.out.println("=".repeat(60));
    }

    public void listarEmergencias() {
        List<Emergencia> emergenciasActivas = listaEmergencias.stream()
                .filter(e -> !e.isAtendida())
                .toList();

        if (emergenciasActivas.isEmpty()) {
            System.out.println("\nNo hay emergencias activas.");
        } else {
            System.out.println("\n--- Emergencias Activas ---");
            for (Emergencia emergencia : emergenciasActivas) {
                System.out.println("- " + emergencia.getTipo() + " en zona " + emergencia.getUbicacion() +
                        " (Gravedad: " + emergencia.getNivelGravedad() + ")");
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
                    double tiempoReal = (emergencia.getTiempoFinAtencion() - emergencia.getTiempoInicioAtencion())
                            / 1000.0 / 60.0; // en minutos
                    tiemposRespuesta.add(tiempoReal);
                }
            } else {
                pendientes++;
            }
        }

        // Contar recursos reales utilizados (solo de emergencias atendidas)
        for (Emergencia emergencia : listaEmergencias) {
            if (emergencia.isAtendida()) {
                // Contar recursos reales asignados a esta emergencia
                // Como no tenemos acceso directo a los recursos asignados, usamos el tipo de
                // emergencia
                String tipoEmergencia = emergencia.getTipo().toUpperCase();
                switch (tipoEmergencia) {
                    case "INCENDIO":
                        recursosUsados.put("BOMBEROS", recursosUsados.getOrDefault("BOMBEROS", 0) + 2);
                        recursosUsados.put("AMBULANCIA", recursosUsados.getOrDefault("AMBULANCIA", 0) + 1);
                        break;
                    case "ROBO":
                        recursosUsados.put("POLICIA", recursosUsados.getOrDefault("POLICIA", 0) + 2);
                        recursosUsados.put("AMBULANCIA", recursosUsados.getOrDefault("AMBULANCIA", 0) + 1);
                        break;
                    case "ACCIDENTE_VEHICULAR":
                        recursosUsados.put("POLICIA", recursosUsados.getOrDefault("POLICIA", 0) + 1);
                        recursosUsados.put("AMBULANCIA", recursosUsados.getOrDefault("AMBULANCIA", 0) + 2);
                        recursosUsados.put("UNIDADRESCATE", recursosUsados.getOrDefault("UNIDADRESCATE", 0) + 1);
                        break;
                }
            }
        }

        // Calcular métricas usando StatisticsSystem
        double tiempoPromedio = StatisticsSystem.calcularTiempoPromedio(tiemposRespuesta);
        double tasaExito = StatisticsSystem.calcularTasaExitoEmergencias(atendidas, totalEmergencias);

        // Calcular eficiencia de recursos
        int totalRecursosDisponibles = 8; // Total de recursos en el sistema (B1, B2, P1, P2, A1, A2, R1, R2)
        int recursosUtilizados = recursosUsados.values().stream().mapToInt(Integer::intValue).sum();
        double eficienciaRecursos = StatisticsSystem.calcularEficienciaRecursos(recursosUtilizados,
                totalRecursosDisponibles);

        System.out.println("\n" + "=".repeat(60));
        System.out.println("ESTADÍSTICAS DEL DÍA");
        System.out.println("=".repeat(60));

        System.out.println("\nEMERGENCIAS:\n");
        System.out.println("Total registradas: " + totalEmergencias);
        System.out.println("Atendidas: " + atendidas);
        System.out.println("Pendientes: " + pendientes);
        System.out.println("Tasa de éxito: " + String.format("%.1f", tasaExito) + "%");

        System.out.println("\nTIEMPOS DE RESPUESTA:");
        System.out.println("\nTiempo promedio: " + String.format("%.1f", tiempoPromedio) + " minutos");
        System.out.println("Emergencias con tiempo registrado: " + tiemposRespuesta.size());

        System.out.println("\nRECURSOS:\n");
        System.out.println("Total disponibles: " + totalRecursosDisponibles);
        System.out.println("Utilizados: " + recursosUtilizados);
        System.out.println("Eficiencia: " + String.format("%.1f", eficienciaRecursos) + "%");

        if (!recursosUsados.isEmpty()) {
            System.out.println("\nDesglose por tipo:\n");
            for (Map.Entry<String, Integer> entry : recursosUsados.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
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
        System.out.println("CERRANDO JORNADA DEL SISTEMA DE EMERGENCIAS");
        System.out.println("=".repeat(60));

        // Mostrar estadísticas finales
        mostrarEstadisticasDelDia();

        // Registrar todas las emergencias del día
        List<String> registrosEmergencias = new ArrayList<>();
        for (Emergencia emergencia : listaEmergencias) {
            String registro = String.format(
                    "Emergencia: %s | Ubicación: %s | Gravedad: %s | Estado: %s | Tiempo: %d min",
                    emergencia.getTipo(),
                    emergencia.getUbicacion(),
                    emergencia.getNivelGravedad(),
                    emergencia.isAtendida() ? "Atendida" : "Pendiente",
                    emergencia.getTiempoReespuesta());
            registrosEmergencias.add(registro);
        }

        if (!registrosEmergencias.isEmpty()) {
            SystemRegistration.registrarEmergencias(registrosEmergencias);
            System.out.println("\nRegistros guardados en el archivo de historial.");
        }

        // Preparar sistema para siguiente ciclo
        prepararSiguienteCiclo();

        System.out.println("\nJornada cerrada exitosamente.");
        System.out.println("Sistema preparado para el siguiente ciclo.");
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

        System.out.println("Recursos liberados y sistema reinicializado.");
    }

    /**
     * Asigna recursos según el tipo de emergencia
     */
    private List<IServicioEmergencia> asignarRecursosSegunTipo(String tipo, Emergencia emergencia) {
        List<IServicioEmergencia> recursosAsignados = new ArrayList<>();

        switch (tipo.toUpperCase()) {
            case "INCENDIO":
                // Para incendios: 2 Bomberos + 1 Ambulancia
                recursosAsignados.addAll(gestorRecursos.asignarRecursosPorTipo("Bomberos", 2));
                recursosAsignados.addAll(gestorRecursos.asignarRecursosPorTipo("Ambulancia", 1));
                break;

            case "ROBO":
                // Para robos: 2 Policía + 1 Ambulancia
                recursosAsignados.addAll(gestorRecursos.asignarRecursosPorTipo("Policia", 2));
                recursosAsignados.addAll(gestorRecursos.asignarRecursosPorTipo("Ambulancia", 1));
                break;

            case "ACCIDENTE_VEHICULAR":
                // Para accidentes: 1 Policía + 2 Ambulancia + 1 Unidad de Rescate
                recursosAsignados.addAll(gestorRecursos.asignarRecursosPorTipo("Policia", 1));
                recursosAsignados.addAll(gestorRecursos.asignarRecursosPorTipo("Ambulancia", 2));
                recursosAsignados.addAll(gestorRecursos.asignarRecursosPorTipo("UnidadRescate", 1));
                break;

            default:
                // Recurso por defecto
                IServicioEmergencia recursoDefault = gestorRecursos.asignarRecurso(emergencia);
                if (recursoDefault != null)
                    recursosAsignados.add(recursoDefault);
                break;
        }

        return recursosAsignados;
    }

    /**
     * Simula el tiempo de atención de una emergencia en un hilo separado
     */
    private void simularAtencionEmergencia(Emergencia emergencia, List<IServicioEmergencia> recursos) {
        Thread hiloAtencion = new Thread(() -> {
            try {
                // Calcular tiempo mínimo real: 80 segundos + tiempo calculado del sistema
                int tiempoMinimoReal = 80000; // 80 segundos en milisegundos
                int tiempoCalculado = emergencia.getTiempoReespuesta() * 1000; // Convertir minutos a milisegundos
                int tiempoTotal = Math.max(tiempoMinimoReal, tiempoCalculado);

                // La emergencia permanece PENDIENTE durante todo este tiempo
                Thread.sleep(tiempoTotal);

                // SOLO DESPUÉS del tiempo de espera, marcar como atendida
                emergencia.setAtendida(true);
                emergencia.setTiempoFinAtencion(System.currentTimeMillis());

                // Liberar recursos
                for (IServicioEmergencia recurso : recursos) {
                    gestorRecursos.liberarRecursoCompleto(recurso);
                }

                System.out.println(
                        "\nEMERGENCIA ATENDIDA: " + emergencia.getTipo() + " en zona:" + emergencia.getUbicacion());
                System.out.println("\nRecursos liberados:\n");
                for (IServicioEmergencia recurso : recursos) {
                    System.out.println(recurso.getId());
                }
                System.out.println("   Tiempo de atención: " + emergencia.getTiempoReespuesta() + " minutos");

                // Notificar a los observadores
                notificarEmergenciaResuelta(emergencia);

            } catch (InterruptedException e) {
                System.out.println("Error en la simulación de atención: " + e.getMessage());
            }
        });

        hiloAtencion.start();
    }
}
