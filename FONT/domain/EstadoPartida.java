package domain;

public class EstadoPartida {
    
    /**
     * Estado de la partida que será o bien "Running" o "Puased" o "Saved"
     */
    private String estado; 

    /**
     * Constructora de la clase EstadoPartida
     * @param estado Estado de la partida que al iniciarla será "Running"
     */
    public EstadoPartida() {
        this.estado = "Running";
    }

    /**
     * Devuelve el estado de la partida
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Introduce el estado de la partida
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

}