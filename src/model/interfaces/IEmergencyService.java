package model.interfaces;


 //Interfaz que define los métodos base para los servicios de emergencia.
 
public interface IEmergencyService {
    /**
     * Método para desplegar una unidad al lugar de la emergencia.
     * @param ubicacion Ubicación de la emergencia.
     */
    void desplegarUnidad(String ubicacion);
    
    
     //Método para evaluar la situación en la emergencia.
     
    void evaluarSituacion();
    
    /**
     * Método para obtener el estado del servicio de emergencia.
     * @return Estado actual del servicio en formato de texto.
     */
    String getEstado();
}

