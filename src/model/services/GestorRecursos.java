// Esta clase se encarga de gestionar todos los recursos de emergencia del sistema:
// bomberos, ambulancias, policías, unidades de rescate, etc.
// Controla su disponibilidad, asignación, liberación y aplica diferentes estrategias
// para decidir qué recurso asignar a cada emergencia.
package model.services;

import java.util.ArrayList;
import java.util.List;
import model.Emergencia;
import model.interfaces.IServicioEmergencia;
import model.strategy.IEstrategyAsignacion;

    /**
     * Constructor principal. Inicializa la lista de recursos con unidades predeterminadas.
     */
public class GestorRecursos {
    private List<IServicioEmergencia> recursosDisponibles = new ArrayList<>();
    private IEstrategyAsignacion estrategiaAsignacion;
    

    public GestorRecursos() {
        inicializarRecursos();
    }
      /**
     * Agrega un nuevo recurso a la lista de recursos disponibles.
     * @param recurso Recurso a registrar.
     */
    public void agregarRecursos(IServicioEmergencia recurso) {
        recursosDisponibles.add(recurso);
    }
     /**
     * Asigna un recurso que tenga el personal y combustible necesarios.
     * @return El recurso asignado o null si no se encontró ninguno disponible.
     */
    public IServicioEmergencia asignarRecurso(int personalRequerido, int combustibleRequerido) {
        for (IServicioEmergencia recurso : recursosDisponibles) {
            if (recurso.estaDisponible() && recurso.getPersonalDisponible() >= personalRequerido
                    && recurso.getCombustible() >= combustibleRequerido) {
                recurso.asignarPersonal(personalRequerido);
                recurso.gastarCombustible(combustibleRequerido);
                recurso.desplegarUnidad(recurso.getUbicacion()); // Enviar recurso correctamente
                return recurso;
            }
        }
        return null; // No hay recursos disponibles
    }
     /**
     * Retorna el primer recurso disponible. Si no hay, retorna uno no disponible como último recurso.
     */
    public IServicioEmergencia obtenerRecursoDisponible() {
        for (IServicioEmergencia recurso : recursosDisponibles) {
            if (recurso.estaDisponible()) { // Busca primero un recurso disponible
                return recurso;
            }
        }
        // Si no hay disponibles, busca uno ocupado para liberar
        for (IServicioEmergencia recurso : recursosDisponibles) {
            if (!recurso.estaDisponible()) {
                return recurso;
            }
        }
        return null; // No hay recursos disponibles
    }
     /**
     * Asigna un recurso disponible en una estación específica.
     * @param ubicacion Zona de la emergencia.
     * @param estacionAsignada Estación desde donde debe salir el recurso.
     * @param tipoRecurso Tipo de recurso requerido (Bomberos, Policia, etc.).
     */
    public IServicioEmergencia asignarRecursoDesde(String ubicacion, String estacionAsignada, String tipoRecurso) {

        if (estacionAsignada == null) {
            System.out.println("No se puede asignar recurso.");
            return null;
        }
        

        for (IServicioEmergencia recurso : recursosDisponibles) {
            if (recurso.estaDisponible() &&
                    recurso.getUbicacion().equalsIgnoreCase(estacionAsignada) &&
                    recurso.getClass().getSimpleName().toUpperCase().contains(tipoRecurso.toUpperCase())) {

                recurso.desplegarUnidad(ubicacion);
                return recurso;
            }
        }

        System.out.println("No hay suficientes recursos de: " + tipoRecurso + " en la estación: " + estacionAsignada);
        return null;
    }
      /**
     * Libera parcialmente un recurso, devolviendo personal específico.
     */
    public void liberarRecurso(IServicioEmergencia recurso, int personalLiberado) {
        recurso.liberarPersonal(personalLiberado);
        recurso.liberarRecurso(); // Se encarga de marcar el recurso como disponible
    }
    /**
     * Libera completamente el recurso. Se asume por defecto que tenía 5 personas asignadas.
     */
    public void liberarRecursoCompleto(IServicioEmergencia recurso) {
        int personalTotal = 5; 
        recurso.liberarPersonal(personalTotal);
        recurso.liberarRecurso();
    }
       /**
     * Muestra en consola todos los recursos registrados, estén o no disponibles.
     */
    public void mostrarRecursos() {
        for (IServicioEmergencia recurso : recursosDisponibles) {
            System.out.println("ID: " + recurso.getId() + " (Personal: " + recurso.getPersonalDisponible()
                    + ", Combustible: " + recurso.getCombustible() + " galones)");
        }
    }
      /**
     * Muestra únicamente los recursos que están disponibles.
     */
    public void mostrarRecursosDisponibles() {
        System.out.println("\nRecursos disponibles:");
    
        boolean hayDisponibles = false;
    
        for (IServicioEmergencia recurso : recursosDisponibles) {
            if (recurso.estaDisponible()) {
                System.out.println("ID: " + recurso.getId() + " | Tipo: " + recurso.getClass().getSimpleName() + " | Ubicación: " + recurso.getUbicacion());
                hayDisponibles = true;
            }
        }
    
        if (!hayDisponibles) {
            System.out.println("No hay recursos disponibles en este momento.");
        }
    }
      /**
     * Define la estrategia a utilizar para asignación de recursos.
     * Esto permite aplicar diferentes criterios como cercanía o gravedad.
     */
    public void setEstrategiaAsignacion(IEstrategyAsignacion estrategia) {
        this.estrategiaAsignacion = estrategia;
    }
    /**
     * Aplica la estrategia definida para asignar un recurso a una emergencia.
     */
    public IServicioEmergencia asignarRecurso(Emergencia emergencia) {
        if (estrategiaAsignacion != null) {
            List<IServicioEmergencia> recursos = new ArrayList<>(recursosDisponibles);
            return estrategiaAsignacion.asignarRecurso(recursos, emergencia);
        }
        return null; // No se asigna recurso
    }
    /**
     * Asigna un único recurso que sea del tipo solicitado (por ejemplo: "Policia").
     */
    public IServicioEmergencia asignarRecursoPorTipo(String tipoRecurso) {
        for (IServicioEmergencia recurso : recursosDisponibles) {
            if (recurso.estaDisponible() && 
                recurso.getClass().getSimpleName().equalsIgnoreCase(tipoRecurso)) {
                // Marcar el recurso como ocupado asignando todo el personal disponible
                recurso.asignarPersonal(recurso.getPersonalDisponible());
                recurso.desplegarUnidad("Emergencia");
                return recurso;
            }
        }
        return null; // No hay recursos disponibles de ese tipo
    }

