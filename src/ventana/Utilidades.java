package ventana;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTable;

public class Utilidades {

	protected static void agregarComponente(JFrame frame, Component[] componentes) {
	
		for (Component component : componentes) {
			
			frame.getContentPane().add(component);
		}
		
		
	}
	
	protected static void modificarComboBox(JComboBox<String> box, String[] elementos) {
		
		for (String elemento : elementos) 
			box.addItem(elemento);
		
	}
	
	protected static void limpiarElementos(JTable table, int fila) {
		
		for (int i = 0; i < 4; i++) 
			
			table.setValueAt("", fila, i);
			
	}
	
	protected static void agregarElementos(JTable table, int fila, String[] elementos) {
		
		int c = 0;
		for (String elemento : elementos) {
			
			table.setValueAt(elemento, fila, c);
			c++;
		}
		
		
		
	}
	
	protected static String[] obtenerDatos(JTable table, int fila) {
		
		String name = table.getValueAt(fila, 0).toString();
		String puesto = table.getValueAt(fila, 1).toString();
		String calificacion = table.getValueAt(fila, 2).toString();
		String dni = table.getValueAt(fila, 3).toString();
		
		String[] datos = new String[] {
				name, puesto, calificacion, dni
		};
		
		return datos;
		
	}
	
	
	
	
	

}
