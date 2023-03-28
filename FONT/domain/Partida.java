package domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;
import domain.Combinacion;
import domain.EstadoPartida;
import domain.EstadoPartida;
import domain.Color;
import domain.ColorFeedBack;
/** 
*Clase Partida
*/
public class Partida {
	
	/** 
	*Atributos 
	*/
	private Date fecha;
	private int puntos;
	private boolean ayuda; 
	private EstadoPartida estadoPartida;
	public Combinacion solution;
	private Dificultad nivel;
	private String username;
	private Vector<Turno> turnos;

	/** 
	*Constructora 
     * @param dificultadEscogida la dificultad elegida para la partida
     * @param usuario el usuario que juega la partida
     * @param ayuda true si se activa la ayuda, false en caso contrario
     * @param rol true si el usuario es el CodeMaker, false si es el CodeBreaker
	*/
	public Partida(int dificultadEscogida, String user, boolean ayuda, boolean rol) {
		this.fecha = getFechaIni();
		/** 
		* 1 = Facil, 2 = Medio, 3 = Dificil
		*/
		this.nivel = new Dificultad(int dificultadEscogida);
		this.turnos = new Vector<Turno>();
		Turno turno = new Turno(rol);
		turnos.add(turno);
		this.ayuda = ayuda;
		this.puntos = 0;
		this.user = user;
		String estado = "running";
		this.estadoPartida = new EstadoPartida(estado);
	}
	
	/** 
	* Métodos privados 
	*/
	private Date getFechaIni() {
		
        Calendar fecha = new GregorianCalendar();
  
		int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);
		
		return new Date(año - 1900, mes, dia, hora, minuto, segundo);
	}

	private void donePartida(){

		boolean choosenRol = this.rol.getRol();
		this.turno = new Turno(!choosenRol);
	}
	private boolean checkIfAllCorrects(Vector<Color> feedBackSolution){
		Color firstElem = feedBackSolution[0];
		if(firstElem != BLACK) return false;
		for(int i = 1; i < feedBackSolution.size(); i++){
			if(firstElem != feedBackSolution[i]) return false;
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
	*/
	public setAyuda(boolean ayuda) { 
		this.ayuda = ayuda ? this.ayuda : !this.ayuda;
	}
	/**
	*Devuelve la dificultad de la partida 
	*/
	public getDificultad() {
		return this.nivel.getDificultad();
	}

	/**
	*Introduce la solución para este turno 
	*/
	public Color[] getSolution() {
        /**
		*Dependiendo de la dificultad el tmaño será 4(fácil, medi) o 5(difícil) 
		*/
		return this?.solution;
	}

	/**
	*Introduce la solución para este turno 
	*/
	public setSolution(Vector<Color> combSolution){
		Combinacion newCombinacion = new Combinacion(combSolution);
		if(this.turno.getRol) this.solution = newCombinacion;
		else throw new Exception("Sólo el CodeBreaker puede hacer la solucion");
	}

	/**
	* Introduce un intento para este turno 
	*/
	public Colors[] setCombinacion(Vector<Color> combSolution){
		boolean lastChance = this.turno.setCombinacion(combSolution);
		if(!this.turno.getRol){
			String feedBack;
			Vector<ColorFeedBack> feedBackSolution = new Vector<ColorFeedBack>(); 
			if(!ayuda) {
				feedBack = nivel.comprobarCombinacion(this.solution, combSolution);
				feedBack.forEach((bola)=>{
					ColorFeedBack cb = feedBack[bola] == "n" ? BLACK : WHITE;
					feedBackSolution.add(cb);
				})
				while(feedBackSolution.size() < nivel.getColumna()) feedBackSolution.add(GREY);
			}
			else {
				feedBack = nivel.comprobarCombinacionPista(this.solution, combSolution);
				feedBack.forEach((bola) => {
					ColorFeedBack cb;
					if(feedBack[bola] == " ") cb = GREY;
					else cb = feedBack[bola] == "n" ? BLACK : WHITE;
					feedBackSolution.add(feedBack[item]);
				})
			}
			if(lastChance || checkIfAllCorrects(feedBackSolution)) donePartida();
			return feedBackSolution;
		}
		else throw new Exception("Sólo el CodeMaker puede hacer combinaciones");
	}

	/**
	*Devuelve la puntuación de la partida 
	*/
	public getPuntuacion() {
        return this.puntos;
	}

	/**
	*Devuelve la fecha de la partida 
	*/
	public getFecha() {
        return this.fecha;
	}
}