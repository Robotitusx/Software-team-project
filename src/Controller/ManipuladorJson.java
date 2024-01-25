package Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import modelo.IncompatiblesJSON;
import modelo.Persona;
import modelo.PersonaJSON;

public class ManipuladorJson {

	private PersonaJSON _base_de_datos;
	private IncompatiblesJSON _incompatiblesJson;
	private Map<Integer, Persona> candidatos_seleccionados;
	
	protected ManipuladorJson(PersonaJSON base_de_datos, IncompatiblesJSON incompatiblesJson, Map<Integer, Persona> candidatos_seleccionados) {
	
		_base_de_datos = base_de_datos;
		_incompatiblesJson = incompatiblesJson;
		this.candidatos_seleccionados = candidatos_seleccionados;
	}
	
	protected void generarPersonaJSON() {
		_base_de_datos.set(candidatos_seleccionados);
		String json = _base_de_datos.generarJson();
		_base_de_datos.guardarJson(json, "Personas");
	}

	protected void generarIncompatiblesJSON() {
		
		Map<Integer, Set<Persona>> mapeado = obtenerIncompatibles();
		IncompatiblesJSON incompatibles = new IncompatiblesJSON(mapeado);
		String jsonInc = incompatibles.generarJson();
		incompatibles.guardarJson(jsonInc, "Incompatibles");
	}
	
	private Map<Integer, Set<Persona>> obtenerIncompatibles() {
		
		Map<Integer, Set<Persona>> incompatibles = new HashMap<>();
		Stream <Persona> personas_con_incompatibles = candidatos_seleccionados.values().stream();
		personas_con_incompatibles.filter(p -> p.get_lista_de_incompatibles().size() != 0).forEach(persona ->{
			
			incompatibles.put(persona.get_dni(), persona.get_lista_de_incompatibles());
	
		});
		
		return incompatibles;
	}

	protected void setCandidatosSeleccionados(Map<Integer, Persona> seleccionados) {
		
		candidatos_seleccionados = seleccionados;
	}
	
	protected void incompatibilizar() {
		
		Map<Integer, Set<Persona>> incompatibles = _incompatiblesJson.getIncompatibles();
		
		for (Integer dni : incompatibles.keySet()) {
			
			for (Persona other : incompatibles.get(dni)) {
				
				other = candidatos_seleccionados.get(other.get_dni());
				candidatos_seleccionados.get(dni).añadir_incompatible(other);
			}
			
		}
		
		
	}
	
	protected Map<Integer, Persona> getPersonas() {
		
		return _base_de_datos.getpersonas();
	}

}
