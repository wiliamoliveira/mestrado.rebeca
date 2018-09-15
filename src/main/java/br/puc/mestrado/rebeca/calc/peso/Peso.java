package br.puc.mestrado.rebeca.calc.peso;

import br.puc.mestrado.rebeca.Opcao;
import br.puc.mestrado.rebeca.config.Config;

public abstract class Peso {

	//private static Logger logger = Logger.getLogger(Peso.class);

	protected double puj;
	protected double pdj;
	protected double pui;
	protected double pdi;
	protected double pm;

	public Peso(int i, int j, int k, double q, Config c, Opcao o) {
		puj = calculaPuj(i, j, q, c, o);
		pdj = calculaPdj(i, j, q, c, o);
		pui = calculaPui(i, j, c, o);
		pdi = calculaPdi(i, j, c, o);
		pm = calculaPm(i, j, c, o);

		//if (logger.isDebugEnabled()) {
			//double sun = puj + pdj + pui + pdi + pm;
			// if (sun != 1 && sun != 0.9999999999999999)
			//if (puj < 0 || pdj < 0 || pui < 0 || pdi < 0 || pm < 0)
				//	logger.debug(this.getClass() + " - Negative weight: puj=" + puj + ", pdj=" + pdj + ", pui=" + pui + ", pdi=" + pdi + ", pm=" + pm + ", sun=" + sun);
			//}
	}

	protected abstract double calculaPuj(int i, int j, double q, Config c, Opcao o);

	protected abstract double calculaPdj(int i, int j, double q, Config c, Opcao o);

	protected abstract double calculaPui(int i, int j, Config c, Opcao o);

	protected abstract double calculaPdi(int i, int j, Config c, Opcao o);

	protected abstract double calculaPm(int i, int j, Config c, Opcao o);

	public double getPuj() {
		return puj;
	}

	public double getPdj() {
		return pdj;
	}

	public double getPui() {
		return pui;
	}

	public double getPdi() {
		return pdi;
	}

	public double getPm() {
		return pm;
	}

}
