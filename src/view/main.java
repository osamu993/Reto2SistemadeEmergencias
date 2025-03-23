package view;

import controller.SistemaEmergencias;

<<<<<<< HEAD
=======
/**
 * Clase principal que ejecuta el sistema de emergencias.
 */
>>>>>>> a643c021564c78432de695379ef6684efa502cec
public class Main {
    public static void main(String[] args) {
<<<<<<< HEAD
        // Inicializar el sistema de emergencias
        SistemaEmergencias sistemaEmergencias = new SistemaEmergencias();

        // Mostrar menú del sistema
        MenuSistemaEmergencia menu = new MenuSistemaEmergencia(sistemaEmergencias);
        menu.mostrarMenu();
=======
        System.out.println("🚨 Iniciando el Sistema de Emergencias...\n");
        SistemaEmergencias sistemaEmergencias = SistemaEmergencias.getInstance();
        MenuSistemaEmergencia menu = new MenuSistemaEmergencia(sistemaEmergencias);
        menu.mostrarMenu();

        System.out.println("🔥 Gracias por usar el Sistema de Emergencias. ¡Hasta luego!");
>>>>>>> a643c021564c78432de695379ef6684efa502cec
    }
}
