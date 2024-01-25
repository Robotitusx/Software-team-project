package ventana;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class Solucion extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JButton botonNext;
	private List<String> equipo_final;
	private List<String> paths;
	private JTextArea elegido;
	private int pos;
	private JTextArea tiempo;
	private Candidatos candidatos;
	private JLabel labelImagen; 
	private Image miimagen;

	public Solucion(ArrayList<String> elegidos, Candidatos _candidatos, long time, List<String> _paths) {
		
		if(elegidos.size() == 0) { 
		
			JOptionPane.showMessageDialog(_candidatos, "Por favor verifica incompatibilidades y requisitos");
			return;
			
		}
		
		paths = _paths;
		
		equipo_final = elegidos;
		pos = 0;
		candidatos = _candidatos;
		
		
		init_frame();
		init_labelImagen();
		init_texto();
		init_tiempo(time);
		colocarEscuchadorBoton();
		
		getContentPane().add(elegido);
		getContentPane().add(tiempo);
		setVisible(true);
	}


	private void init_labelImagen() {
		labelImagen = new JLabel();
		labelImagen.setBounds(300, 110, 100, 100);
		getobjeto().getContentPane().add(labelImagen);
	}


	private void init_tiempo(long time) {
		
		tiempo = new JTextArea();
		tiempo.setText("Tiempo: " + time);
		tiempo.setBounds(31, 230, 244, 20);
		tiempo.setEditable(false);
		repaint();
		
	}


	private void init_texto() {
		
		elegido = new JTextArea();
		elegido.setText(equipo_final.get(0));
		elegido.setBounds(31, 10, 244, 201);
		elegido.setEditable(false);
		colocarImagen();
		repaint();
	}


	private void colocarEscuchadorBoton() {
		
		botonNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					pos++;
					colocarElegido(equipo_final.get(pos));
					colocarImagen();
					
					
				} catch (IndexOutOfBoundsException exception) {
					
					pos = 0;
					colocarElegido(equipo_final.get(pos));
					colocarImagen();
				}
				
				getobjeto().repaint();
				
			}

		
		});
		
		
	}


	private void colocarElegido(String datos) {
		
		elegido.setText(datos);
	}
	
	private void colocarImagen() {
			
		miimagen = new ImageIcon(paths.get(pos)).getImage();
		ImageIcon icono = new ImageIcon(miimagen.getScaledInstance(labelImagen.getWidth(), labelImagen.getHeight(), Image.SCALE_SMOOTH));
		
		labelImagen.setIcon(icono);
		labelImagen.repaint();
		
	}


	private void init_frame() {
		
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		getContentPane().setLayout(null);
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				
				candidatos.setVisible(false);
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				candidatos.setVisible(true);
				dispose();
				
			}
		
		});
		
		botonNext = new JButton("Next");
		botonNext.setBounds(310, 11, 89, 23);
		getContentPane().add(botonNext);
	}
	
	protected Solucion getobjeto() {
		
		return this;
	}
	
	protected void setElegidos(ArrayList<String> elegidos) {
		
		equipo_final = elegidos;
	}


	public void setpaths(List<String> paths) {
		
		this.paths = paths;
		
	}
}
