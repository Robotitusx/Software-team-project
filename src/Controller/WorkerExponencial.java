package Controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import modelo.Persona;
import modelo.SolverExponencial;

public class WorkerExponencial extends SwingWorker<List<String>, Void> {

	
	private SolverExponencial _solver;
	
	public WorkerExponencial(SolverExponencial solver) {
	
		_solver = solver;
	}
	
	@Override
	protected List<String> doInBackground() throws Exception {
		
		
		_solver.resolver();
		return Conversor.enviardatos(_solver);
	}
	
	

	public static long getTiempoFinal() {
		
		return SolverExponencial.getTiempoFinal();
	}

	public List<String> getPaths() {
		
		List<Persona> personas = _solver.getEquipo().miembros();
		List<String> paths = new ArrayList<>();
		personas.stream().map(Persona::getPath).forEach(path -> paths.add(path));
	
		return paths;
	}
}
