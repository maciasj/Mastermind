package main.domain;

import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashMap;
import java.util.Map;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import main.domain.Combinacion;
import main.domain.EstadoPartida;
import main.domain.Color;
import main.domain.Turno;
import main.domain.Dificultad;
import main.domain.ColorFeedBack;
/** 
*Clase Partida
*/
public class Partida {
	
	/** 
	*Atributos 
	*/
	private Date data;
	private int puntos;
	private boolean ayuda; 
	private EstadoPartida estadoPartida;
	public Combinacion solution;
	private Dificultad nivel;
	private String username;
	private ArrayList<Turno> turnos;

	/** 
	*Constructora 
     * @param dificultadEscogida la dificultad elegida para la partida
     * @param usuario el usuario que juega la partida
     * @param ayuda true si se activa la ayuda, false en caso contrario
     * @param rol true si el usuario es el CodeMaker, false si es el CodeBreaker
	*/
	public Partida(int dificultadEscogida, String user, boolean ayuda, boolean rol) {
		this.data = getFechaIni();
		/** 
		* 1 = Facil, 2 = Medio, 3 = Dificil
		*/
		this.nivel = new Dificultad(dificultadEscogida);
		this.turnos = new ArrayList<Turno>();
		this.turnos.add(new Turno(rol));
		this.ayuda = ayuda;
		this.puntos = 0;
		this.username = user;
		String estado = "running";
		this.estadoPartida = new EstadoPartida(estado);
	}
	
	/** 
	 * Métodos privados 
	 */
	private Date getFechaIni() {
		LocalDateTime fechaHoraActual = LocalDateTime.now();
		long millis = fechaHoraActual.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
		return new Date(millis);
	}
	
	private void donePartida(){
		boolean choosenRol;
		if(turnos.size() == 1) {
			choosenRol = this.turnos.get(0).getRol();
			this.turnos.add(new Turno(!choosenRol));
		}
	}
	private boolean checkIfAllCorrects(ArrayList<ColorFeedBack> feedBackSolution){
		ColorFeedBack firstElem = feedBackSolution.get(0);
		if(firstElem != ColorFeedBack.BLACK) return false;
		for(int i = 1; i < feedBackSolution.size(); i++){
			if(firstElem != feedBackSolution.get(i)) return false;
		}
		return true;
	}

	/** 
	*Métodos públicos 
	*/

	/**
	*Devuelve el estado de la partida 
	*/
	public String getEstadoPartida() {
        return estadoPartida.getEstado();
    }

	/**
	*Devuelve la data de la partida 
	*/
	public Date getData() {
        return this.data;
    }

	/**
	*Devuelve el jugador de la partida 
	*/
	public String getUsuario() {
        return this.username;
    }


	/**
	*Activa el modo ayuda dentro de la partida
	 * @return 
	*/
	public void setAyuda(boolean ayuda) { 
		this.ayuda = ayuda ? this.ayuda : !this.ayuda;
	}
	/**
	*Devuelve la dificultad de la partida 
	 * @return 
	*/
	public Dificultad getDificultad() {
		return this.nivel.getDificultad();
	}

	/**
	*Introduce la solución para este turno 
	*/
	public Combinacion getSolution() {
		return this.solution != null ? this.solution : null;
	}

	/**
	*Introduce la solución para este turno 
	 * @return 
	 * @throws Exception 
	*/
	public void setSolution(ArrayList<Color> combSolution) throws Exception{
		Combinacion newCombinacion = new Combinacion(combSolution);
		Turno lastTurno = this.turnos.get(turnos.size() -1);
		if(lastTurno.getRol()) this.solution = newCombinacion;
		else throw new Exception("Sólo el CodeBreaker puede hacer la solucion");
	}
	/**
	* Introduce un intento para este turno 
	*/
	public ArrayList<ColorFeedBack> setCombinacion(ArrayList<Color> combSolution) throws Exception{
		Turno lastTurno = this.turnos.get(turnos.size() -1);
		lastTurno.setCombinacion(combSolution);
		if(!lastTurno.getRol()){
			ArrayList<ColorFeedBack> feedBackSolution = new ArrayList<ColorFeedBack>(); 
			if(!ayuda) {
				String feedBack = nivel.comprobarCombinacion(this.solution, combSolution);
				for(char bola : feedBack.toCharArray()) {
				    ColorFeedBack cb = bola == 'n' ? ColorFeedBack.BLACK : ColorFeedBack.WHITE;
				    feedBackSolution.add(cb);
				}
				while(feedBackSolution.size() < nivel.getColumna()) {
				    feedBackSolution.add(ColorFeedBack.GREY);
				}
			}
			else {
				String feedBack = nivel.comprobarCombinacion(this.solution, combSolution);
				for(char bola : feedBack.toCharArray()) {
					ColorFeedBack cb;
					if(bola == ' ') cb = ColorFeedBack.GREY;
					else cb = bola == 'n' ? ColorFeedBack.BLACK : ColorFeedBack.WHITE;
					feedBackSolution.add(cb);
				}
			}
			Boolean lastChance = lastTurno.getNumberComb() - 1 == 10 ? true : false;
			if(lastChance || checkIfAllCorrects(feedBackSolution)) donePartida();
			return feedBackSolution;
		}
		else throw new Exception("Sólo el CodeMaker puede hacer combinaciones");
	}

	/**
	*Devuelve la puntuación de la partida 
	 * @return 
	*/
	public int getPuntuacion() {
        return this.puntos;
	}

	/**
	*Devuelve la fecha de la partida 
	 * @return 
	*/
	public Object getFecha() {
        return this.data;
	}
}