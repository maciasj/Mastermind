package main.domain;

import java.sql.Date;
import java.util.ArrayList;


/**
*Clase Usuario.
*/
public class Usuario {
	
	/** 
	*Atributos 
	*/
	
	private String username;
	private Integer maxScore;
	private ArrayList<Partida> partidas;

	/** 
	*Constructora 
	*/
	
	public Usuario(String username) {
		this.username = username;
		this.maxScore = 0;
		this.partidas = new ArrayList<>();
	}


	/**
	 * Devuelve el nombre de usuario.
	 */
	public String getUsername() {
		return username;
	}
	
	public Usuario getUsuario() {
		return this;
	}

	/**
     * Devuelve la puntuación más alta que ha conseguido el jugador
     */
    public int getMaxScore() {
        return maxScore;
    }

	/**
     * Establece la puntuación más alta que ha conseguido el jugador
     */
    public void setMaxScore(int maxScore) {
        if (maxScore > this.maxScore){
        this.maxScore = maxScore;
        }
    }

	/**
	 * Añade una partida al ArrayList de partidas del usuario
	 */
	public void addPartida(Partida p) {
		this.partidas.add(p);
	}

	
	/**
	 * Borra una partida del ArrayList de partidas del usuario
	 */
	public void deletePartida(Date fecha){
		Partida p = this.getPartida(fecha);
		if (p != null) {
			this.partidas.remove(p);
		}
	}

	/**
	 * Devuelve una partida del ArrayList de partidas del usuario
	 */
	public Partida getPartida(Date fecha){
		for (Partida p : this.partidas) {
			if (p.getFecha().equals(fecha)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * Devuelve el ArrayList de partidas del usuario
	 */
	public ArrayList<Partida> getPartidasGuardadas() {
		return this.partidas;
	}
	
	
	
}
	