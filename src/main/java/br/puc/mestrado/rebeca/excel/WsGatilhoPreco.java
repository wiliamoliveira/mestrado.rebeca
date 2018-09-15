package br.puc.mestrado.rebeca.excel;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import br.puc.mestrado.rebeca.Gatilho;
import br.puc.mestrado.rebeca.Opcao;
import br.puc.mestrado.rebeca.calc.ProcessTask;
import br.puc.mestrado.rebeca.config.Config;
import br.puc.mestrado.rebeca.utils.ExcelUtils;
import br.puc.mestrado.rebeca.utils.ProgressLogIndicator;

public class WsGatilhoPreco implements ExcelWS {
	private static Logger logger = Logger.getLogger(WsGatilhoPreco.class);

	private ProgressLogIndicator progress;
	private String name;
	private Opcao o;
	private Config c;
	private Sheet sh;

	public WsGatilhoPreco(Sheet sh, Config c, Opcao o) {
		this.name = sh.getSheetName();
		this.o = o;
		this.c = c;
		this.sh = sh;
		this.progress = new ProgressLogIndicator(logger, "Sheet [" + name + "]", (int) c.getT() + 1);
	}

	public void create(ProcessTask pt) {
		logger.info("Creating worksheet [" + name + "]");

		createFirstLine();

		for (int k = 0, line = 1; k < o.getArrayF()[0][0].length; k++, line++) {
			Row row = sh.createRow(line);
			ExcelUtils.createCell(row, 0, k);

			for (int j = 0, col = 1; j < o.getArrayF()[0].length; j++, col++) {
				Gatilho g = o.getGatilho(j, k);

				if (g == null)
					createCell(row, line, col, "-");
				else
					createCell(row, line, col, g.getP());
			}

			progress.next();
			pt.updateProgress();
		}

		logger.info("Worksheet [" + name + "] has been created.");
	}

	private void createFirstLine() {
		int col = 1;
		int line = 0;
		Row row = sh.createRow(line);
		ExcelUtils.createCell(row, 0, sh.getSheetName()+"(T x E)");

		for (int j = 0; j < o.getArrayE().length; j++, col++) {
			ExcelUtils.createCell(row, col, o.getArrayE()[j]);
		}
	}

	private void createCell(Row row, int line, int col, double P) {
		ExcelUtils.createCell(row, col, P);
	}

	private void createCell(Row row, int line, int col, String nullP) {
		ExcelUtils.createCell(row, col, nullP);
	}

	public int getMaxProgress() {
		return (int) c.getT() + 1;
	}
}
