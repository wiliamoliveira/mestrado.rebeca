package br.puc.mestrado.rebeca;

public class TmpF {
	private int maxStepP;
	private int maxStepE;
	private F[][] fk;
	private F[][] fkPlusOne;

	public TmpF(int maxStepP, int maxStepE) {
		this.maxStepP = maxStepP;
		this.maxStepE = maxStepE;
		fk = new F[maxStepP][maxStepE];
		fkPlusOne = new F[maxStepP][maxStepE];

		init(fk);
		init(fkPlusOne);
	}

	private void init(F[][] array) {
		for (int i = 0; i < maxStepP; i++) {
			for (int j = 0; j < maxStepE; j++) {
				array[i][j] = new F();
			}
		}
	}

	public void newK() {
		fkPlusOne = fk;
		fk = new F[maxStepP][maxStepE];

		init(fk);
	}

	public double getFk(int i, int j) {
		return fk[i][j].getFijk();
	}

	public double getFkPlusOne(int i, int j) {
		return fkPlusOne[i][j].getFijk();
	}
	
	public double getQStarKPlusOne(int i, int j) {
		return fkPlusOne[i][j].getQ();
	}

	public void configura(int i, int j, double fijk, double q) {
		fk[i][j].configuraF(fijk, q);
	}
}
