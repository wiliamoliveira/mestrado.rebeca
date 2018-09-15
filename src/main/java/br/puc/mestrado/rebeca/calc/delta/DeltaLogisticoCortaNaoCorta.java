package br.puc.mestrado.rebeca.calc.delta;

import org.apache.log4j.Logger;

import br.puc.mestrado.rebeca.Opcao;
import br.puc.mestrado.rebeca.config.Config;
import br.puc.mestrado.rebeca.exception.ConvergenceException;
import br.puc.mestrado.rebeca.utils.NumberUtils;

public class DeltaLogisticoCortaNaoCorta extends Delta {

	private static Logger logger = Logger.getLogger(DeltaLogisticoCortaNaoCorta.class);

	public DeltaLogisticoCortaNaoCorta(Config c) throws ConvergenceException {
		super(c);
	}

	@Override
	protected void calcDeltaT(Config c) {
		this.deltaT = 1 / ((Math.pow(c.getSigma(), 2) * Math.pow(c.getPmax() / deltaP, 2)));

		this.maxStepT = NumberUtils.roundUp(c.getT() / this.deltaT);

		// after calc maxStepT, deltaT will be redefined
		this.deltaT = c.getT() / this.maxStepT;

		if (logger.isDebugEnabled()) {
			logger.debug("Init deltaT: [" + this.deltaT + "]");
			logger.debug("Init maxStepT: [" + this.maxStepT + "]");
		}
	}

	public double calculaFC(double P, double E, double q, Config c) {
		return ((P * q) - (c.getC1() * q) - (c.getC2() * Math.pow(q, 2) / 2)) * (1 - c.getTau());
		//return ((P * q) - (c.getC1() * q)) * (1 - c.getTau());
	}

	public double calculaVPL(Config c, Opcao o) {
		double sun = 0;
		for (int k = 0; k <= c.getT(); k++) {
			sun += calculaFC(c.getP0(), c.getE0(), c.getQMax(), c) / Math.pow(1 + c.getR(), k);
		}

		return sun;
	}
}
