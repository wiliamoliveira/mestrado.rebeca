package br.puc.mestrado.rebeca.calc.delta;

import org.apache.log4j.Logger;

import br.puc.mestrado.rebeca.Opcao;
import br.puc.mestrado.rebeca.config.Config;
import br.puc.mestrado.rebeca.exception.ConvergenceException;
import br.puc.mestrado.rebeca.utils.ArrayUtils;
import br.puc.mestrado.rebeca.utils.NumberUtils;

public abstract class Delta implements FluxoDeCaixa {
	private static Logger logger = Logger.getLogger(Delta.class);

	protected Double deltaP;
	protected Double deltaE;
	protected Double deltaT;
	protected int maxStepP;
	protected int maxStepE;
	protected int maxStepT;
	protected Double df;
	protected double q;

	public Delta(Config c) throws ConvergenceException {
		calcQ(c);
		calcDeltaP(c);
		calcDeltaE(c);
		calcDeltaT(c);
		calcDf(c);
	}

	protected void calcQ(Config c) {
		this.q = (c.getE0() * Math.pow((1 + c.getMiE()), c.getT())) / c.getT();
	}

	protected void calcDeltaP(Config c) throws ConvergenceException {
		double criteria = Math.sqrt((Math.pow(c.getPmax(), 2) * Math.pow(c.getSigma(), 2)) / Math.abs(c.getR() - c.getConvYield()));

		this.deltaP = c.getP0() / (100 / c.getAccuracy());
		this.maxStepP = NumberUtils.roundUp(c.getPmax() / this.deltaP) + 1;

		if ((this.maxStepP * this.deltaP) < c.getPmax()) {
			this.maxStepP++;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Init deltaP: [" + this.deltaP + "]");
			logger.debug("Init maxStepP: [" + this.maxStepP + "]");
		}

		if (this.deltaP > criteria)
			throw new ConvergenceException("deltaP", this.deltaP, criteria);
	}

	protected void calcDeltaE(Config c) throws ConvergenceException {
		// double criteria = (Math.pow(c.getEbarra(), 2) * Math.pow(c.getSigmaE(), 2)) / Math.abs(c.getR() - c.getMiE() - (q / c.getEbarra()));

		this.deltaE = c.getE0() / (100 / c.getAccuracy());
		this.maxStepE = NumberUtils.roundUp(c.getEbarra() / this.deltaE) + 1;

		if ((this.maxStepE * this.deltaE) < c.getEbarra()) {
			this.maxStepE++;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Init deltaE: [" + this.deltaE + "]");
			logger.debug("Init maxStepE: [" + this.maxStepE + "]");
		}

		// if (this.deltaE > criteria)
		// throw new ConvergenceException("deltaE", this.deltaE, criteria);
	}

	protected void calcDeltaT(Config c) {
		this.deltaT = Math.pow(this.deltaP, 2) / (Math.pow(c.getSigma(), 2) * Math.pow(c.getPmax(), 2));
		this.maxStepT = NumberUtils.roundUp(c.getT() / this.deltaT);

		// after calc maxStepT deltaT will be redefined
		this.deltaT = c.getT() / this.maxStepT;

		if (logger.isDebugEnabled()) {
			logger.debug("Init deltaT: [" + this.deltaT + "]");
			logger.debug("Init maxStepT: [" + this.maxStepT + "]");
		}
	}

	protected void calcDf(Config c) {
		this.df = 1 / (1 + (c.getR() * this.deltaT));

		if (logger.isDebugEnabled()) {
			logger.debug("Init df: [" + this.df + "]");
		}
	}

	public abstract double calculaFC(double P, double E, double q, Config c);

	public abstract double calculaVPL(Config c, Opcao o);

	public static int indexP(double[] arrayP, double P) {
		try {
			return ArrayUtils.find(arrayP, P);
		} catch (RuntimeException e) {
			System.out.println("P: " + P);
			System.out.println(org.apache.commons.lang3.ArrayUtils.toString(arrayP));
			throw e;
		}
	}

	public static int indexE(double[] arrayE, double E) {
		try {
			if (E < 0) {
				System.out.println("E negativo, ignorando. " + E);
				return 0;
			} else {
				return ArrayUtils.find(arrayE, E);
			}
		} catch (RuntimeException e) {
			System.out.println("E: " + E);
			System.out.println(org.apache.commons.lang3.ArrayUtils.toString(arrayE));
			throw e;
		}

	}

	public static Logger getLogger() {
		return logger;
	}

	public Double getDeltaP() {
		return deltaP;
	}

	public Double getDeltaE() {
		return deltaE;
	}

	public Double getDeltaT() {
		return deltaT;
	}

	public int getMaxStepP() {
		return maxStepP;
	}

	public int getMaxStepE() {
		return maxStepE;
	}

	public int getMaxStepT() {
		return maxStepT;
	}

	public Double getDf() {
		return df;
	}

	public double getQ() {
		return q;
	}
}
