package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SolverBeneficioTest {

	private SolverBeneficio solver;
	private List<Persona> candidatos;
	private Equipo teamFinal; 
	
	@Before
	public void setUp() throws Exception {
		
		candidatos = new ArrayList<>();
		equipoEstandar();
	}

	@Test (expected = NullPointerException.class)
	public void candidatosNullTest() {
		solver = new SolverBeneficio(null, null);
	}
	
	@Test
	public void sinIncompatiblesTest() {
		
		
		
		solver = new SolverBeneficio(candidatos, new Integer[] {1, 1, 1, 1});
		solver.resolver();
		teamFinal = solver.getEquipo();
		assertEquals("[12435881] [43729568] [85963584] [95963584] ", teamFinal.listamiembros());
	}

	
	
	@Test
	public void unIncompatibleTest() {
		
		
		candidatos.get(2).añadir_incompatible(candidatos.get(3));
		solver = new SolverBeneficio(candidatos, new Integer[] {1, 1, 1, 1});
		solver.resolver();
		teamFinal = solver.getEquipo();
		assertEquals("[12435881] [15963584] [43729568] [85963584] ", teamFinal.listamiembros());
	}
	
	@Test
	public void solucionAproximadaTest() { //no es optima, se aproxima a ella
		
		candidatos.get(1).añadir_incompatible(candidatos.get(2));
		solver = new SolverBeneficio(candidatos, new Integer[] {1, 1, 1, 1});
		solver.resolver();
		teamFinal = solver.getEquipo();
		assertEquals("[12435881] [43729568] [85963584] [95963584] ", teamFinal.listamiembros());
	}
	
	@Test
	public void sinSolucionTest() {
		
		
		candidatos.get(2).añadir_incompatible(candidatos.get(3));
		candidatos.get(1).añadir_incompatible(candidatos.get(3));
		
		solver = new SolverBeneficio(candidatos, new Integer[] {1, 1, 1, 1});
		solver.resolver();
		teamFinal = solver.getEquipo();
		assertEquals("", teamFinal.listamiembros());
		
		
		
	}
	
	@Test
	public void todosIncompatiblesTest(){
		
		
		incompatibilizar();
		
		solver = new SolverBeneficio(candidatos, new Integer[] {1, 1, 1, 1});
		solver.resolver();
		teamFinal = solver.getEquipo();
		assertEquals("", teamFinal.listamiembros());
		
	}	
	
	@Test
	public void ordenarPorCalificacionTest() {
		
	
		solver = new SolverBeneficio(candidatos, new Integer[] {1, 1, 1, 1});
		solver.ordenarPorCalificacion();
		assertEquals("[5, 5, 3, 3, 1]", obtenercalificaciones(solver.getCandidatos()));
		
	}
	
	@Test
	public void noEsIncompatibleConOtrosTest() {
		
		solver = new SolverBeneficio(candidatos, new Integer[] {1, 1, 1, 1});
		assertTrue(solver.noEsIncompatibleConOtros(candidatos.get(0), 0));
		
	}
	
	@Test
	public void esIncompatibleConAlgunoTest() {
		
		candidatos.get(0).añadir_incompatible(candidatos.get(3));
		candidatos.get(0).añadir_incompatible(candidatos.get(4));
		
		solver = new SolverBeneficio(candidatos, new Integer[] {1, 1, 1, 1});
		assertFalse(solver.noEsIncompatibleConOtros(candidatos.get(0), 0));
	}
	
	private void incompatibilizar() {
	
		Persona p1 = candidatos.get(0);
		candidatos.stream().forEach(x -> {
			if(!x.equals(p1)) p1.añadir_incompatible(x);
		});
		
	}

	protected void equipoEstandar() {
		
		Persona arquitecto = Herramientas.arquitectoEstandar("a", 12435881, 5);
		Persona lider = Herramientas.liderproyectoEstandar("b", 15963584, 1);
		Persona lider2 = Herramientas.liderproyectoEstandar("c", 95963584, 5);
		Persona tester = Herramientas.testerEstandar("d", 43729568, 3);
		Persona programador = Herramientas.programadorEstandar("e", 85963584, 3);
		
		//arquitecto - lider2 - tester - programador - lider
		candidatos = Herramientas.agregarPersonas(candidatos, new Persona[] {arquitecto, lider, lider2, tester, programador});
	}
	
	private String obtenercalificaciones(ArrayList<Persona> candidatos) {
		
		StringBuilder st = new StringBuilder("[");
		candidatos.stream().forEach(p -> st.append(p.get_calificacion_historica()).append(", "));
		return st.substring(0, st.length() - 2) + "]";
	}

	
	
	

}
