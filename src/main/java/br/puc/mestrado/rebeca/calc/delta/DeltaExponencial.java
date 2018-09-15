package br.puc.mestrado.rebeca.calc.delta;

import org.apache.log4j.Logger;

import br.puc.mestrado.rebeca.Opcao;
import br.puc.mestrado.rebeca.config.Config;
import br.puc.mestrado.rebeca.exception.ConvergenceException;
import br.puc.mestrado.rebeca.utils.NumberUtils;

public class DeltaExponencial extends Delta {

	private static Logger logger = Logger.getLogger(DeltaExponencial.class);

	public DeltaExponencial(Config c) throws ConvergenceException {
		super(c);
	}

	@Override
	protected void calcDeltaT(Config c) {
		double termo1 = Math.pow(c.getSigma(), 2) * Math.pow(c.getPmax(), 2) / Math.pow(this.getDeltaP(), 2);
		double termo2 = Math.pow(c.getSigmaE(), 2) * Math.pow(c.getEbarra(), 2) / Math.pow(this.getDeltaE(), 2);
		this.deltaT = 1 / (termo1 + termo2);

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
	}
	
	public double calculaVPL(Config c, Opcao o) {
		// there is no VPL, return -1 as a signal of ignore;
		return -1;
	}
}
