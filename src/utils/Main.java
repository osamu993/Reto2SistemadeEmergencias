package utils;

import controller.SistemaEmergencias;
import view.MenuSistemaEmergencia;

/**
 * Clase principal que ejecuta el sistema de emergencias.
 */
public class Main {
    public static void main(String[] args) {
        
        System.out.println("Iniciando el Sistema de Emergencias...\n");
        SistemaEmergencias sistemaEmergencias = SistemaEmergencias.getInstance();
        MenuSistemaEmergencia menu = new MenuSistemaEmergencia(sistemaEmergencias);
        menu.mostrarMenu();

        System.out.println("\nGracias por usar el Sistema de Emergencias. ¡Hasta luego!\n");
    }
} 