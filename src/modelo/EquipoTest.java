package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import Controller.Cargo;

public class EquipoTest {

	Equipo team;
	@Before
	public void setUp() throws Exception {
		
		team = new Equipo(new Integer[] {1, 1, 1, 1});
	}

	@Test (expected = NullPointerException.class)
	public void equipoNullTest() {
		
		team = new Equipo(null);
		
	}
	
	@Test
	public void existeMiembroTest() {
		
		equipoCompleto();
		assertTrue(team.existeMiembro(78963258));
	}
	
	@Test
	public void noExisteMiembroTest() {
		
		assertFalse(team.existeMiembro(78963259));
	}
	
	@Test
	public void existeMiembroNull() {
		
		assertFalse(team.existeMiembro(null));
	}
		
	@Test
	public void agregarMiembroTest() {
		
		team.añadir_miembro(new Persona("Rafael Fuentes", 18543588).cargo(Cargo.get_liderProyecto()).calificacion(3));
		assertTrue(team.existeMiembro(18543588));
		
	}
	
	@Test (expected = RuntimeException.class)
	public void agregarMiembroInexistenteTest() {
		
		team.añadir_miembro(null);
		
	}
	
	@Test  (expected = RuntimeException.class)
	public void borrarMiembroInexistenteTest() {
		
		team.borrar_miembro(new Persona("a", 18543588).calificacion(3).cargo(Cargo.get_liderProyecto()));
	}
	
	@Test  
	public void borrarMiembroExistenteTest() {
		

		Persona persona = new Persona("a", 78963258).calificacion(2).cargo(Cargo.get_programador());
		team.añadir_miembro(persona);
		team.borrar_miembro(persona);
		assertFalse(team.existeMiembro(persona.get_dni()));
	}
	
	@Test
	public void equipoCompletoTest() {
		
		equipoCompleto();
		assertTrue(team.estacompleto());
	}
	
	@Test
	public void equipoIncompletoTest() {
		
		assertFalse(team.estacompleto());
	}
	
	@Test
	public void cloneableTest() throws CloneNotSupportedException {
		
		assertNotEquals(team, (Equipo) team.clone());
		assertEquals(team.listamiembros(), ((Equipo) team.clone()).listamiembros());
	}

	@Test
	public void detectarIncompatibleTest() {
		
		Persona persona =  Herramientas.liderproyectoEstandar("a",  78963258, 2);
		Persona persona2 = Herramientas.arquitectoEstandar("a", 78963257, 1);
		persona.añadir_incompatible(persona2);
		team.añadir_miembro(persona);
		assertTrue(team.detectarIncompatible(persona2));
	}
	
	@Test
	public void compatiblesTest() {
		

		Persona persona =  Herramientas.liderproyectoEstandar("a",  78963258, 2);
		Persona persona2 = Herramientas.arquitectoEstandar("a", 78963257, 1);;
		team.añadir_miembro(persona);
		assertFalse(team.detectarIncompatible(persona2));
		
	}
	
	@Test
	public void listaDeMiembrosTest() {
		

		Persona persona =  Herramientas.liderproyectoEstandar("a",  78963258, 2);
		Persona persona2 = Herramientas.arquitectoEstandar("a", 78963257, 1);;
		team.añadir_miembro(persona);
		team.añadir_miembro(persona2);
		
		assertEquals("[78963257] [78963258] ", team.listamiembros());
		
		
	}
	
	
	@Test
	public void listaDeMiembrosVaciaTest() {
	
	
		
		assertEquals("", team.listamiembros());
		
		
	}
	
	protected void equipoCompleto() {
		
		Persona persona =  Herramientas.liderproyectoEstandar("a",  78963258, 2);
		Persona persona2 = Herramientas.arquitectoEstandar("a", 78963257, 1);
		Persona persona3 = Herramientas.programadorEstandar("a", 78963256, 3);
		Persona persona4 = Herramientas.testerEstandar("a", 78963250, 5);
		
		team.añadir_miembro(persona);
		team.añadir_miembro(persona2);
		team.añadir_miembro(persona3);
		team.añadir_miembro(persona4);
	}
	
	

}
