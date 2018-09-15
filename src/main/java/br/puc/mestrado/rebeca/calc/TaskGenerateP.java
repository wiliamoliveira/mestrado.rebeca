package br.puc.mestrado.rebeca.calc;

import org.apache.log4j.Logger;

import br.puc.mestrado.rebeca.Opcao;
import br.puc.mestrado.rebeca.exception.ConvergenceException;
import br.puc.mestrado.rebeca.utils.ProgressLogIndicator;

public class TaskGenerateP implements Task {
	private static Logger logger = Logger.getLogger(TaskGenerateP.class);

	private Opcao o;
	private ProcessTask pt;

	public TaskGenerateP(Opcao o) {
		this.o = o;
	}

	public void doInit(ProcessTask pt) {
		this.pt = pt;
		o.setArrayP(new double[o.getMaxStepP()]);
	}

	public void doJob() throws ConvergenceException {
		ProgressLogIndicator progress = new ProgressLogIndicator(logger, "Task Generate P", o.getMaxStepP() - 1);

		for (int i = 0; i < o.getArrayP().length; i++) {
			o.getArrayP()[i] = i * o.getDeltaP();

			progress.next();
			pt.updateProgress();
		}

		logger.info("Task Generate P has finished!");
	}

	public int getMaxProgress() {
		return o.getMaxStepP() - 1;
	}
	
	public String taskName() {
		return "Calculating Prices...";
	}
}
