package Controller;

public enum Cargo {

	LIDER_DE_PROYECTO, TESTER , ARQUITECTO, PROGRAMADOR;
	
	public static String[] getListaCargos() {
		
		String[] cargos = new String[] {LIDER_DE_PROYECTO.toString(), ARQUITECTO.toString(), 
				PROGRAMADOR.toString(), TESTER.toString()};
		
		return cargos;
	}
	
	public static Cargo[] getLista() {
		
		Cargo[] cargos = new Cargo[] {LIDER_DE_PROYECTO, ARQUITECTO, 
				PROGRAMADOR, TESTER};
		
		return cargos;
		
		
		
		
	}
	
	public static String get_liderProyecto() {
		
		return LIDER_DE_PROYECTO.toString();
	}
	
	public static String get_tester() {
		
		return TESTER.toString();
	}
	public static String get_arquitecto() {
	
	return ARQUITECTO.toString();
}
	public static String get_programador() {
	
		return PROGRAMADOR.toString();
	}
	

	
}
