package utils;

import controller.SistemaEmergencias;
import view.MenuSistemaEmergencia;


/**
 * Clase principal que ejecuta el sistema de gestión de emergencias urbanas.
 * Sirve como punto de entrada del programa.
 */
public class Main {
    public static void main(String[] args) {
        
        System.out.println("Iniciando el Sistema de Emergencias...\n");
        // Se obtiene la instancia única del sistema (patrón Singleton)
        SistemaEmergencias sistemaEmergencias = SistemaEmergencias.getInstance();
          // Se crea el menú principal y se le pasa el sistema como dependencia
        MenuSistemaEmergencia menu = new MenuSistemaEmergencia(sistemaEmergencias);
        // Se muestra el menú al usuario para interactuar con el sistema
        menu.mostrarMenu();

        System.out.println("\nGracias por usar el Sistema de Emergencias. ¡Hasta luego!\n");
    }
} 