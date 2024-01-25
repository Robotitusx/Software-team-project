package ventana;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.SwingWorker;

import Controller.WorkerBeneficio;
import Controller.WorkerExponencial;

class BotonesCandidatos {

	private JButton eliminar;
	private JButton agregar_persona;
	private JButton myteam;
	private JButton boton_incompatibles;
	private JButton guardar;
	
	private Candidatos _candidatos;
	private JComboBox<String> _modo;
	
	public BotonesCandidatos(Candidatos candidatos) {
	
		_candidatos = candidatos;
		
		configurarBotones();
		configurarModo();
		
		
	}
	
	private void configurarBotones() {
		
		init();
		
		escuchadorBotonAgregar();
		escuchadorBotonEliminar();
		escuchadorBotonMyTeam();
		escuchadorBotonIncompatibles();
		escuchadorBotonGuardar();
		
		colocarlimites();
		agregarBotones();
			
	}
	
	

	protected void init() {
		
		eliminar = new JButton("Eliminar");
		agregar_persona = new JButton("Agregar");
		myteam = new JButton("My Team");
		boton_incompatibles = new JButton("Incompatibles");
		guardar = new JButton("Guardar");
	}

	protected void agregarBotones() {
		
		_candidatos.getContentPane().add(myteam);
		_candidatos.getContentPane().add(agregar_persona);
		_candidatos.getContentPane().add(eliminar);
		_candidatos.getContentPane().add(boton_incompatibles);
		_candidatos.getContentPane().add(guardar);
	}

	protected void colocarlimites() {
		
		eliminar.setBounds(492, 49, 98, 26);
		guardar.setBounds(492, 100, 98, 26);
		agregar_persona.setBounds(492, 11, 98, 26);
		myteam.setBounds(492, 223, 98, 26);
		boton_incompatibles.setBounds(12, 265, 114, 26);
	}

	

	private void escuchadorBotonAgregar() {
	
	
		agregar_persona.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			
			new Agregar(_candidatos);
			}
		});
	
}
	private void escuchadorBotonGuardar() {
		
		
		guardar.addMouseListener(new MouseAdapter() {
		
			@Override
			public void mouseClicked(MouseEvent e) {
				
				_candidatos.getController().guardardatos();
			}
		});
	
		
	}
	
	private void escuchadorBotonEliminar() {
		
		eliminar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(!_candidatos.getTabla().sinseleccion()) {
					
					JOptionPane.showMessageDialog(_candidatos, "No ha seleccionado nada");
					
				}
				else {
					
					Integer dni = _candidatos.getTabla().borrarCandidato();
					_candidatos.getController().borrarCandidato(dni);
				}		
			}
		});
	
		
	}
	
	private void escuchadorBotonMyTeam() {
		
		myteam.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String modo = _modo.getSelectedItem().toString();
				myteam.setEnabled(false);
				modoBacktracking(modo);
				modoBeneficio(modo);
				
			}

			protected void modoBeneficio(String modo) {
				if(modo.equals("Por beneficio")) {
				
					WorkerBeneficio worker = _candidatos.getController().solucionPorBeneficio();
					worker.execute();
					
					_candidatos.setbarra();
					worker.addPropertyChangeListener(new PropertyChangeListener() {
						
						@Override
						public void propertyChange(PropertyChangeEvent evt) {
							
							if(evt.getNewValue() == SwingWorker.StateValue.DONE)
							
								try {
									new Solucion((ArrayList<String>) worker.get(), _candidatos, WorkerBeneficio.getTiempoFinal(), worker.getpaths());
									_candidatos.setbarra();
									myteam.setEnabled(true);
								} catch (InterruptedException | ExecutionException e1) {
									e1.printStackTrace();
								}
							
						}
					});
					
					
				}
			}

			protected void modoBacktracking(String modo) {
				if(modo.equals("Backtracking")){
					
					WorkerExponencial worker = _candidatos.getController().solucionExponencial();
					worker.execute();
					
					_candidatos.setbarra();
					worker.addPropertyChangeListener(new PropertyChangeListener() {
						
						@Override
						public void propertyChange(PropertyChangeEvent evt) {
			
							if(evt.getNewValue() == SwingWorker.StateValue.DONE) { 
								
								iniciarSolucion(worker);
								_candidatos.setbarra();
								myteam.setEnabled(true);
							}
									
						}

						protected void iniciarSolucion(WorkerExponencial worker) {
							
							try {
								
								new Solucion((ArrayList<String>) worker.get(), getCandidatos(), WorkerExponencial.getTiempoFinal(), 
										worker.getPaths());
								
								
								
							} 
							catch (InterruptedException | ExecutionException e) {
								e.printStackTrace();
							}
						}
					});
					
				}
			}
		});
		
		
	}
	
	private void escuchadorBotonIncompatibles() {
		
		boton_incompatibles.addActionListener(new ActionListener() {
			
			private ItemEvent selected;
			private JRadioButton agregar;
			private JRadioButton mostrar;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			
				init();
				if(!sinseleccion()) return;
				mostrar();
				agregar();
				
			}

			private boolean sinseleccion() throws HeadlessException {
				
				if(selected == null) {
					
					JOptionPane.showMessageDialog(_candidatos, "No ha seleccionado ninguna opcion");
					return false;
				}
				return true;
			}

			private void agregar() {
				
				if(selected.getSource().equals(agregar)) {
					
					int fila = _candidatos.tabla.getfilaseleccionada();
			
					if(fila == -1) return;
					if(_candidatos.tabla.seleccionvacia()) return;
					new Incompatible(_candidatos.getController(), Utilidades.obtenerDatos(_candidatos.tabla, fila)).setVisible(true);
				}
			}

			private void mostrar() {
				
				if(selected.getSource().equals(mostrar)) {
					
					_candidatos.getTabla().ResaltarIncompatibles(_candidatos.getController());
					_candidatos.getTabla().repaint();
					
				}
				
			}
			
			void init(){
				
				selected = _candidatos.getRadioBotonSeleccionado();
				mostrar = _candidatos.getMostrarBoton();
				agregar = _candidatos.getAgregarBoton();
			}
			
		});
		
		
	}
	
	private void configurarModo() {
		
		_modo = new JComboBox<>();
		_modo.addItem("Backtracking");
		_modo.addItem("Por beneficio");
		_modo.setBounds(492, 180, 98, 26);
		_candidatos.add(_modo);
	}
	
	protected Candidatos getCandidatos() {
		
		return _candidatos;
	}
	
	

}
