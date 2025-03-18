package view;

import controller.SistemaEmergencias;

//Clase principal que ejecuta el sistema de emergencias.
public class main {

    public static void main(String[] args) {

        SistemaEmergencias sistemaEmergencias = new SistemaEmergencias();
        MenuSistemaEmergencia menu = new MenuSistemaEmergencia(sistemaEmergencias);
        menu.mostrarMenu();
   
    }
}
