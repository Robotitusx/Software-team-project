package ventana;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;

import Controller.Controlador;


public class Candidatos extends JFrame  {

	private static final long serialVersionUID = 1L;
	protected Tabla tabla;
	private Controlador controller;
	private JRadioButton mostrar;
	private JRadioButton agregar;
	private JProgressBar barra;
	
	private ItemEvent selected_radio_boton;
	

	public Candidatos(Controlador controlador) {
		
		controller = controlador;   
		barra = new JProgressBar();
		barra.setBounds(492, 270, 98, 26);
		getContentPane().add(barra);
		iniciarFrame();
		iniciarTabla();	
		
		configurarBotones();
		configurarRadioBotones();
		
		
		setVisible(true);
	}
	

	public Candidatos() {
		// TODO Auto-generated constructor stub
	}


	private void iniciarFrame() {
		
		setBounds(100, 150, 650, 350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
	}

	
	
	private void iniciarTabla() {
		
		tabla = new Tabla(this, controller);	
	}
	
	private void configurarRadioBotones() {
		
		mostrar = new JRadioButton("Mostrar");
		mostrar.setBounds(134, 279, 121, 24);
		getContentPane().add(mostrar);
		
		agregar = new JRadioButton("Agregar");
		agregar.setBounds(134, 254, 121, 24);
		getContentPane().add(agregar);
		
		agregarEscuchador();
	}



	private void agregarEscuchador() {
		
		agregar.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				
				if(e.getStateChange() == ItemEvent.SELECTED) {
					
					mostrar.setSelected(false);
					getObject().selected_radio_boton = e;
				}
				
				if(e.getStateChange() == ItemEvent.DESELECTED) {
					
					getObject().selected_radio_boton = null;
				}
			
				
			}
		});
		
		mostrar.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				
				if(e.getStateChange() == ItemEvent.SELECTED) {
					
					agregar.setSelected(false);
					getObject().selected_radio_boton = e;
				}
				
				if(e.getStateChange() == ItemEvent.DESELECTED) {
					
					getObject().selected_radio_boton = null;
				}
				
			}
		});
		
		
		
	}
	
	private void configurarBotones() {
		
		new BotonesCandidatos(this);
			
	}
	
	protected Candidatos getObject(){
		
		return this;
	}
	
	protected JRadioButton getMostrarBoton() {
		
		return mostrar;
	}
	
	protected JRadioButton getAgregarBoton() {
		
		return agregar;
	}

	protected ItemEvent getRadioBotonSeleccionado() {
		
		return selected_radio_boton;
	}

	public void agregarCandidato(String[] datos, String path) {
		tabla.agregarCandidato(datos, path);
	}
	
	protected void agregarCandidatoAleatorio(Agregar agregar) {
		
		tabla.agregarCandidatoAleatorio(agregar);
		
	}
	
	Tabla getTabla() {
		return tabla;
	}


	protected Controlador getController() {
		
		return controller;
	}
	
	public void setbarra() {
		
		barra.setIndeterminate(!barra.isIndeterminate());
	}
}

