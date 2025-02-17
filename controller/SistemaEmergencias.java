package controller;

import view.MenuSistemaEmergencia;


 //Clase principal para gestionar la ejecución del sistema de emergencias.
 
public class SistemaEmergencias {
    public static void main(String[] args) {
        // Crear instancia del menú
        MenuSistemaEmergencia menu = new MenuSistemaEmergencia();
        
        // Iniciar el menú de usuario
        menu.mostrarMenu();
    }
}
