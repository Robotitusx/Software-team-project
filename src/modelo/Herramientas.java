package modelo;

import java.util.List;
import java.util.function.Supplier;

import Controller.Cargo;

public class Herramientas {

	static Persona testerEstandar(String name, int dni, int calificacion) {
		Persona p = new Persona(name, dni);
		p.calificacion(calificacion);
		p.cargo(Cargo.get_tester());
		return p;
	}

	static Persona programadorEstandar(String name, int dni, int calificacion) {
		
		Persona p = new Persona(name, dni);
		p.calificacion(calificacion);
		p.cargo(Cargo.get_programador());
		return p;
	}

	static Persona liderproyectoEstandar(String name, Integer dni, int calificacion) {
		
		Persona p = new Persona(name, dni);
		p.calificacion(calificacion);
		p.cargo(Cargo.get_liderProyecto());
		return p;
	}

	static Persona arquitectoEstandar(String name, Integer dni, int calificacion) {
		
		Persona p = new Persona(name, dni);
		p.calificacion(calificacion);
		p.cargo(Cargo.get_arquitecto());
		return p;
	}
	
	static List<Persona> agregarPersonas(List<Persona> candidatos, Persona[] personas) {
		
		
		for (Persona persona : personas) {
			
			candidatos.add(persona);
		}
		
		return candidatos;
	}
	
	public static Persona generarAlAzar(int i) {
		
		Supplier<String> suplier = new Supplier<String>() {

			@Override
			public String get() {
				int azar = (int) (Math.random() * 4);
				
				if(azar == 0) return Cargo.get_arquitecto();
				if(azar == 1) return Cargo.get_liderProyecto();
				if(azar == 2) return Cargo.get_programador();
				else return Cargo.get_tester();
			}
		};
		int ochoDigitos = (int) (10000000 + Math.random() * 90000000);
		return new Persona("persona " + i, ochoDigitos).calificacion((int) (Math.random() * 5 + 1)).cargo(suplier.get());
		
	
	}

}
