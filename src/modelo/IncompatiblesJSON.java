package modelo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class IncompatiblesJSON {

	private Map<Integer, Set<Persona>> _incompatibles;
	
	public IncompatiblesJSON(Map<Integer, Set<Persona>> incompatibles) {
		
		
		_incompatibles = incompatibles;
	}
	
	public IncompatiblesJSON() {
		_incompatibles = new HashMap<>();
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
	
	public static IncompatiblesJSON leerJson(String archivo) throws FileNotFoundException {
		
		Gson gson = new Gson();
		IncompatiblesJSON ret = null;
		
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			ret = gson.fromJson(br, IncompatiblesJSON.class);
			
		} catch (IOException e) {
			
			
			return null;
		}
		
		return ret;
		
	}
	
	public Map<Integer, Set<Persona>> getIncompatibles(){
		
		return _incompatibles;
	}
}
