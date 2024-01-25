package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import Controller.Cargo;

public class Equipo implements Cloneable {
    
    private HashSet<Persona> miembros;
    private HashMap<String, Integer> cantidad_roles; //la cantidad de roles que necesito
    private Integer puntuacion;
    

    //Constructores
    protected Equipo() {
    	
    	miembros = new HashSet<>();
        cantidad_roles = new HashMap<>();
        puntuacion = 0;
    	
    }
    
    public Equipo(Integer[] roles){

        this();
        establecerRoles(roles);
    }
    
 
    
    private void establecerRoles(Integer[] valores) {

    	
    	if(valores == null) throw new NullPointerException("los roles no pueden ser nulos");
    	
        cantidad_roles.put(Cargo.get_liderProyecto(), valores[0]);
        cantidad_roles.put(Cargo.get_arquitecto(), valores[1]);
        cantidad_roles.put(Cargo.get_programador(), valores[2]);
        cantidad_roles.put(Cargo.get_tester(), valores[3]);

    
    }

  //Funciones importantes
    protected boolean añadir_miembro(Persona persona){

       personanula(persona);
       miembros.add(persona);
       Integer valor = cantidad_roles.get(persona.get_cargo().toString())  - 1;
        
       cantidad_roles.replace(persona.get_cargo().toString(), valor);
       puntuacion += persona.get_calificacion_historica();
        
       return true;
    }
    
    private void personanula(Persona persona) {
		
    	if(persona == null) throw new RuntimeException("No puede ser null la persona");
		
	}

	public void borrar_miembro(Persona actual) {
		
		if(!existeMiembro(actual.get_dni())) throw new RuntimeException("Persona no existe");
    	miembros.remove(actual);
    	Integer valor = cantidad_roles.get(actual.get_cargo().toString())  + 1;
        cantidad_roles.replace(actual.get_cargo().toString(), valor);
        puntuacion -= actual.get_calificacion_historica();
    	
	}

 
    //Compatibilidad del ingresante
    protected boolean cantidad_roles(Persona persona) {
        
        String rol = persona.get_cargo().toString();
        if(cantidad_roles.get(rol) == 0) return false;
        
        return true;
    }

    protected boolean detectarIncompatible(Persona personaother) {
        
        for (Persona persona : miembros) {
        	
        	
        	 if(persona.esIncompatible(personaother)) return true;
        } 
           
        
        return false;
    }
    
    protected boolean estacompleto() {
    	
    	for (Integer valor : cantidad_roles.values()) 
			if(valor != 0) return false;
		
    	return true;
    }

	//Equipo implementa cloneable
	@SuppressWarnings("unchecked")
	@Override
	protected Object clone() throws CloneNotSupportedException {
		
		Equipo equipo = null;
		equipo = (Equipo) super.clone();
		equipo.miembros = (HashSet<Persona>) miembros.clone();
		equipo.cantidad_roles = (HashMap<String, Integer>) cantidad_roles.clone();
		return equipo;
	}
	
	//Devuelve la lista de miembros con sus dnis de menor a mayor
	protected String listamiembros() {
		
		StringBuilder st = new StringBuilder();
		ArrayList<Persona> personas = new ArrayList<>();
		personas.addAll(miembros);
		ordenarPorDni(personas);
		establecerLista(st, personas);
		return st.toString();
		
	}

	private void establecerLista(StringBuilder st, ArrayList<Persona> personas) {
		for (Persona persona : personas) 
			st.append("[").append(persona.get_dni().toString()).append("]").append(" ");
	}

	
	private void ordenarPorDni(ArrayList<Persona> personas) {
		
		Collections.sort(personas, new Comparator<Persona>(){

			@Override
			public int compare(Persona persona, Persona personaother) {
				return persona.get_dni() - personaother.get_dni();
			}			
			
		});
	}
	
	public ArrayList<Persona> miembros() {
		
		ArrayList<Persona> personas = new ArrayList<>();
		
		for (Persona persona : miembros) {
			
			personas.add(persona);
		}
		
		return personas;
		
		
	}

	public void setMiembros(Persona[] members) {
		
		for (Persona persona : members) {
			
			añadir_miembro(persona);
			
		}
		
		
	}
	
	
	
    protected Integer getPuntuacion() {
    	return puntuacion;
    }
    
    public int getcalificacion() {
		
		return puntuacion;
	}
    
	public void print() {
		
		for (Persona persona : miembros) 
			System.out.print("[" + persona.get_nombre() + "] ");
		
		System.out.println();
	}

	public boolean existeMiembro(Integer dni) {
		
		for (Persona persona : miembros) 
			
			if(persona.get_dni().compareTo(dni) == 0) return true;

		return false;
	}

	public void clear() {
		
		
		
	}

    
}
