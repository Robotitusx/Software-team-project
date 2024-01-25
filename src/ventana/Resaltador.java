package ventana;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

class Resaltador implements TableCellRenderer{

	int fila;
	static DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		Component componente = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		if(fila == row && isSelected) componente.setBackground(Color.green);
		else componente.setBackground(Color.white);
		
		
		return componente;
	}
	
	public void setfila(int row) {
		
		fila = row;
		
	}
	
	

}
