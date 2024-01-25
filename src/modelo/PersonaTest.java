package modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import Controller.Cargo;

public class PersonaTest {

	Persona persona;
	Persona otherPersona;
	@Before
	public void setUp() throws Exception {
		
		crearOtraPersona();
		crearPersonaEstandar();
		
	}

	@Test (expected = IllegalArgumentException.class)
	public void personaSinNombreTest() {
		
		persona = new Persona("", 79586154);
		
	}
	
	@Test (expected = NullPointerException.class)
	public void personaSindniTest() {
		
		persona = new Persona("Fernando de la Fuente", null);
		
	}
	
	@Test (expected = NullPointerException.class)
	public void personanullTest() {
		
		persona = new Persona(null, null);
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void personasinCargoTest() {
		
		persona = new Persona("Daniel bertaccini", 79845123);
		persona.get_cargo();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void personasincalificacionTest() {
		
		persona = new Persona("Daniel bertaccini", 79845123);
		persona.get_calificacion_historica();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void personacalificacionInvalida() {
		
		persona = new Persona("Daniel bertaccini", 79845123);
		persona.calificacion(10);
		
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void personacargoInvalido() {
		
		persona = new Persona("Daniel bertaccini", 79845123);
		persona.cargo("soy un cargo invalido");
		
	}
	
	@Test 
	public void personacargovalido() {
		
		persona = new Persona("Daniel bertaccini", 79845123);
		persona.cargo("PROGRAMADOR");
		assertCargo(Cargo.PROGRAMADOR, persona.get_cargo());
	}
	
	@Test
	public void personasiguales() {
		
		persona = new Persona("Javier Marenco", 79845123);
		//por dni una persona es igual a otra.
		assertTrue(persona.equals(otherPersona));
		
		
	}
	@Test
	public void personasnoiguales() {
		
		assertFalse(persona.equals(otherPersona));
	}
	
	@Test
	public void equalsNoEsPersonaTest() {
		
		assertFalse(persona.equals(new Object()));
	}
	
	@Test (expected = NullPointerException.class)
	public void esIncompatibleNull() {
		
		persona.añadir_incompatible(null);
	}
	
	@Test
	public void esIncompatibleTest() {
		
		persona.añadir_incompatible(otherPersona);
		assertTrue(persona.esIncompatible(otherPersona));
		assertTrue(otherPersona.esIncompatible(persona));
	
	}
	
	@Test
	public void esCompatibleTest() {
		
		assertFalse(persona.esIncompatible(otherPersona));
		assertFalse(otherPersona.esIncompatible(persona));
		
	}
	
	@Test
	public void borrarIncompatibleTest() {
		
		persona.añadir_incompatible(otherPersona);
		persona.borrar_incompatible(otherPersona);
		otherPersona.borrar_incompatible(persona);
		
		assertFalse(persona.esIncompatible(otherPersona));
		assertFalse(otherPersona.esIncompatible(persona));
	}
	
	@Test (expected = RuntimeException.class)
	public void borrar_Incompatible_Inexistente_Test() {
		
		persona.borrar_incompatible(otherPersona);
	}
	
	@Test
	public void clonarTest() throws CloneNotSupportedException {
		
		assertEquals(persona, (Persona) persona.clone());
	}
	

	private void crearPersonaEstandar() {
		persona = new Persona("Fernando de la Fuente", 79845120);
		persona.cargo("PROGRAMADOR");
		persona.calificacion(3);
	}
	
	protected void crearOtraPersona() {
		otherPersona = new Persona("Javier Marenco",  79845123);
		otherPersona.cargo("PROGRAMADOR");
		otherPersona.calificacion(5);
	}
	

	private void assertCargo(Cargo programador, Cargo get_cargo) {
		assertTrue(programador.toString().equals(get_cargo.toString()));
		
	}
	
	
	

}
