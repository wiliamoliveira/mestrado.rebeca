package br.puc.mestrado.rebeca.excel;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import br.puc.mestrado.rebeca.F;
import br.puc.mestrado.rebeca.Opcao;
import br.puc.mestrado.rebeca.calc.ProcessTask;
import br.puc.mestrado.rebeca.config.Config;
import br.puc.mestrado.rebeca.utils.ExcelUtils;
import br.puc.mestrado.rebeca.utils.ProgressLogIndicator;

public class WsF implements ExcelWS {
	private static Logger logger = Logger.getLogger(WsF.class);

	private ProgressLogIndicator progress;
	private String name;
	private Opcao o;
	private Config c;
	private Sheet sh;

	public WsF(Sheet sh, Config c, Opcao o) {
		this.name = sh.getSheetName();
		this.o = o;
		this.c = c;
		this.sh = sh;
		this.progress = new ProgressLogIndicator(logger, "Sheet [" + name + "]", (int) c.getT() + 1);
	}

	public void create(ProcessTask pt) {
		logger.info("Creating worksheet [" + name + "]");

		createHeader();

		int line = 1;
		for (int k = 0; k < o.getArrayF()[0][0].length; k++) {
			for (int j = 0; j < o.getArrayF()[0].length; j++) {
				for (int i = 0; i < o.getArrayF().length; i++) {
					F f = o.getF(i, j, k);
					createRow(line++, i, j, k, f);
				}
			}

			progress.next();
			pt.updateProgress();
		}
		logger.info("Worksheet [" + name + "] has been created.");
	}

	private void createRow(int line, int i, int j, int k, F f) {
		Row row = sh.createRow(line);
		int col = 0;
		ExcelUtils.createCell(row, col++, i);
		ExcelUtils.createCell(row, col++, j);
		ExcelUtils.createCell(row, col++, k);
		ExcelUtils.createCell(row, col++, o.getArrayP()[i]);
		ExcelUtils.createCell(row, col++, o.getArrayE()[j]);
		ExcelUtils.createCell(row, col++, f.getFijk());
		ExcelUtils.createCell(row, col++, f.getQ());
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

		int col = 0;
		ExcelUtils.createCell(row, col++, "i", cs);
		ExcelUtils.createCell(row, col++, "j", cs);
		ExcelUtils.createCell(row, col++, "k", cs);
		ExcelUtils.createCell(row, col++, "P(i)", cs);
		ExcelUtils.createCell(row, col++, "E(j)", cs);
		ExcelUtils.createCell(row, col++, "F(i,j,k)", cs);
		ExcelUtils.createCell(row, col++, "q", cs);
	}

	public int getMaxProgress() {
		return (int) c.getT() + 1;
	}
}
