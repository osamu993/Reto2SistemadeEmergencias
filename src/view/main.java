package view;

import controller.SistemaEmergencias;

public class Main {
    public static void main(String[] args) {
        // Inicializar el sistema de emergencias
        SistemaEmergencias sistemaEmergencias = new SistemaEmergencias();

        // Mostrar menú del sistema
        MenuSistemaEmergencia menu = new MenuSistemaEmergencia(sistemaEmergencias);
        menu.mostrarMenu();
    }
}
