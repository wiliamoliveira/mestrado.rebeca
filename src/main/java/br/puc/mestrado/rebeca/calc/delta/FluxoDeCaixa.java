package br.puc.mestrado.rebeca.calc.delta;

import br.puc.mestrado.rebeca.Opcao;
import br.puc.mestrado.rebeca.config.Config;

public interface FluxoDeCaixa {
	public double calculaFC(double P, double E, double q, Config c);
	public double calculaVPL(Config c, Opcao o);
}
