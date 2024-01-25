package ventana;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import Controller.Controlador;

public class Incompatible extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<String> box_dni;
	private String dni_actual;
	private JButton accionar;
	private Controlador controlador;
	
	
	private Incompatible(String[] datos) {
		
		init_frame();
		init_datos(datos);
		init_box();
		init_accionar();
	
	}
	
	protected Incompatible(Controlador controller, String[] datos) {
		
		this(datos);
		controlador = controller;
		
		colocardnis();
			
		
	}

	private void colocardnis() {
		
		ArrayList<String> dnis = controlador.obtener_dnis_no_incompatibles(dni_actual);
		for (String dni : dnis) 
			
			if(!dni.equals(dni_actual))
				box_dni.addItem(dni);
	}

	private void init_accionar() {
		
		accionar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			
				if(box_dni.getItemCount() == 0) return;
				String dniOther = getObject().box_dni.getSelectedItem().toString();
				getObject().controlador.agregarIncompatble(dni_actual, dniOther);
				
				dispose();
			}
			
			
			
		});
		
		
	}

	private void init_datos(String[] datos) {
		
		JTextArea datos_persona = new JTextArea();
		datos_persona.setEditable(false);
		datos_persona.setBounds(10, 10, 264, 195);
		
		StringBuilder st = new StringBuilder();
		
		Arrays.stream(datos).forEach(dato -> st.append(dato).append("\n"));

		dni_actual = datos[3];
		datos_persona.setText(st.toString());
		contentPane.add(datos_persona);
	}

	private void init_box() {
		
		box_dni = new JComboBox<String>();
		box_dni.setBounds(298, 11, 126, 22);
		contentPane.add(box_dni);
		
		accionar = new JButton("¡incompatibles!");
		accionar.setBounds(298, 227, 126, 23);
		contentPane.add(accionar);
	}

	

	private void init_frame() {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
	}
	
	private Incompatible getObject() {return this;}
}
