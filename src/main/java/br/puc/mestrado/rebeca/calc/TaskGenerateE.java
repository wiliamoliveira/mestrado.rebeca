package br.puc.mestrado.rebeca.calc;

import org.apache.log4j.Logger;

import br.puc.mestrado.rebeca.Opcao;
import br.puc.mestrado.rebeca.exception.ConvergenceException;
import br.puc.mestrado.rebeca.utils.ProgressLogIndicator;

public class TaskGenerateE implements Task {
	private static Logger logger = Logger.getLogger(TaskGenerateE.class);

	private Opcao o;
	private ProcessTask pt;

	public TaskGenerateE(Opcao o) {
		this.o = o;
	}

	public void doInit(ProcessTask pt) {
		this.pt = pt;
		o.setArrayE(new double[o.getMaxStepE()]);
	}

	public void doJob() throws ConvergenceException {
		ProgressLogIndicator progress = new ProgressLogIndicator(logger, "Task Generate E", o.getMaxStepE() - 1);

		for (int j = 0; j < o.getArrayE().length; j++) {
			o.getArrayE()[j] = j * o.getDeltaE();

			progress.next();
			pt.updateProgress();
		}

		logger.info("Task Generate E has finished!");

	}

	public int getMaxProgress() {
		return o.getMaxStepE() - 1;
	}

	public String taskName() {
		return "Calculating Invetory...";
	}
}
