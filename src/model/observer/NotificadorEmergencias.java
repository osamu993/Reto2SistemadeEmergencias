package model.observer;

import model.Emergencia;

/**
 * Clase concreta que implementa ObserverEmergencias para notificar eventos
 * de emergencias al usuario y al sistema.
 */
public class NotificadorEmergencias implements ObserverEmergencias {

    @Override
    public void onNuevasEmergencias(Emergencia emergencia) {
        System.out.println("\n🚨 NUEVA EMERGENCIA REGISTRADA:");
        System.out.println("   Tipo: " + emergencia.getTipo());
        System.out.println("   Ubicación: " + emergencia.getUbicacion());
        System.out.println("   Gravedad: " + emergencia.getNivelGravedad());
        System.out.println("   Tiempo de respuesta estimado: " + emergencia.getTiempoReespuesta() + " minutos");
        System.out.println("   Estado: " + (emergencia.isAtendida() ? "Atendida" : "Pendiente"));
        System.out.println("   " + "=".repeat(50));
    }

    @Override
    public void onEmergenciaResuelta(Emergencia emergencia) {
        System.out.println("\n✅ EMERGENCIA RESUELTA:");
        System.out.println("   Tipo: " + emergencia.getTipo());
        System.out.println("   Ubicación: " + emergencia.getUbicacion());
        System.out.println("   Gravedad: " + emergencia.getNivelGravedad());
        System.out.println("   Tiempo total de atención: " + 
            ((emergencia.getTiempoFinAtencion() - emergencia.getTiempoInicioAtencion()) / 1000) + " segundos");
        System.out.println("   " + "=".repeat(50));
    }

    @Override
    public void onRecursosReasignados() {
        System.out.println("\n🔄 RECURSOS REASIGNADOS:");
        System.out.println("   Se han reasignado recursos en el sistema.");
        System.out.println("   Verifique el estado actual de los recursos.");
        System.out.println("   " + "=".repeat(50));
    }
} 