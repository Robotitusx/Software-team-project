package Controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelo.Herramientas;
import modelo.IncompatiblesJSON;
import modelo.Persona;
import modelo.PersonaJSON;
import modelo.SolverBeneficio;
import modelo.SolverExponencial;
import ventana.Candidatos;
import ventana.Entrada;

public class Controlador {

	private Candidatos candidatos;
	private ManipuladorJson manipuladorJson;
	
	private Conversor conversor;
	private Map<Integer, Persona> candidatos_seleccionados;
	private Map<String, Integer> requerimientos;
	
	public Controlador() throws FileNotFoundException  {
		
		candidatos_seleccionados = new HashMap<>();
		conversor = new Conversor(candidatos_seleccionados);
		
		leerJson();
		new Entrada(this);
	
	}
	
	private void leerJson() throws FileNotFoundException {
		
		IncompatiblesJSON incompatiblesJson = IncompatiblesJSON.leerJson("Incompatibles");
		PersonaJSON base_de_datos = PersonaJSON.leerJson("Personas");
		manipuladorJson = new ManipuladorJson(base_de_datos, incompatiblesJson, candidatos_seleccionados);
	}
	
	public void iniciarCandidatos(Candidatos candidatos, HashMap<String, Integer> cantidad_por_profesion) {
		
		this.candidatos = candidatos;
		requerimientos = cantidad_por_profesion;
		colocarCandidatosIniciales();
		manipuladorJson.incompatibilizar();
	}
	
	private void colocarCandidatosIniciales() {
		
		HashMap<Integer, Persona> personas = (HashMap<Integer, Persona>) manipuladorJson.getPersonas();
		
		personas.values()
		.stream()
		.forEach(persona -> {
			
			String[] datos = conversor.generarPersonaString(persona);
			candidatos.agregarCandidato(datos, "");
			
		});
		
	}
	
	//Invocados desde el GUI
	public boolean agregarCandidato(String[] datos, Integer[] datosNumericos, String ruta) {
		
		Persona persona = Conversor.generarPersona(datos, datosNumericos);
		Persona candidatovalido = candidatos_seleccionados.putIfAbsent(persona.get_dni(), persona);

		if(candidatovalido == null) { 
			
			
			if(estaEnLaBaseDeDatos(persona)) {
				String path = manipuladorJson.getPersonas().get(persona.get_dni()).getPath();
				persona.setPath(path);
				return true;
			}
			
			if(!espathDefault(ruta)) {
				persona.setPath(ruta);
			}
			
			return true;
		
		}
		return false;
			
	}

	private boolean espathDefault(String ruta) {
		return ruta.equals("");
	}

	private boolean estaEnLaBaseDeDatos(Persona persona) {
		return manipuladorJson.getPersonas().containsKey(persona.get_dni());
	}
	
	
	public List<String> agregarCandidatoAleatorio() {
		
		Persona aleatoria = Herramientas.generarAlAzar(candidatos_seleccionados.size());
		boolean esvalido = candidatos_seleccionados.putIfAbsent(aleatoria.get_dni(), aleatoria) == null;
		if(esvalido) return new ArrayList<> (Arrays.asList(conversor.generarPersonaString(aleatoria)));
		return null;
	} 
	
	public void borrarCandidato(Integer dni) {
		
		Persona eliminado = candidatos_seleccionados.get(dni);
		conversor.quitarIncompatibles(eliminado);
		candidatos_seleccionados.remove(dni);
	}
	
	public void agregarIncompatble(String dni, String dniother) {
		
		Integer _dni = conversor.convertirInteger(dni);
		Integer _otherdni = conversor.convertirInteger(dniother);
		
		Persona persona1 = candidatos_seleccionados.get(_dni);
		Persona persona2 = candidatos_seleccionados.get(_otherdni);
		
		persona1.añadir_incompatible(persona2);
		
	}
	
	public ArrayList<String> devolverIncompatibles(String dni) {
	
		return conversor.obtenerDnisIncompatibles(dni);
				
	}

	public WorkerBeneficio solucionPorBeneficio(){
		
		Integer[] valores = conversor.requisitos(requerimientos);
		ArrayList<Persona> candidatos = new ArrayList<>();
	
		candidatos.addAll(getCandidatos());
	
		SolverBeneficio solverBeneficio = new SolverBeneficio(candidatos, valores);
		WorkerBeneficio worker = new WorkerBeneficio(solverBeneficio);
		
		return worker;
		
	}

	public WorkerExponencial solucionExponencial() {
		
		Integer[] valores = conversor.requisitos(requerimientos);
		ArrayList<Persona> candidatos = new ArrayList<>();
	
		candidatos.addAll(getCandidatos());
	
		SolverExponencial solverExponencial = new SolverExponencial(candidatos, valores);
		WorkerExponencial worker = new WorkerExponencial(solverExponencial);
		
		return worker;
	}
	
	
	
	public ArrayList<String> obtener_dnis_no_incompatibles(String dni) {
		
		Integer dnithat = conversor.convertirInteger(dni);
		Persona thatPersona = candidatos_seleccionados.get(dnithat);
		return conversor.obtenerDnisNoIncompatibles(thatPersona);	
	}

	
	
	public void guardardatos() {
		
		if(candidatos_seleccionados.size() > 4) {
			
			manipuladorJson.generarIncompatiblesJSON();
			manipuladorJson.generarPersonaJSON();
			
		}
	}
	
	
	protected Collection<Persona> getCandidatos() {
		return candidatos_seleccionados.values();
	}
	
}
