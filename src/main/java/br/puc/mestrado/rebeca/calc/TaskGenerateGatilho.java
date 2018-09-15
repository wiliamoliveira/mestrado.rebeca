package br.puc.mestrado.rebeca.calc;

import org.apache.log4j.Logger;

import br.puc.mestrado.rebeca.Gatilho;
import br.puc.mestrado.rebeca.Opcao;
import br.puc.mestrado.rebeca.config.Config;
import br.puc.mestrado.rebeca.exception.ConvergenceException;
import br.puc.mestrado.rebeca.utils.ProgressLogIndicator;

public class TaskGenerateGatilho implements Task {
	private static Logger logger = Logger.getLogger(TaskGenerateGatilho.class);

	private Opcao o;
	//private Config c;
	private ProcessTask pt;

	public TaskGenerateGatilho(Config c, Opcao o) {
		//this.c = c;
		this.o = o;
	}

	public void doInit(ProcessTask pt) {
		this.pt = pt;
		o.setArrayGatilho(new Gatilho[o.getArrayF()[0].length][o.getArrayF()[0][0].length]);
	}

	public void doJob() throws ConvergenceException {
		ProgressLogIndicator progress = new ProgressLogIndicator(logger, "calculaGatilho", o.getArrayF()[0][0].length);
		double currentQ = 0;

		for (int k = 0; k < o.getArrayF()[0][0].length; k++) {
			for (int j = 0; j < o.getArrayF()[0].length; j++) {
				currentQ = 0;
				for (int i = 0; i < o.getArrayF().length; i++) {

					currentQ = o.getF(i, j, k).getQ();
					if (currentQ > 0) {
						o.getArrayGatilho()[j][k] = new Gatilho(o.getArrayP()[i], o.getArrayE()[j], o.getFijk(i - 1, j, k));
						break;
					}
				}
			}

			progress.next();
			pt.updateProgress();
		}
	}

	public int getMaxProgress() {
		return 0;
	}

	public String taskName() {
		return "Calculating Trigger...";
	}

}
