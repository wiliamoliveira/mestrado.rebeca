package br.puc.mestrado.rebeca.calc;

import br.puc.mestrado.rebeca.Opcao;
import br.puc.mestrado.rebeca.config.Config;
import br.puc.mestrado.rebeca.exception.ConvergenceException;

public class TaskGenerateFLogistico extends TaskF {

	public TaskGenerateFLogistico(Config c, Opcao o) {
		super(c, o);
	}

	public double calcQStar(int i, int j, int k) throws ConvergenceException {
		double qStar;
		double termo1 = ((i * o.getDeltaP()) - c.getC1()) / c.getC2();

		if (j == o.getMaxStepE() - 1) {
			qStar = termo1;
		} else {
			double termo2 = tmpF.getFkPlusOne(i, j + 1) - tmpF.getFkPlusOne(i, j - 1);
			double termo3 = ((1 - c.getTau()) * c.getC2() * 2 * o.getDeltaE());
			qStar = termo1 - (termo2 / termo3);
		}

		qStar = Math.max(0, qStar);

		if (qStar > 0) {
			double fluxoDeCaixa = ((i * o.getDeltaP() * qStar) - (c.getC1() * qStar) - ((c.getC2() * Math.pow(qStar, 2)) / 2)) * (1 - c.getTau());

			if (fluxoDeCaixa <= 0) {
				qStar = 0;
			}
		}

		return qStar;
	}

	public double calculaFijk(int i, int j, int k, double qStar) throws ConvergenceException {

		double fijk = 0;
		double fator = 1 / (c.getR() + (1 / o.getDeltaT()));

		if ((j == o.getMaxStepE() - 1) && (i == o.getMaxStepP() - 1)) {
			double termo1 = (c.getR() - c.getConvYield()) * o.getArrayP()[i] * (o.getArrayE()[j] - c.getEmin());
			double fluxo = (1 - c.getTau()) * ((o.getArrayP()[i] * qStar) - (c.getC1() * qStar) - ((c.getC2() * Math.pow(qStar, 2)) / 2));

			fijk = fator * (termo1 + (tmpF.getFkPlusOne(i, j) / o.getDeltaT()) + fluxo);

		} else if (j == o.getMaxStepE() - 1) {
			double pitermo1 = Math.pow(c.getSigma(), 2) * Math.pow(i, 2);
			double pitermo2 = (c.getR() - c.getConvYield()) * i;
			double puifinal = (pitermo1 + pitermo2) / 2;
			double pdifinal = (pitermo1 - pitermo2) / 2;
			double termo1 = puifinal * tmpF.getFkPlusOne(i + 1, j);
			double termo2 = pdifinal * tmpF.getFkPlusOne(i - 1, j);
			double termo3 = ((1 / o.getDeltaT()) - (Math.pow(c.getSigma(), 2)) * Math.pow(i, 2)) * tmpF.getFkPlusOne(i, j);
			double fluxo = (1 - c.getTau()) * ((o.getArrayP()[i] * qStar) - (c.getC1() * qStar) - ((c.getC2() * Math.pow(qStar, 2)) / 2));

			fijk = fator * (termo1 + termo2 + termo3 + fluxo);

		} else if (i == o.getMaxStepP() - 1) {
			double pjtermo1 = (((c.getVelRev() * (c.getEbarra() - (j * o.getDeltaE()))) * j * o.getDeltaE()) - qStar) / o.getDeltaE();
			double pujfinal = (pjtermo1) / 2;
			double pdjfinal = (-pjtermo1) / 2;
			double termo1 = pujfinal * tmpF.getFkPlusOne(i, j + 1);
			double termo2 = pdjfinal * tmpF.getFkPlusOne(i, j - 1);
			double termo3 = (c.getR() - c.getConvYield()) * o.getArrayP()[i] * (o.getArrayE()[j] - c.getEmin());
			double termo4 = ((1 / o.getDeltaT()) - (Math.pow(c.getSigmaE(), 2)) * Math.pow(j, 2)) * tmpF.getFkPlusOne(i, j);
			double fluxo = (1 - c.getTau()) * ((o.getArrayP()[i] * qStar) - (c.getC1() * qStar) - ((c.getC2() * Math.pow(qStar, 2)) / 2));

			fijk = fator * (termo1 + termo2 + termo3 + termo4 + fluxo);

		} else {
			double pmtermo1 = Math.pow(c.getSigma(), 2) * Math.pow(i, 2);
			double pmtermo2 = 1 / o.getDeltaT();
			double pmfinal = pmtermo2 - pmtermo1;
			double pitermo1 = Math.pow(c.getSigma(), 2) * Math.pow(i, 2);
			double pitermo2 = (c.getR() - c.getConvYield()) * i;
			double puifinal = (pitermo1 + pitermo2) / 2;
			double pdifinal = (pitermo1 - pitermo2) / 2;
			double pjtermo1 = (((c.getVelRev() * (c.getEbarra() - (j * o.getDeltaE()))) * j * o.getDeltaE()) - qStar) / o.getDeltaE();
			double pujfinal = (pjtermo1) / 2;
			double pdjfinal = (-pjtermo1) / 2;
			double termo1 = puifinal * tmpF.getFkPlusOne(i + 1, j);
			double termo2 = pdifinal * tmpF.getFkPlusOne(i - 1, j);
			double termo3 = pmfinal * tmpF.getFkPlusOne(i, j);
			double termo4 = pujfinal * tmpF.getFkPlusOne(i, j + 1);
			double termo5 = pdjfinal * tmpF.getFkPlusOne(i, j - 1);
			double fluxo = (1 - c.getTau()) * ((o.getArrayP()[i] * qStar) - (c.getC1() * qStar) - ((c.getC2() * Math.pow(qStar, 2)) / 2));

			fijk = fator * (termo1 + termo2 + termo3 + termo4 + termo5 + fluxo);
		}

		return fijk;
	}

	public String taskName() {
		return "Calculating F Logístico...";
	}
}
