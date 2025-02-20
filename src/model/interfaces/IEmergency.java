package interfaces;


 // Interfaz que define los métodos base para una emergencia.

public interface IEmergency{
    
     //Método para atender la emergencia.
     
    void atenderEmergencia();
    
    
     //Método para asignar recursos a la emergencia.
     
    void asignarRecursos();
    
    /**
     * Obtiene la descripción de la emergencia.
     * @return Descripción en formato de texto.
     */
    String getDescripcion();
}
