package utils;

<<<<<<< HEAD
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import model.Emergencia;


 //Clase para gestionar el registro de emergencias en el sistema.
 
=======
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase para gestionar el registro de emergencias en el sistema.
 */
>>>>>>> a643c021564c78432de695379ef6684efa502cec
public class SystemRegistration {
    private static final String NOMBRE_ARCHIVO = "registro_emergencias.txt";
    private static List<Emergencia> historialEmergencias = new ArrayList<>();

    /**
     * Guarda una emergencia en el archivo de registro.
     * @param emergencia Información de la emergencia.
     */
<<<<<<< HEAD
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
=======
    public static void registrarEmergencia(String emergencia) {
        if (emergencia == null || emergencia.trim().isEmpty()) {
            System.err.println("⚠️ No se puede registrar una emergencia vacía.");
            return;
        }
>>>>>>> a643c021564c78432de695379ef6684efa502cec
        try (FileWriter fw = new FileWriter(NOMBRE_ARCHIVO, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(emergencia);
        } catch (IOException e) {
            System.err.println("❌ Error al escribir en el archivo: " + e.getMessage());
        }
    }
<<<<<<< HEAD
=======

    /**
     * Guarda una lista de emergencias en el archivo de registro.
     * @param emergencias Lista de emergencias a registrar.
     */
    public static void registrarEmergencias(List<String> emergencias) {
        if (emergencias == null || emergencias.isEmpty()) {
            System.err.println("⚠️ No hay emergencias para registrar.");
            return;
        }
        try (FileWriter fw = new FileWriter(NOMBRE_ARCHIVO, true);
             PrintWriter pw = new PrintWriter(fw)) {
            for (String emergencia : emergencias) {
                if (emergencia != null && !emergencia.trim().isEmpty()) {
                    pw.println(emergencia);
                }
            }
        } catch (IOException e) {
            System.err.println("❌ Error al escribir en el archivo: " + e.getMessage());
        }
    }

    /**
     * Lee y devuelve todas las emergencias registradas en el archivo.
     * @return Lista de emergencias registradas.
     */
    public static List<String> leerRegistros() {
        List<String> registros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(NOMBRE_ARCHIVO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                registros.add(linea);
            }
        } catch (IOException e) {
            System.err.println("❌ Error al leer el archivo: " + e.getMessage());
        }
        return registros;
    }

    /**
     * Limpia el contenido del archivo de registro.
     */
    public static void limpiarRegistro() {
        try (FileWriter fw = new FileWriter(NOMBRE_ARCHIVO, false)) { // Modo "false" para sobrescribir
            System.out.println("🗑️ Registro de emergencias limpiado correctamente.");
        } catch (IOException e) {
            System.err.println("❌ Error al limpiar el archivo: " + e.getMessage());
        }
    }
>>>>>>> a643c021564c78432de695379ef6684efa502cec
}
