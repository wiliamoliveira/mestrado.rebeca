package br.puc.mestrado.rebeca.excel;

import br.puc.mestrado.rebeca.calc.ProcessTask;

public interface ExcelWS {

	public void create(ProcessTask pt);
	public int getMaxProgress();
}
