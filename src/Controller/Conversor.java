package Controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import modelo.Persona;
import modelo.Solver;

//realiza operaciones como tranformaciones a otras estructuras de datos y demas
public class Conversor {

	private Map<Integer, Persona> candidatos_seleccionados;
	
	public Conversor(Map<Integer, Persona> candidatos_seleccionados) {
		this.candidatos_seleccionados = candidatos_seleccionados;
	}

	static ArrayList<String> enviardatos(Solver solver) {
		
		ArrayList<Persona> elegidos = solver.getEquipo().miembros();
		ArrayList<String> enviar = new ArrayList<>();
		
		for (Persona persona : elegidos) {
			
			StringBuilder st = agregarDatosPersona(persona);
			enviar.add(st.toString());
		}
		return enviar;
	}

	private static StringBuilder agregarDatosPersona(Persona persona) {
		
		StringBuilder st = new StringBuilder();
		st.append(persona.get_nombre()).append("\n");
		st.append(persona.get_dni()).append("\n");
		st.append(persona.get_cargo()).append("\n");
		st.append(persona.get_calificacion_historica());
		return st;
	}
	
	 String[] generarPersonaString(Persona persona) {
		
		String nombre = persona.get_nombre();
		String cargo = persona.get_cargo().toString();
		String dni = persona.get_dni().toString();
		String calificacion = persona.get_calificacion_historica().toString();
		
		String[] datos = new String[] {nombre, dni, calificacion, cargo};
		return datos;
	}
	

	static Persona generarPersona(String[] datos, Integer[] datosNumericos) {
		
		String nombre = datos[0];
		String cargo = datos[1];
		
		Integer desempeño = datosNumericos[0];
		Integer dni = datosNumericos[1];
		
		Persona persona = new Persona(nombre, dni);
		persona.calificacion(desempeño);
		persona.cargo(cargo);
		
		return persona;
		
	}
	
	ArrayList<String> obtenerDnisNoIncompatibles(Persona thatPersona){
		
		ArrayList<String> dnis = new ArrayList<>();
		candidatos_seleccionados.values().stream().
		filter(p -> !p.esIncompatible(thatPersona)).
		forEach(p -> dnis.add(p.get_dni().toString()));
			
		return dnis;
	}
	
	ArrayList<String> obtenerDnisIncompatibles(String dni){
		
		ArrayList<String> incompatibles = new ArrayList<>();
		Integer _dni = convertirInteger(dni);
		
		if(candidatos_seleccionados.get(_dni) != null) {
			
			Set<Persona> lista = candidatos_seleccionados.get(_dni).get_lista_de_incompatibles();
			lista.
			stream().
			forEach(persona -> incompatibles.add(persona.get_dni().toString()));
			return incompatibles;
		}
			
		return null;
		
	}
	
	Integer[] requisitos(Map<String, Integer> requerimientos) {
		
		Integer[] valores = new Integer[4];
	
		int i = 0;
		for (Integer valor : requerimientos.values()) {
			
			valores[i] = valor;
			i++;
			
		}
	
		return valores;
	}
	
	void quitarIncompatibles(Persona eliminado) {
		
		eliminado.
		get_lista_de_incompatibles().
		stream().
		mapToInt(Persona::get_dni).
		forEach(dni_other -> {
	
			Persona other = candidatos_seleccionados.get(dni_other);
			other.borrar_incompatible(eliminado);
	
			});
		
	}

	Integer convertirInteger(String valor) {
		
		return Integer.parseInt(valor);
	}



}
