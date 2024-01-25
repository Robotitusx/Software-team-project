package ventana;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Controller.Controlador;

public class Tabla extends JTable {

	protected static final int CANTIDAD_PERSONAS = 30;
	private static final long serialVersionUID = 1L;
	private JPanel panel_tabla;
	private Integer filaseleccionada;
	private Controlador controller;
	private JFrame candidatos;
	private Resaltador render;
	
	public Tabla(JFrame frame, Controlador controller) {
		
		this.controller = controller;
		candidatos = frame;
		
		filaseleccionada = -1;
		iniciarTabla();
		candidatos.repaint();
		
	}
	
	private void iniciarTabla() {
		
		configurarTabla();
		JScrollPane scroll_tabla = new JScrollPane(this);
		scroll_tabla.setBounds(0, 0, 346, 190);
		panel_tabla.add(scroll_tabla);
		
		
		escuchador_usuario();
		
		panel_tabla.add(scroll_tabla, BorderLayout.CENTER);
		candidatos.getContentPane().add(panel_tabla);
		
		
	}
	
	private void configurarTabla() {
		
		panel_tabla = new JPanel();
		panel_tabla.setBounds(10, 11, 453, 238);
		panel_tabla.setLayout(new BorderLayout()); 
	
		establecerModel();
		getColumn("Puesto").setPreferredWidth(150);
		getColumn("Nombre").setPreferredWidth(100);
		
	}

	private void establecerModel() {
		
		setModel(new DefaultTableModel(
					setdefaultTable(new String[CANTIDAD_PERSONAS][4]),
					new String[] {"Nombre", "Puesto", "Desempeño", "Dni" }) {

			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
					return false;
			}
		}
					
					
		);
			
		
	}

	private String[][]  setdefaultTable(String[][] objeto) {
		
		for(int f=0; f < CANTIDAD_PERSONAS; f++)
			for (int c = 0; c < 4; c++)
				objeto[f][c] = "";
		return objeto;
		
	}
		
		private void escuchador_usuario() {
			
			render = new Resaltador();
			setDefaultRenderer(Object.class,  render);
			
			addMouseListener(new MouseAdapter() {
				
				@Override
				public void mousePressed(MouseEvent e) {
					
					
					render.setfila(getSelectedRow());
					filaseleccionada = getSelectedRow();
				}
				
			});
		}
		
		public void agregarCandidatoAleatorio(Agregar agregar) {
			
			if(tablallena()) { 
				JOptionPane.showMessageDialog(agregar, "La tabla esta llena");
				return;
			}
			
			List<String> elementos = controller.agregarCandidatoAleatorio();
			colocarCandidato(elementos.get(0), elementos.get(3), Integer.parseInt(elementos.get(1)), Integer.parseInt(elementos.get(2)));
			
		}
	
	protected void agregarCandidato(String[] datos, String path) {

		if(tablallena()) { 
			JOptionPane.showMessageDialog(this, "La tabla esta llena");
			return;
		}
		String nombre = datos[0];
		String dni = datos[1];

		String calificacion = datos[2];
		String puesto = datos[3];

		String[] data = new String[] { nombre, puesto };
		Integer[] datos_numericos = new Integer[] { Integer.parseInt(calificacion), Integer.parseInt(dni) };

		boolean esvalido = controller.agregarCandidato(data, datos_numericos, path);
		if (esvalido)
			colocarCandidato(nombre, puesto, Integer.parseInt(dni), Integer.parseInt(calificacion));

		repaint();
		}

		

		public void colocarCandidato(String nombre, String puesto, Integer dni, Integer calificacion) {

			for (int f = 0; f < getRowCount(); f++)
				for (int c = 0; c < getColumnCount(); c++)

					if (getValueAt(f, c).equals("")) {

						setValueAt(nombre, f, c);
						setValueAt(puesto, f, c + 1);
						setValueAt(calificacion.toString(), f, c + 2);
						setValueAt(dni.toString(), f, c + 3);
						return;
					}

		}
		
		protected Integer borrarCandidato() {
			
			String dni = getValueAt(filaseleccionada, 3).toString();
			for(int c = 0; c < 4; c++) {
				
				setValueAt("", filaseleccionada, c);
				
			}
			reorganizar(filaseleccionada, 0);
			return Integer.parseInt(dni);
		}
		
	

		
		
		private void reorganizar(int fila, int column) {
			
			
			if(fila + 1 >= getRowCount()) return;
			if(!getValueAt(fila + 1, column).equals("")) {
				
				String[] elementos = obtenervalores(fila, column);
			
				Utilidades.agregarElementos(this, fila, elementos);
				Utilidades.limpiarElementos(this, fila + 1);
				
				reorganizar(fila  + 1 , column); 
			
			}
		
			return;
		}

		private String[] obtenervalores(int fila, int column) {
			
			String name = (String) getValueAt(fila + 1, column);
			String puesto = (String) getValueAt(fila + 1, column + 1);
			String calificacion = (String) getValueAt(fila + 1, column + 2);
			String dni  = (String) getValueAt(fila + 1, column + 3);

			String[] elementos = new String[] {name, puesto, calificacion, dni};
			return elementos;
		}

		public void ResaltarIncompatibles(Controlador controller) {
			
			if(filaseleccionada == -1) { 
				
				JOptionPane.showMessageDialog(candidatos, "Por favor seleccione algun candidato");
				return;
			}
			
			if(seleccionvacia()) {
	
				JOptionPane.showMessageDialog(candidatos, "Por favor no seleccione filas vacias");
				return;			
			}
			
			String dni = getValueAt(filaseleccionada, 3).toString();
			ResaltarIncompatibles render = new ResaltarIncompatibles(controller.devolverIncompatibles(dni));
			setDefaultRenderer(Object.class, render);
			
			recuperarestado();
		}

		

		private void recuperarestado() {
			
			addMouseListener(new MouseAdapter() {
			
				@Override
				public void mouseEntered(MouseEvent e) {
					
					setDefaultRenderer(Object.class, getObject().render);
					repaint();
					
				}
			});
			
		}

		public int getfilaseleccionada() {
			
			return filaseleccionada;
		}

		protected boolean seleccionvacia() {
			return getValueAt(filaseleccionada, 0).equals("");
		}
		
		public boolean sinseleccion() {
			
			if(filaseleccionada == -1) return false;
			return !seleccionvacia();
		}
		
		protected boolean tablallena() {
			
			return !getValueAt(CANTIDAD_PERSONAS - 1, 0).equals("");
		}
		
		protected Tabla getObject(){return this;}

	

}




