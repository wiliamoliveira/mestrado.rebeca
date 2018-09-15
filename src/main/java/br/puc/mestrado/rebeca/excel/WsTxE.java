package br.puc.mestrado.rebeca.excel;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import br.puc.mestrado.rebeca.Opcao;
import br.puc.mestrado.rebeca.calc.ProcessTask;
import br.puc.mestrado.rebeca.config.Config;
import br.puc.mestrado.rebeca.utils.ArrayUtils;
import br.puc.mestrado.rebeca.utils.ExcelUtils;
import br.puc.mestrado.rebeca.utils.ProgressLogIndicator;

public class WsTxE implements ExcelWS {
	private static Logger logger = Logger.getLogger(WsTxE.class);

	private ProgressLogIndicator progress;
	private String name;
	private Opcao o;
	private Config c;
	private Sheet sh;

	public WsTxE(Sheet sh, Config c, Opcao o) {
		this.name = sh.getSheetName();
		this.o = o;
		this.c = c;
		this.sh = sh;
		this.progress = new ProgressLogIndicator(logger, "Sheet [" + name + "]", (int) c.getT()+1);
	}

	public void create(ProcessTask pt) {
		logger.info("Creating worksheet [" + name + "]");

		//TODO P0 /detalT
		Integer indexP0 = ArrayUtils.find(o.getArrayP(), c.getP0());

		if (indexP0 == null)
			return;

		createFirstLine();

		for (int k = 0, line = 1; k < o.getArrayF()[0][0].length; k++, line++) {
			Row row = sh.createRow(line);
			ExcelUtils.createCell(row, 0, k);

			for (int j = 0, col = 1; j < o.getArrayF()[0].length; j++, col++) {
				createCell(row, line, col, o.getFijk(indexP0, j, k));
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

		for (int j = 0; j < o.getArrayE().length; j++, col++) {
			ExcelUtils.createCell(row, col, o.getArrayE()[j]);
		}
	}

	private void createCell(Row row, int line, int col, double Fijk) {
		ExcelUtils.createCell(row, col, Fijk);
	}
	
	public int getMaxProgress() {
		return (int) c.getT() + 1;
	}
}
