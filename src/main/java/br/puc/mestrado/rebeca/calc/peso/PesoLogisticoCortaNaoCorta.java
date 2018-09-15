package br.puc.mestrado.rebeca.calc.peso;

import br.puc.mestrado.rebeca.Opcao;
import br.puc.mestrado.rebeca.config.Config;

public class PesoLogisticoCortaNaoCorta extends Peso {
	public PesoLogisticoCortaNaoCorta(int i, int j, int k, double q, Config c, Opcao o) {
		super(i, j, k, q, c, o);
	}

	@Override
	protected double calculaPuj(int i, int j, double q, Config c, Opcao o) {
		double termo1= c.getVelRev() * (c.getEbarra() - (j * o.getDeltaE()))* j * o.getDeltaE();
		return ((termo1 - q) * o.getDeltaT())
				/ (2 * o.getDeltaE());
	}

	@Override
	protected double calculaPdj(int i, int j, double q, Config c, Opcao o) {
		return -1 * calculaPuj(i, j, q, c, o);
	}

	@Override
	protected double calculaPui(int i, int j, Config c, Opcao o) {
		return ((Math.pow(c.getSigma(), 2) * Math.pow(i, 2)) + ((c.getR() - c.getConvYield()) * i))
				* (o.getDeltaT() / 2);
	}

	@Override
	protected double calculaPdi(int i, int j, Config c, Opcao o) {
		return ((Math.pow(c.getSigma(), 2) * Math.pow(i, 2)) - ((c.getR() - c.getConvYield()) * i))
				* (o.getDeltaT() / 2);
	}

	@Override
	protected double calculaPm(int i, int j, Config c, Opcao o) {
		return 1 - (Math.pow(c.getSigma(), 2) * Math.pow(i, 2) * o.getDeltaT());
	}
}
