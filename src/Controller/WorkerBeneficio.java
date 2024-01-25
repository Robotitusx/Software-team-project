package Controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

import modelo.Persona;
import modelo.SolverBeneficio;

public class WorkerBeneficio extends SwingWorker<List<String>, Void>{

	private SolverBeneficio _solver;
	
	public WorkerBeneficio(SolverBeneficio solver) {
		
		_solver = solver;
	}

	@Override
	protected List<String> doInBackground() throws Exception {
		
		_solver.resolver();
		
		return Conversor.enviardatos(_solver);
	}
	
	public static long getTiempoFinal()
	{
		return SolverBeneficio.getTiempoFinal();
		
	}

	public List<String> getpaths() {
		
		List<Persona> personas = _solver.getEquipo().miembros();
		List<String> paths = new ArrayList<>();
		personas.stream().map(Persona::getPath).forEach(path -> paths.add(path));
		
		return paths;
	}
	
	
}
