package domain;

import java.util.Collection;
import domain.Combinacion;


/** Clase Turno. **/
public class Turno {
	
	/** Atributos **/
    /** true = CodeMaker, false = CodeBreaker**/
	private boolean rol;
	public Vector <Combinacion> combinaciones;


	/** Constructora **/
	
	public Turno(boolean rolEscogido) {
		/** 1 = CodeMaker, 2 = CodeBreaker**/
		this.rol = rolEscogido;
	}
	
	/** Métodos privados **/
    
	/** Métodos públicos **/
	/*
	public changeTurno() {
		this.rol = !this.rol;
	}*/

	public boolean getRol(){
		return this.rol;
	}
	public boolean setCombinacion(Combinacion comb){
		Combinacion newCombinacion = new Combinacion(combSolution);
		combinaciones.push(newCombinacion);
		if(combinaciones.size() == 10) return true;
		return false;
	}


}