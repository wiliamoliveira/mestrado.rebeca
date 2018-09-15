package br.puc.mestrado.rebeca.excel;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import br.puc.mestrado.rebeca.Opcao;
import br.puc.mestrado.rebeca.calc.ProcessTask;
import br.puc.mestrado.rebeca.calc.delta.Delta;
import br.puc.mestrado.rebeca.config.Config;
import br.puc.mestrado.rebeca.utils.ExcelUtils;
import br.puc.mestrado.rebeca.utils.ProgressLogIndicator;

public class WsGatilhoCorte implements ExcelWS {
	private static Logger logger = Logger.getLogger(WsGatilhoCorte.class);

	private ProgressLogIndicator progress;
	private String name;
	private Opcao o;
	private Config c;
	private Sheet sh;
	private double[] P;
	private double[] Pgrid;
	private double[] E;
	private double[] Egrid;
	private double[] Q;

	public WsGatilhoCorte(Sheet sh, Config c, Opcao o) {
		this.name = sh.getSheetName();
		this.o = o;
		this.c = c;
		this.sh = sh;
		this.progress = new ProgressLogIndicator(logger, "Sheet [" + name + "]", 5);
	}

	private void createArrays() {
		P = new double[o.getArrayF()[0][0].length];
		Pgrid = new double[o.getArrayF()[0][0].length];
		E = new double[o.getArrayF()[0][0].length];
		Egrid = new double[o.getArrayF()[0][0].length];
		Q = new double[o.getArrayF()[0][0].length];
	}

	private void createP() {
		double termo1 = c.getR() - c.getConvYield();
		double time;

		for (int k = 0; k < o.getArrayF()[0][0].length; k++) {
			if (c.isTimeInMonth())
				time = ((double) k / 12);
			else
				time = k;

			P[k] = c.getP0() * Math.pow(Math.E, termo1 * time);

			double resto = P[k] % o.getDeltaP();
			if (resto == 0)
				Pgrid[k] = P[k];
			else
				Pgrid[k] = P[k] - resto;

			if (Pgrid[k] > c.getPmax())
				Pgrid[k] = c.getPmax();
		}
	}

	private void createEandQ() {
		int indexP0 = Delta.indexP(o.getArrayP(), c.getP0());
		int indexE0 = Delta.indexE(o.getArrayE(), c.getE0());

		E[0] = c.getE0();
		Egrid[0] = c.getE0();
		Q[0] = o.getF(indexP0, indexE0, 0).getQ();

		double termo1;

		if (this.c.isTimeInMonth()) {
			termo1 = Math.pow(Math.E, c.getEbarra() * c.getVelRev() / 12);
		} else {
			termo1 = Math.pow(Math.E, c.getEbarra() * c.getVelRev());
		}

		double termo2 = termo1 - 1;

		for (int k = 1; k < o.getArrayF()[0][0].length; k++) {
			// calc E[k] using Q[k-1]
			double termo3 = c.getEbarra() * E[k - 1] * termo1;
			double termo4 = (E[k - 1] * termo2) + c.getEbarra();
			E[k] = (termo3 / termo4) - Q[k - 1];

			// calc Egrid[k]
			double resto = E[k] % o.getDeltaE();
			if (resto == 0)
				Egrid[k] = E[k];
			else
				Egrid[k] = E[k] - resto;

			if (Egrid[k] > c.getEbarra())
				Egrid[k] = c.getEbarra();

			// calc Q[k]
			int indexPgrid = Delta.indexP(o.getArrayP(), Pgrid[k]);
			int indexEgrid = Delta.indexE(o.getArrayE(), Egrid[k]);
			Q[k] = o.getF(indexPgrid, indexEgrid, k).getQ();

		}
	}

	public void create(ProcessTask pt) {
		logger.info("Creating worksheet [" + name + "]");

		createArrays();
		updateStatus(pt);

		createP();
		updateStatus(pt);

		createEandQ();
		updateStatus(pt);

		createHeader();
		updateStatus(pt);

		createSheet();
		updateStatus(pt);

		logger.info("Worksheet [" + name + "] has been created.");
	}

	private void createSheet() {
		for (int k = 0, line = 1; k < o.getArrayF()[0][0].length; k++, line++) {
			Row row = sh.createRow(line);
			int col = 0;
			ExcelUtils.createCell(row, col++, k);
			ExcelUtils.createCell(row, col++, P[k]);
			ExcelUtils.createCell(row, col++, Pgrid[k]);
			ExcelUtils.createCell(row, col++, E[k]);
			ExcelUtils.createCell(row, col++, Egrid[k]);
			ExcelUtils.createCell(row, col++, Q[k]);
		}
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
		ExcelUtils.createCell(row, col++, "t", cs);
		ExcelUtils.createCell(row, col++, "P", cs);
		ExcelUtils.createCell(row, col++, "P(grid)", cs);
		ExcelUtils.createCell(row, col++, "E", cs);
		ExcelUtils.createCell(row, col++, "E(grid)", cs);
		ExcelUtils.createCell(row, col++, "Q(Pgrid,Egrid,t)", cs);
	}

	private void updateStatus(ProcessTask pt) {
		progress.next();
		pt.updateProgress();
	}

	public int getMaxProgress() {
		return (int) c.getT() + 1;
	}
}
