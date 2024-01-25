package ventana;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import Controller.Cargo;

public class Agregar extends JFrame {

	private static final long serialVersionUID = 1L;
	private List<JTextArea> datosDelCandidato;
	private List<JComboBox<String>> datosProfesionales;
	private JButton goboton;
	private JButton azarboton;
	private JButton botonImagen;
	private JRadioButton allRadioBoton;
	private String path;
	private Candidatos _candidatos;

	public Agregar(Candidatos candidatos) {
		
		datosDelCandidato = new ArrayList<>();
		datosProfesionales = new ArrayList<>();
		_candidatos = candidatos;
		
		frame();
		estableceretiquetas();
		establecerDatos();
		escuchadorGoBoton();
		escuchadorAzarBoton(); // opcion para generar personas al azar
		escuchadorBotonImagen();
		setVisible(true);
		path = "";
	}


	


	private void frame() {
		
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
	}

	private void estableceretiquetas() {
		
		
		String[] datos = new String[] {"Nombre", "Dni", "Calificacion Historica", "Puesto"};
		List<JLabel> etiquetas = new ArrayList<>(4);
		
		for (String etiqueta : datos) {

			JLabel label = new JLabel(etiqueta);
			
			label.setFont(new Font("Tahoma", Font.PLAIN, 17));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			
			etiquetas.add(label);
			getContentPane().add(label);
		}
		
		etiquetas.get(0).setBounds(10, 11, 158, 42);
		etiquetas.get(1).setBounds(10, 64, 158, 42);
		etiquetas.get(2).setBounds(10, 117, 158, 42);
		etiquetas.get(3).setBounds(10, 170, 158, 42);
		
	}


	
	
	private void establecerDatos() {
		
		boxcalificacion();
		puestobox();
		textoNombre();
		textodni();
		
		Utilidades.agregarComponente(this, new Component[] {datosProfesionales.get(0), datosProfesionales.get(1)});
		Utilidades.agregarComponente(this, new Component[] {datosDelCandidato.get(0), datosDelCandidato.get(1)});
	}

	private void puestobox() {
		
		String[] cargos = Cargo.getListaCargos();
	
		JComboBox<String> puesto_box = new JComboBox<String>();
		puesto_box.setBounds(233, 183, 158, 22);
		
		datosProfesionales.add(puesto_box);
		Utilidades.modificarComboBox(puesto_box, cargos);
	
		
	}
	

	private void boxcalificacion() {
		
		JComboBox<String> box_calificacion = new JComboBox<String>();
		box_calificacion.setBounds(233, 130, 158, 22);

		String[] calificaciones = new String[]{"1", "2", "3", "4", "5"};
	
		datosProfesionales.add(box_calificacion);
		Utilidades.modificarComboBox(box_calificacion, calificaciones);
		
		
		
		
	}
	
	private void textoNombre() {
		
		JTextArea name = new JTextArea();
		name.setBounds(233, 23, 158, 22);
		
		name.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				
				if(name.getText().length() > 21) e.consume();
				if(String.valueOf(e.getKeyChar()).matches("\\d+")) e .consume();
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if(((int) e.getKeyChar()) == 10) e.consume();
				
			}
			
		
			
		});
		datosDelCandidato.add(name);
		
	}
	
	private void textodni() {
		
		JTextArea dni = new JTextArea();
		dni.setBounds(233, 76, 158, 22);
		
		dni.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				
				if(dni.getText().length() > 7) e.consume();
				if(!String.valueOf(e.getKeyChar()).matches("[0-9]+")) e.consume();
				if(((int) e.getKeyChar()) == 10) e.consume();		
				
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if(((int) e.getKeyChar()) == 10) e.consume();
			}
		});
	
		datosDelCandidato.add(dni);
		
	}
	

	private void escuchadorGoBoton() {
		
		goboton = new JButton("go!");
		goboton.setBounds(315, 227, 60, 23);
		getContentPane().add(goboton);
		
		goboton.addActionListener(new ActionListener() {
			
			private String nombre; 
			private String dni;
			
			private String calificacion;
			private String puesto;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				nombre = getObject().datosDelCandidato.get(0).getText();
				dni = getObject().datosDelCandidato.get(1).getText();
				
				if(!verificarnombre()) return;
				if(!verificardni()) return;
				
				calificacion =  datosProfesionales.get(0).getSelectedItem().toString();
				puesto = datosProfesionales.get(1).getSelectedItem().toString();
				
				String[] datos = new String[] {nombre, dni, calificacion, puesto};
				_candidatos.agregarCandidato(datos, path);
				
				
				dispose();
			
			}

			private boolean verificarnombre() {
						
				if(nombre.equals("")) {
					
					JOptionPane.showMessageDialog(getObject(), "El nombre no puede ser vacio");
					return false;
				}
				return true;
				
				
			}
			

			private boolean verificardni() {
				
				if(dni.length() != 8 || Integer.parseInt(dni) <  10000000) { 
					
					JOptionPane.showMessageDialog(getObject(), "El dni es invalido");
					return false;
				}
				return true;
			}
		});
		
		
	}
	
	private void escuchadorAzarBoton() {
		azarboton = new JButton("Azar");
		azarboton.setBounds(65, 227, 60, 23);
		getContentPane().add(azarboton);
		
		allRadioBoton = new JRadioButton("All");
		allRadioBoton.setBounds(130, 227, 60, 23);
		
		getContentPane().add(allRadioBoton);
		azarboton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(allRadioBoton.isSelected()) {
					
					while(!_candidatos.tabla.tablallena()) 
						_candidatos.agregarCandidatoAleatorio(getObject());
					
					dispose();
					
				}
				else {
					_candidatos.agregarCandidatoAleatorio(getObject());
				}
				
				
				
			}
		});
		
	}
	
	private void escuchadorBotonImagen() {
		
		botonImagen = new JButton("...");
		botonImagen.setBounds(250, 227, 60, 23);
		getContentPane().add(botonImagen);
		
		
		botonImagen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String ruta;
				JFileChooser filechooser = new JFileChooser();
				int respuesta = filechooser.showOpenDialog(getObject());
				
				if(respuesta == JFileChooser.APPROVE_OPTION) {
					
					ruta = filechooser.getSelectedFile().getPath();
					if(ruta.matches("(.*)(\\.png|\\.jpg)"))
						path = filechooser.getSelectedFile().getPath();	
				}
				
			}
		});
		
		
	}
	private Agregar getObject() {return this;}
	
}
