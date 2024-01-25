package ventana;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Controller.Controlador;


public class Entrada extends JFrame {

	
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private List<JLabel> etiquetas;
	private Map<String, JComboBox<String>> profesiones;
	private JButton comenzar;
	
	private Controlador controlador;

	public Entrada(Controlador controller) {
		
		controlador = controller;
		
		iniciarPanel();
		iniciarEtiquetas();
		iniciarTextos();
		iniciarBoton();
		setVisible(true);
	
	}
	
	

	private void iniciarPanel() {
		
		contentPane = new JPanel();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		

		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			
	}
	
	private void iniciarEtiquetas() {
		
		etiquetas = new ArrayList<>();
		String[] cargos  = new String[] {
				
				"Testers", "Lideres de proyecto", "Programadores", "Arquitectos"
		};
		
		
		for (String cargo : cargos) {
			
			agregarcargo(cargo);
			
		}
		
		
		etiqueta_programadores();
		etiqueta_Arquitectos();
		etiqueta_lideres();
		etiqueta_testers();
		
	}

	private void agregarcargo(String cargo) {
		
		JLabel label = new JLabel(cargo);
		
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Trebuchet MS", Font.ITALIC, 20));
		
		etiquetas.add(label);
		contentPane.add(label);
		
		
	}

	
	
	private void iniciarTextos() {
		
		profesiones = new HashMap<>();
		String[] cargos  = new String[] {
				
				"Testers", "Lideres de proyecto", "Programadores", "Arquitectos"
		};
		
		
		for (String cargo : cargos) {
			
			profesiones.put(cargo, armartexto());
			
		}
	
		texto_programadores();
		texto_arquitectos();
		texto_lideres();
		texto_testers();
		
	}


	private JComboBox<String> armartexto() {
		
		JComboBox<String> box = new JComboBox<>();
		box.addItem("1");
		box.addItem("2");
		box.addItem("3");
		
		contentPane.add(box);
		return box;
	}

	private void texto_testers() {
		
		
		profesiones.get("Testers").setBounds(280, 194, 50, 20);
		
		
		
		
	}

	private void texto_lideres() {
		
		profesiones.get("Lideres de proyecto").setBounds(280, 143, 50, 20);
		
			
	}

	private void texto_arquitectos() {
		
		profesiones.get("Arquitectos").setBounds(280, 92, 50, 20);
		
	}


	private void texto_programadores() {
		
		profesiones.get("Programadores").setBounds(280, 41, 50, 20);
	}
	
	private void iniciarBoton() {
		
		comenzar = new JButton("Start");
		comenzar.setBounds(350, 115, 80, 20);
		
		escuchadorBoton();
		getContentPane().add(comenzar);
	}



	private void escuchadorBoton() {
		
		comenzar.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				HashMap<String, Integer> cantidad_por_profesion = new HashMap<>();
				
				for (String profesion: profesiones.keySet()) 
					cantidad_por_profesion.put(profesion, Integer.parseInt(profesiones.get(profesion).getSelectedItem().toString()));
							
				Candidatos candidatos = new Candidatos(controlador);
				
				controlador.iniciarCandidatos(candidatos, cantidad_por_profesion);	
				dispose();
				
			}

		
		});
		
		
	}
	
	protected Entrada getobjeto(){
		
		return this;
	}
	
	private void etiqueta_testers() {
		
		etiquetas.get(0).setBounds(69, 181, 187, 40);
	}

	private void etiqueta_lideres() {
		
		etiquetas.get(1).setBounds(69, 130, 187, 40);
				
	}

	private void etiqueta_programadores() {
		
		etiquetas.get(2).setBounds(87, 28, 158, 40);
			
	}
	
	private void etiqueta_Arquitectos() {
		
		etiquetas.get(3).setBounds(87, 79, 158, 40);
		
	}
	
}
