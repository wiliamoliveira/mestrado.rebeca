package br.puc.mestrado.rebeca;

import java.util.Arrays;

import org.apache.log4j.Logger;

import br.puc.mestrado.rebeca.calc.delta.Delta;
import br.puc.mestrado.rebeca.config.Config;
import br.puc.mestrado.rebeca.exception.ConvergenceException;
import br.puc.mestrado.rebeca.utils.PrintUtils;

public class Opcao {
	private static Logger logger = Logger.getLogger(Opcao.class);

	protected Config c;
	protected Delta d;
	protected Double vpl;
	protected double[] arrayP;
	protected double[] arrayE;
	protected F[][][] arrayF; // F(P,E,T)
	protected Gatilho[][] arrayGatilho; // P(E,T)

	public Opcao(Config c, Delta d) throws ConvergenceException {
		if (logger.isDebugEnabled()) {
			logger.debug("Creating object Opcao.");
		}

		this.c = c;
		this.d = d;

		vpl = null;

		if (logger.isDebugEnabled()) {
			logger.debug("Initialization has finished.");
		}
	}

	public double getYear(int k) {
		double year = k * d.getDeltaT();

		if (c.isTimeInMonth())
			year *= 12;

		return year;
	}

	public double getFijk(int i, int j, int k) {
		if (this.arrayF[i][j][k] != null) {
			return this.arrayF[i][j][k].getFijk();
		} else {
			return 0;
		}
	}

	public F getF(int i, int j, int k) {
		return this.arrayF[i][j][k];
	}

	public Gatilho getGatilho(int j, int k) {
		return this.arrayGatilho[j][k];
	}

	public F[][][] getArrayF() {
		return arrayF;
	}

	public void setArrayF(F[][][] arrayF) {
		this.arrayF = arrayF;
	}

	public Gatilho[][] getArrayGatilho() {
		return arrayGatilho;
	}

	public void setArrayGatilho(Gatilho[][] arrayGatilho) {
		this.arrayGatilho = arrayGatilho;
	}

	public double[] getArrayP() {
		return arrayP;
	}

	public void setArrayP(double[] arrayP) {
		this.arrayP = arrayP;
	}

	public double[] getArrayE() {
		return arrayE;
	}

	public void setArrayE(double[] arrayE) {
		this.arrayE = arrayE;
	}

	public Double getDf() {
		return d.getDf();
	}

	public Double getDeltaP() {
		return d.getDeltaP();
	}

	public Double getDeltaE() {
		return d.getDeltaE();
	}

	public Double getDeltaT() {
		return d.getDeltaT();
	}

	public int getMaxStepP() {
		return d.getMaxStepP();
	}

	public int getMaxStepE() {
		return d.getMaxStepE();
	}

	public int getMaxStepT() {
		return d.getMaxStepT();
	}

	public Delta getD() {
		return d;
	}

	public void setD(Delta d) {
		this.d = d;
	}

	public double getQ() {
		return d.getQ();
	}

	public void print() {
		logger.info(this.toStringBuilder());
	}

	public StringBuilder toStringBuilder() {
		StringBuilder sb = new StringBuilder();
		sb.append(PrintUtils.printConfig(c));

		sb.append("\n\nmaxStepT = [" + d.getMaxStepT() + "]");

		sb.append("\n\nArray P[" + d.getMaxStepP() + "]:\n");
		sb.append(Arrays.toString(arrayP));
		sb.append("\n\nArray E[" + d.getMaxStepE() + "]:\n");
		sb.append(Arrays.toString(arrayE));

		return sb;
	}

	@Override
	public String toString() {
		return this.toStringBuilder().toString();
	}
}
