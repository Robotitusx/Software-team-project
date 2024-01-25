package modelo;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;



public class PersonaJSON {

	Map<Integer, Persona> personas;
	
	public PersonaJSON() {
		
		personas = new HashMap<>();
		
		
	}
		
	public String generarJson() {
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(this);
		
		return json;
	}
	
	public void guardarJson(String jsonsave, String destino) {
		
		try {
			
			FileWriter writer = new FileWriter(destino);
			writer.write(jsonsave);
			writer.close();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
	public static PersonaJSON leerJson(String archivo) throws FileNotFoundException {
		
		Gson gson = new Gson();
		PersonaJSON ret = null;
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			ret = gson.fromJson(br, PersonaJSON.class);
			
		} catch (IOException e) {
			
			
			return null;
		}
		
		return ret;
		
	}
	
	public Map<Integer, Persona> getpersonas() {
		return personas;
	}

	public void set(Map<Integer, Persona> personas) {
		
		this.personas = (HashMap<Integer, Persona>) personas;
		
	}

}
