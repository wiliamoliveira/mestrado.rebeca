package br.puc.mestrado.rebeca.calc;

import org.apache.log4j.Logger;

import br.puc.mestrado.rebeca.F;
import br.puc.mestrado.rebeca.Opcao;
import br.puc.mestrado.rebeca.TmpF;
import br.puc.mestrado.rebeca.config.Config;
import br.puc.mestrado.rebeca.exception.ConvergenceException;
import br.puc.mestrado.rebeca.utils.ProgressLogIndicator;

public abstract class TaskF implements Task {

	private static Logger logger = Logger.getLogger(TaskF.class);

	protected Opcao o;
	protected Config c;
	protected TmpF tmpF;
	protected ProcessTask pt;
	protected int maxTime;

	public TaskF(Config c, Opcao o) {
		this.o = o;
		this.c = c;
	}

	public void doInit(ProcessTask pt) {
		this.pt = pt;

		if (c.isTimeInMonth())
			maxTime = (int) (c.getT() * 12) + 1;
		else
			maxTime = (int) c.getT() + 1;

		ProgressLogIndicator progress = new ProgressLogIndicator(logger, "Task Generate F - initialize", maxTime);

		o.setArrayF(new F[o.getMaxStepP()][o.getMaxStepE()][maxTime]);
		for (int k = 0; k < maxTime; k++) {
			for (int i = 0; i < o.getMaxStepP(); i++) {
				for (int j = 0; j < o.getMaxStepE(); j++) {
					o.getArrayF()[i][j][k] = new F();
				}
			}

			progress.next();
		}

		tmpF = new TmpF(o.getMaxStepP(), o.getMaxStepE());

		logger.info("Task Generate F - initialize has finished!");
	}

	public void doJob() throws ConvergenceException {
		int year;
		int yearBefore;
		ProgressLogIndicator progress = new ProgressLogIndicator(logger, "Task Generate F - run", o.getMaxStepT() - 2);

		double fijk, qStar;

		for (int k = o.getMaxStepT() - 2; k >= 0; k--) {
			year = (int) o.getYear(k);
			yearBefore = (int) o.getYear(k - 1);

			for (int i = o.getMaxStepP() - 1; i >= 1; i--) {
				for (int j = o.getMaxStepE() - 1; j >= 1; j--) {

					qStar = calcQStar(i, j, k);
					fijk = calculaFijk(i, j, k, qStar);
					double q = qStar;

					tmpF.configura(i, j, fijk, q);

					if ((yearBefore < year || k == 0) && year != maxTime - 1) {
						o.getF(i, j, year).configuraF(fijk, q);
					}
				}
			}

			tmpF.newK();

			progress.next();
			pt.updateProgress();
		}

		logger.info("Task Generate F - run has finished!");
	}

	public int getMaxProgress() {
		return o.getMaxStepT() - 2;
	}

	public abstract double calculaFijk(int i, int j, int k, double q) throws ConvergenceException;

	public abstract double calcQStar(int i, int j, int k) throws ConvergenceException;

}