    /**
     * Asigna múltiples recursos de un tipo específico
     */
    public List<IServicioEmergencia> asignarRecursosPorTipo(String tipoRecurso, int cantidad) {
        List<IServicioEmergencia> recursosAsignados = new ArrayList<>();
        int asignados = 0;
        
        for (IServicioEmergencia recurso : recursosDisponibles) {
            if (asignados >= cantidad) break;
            
            if (recurso.estaDisponible() && 
                recurso.getClass().getSimpleName().equalsIgnoreCase(tipoRecurso)) {
                // Marcar el recurso como ocupado asignando todo el personal disponible
                recurso.asignarPersonal(recurso.getPersonalDisponible());
                recurso.desplegarUnidad("Emergencia");
                recursosAsignados.add(recurso);
                asignados++;
            }
        }
        
        return recursosAsignados;
    }
    /**
     * Inicializa todos los recursos del sistema (bomberos, policías, ambulancias, rescates).
     * Este método se llama en el constructor y actúa como carga inicial del sistema.
     */
    private void inicializarRecursos() {
        recursosDisponibles.add(new Bomberos("B1", "Bomberos"));
        recursosDisponibles.add(new Bomberos("B2", "Bomberos"));
    
        recursosDisponibles.add(new Policia("P1", "Policia"));
        recursosDisponibles.add(new Policia("P2", "Policia"));
    
        recursosDisponibles.add(new Ambulancia("A1", "Hospital"));
        recursosDisponibles.add(new Ambulancia("A2", "Hospital"));
    
        recursosDisponibles.add(new UnidadRescate("R1", "Rescate"));
        recursosDisponibles.add(new UnidadRescate("R2", "Rescate"));
    }
}
