package ventana;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

class ResaltarIncompatibles implements TableCellRenderer{

	private ArrayList<String> _dnis;
	private static DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		Component componente = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		if(columnavalida(table, row, column)) componente.setBackground(Color.red);
		else componente.setBackground(Color.white);
		
		return componente;
	}
	
	private boolean columnavalida(JTable table, int row, int column) {
		
		if(_dnis.contains(table.getValueAt(row, 3).toString())) return true;
		return false;
	}

	public ResaltarIncompatibles(ArrayList<String> incompatibles) {
		
		_dnis = new ArrayList<>();
		for (String dni : incompatibles) {
			
			_dnis.add(dni);
		}
		
	}
	
}
