package view;
import java.util.Scanner;
import controller.SistemaEmergencias;


 //Clase que representa el menú del sistema de emergencias.
 
public class MenuSistemaEmergencia {
    private Scanner scanner;

    
     // Constructor que inicializa el menú con el controlador.
     
    public MenuSistemaEmergencia() {
        this.scanner = new Scanner(System.in);
        this.controller = new SistemaEmergencias();
    }

    
     //Método para mostrar el menú de opciones al usuario.
     
    public void mostrarMenu() {
        int opcion;
        do {
            System.out.println("\n--- Sistema de Emergencias ---");
            System.out.println("1. Registrar emergencia");
            System.out.println("2. Mostrar emergencias activas");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    registrarEmergencia();
                    break;
                case 2:
                    mostrarEmergencias();
                    break;
                case 3:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida, intente nuevamente.");
            }
        } while (opcion != 3);
    }

    
     // Método para registrar una emergencia.
     
    private void registrarEmergencia() {
        System.out.println("\nRegistro de nueva emergencia...");
        // Aquí se llamará al controlador para manejar la lógica de registro
    }

    
     //Método para mostrar emergencias activas.
     
    private void mostrarEmergencias() {
        System.out.println("\nListado de emergencias activas...");
        // Aquí se llamará al controlador para obtener las emergencias activas
    }
}
