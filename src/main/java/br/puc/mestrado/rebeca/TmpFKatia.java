package br.puc.mestrado.rebeca;

public class TmpFKatia {
	private int maxStepP;
	private int maxStepE;
	private FKatia[][] fk;
	private FKatia[][] fkPlusOne;

	public TmpFKatia(int maxStepP, int maxStepE) {
		this.maxStepP = maxStepP;
		this.maxStepE = maxStepE;
		fk = new FKatia[maxStepP][maxStepE];
		fkPlusOne = new FKatia[maxStepP][maxStepE];

		init(fk);
		init(fkPlusOne);
	}

	private void init(FKatia[][] array) {
		for (int i = 0; i < maxStepP; i++) {
			for (int j = 0; j < maxStepE; j++) {
				array[i][j] = new FKatia();
			}
		}
	}

	public void newK() {
		fkPlusOne = fk;
		fk = new FKatia[maxStepP][maxStepE];

		init(fk);
	}

	public double getFk(int i, int j) {
		return fk[i][j].getFijk();
	}

	public double getFkPlusOne(int i, int j) {
		return fkPlusOne[i][j].getFijk();
	}

	public void configura(int i, int j, double fijk, double qStar) {
		fk[i][j].configuraF(fijk, qStar);
	}
	
	
}
