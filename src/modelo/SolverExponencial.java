package modelo;

import java.util.ArrayList;
import java.util.Collection;

public class SolverExponencial implements Solver {

	private ArrayList<Persona> candidatos;
	private Equipo mejorEquipo;
	private Equipo equipo_actual;
	private static long tiempoFinal;

	
	
	protected SolverExponencial() {
		
		mejorEquipo = new Equipo();
	}
	
	protected SolverExponencial(Integer[] requisitos) {
		
		mejorEquipo = new Equipo(requisitos);
		equipo_actual = new Equipo(requisitos);
		
	}
	public SolverExponencial(Collection<Persona> miscandidatos, Integer[] requisitos) {
		
		this(requisitos);
		candidatos = (ArrayList<Persona>) miscandidatos;
		
	}
	
	public void setEquipoActual(Equipo equipo) {
		
		equipo_actual = equipo;
		
	}
	
	
	public void resolver() {
		
		
		try {
			long inicio = System.nanoTime();
			resolver(0);
			SolverExponencial.tiempoFinal = System.nanoTime() - inicio;
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}	
		
	}
	
	public void resolver(int i) throws CloneNotSupportedException {
	
	
		if(equipo_actual.estacompleto() && equipo_actual.getcalificacion() > mejorEquipo.getcalificacion()) {
			
			mejorEquipo = (Equipo) equipo_actual.clone();
			
		}
		
		if(i == candidatos.size()) return;
		
		Persona persona = candidatos.get(i);
		if(equipo_actual.detectarIncompatible(persona) || !equipo_actual.cantidad_roles(persona)) {
			
			resolver(i  + 1);
			
			
		}
		else {
			
			equipo_actual.añadir_miembro(persona);
			resolver(i + 1);
			equipo_actual.borrar_miembro(persona);
			resolver(i + 1);
		}
		
	}
	
	protected void setCandidatos(Collection<Persona> miscandidatos) {
		
		
		candidatos = (ArrayList<Persona>) miscandidatos;
		
	}
	
	public Equipo getEquipo() {
		
		return mejorEquipo;
	}
	
	public static long getTiempoFinal() {
		return SolverExponencial.tiempoFinal;
	}

}
