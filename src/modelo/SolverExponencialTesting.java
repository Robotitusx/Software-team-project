package modelo;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SolverExponencialTesting {

	
	private SolverExponencial solverExponencial;
	private ArrayList<Persona> candidatos;
	
	@Before
	public void setUp() throws Exception {
		
	
		equipoEstandar();
		solverExponencial = initSolver(candidatos);
	}

	@Test (expected = NullPointerException.class)
	public void equipoNulo() throws CloneNotSupportedException {
		
		solverExponencial.setCandidatos(null);
		solverExponencial.resolver(0);
		
	}
	
	@Test
	public void noExisteSolucionTest() {
		
		ArrayList<Persona> personas = new ArrayList<>();
		Persona alazar = Herramientas.generarAlAzar(0);
		personas.add(alazar);
		solverExponencial.setCandidatos(personas);
		
		assertEquals("", solverExponencial.getEquipo().listamiembros());
	}
	
	
	@Test
	public void casoTodosCompatiblesTest() throws CloneNotSupportedException {
		
	
		
		solverExponencial.resolver();;
		Equipo team = solverExponencial.getEquipo();
	
		assertEquals("[12435881] [43729568] [85963584] [95963584] ", team.listamiembros());
		
	}

	

	
	@Test
	public void listaConIncompatibleTest() {
		
		
		candidatos.get(2).añadir_incompatible(candidatos.get(3));
		solverExponencial.resolver();;
		Equipo team = solverExponencial.getEquipo();
	
		assertEquals("[12435881] [15963584] [43729568] [85963584] ", team.listamiembros());
		
	}


	@Test
	public void listaVariosIncompatiblesTest() {
		
		
		
		candidatos.get(0).añadir_incompatible(candidatos.get(1)); // a inc b
		candidatos.get(1).añadir_incompatible(candidatos.get(4)); // b inc e
		
		solverExponencial.resolver();;
		Equipo team = solverExponencial.getEquipo();
	
		assertEquals("[12435881] [43729568] [85963584] [95963584] ", team.listamiembros());
		
		
	}


	
	@Test
	public void todosIncompatiblesTest() {
		
		
		Persona first = candidatos.get(0);
		candidatos.stream().forEach((x) ->{
			if(!first.equals(x)) first.añadir_incompatible(x);
		});
		
		solverExponencial.resolver();
		Equipo team = solverExponencial.getEquipo();
	
		assertEquals("", team.listamiembros());
		
	}
	
	protected void equipoEstandar() {
		
		Persona arquitecto = Herramientas.arquitectoEstandar("a", 12435881, 5);
		Persona lider = Herramientas.liderproyectoEstandar("b", 15963584, 1);
		Persona lider2 = Herramientas.liderproyectoEstandar("c", 95963584, 5);
		Persona tester = Herramientas.testerEstandar("d", 43729568, 3);
		Persona programador = Herramientas.programadorEstandar("e", 85963584, 3);
		Persona programador2 = Herramientas.programadorEstandar("f", 96894538, 2);
		
		//arquitecto - lider2 - tester - programador - lider
		candidatos = (ArrayList<Persona>) Herramientas.agregarPersonas(new ArrayList<>(), new Persona[] {arquitecto, lider, lider2, tester, programador, programador2});
	}
	
	
	private SolverExponencial initSolver(List<Persona> candidatos2) {
		return new SolverExponencial(candidatos2, new Integer[] {1, 1, 1, 1});
	}


}
