package br.puc.mestrado.rebeca.excel;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import br.puc.mestrado.rebeca.Opcao;
import br.puc.mestrado.rebeca.calc.ProcessTask;
import br.puc.mestrado.rebeca.utils.ExcelUtils;
import br.puc.mestrado.rebeca.utils.ProgressLogIndicator;

public class WsExP implements ExcelWS {
	private static Logger logger = Logger.getLogger(WsExP.class);

	private ProgressLogIndicator progress;
	private String name;
	private Opcao o;
	private Sheet sh;

	public WsExP(Sheet sh, Opcao o) {
		this.name = sh.getSheetName();
		this.o = o;
		this.sh = sh;
		this.progress = new ProgressLogIndicator(logger, "Sheet [" + name + "]", o.getMaxStepT() - 1);
	}

	public void create(ProcessTask pt) {
		logger.info("Creating worksheet [" + name + "]");

		createFirstLine();

		for (int j = 0, line = 1; j < o.getMaxStepE(); j++, line++) {
			Row row = sh.createRow(line);
			ExcelUtils.createCell(row, 0, o.getArrayE()[j]);

			for (int i = 0, col = 1; i < o.getMaxStepP(); i++, col++) {
				createCell(row, line, col, o.getFijk(i, j, 0));
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
		ExcelUtils.createCell(row, 0, sh.getSheetName());

		for (int i = 0; i < o.getArrayP().length; i++, col++) {
			ExcelUtils.createCell(row, col, o.getArrayP()[i]);
		}
	}

	private void createCell(Row row, int line, int col, double Fijk) {
		ExcelUtils.createCell(row, col, Fijk);

	}
	
	public int getMaxProgress() {
		return o.getMaxStepE();
	}
}
