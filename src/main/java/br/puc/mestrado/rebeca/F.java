package br.puc.mestrado.rebeca;

public class F {
	private double fijk;
	private double q;

	public void configuraF(double fijk, double q) {
		this.q = q;
		this.fijk = fijk;
	}

	public F() {
		this.q = 0;
		this.fijk = 0;
	}

	public double getFijk() {
		return fijk;
	}

	public double getQ() {
		return q;
	}

}
