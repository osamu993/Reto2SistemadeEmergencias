package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


 //Clase para gestionar el registro de emergencias en el sistema.
 
public class SystemRegistration {
    private static final String NOMBRE_ARCHIVO = "registro_emergencias.txt";

    /**
     * Guarda una emergencia en el archivo de registro.
     * @param emergencia Información de la emergencia.
     */
    public static void registrarEmergencia(String emergencia) {
        try (FileWriter fw = new FileWriter(NOMBRE_ARCHIVO, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(emergencia);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    /**
     * Guarda una lista de emergencias en el archivo de registro.
     * @param emergencias Lista de emergencias a registrar.
     */
    public static void registrarEmergencias(List<String> emergencias) {
        try (FileWriter fw = new FileWriter(NOMBRE_ARCHIVO, true);
             PrintWriter pw = new PrintWriter(fw)) {
            for (String emergencia : emergencias) {
                pw.println(emergencia);
            }
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
}
