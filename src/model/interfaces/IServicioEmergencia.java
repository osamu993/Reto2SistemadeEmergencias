package model.interfaces;

public interface IServicioEmergencia {

    String getId();
    int getPersonalDisponible();
    int getCombustible();
    boolean estaDisponible();
    void asignarPersonal(int cantidad);
    void liberarPersonal(int cantidad);
    void gastarCombustible(int cantidad);
    void tanquearCombustible(int cantidad);
    //void atenderEmergencia(Emergencia emergencia); 
   
        /**
         * Método para desplegar una unidad al lugar de la emergencia.
         * @param ubicacion Ubicación de la emergencia.
         */
        void desplegarUnidad(String ubicacion);
        
        /**
         * Método para evaluar la situación en la emergencia.
         */
        void evaluarSituacion();
        
        /**
         * Método para obtener el estado del servicio de emergencia.
         * @return Estado actual del servicio en formato de texto.
         */
        String getEstado();
        
        /**
         * Método para liberar el recurso después de atender una emergencia.
         */
        void liberarRecurso();

        
    }

    

