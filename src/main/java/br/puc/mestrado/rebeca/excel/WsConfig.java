package br.puc.mestrado.rebeca.excel;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import br.puc.mestrado.rebeca.Opcao;
import br.puc.mestrado.rebeca.calc.ProcessTask;
import br.puc.mestrado.rebeca.calc.delta.Delta;
import br.puc.mestrado.rebeca.config.Config;

public class WsConfig implements ExcelWS {
	private static Logger logger = Logger.getLogger(WsConfig.class);

	private String name;
	private Config c;
	private Opcao o;
	private Sheet sh;

	public WsConfig(Sheet sh, Config c, Opcao o) {
		this.name = sh.getSheetName();
		this.c = c;
		this.o = o;
		this.sh = sh;
	}

	public void create(ProcessTask pt) {
		logger.info("Creating worksheet [" + name + "]");

		CellStyle csColName = sh.getWorkbook().createCellStyle();
		Font font = sh.getWorkbook().createFont();
		font.setFontHeightInPoints((short) 12);
		font.setBold(true);
		csColName.setFont(font);
		csColName.setAlignment(CellStyle.ALIGN_RIGHT);
		
		int rowNum = 1;

		createRow(sh, rowNum++, "r", c.getR(), csColName);
		createRow(sh, rowNum++, "sigma", c.getSigma(), csColName);
		createRow(sh, rowNum++, "convYield", c.getConvYield(), csColName);
		createRow(sh, rowNum++, "velRev", c.getVelRev(), csColName);
		createRow(sh, rowNum++, "T", c.getT(), csColName);
		createRow(sh, rowNum++, "cf", c.getC2(), csColName);
		createRow(sh, rowNum++, "cv", c.getC1(), csColName);
		createRow(sh, rowNum++, "tau", c.getTau(), csColName);
		createRow(sh, rowNum++, "q", o.getQ(), csColName);
		createRow(sh, rowNum++, "accuracy", c.getAccuracy(), csColName);
		createRow(sh, rowNum++, "P0", c.getP0(), csColName);
		createRow(sh, rowNum++, "Pmax", c.getPmax(), csColName);
		createRow(sh, rowNum++, "E0", c.getE0(), csColName);
		createRow(sh, rowNum++, "Emin", c.getEmin(), csColName);
		createRow(sh, rowNum++, "Ebarra", c.getEbarra(), csColName);
		createRow(sh, rowNum++, "growthRate", c.getGrowthRate(), csColName);
		
		// new lines - just looking feel
		rowNum++;
		rowNum++;

		createRow(sh, rowNum++, "df", o.getDf(), csColName);
		createRow(sh, rowNum++, "detalP", o.getDeltaP(), csColName);
		createRow(sh, rowNum++, "detalE", o.getDeltaE(), csColName);
		createRow(sh, rowNum++, "detalT", o.getDeltaT(), csColName);
		createRow(sh, rowNum++, "maxStepP", o.getMaxStepP(), csColName);
		createRow(sh, rowNum++, "maxStepE", o.getMaxStepE(), csColName);
		createRow(sh, rowNum++, "maxStepT", o.getMaxStepT(), csColName);

		// new lines - just looking feel
		rowNum++;
		rowNum++;
				
		createRow(sh, rowNum++, "VPL", o.getD().calculaVPL(c, o), csColName);

		int indexP0 = Delta.indexP(o.getArrayP(), c.getP0());
		int indexE0 = Delta.indexE(o.getArrayE(), c.getE0());
		createRow(sh, rowNum++, "F(P0,E0,t0)", o.getFijk(indexP0, indexE0, 0), csColName);

		createRow(sh, rowNum++, "P0", o.getArrayP()[indexP0], csColName);
		createRow(sh, rowNum++, "E0", o.getArrayE()[indexE0], csColName);

		pt.updateProgress();

		logger.info("Worksheet [" + name + "] has been created.");
	}

	private void createRow(Sheet sh, int line, String colName, double colValue, CellStyle csColName) {
		Row row = sh.createRow(line);
		Cell cell = row.createCell(0);
		cell.setCellStyle(csColName);
		cell.setCellValue(colName);
		cell = row.createCell(1);
		cell.setCellValue(colValue);
	}

	public int getMaxProgress() {
		return 1;
	}
}
