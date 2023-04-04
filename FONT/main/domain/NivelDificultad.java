package main.domain;
import java.util.ArrayList;
import java.util.List;


public abstract class NivelDificultad {
   
	protected boolean  tieneBlancas;
	protected boolean  sePuedeRepetir;
	protected int numColors;
	protected int numcolumnas;
    boolean solucionEncontrada;
	protected Integer turn;
    
    public abstract void setSolucion(Combinacion solucion);

    public abstract int calculaPuntuacion(int numIntentCodeMaker, int numIntentCodeBraker);

    public abstract int resolve(Combinacion solucion);

	public abstract Integer getDificultad();
	
	public abstract Integer getNumColumnas();
	
	public String comprobarCombinacion(Combinacion solution, Combinacion solEnviada){
    	
        int aciertos = 0;
    	int semiaciertos = 0;
        String feedback = "";
        
        for(int i = 0; i < numcolumnas; i++){
            if(solEnviada.getPosition(i) == solution.getPosition(i)){
                aciertos++;
                feedback += "N";
            }
            else if(solEnviada.contains(solution.getPosition(i))){
                semiaciertos++;
            }
        }  
        if(aciertos == numcolumnas)  solucionEncontrada = true;
        for(int i = aciertos; i < aciertos + semiaciertos; i++){
            feedback += "B";
        }

        System.out.print("Comprobado  ");
        return feedback;
    }
    
    public String comprobarCombinacionPista(Combinacion solution, Combinacion solEnviada){
        int aciertos = 0;
    	String feedback = "";
        for(int i = 0; i < numcolumnas; i++){
            if(solution.getPosition(i) == solEnviada.getPosition(i)){
                feedback += "N";
               aciertos++;
            }
            else if(solution.contains(solEnviada.getPosition(i))){
                feedback += "B";
            }
            else {
                feedback += " ";
        }  }
        if(aciertos == numcolumnas)  solucionEncontrada = true;
        return feedback;
    }
    
	
	
}

