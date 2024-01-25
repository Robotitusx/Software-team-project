package modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SolverBeneficio implements Solver {

	private ArrayList<Persona> _candidatos;
	private Equipo _equipo;
	private Integer[] roles;
	private static long tiempoFinal;
	
	public SolverBeneficio(Collection<Persona> candidatos, Integer[] requisitos) {
		
		verificarCandidatos(candidatos);
		verificarRequisitos(requisitos);
		_equipo = new Equipo(requisitos);
		
		_candidatos = (ArrayList<Persona>) candidatos;
		roles = requisitos;
		
		
	}
	
	private void verificarRequisitos(Integer[] requisitos) {
	
		if(requisitos == null) throw new NullPointerException("Los requisitos no pueden ser null");
		if(requisitos.length == 0) throw new IllegalStateException("No puede no haber requisitos");
		for (Integer valor : requisitos) 
			if(valor == null)  throw new IllegalStateException("No puede haber un requisito nulo");
	}

	private void verificarCandidatos(Collection<Persona> candidatos) {
	
		if(candidatos == null) throw new NullPointerException("Los candidatos no pueden ser null");
		if(candidatos.size() == 0) throw new IllegalStateException("No puede no haber candidatos");	
	}
	

	public void resolver(){
		long inicio = System.nanoTime();
		resolver(0);
		tiempoFinal = System.nanoTime() - inicio;
	}
	
	private void resolver(int index) {
		
	
		if(index > _candidatos.size()) return;
		int i = index;
		Persona actual;
		
		ordenarPorCalificacion();
		while (!satisfactoria(i)) { // O(n2)
			
			
			if(_equipo.estacompleto()) return;
			actual = _candidatos.get(i);
			if(!_equipo.detectarIncompatible(actual) && _equipo.cantidad_roles(actual) && noEsIncompatibleConOtros(actual, i))
				_equipo.añadir_miembro(actual);
				
			i++;
		}
		if(!_equipo.estacompleto()) _equipo = new Equipo(roles);
		
	}

	
	protected boolean noEsIncompatibleConOtros(Persona actual, int i) {
		
		for (int j = i; j < _candidatos.size() - i; j++) {
		
			Persona other = _candidatos.get(j);
			if(other.esIncompatible(actual)) return false;
			
		}
		return true;
	}


	protected void ordenarPorCalificacion() {
		
		Collections.sort(_candidatos, (p1, p2) -> -p1.get_calificacion_historica() + p2.get_calificacion_historica());
	}
	
	protected boolean satisfactoria(int i) {
		return i >= _candidatos.size();
	}

	public Equipo getEquipo() {
		
		return _equipo;
	}
	
	protected ArrayList<Persona> getCandidatos(){
		
		return _candidatos;
	}
	
	public static long getTiempoFinal() {
		return SolverBeneficio.tiempoFinal;
	}
	

}
