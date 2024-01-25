package principal;

import Controller.Controlador;


public class Principal extends Thread {

	public static void main(String[] args) {
		
		Principal correr = new Principal();
		correr.run();
		
	}
	
	@Override
	public void run() {
		try {
			new Controlador();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}
