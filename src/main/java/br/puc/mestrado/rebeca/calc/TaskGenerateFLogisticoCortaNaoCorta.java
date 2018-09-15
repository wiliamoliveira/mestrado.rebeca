package br.puc.mestrado.rebeca.calc;

import br.puc.mestrado.rebeca.Opcao;
import br.puc.mestrado.rebeca.calc.peso.Peso;
import br.puc.mestrado.rebeca.calc.peso.PesoLogisticoCortaNaoCorta;
import br.puc.mestrado.rebeca.config.Config;
import br.puc.mestrado.rebeca.exception.ConvergenceException;

public class TaskGenerateFLogisticoCortaNaoCorta extends TaskF {

	public TaskGenerateFLogisticoCortaNaoCorta(Config c, Opcao o) {
		super(c, o);
	}

	public double calcQStar(int i, int j, int k) throws ConvergenceException {
		double f = 0;
		double fmaximo = 0;
		double qStar = 0;
		double fQmax = 0;

		f = calculaFijk(i, j, k, 0);
		fQmax = calculaFijk(i, j, k, c.getQMax());
		if (f > fQmax) {
			f = fmaximo;
			qStar = 0;
		} else if (f <= fQmax) {
			fQmax = fmaximo;
			qStar = c.getQMax();
		}

		return qStar;

	}
	
	public double calculaFijk(int i, int j, int k, double qStar) throws ConvergenceException {
		Peso peso = new PesoLogisticoCortaNaoCorta(i, j, k, qStar, c, o);
		double fluxoDeCaixa = o.getD().calculaFC(o.getArrayP()[i], o.getArrayE()[j], qStar, c);

		double fijk = 0;

		if ((j == o.getMaxStepE() - 1) && (i == o.getMaxStepP() - 1)) {
			double termo1 = (c.getR() - c.getConvYield()) * o.getArrayP()[i] * (o.getArrayE()[j] - c.getEmin()) * o.getDeltaT();
			fijk = (o.getDf() * termo1) + (tmpF.getFkPlusOne(i, j)) + (fluxoDeCaixa * o.getDeltaT());
		} else if (j == o.getMaxStepE() - 1) {
			fijk = o.getDf() * (((peso.getPui() * tmpF.getFkPlusOne(i + 1, j)) + (peso.getPdi() * tmpF.getFkPlusOne(i - 1, j)) + (peso.getPm() * tmpF.getFkPlusOne(i, j)))
					+ (fluxoDeCaixa * o.getDeltaT()));
		} else if (i == o.getMaxStepP() - 1) {
			double termo1 = (c.getR() - c.getConvYield()) * o.getArrayP()[i] * (o.getArrayE()[j] - c.getEmin()) * o.getDeltaT();
			fijk = (o.getDf() * termo1) + (tmpF.getFkPlusOne(i, j)) + (peso.getPuj() * tmpF.getFkPlusOne(i, j + 1)) + (peso.getPdj() * tmpF.getFkPlusOne(i, j - 1))
					+ (fluxoDeCaixa * o.getDeltaT());
		} else {
			fijk = o.getDf() * ((peso.getPui() * tmpF.getFkPlusOne(i + 1, j)) + (peso.getPdi() * tmpF.getFkPlusOne(i - 1, j)) + (peso.getPm() * tmpF.getFkPlusOne(i, j))
					+ (peso.getPuj() * tmpF.getFkPlusOne(i, j + 1)) + (peso.getPdj() * tmpF.getFkPlusOne(i, j - 1)) + (fluxoDeCaixa * o.getDeltaT()));
		}

		return fijk;
	}

	public String taskName() {
		return "Calculating F Logístico Corta ou Não Corta...";
	}
}
