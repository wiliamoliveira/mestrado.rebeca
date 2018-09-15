package br.puc.mestrado.rebeca.excel;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import br.puc.mestrado.rebeca.Gatilho;
import br.puc.mestrado.rebeca.Opcao;
import br.puc.mestrado.rebeca.calc.ProcessTask;
import br.puc.mestrado.rebeca.config.Config;
import br.puc.mestrado.rebeca.utils.ExcelUtils;
import br.puc.mestrado.rebeca.utils.ProgressLogIndicator;

public class WsGatilhoOld implements ExcelWS {
	private static Logger logger = Logger.getLogger(WsGatilhoPreco.class);

	private ProgressLogIndicator progress;
	private String name;
	private Opcao o;
	private Config c;
	private Sheet sh;

	public WsGatilhoOld(Sheet sh, Config c, Opcao o) {
		this.name = sh.getSheetName();
		this.o = o;
		this.c = c;
		this.sh = sh;
		this.progress = new ProgressLogIndicator(logger, "Sheet [" + name + "]", (int)c.getT()+1);
	}

	public void create(ProcessTask pt) {
		logger.info("Creating worksheet [" + name + "]");
		
		createHeader();

		int line = 1;
		for (int k = 0; k < (int)c.getT()+1; k++) {
			for (int j = 0; j < o.getMaxStepE(); j++) {
				Gatilho gatilho = o.getGatilho(j, k);

				if (gatilho != null)
					createRow(line++, k, gatilho.getE(), gatilho.getP(), gatilho.getF());
			}

			progress.next();
			pt.updateProgress();
		}
		logger.info("Worksheet [" + name + "] has been created.");
	}

	public void createRow(int line, int k, double E, double P, double F) {
		Row row = sh.createRow(line);

		ExcelUtils.createCell(row, 0, k);
		ExcelUtils.createCell(row, 1, E);
		ExcelUtils.createCell(row, 2, P);
		ExcelUtils.createCell(row, 3, F);
	}

	private void createHeader() {
		CellStyle cs = sh.getWorkbook().createCellStyle();
		Font font = sh.getWorkbook().createFont();
		font.setBold(true);
		font.setFontHeightInPoints((short) 12);
		cs.setFont(font);
		cs.setAlignment(CellStyle.ALIGN_CENTER);

		Row row = sh.createRow(0);
		row.setRowStyle(cs);

		ExcelUtils.createCell(row, 0, "k", cs);
		ExcelUtils.createCell(row, 1, "E(j)", cs);
		ExcelUtils.createCell(row, 2, "P(i)", cs);
		ExcelUtils.createCell(row, 3, "F(i,j,k)", cs);
	}
	
	public int getMaxProgress() {
		return (int) c.getT() + 1;
	}
}
