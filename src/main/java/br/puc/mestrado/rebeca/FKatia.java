package br.puc.mestrado.rebeca;

public class FKatia {
	private double fijk;
	private double qStar;

	public void configuraF(double fijk, double qStar) {
		this.fijk = fijk;
		this.qStar = qStar;
	}

	public FKatia() {
		this.fijk = 0;
		this.qStar = 0;
	}

	public double getFijk() {
		return fijk;
	}

	public double getqStar() {
		return qStar;
	}

}
