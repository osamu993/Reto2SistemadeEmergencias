package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import model.Emergencia;


 //Clase para gestionar el registro de emergencias en el sistema.
 
public class SystemRegistration {
    private static final String NOMBRE_ARCHIVO = "registro_emergencias.txt";
    private static List<Emergencia> historialEmergencias = new ArrayList<>();

    /**
     * Guarda una emergencia en el archivo de registro.
     * @param emergencia Información de la emergencia.
     */
    public static void registrarEmergencia(Emergencia emergencia) {
        historialEmergencias.add(emergencia);
        guardarEnArchivo(emergencia.toString());
    }

    /**
     * Guarda una lista de emergencias en el archivo de registro.
     * @param emergencias Lista de emergencias a registrar.
     */
    public static void registrarEmergencias(List<Emergencia> emergencias) {
        historialEmergencias.addAll(emergencias);
        for (Emergencia emergencia : emergencias) {
            guardarEnArchivo(emergencia.toString());
        }
    }

    /**
     * Devuelve el historial de emergencias en memoria.
     * @return Lista de emergencias registradas en memoria.
     */
    public static List<Emergencia> obtenerHistorial() {
        return new ArrayList<>(historialEmergencias);
    }

    /**
     * Limpia el historial de emergencias en memoria.
     */
    public static void limpiarHistorial() {
        historialEmergencias.clear();
    }

    /**
     * Guarda una emergencia en el archivo de registro.
     * @param emergencia Información de la emergencia como String.
     */
    private static void guardarEnArchivo(String emergencia) {
        try (FileWriter fw = new FileWriter(NOMBRE_ARCHIVO, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(emergencia);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
}
