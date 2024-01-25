package modelo;

import java.util.HashSet;
import java.util.Set;

import Controller.Cargo;

public class Persona implements Cloneable {

	private String _nombre;
	private Cargo _cargo;
	private Integer _dni;
	private Integer _calificacion_historica;
	private transient HashSet<Persona> _lista_de_incompatibles;
	private String path;
	public static final String DEFAULT_PATH = "Imagenes/unknown.png";

	public Persona(String nombre, Integer dni) {
		
		verificarnombre(nombre);
		verificardni(dni);
		
		_nombre = nombre;
		_dni = dni;
		_lista_de_incompatibles = new HashSet<>();
		path = new String(DEFAULT_PATH);
	
	}
	
	public void añadir_incompatible(Persona persona) {
		
		verificar_persona(persona);	
		_lista_de_incompatibles.add(persona);
		persona._lista_de_incompatibles.add(this);
	}
	
	public void borrar_incompatible(Persona persona) {
		
		if(!_lista_de_incompatibles.remove(persona))
			throw new RuntimeException("No existe la persona"); 
	}
	
	public boolean esIncompatible(Persona personaother) {
		
		for (Persona persona : _lista_de_incompatibles) 
			if(personaother.equals(persona)) return true;
		return false;
	}

	public Persona cargo(String cargo) {
		
		verificarCargo(cargo);
		return this;
	}

	public Persona calificacion(Integer calificacion_historica) {
		
		verificar_calificacion(calificacion_historica);		
		_calificacion_historica = calificacion_historica;
		return this;
		
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(!(obj instanceof Persona)) return false;
		return _dni.compareTo(((Persona) obj)._dni) == 0;
	}
	
	@Override
	public int hashCode() {
		return _dni;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Object clone() throws CloneNotSupportedException {
		
		Persona other = (Persona) super.clone();
		other._lista_de_incompatibles = (HashSet<Persona>) _lista_de_incompatibles.clone();
		other._dni = _dni;
		return other;
	}
	
	private void verificar_persona(Persona persona) throws NullPointerException {
		if(persona == null) throw new NullPointerException("La persona no puede ser null");
	}
	
	public void verificar_calificacion(Integer calificacion_historica) throws IllegalArgumentException {
		if(calificacion_historica < 0 || calificacion_historica > 5) 
			throw new IllegalArgumentException("La calificacion historica no puede ser mayor a 5 o menor a 0");
	}
	
	private void verificarCargo(String cargo) {
	
		for (Cargo that_cargo : Cargo.getLista()) 
			
			if(cargo.equals(that_cargo.toString())) {
				
				_cargo = that_cargo;
				return;
			}
				
		throw new IllegalArgumentException("el cargo es invalido");	
	}
	
	private void verificardni(Integer dni) {
		
		if(dni == null) throw new NullPointerException("no se permite valores nulos"); 
		if(dni < 10000000) throw new IllegalArgumentException("El dni no tiene 8 digitos");
		if(dni > 99999999) throw new IllegalArgumentException("El dni supera los 8 digitos");
		
	}

	private void verificarnombre(String nombre) {
		
		if(nombre.equals("")) throw new IllegalArgumentException("El nombre no puede ser vacio");
		
		
	}

	public String get_nombre() {
		return _nombre;
	}

	public Cargo get_cargo() {
		
		if(_cargo == null) throw new IllegalArgumentException("cargo no configurado");
		return _cargo;
	}

	public Integer get_dni() {
		return _dni;
	}

	public Integer get_calificacion_historica() {
		if(_calificacion_historica == null) throw new IllegalArgumentException("calificacion no configurada");
		return _calificacion_historica;
	}

	public Set<Persona> get_lista_de_incompatibles() {
		return _lista_de_incompatibles;
	}
	
	public Persona getObjeto() {
		
		return this;
	}

	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		
		this.path = path;
	}

	
	
}

