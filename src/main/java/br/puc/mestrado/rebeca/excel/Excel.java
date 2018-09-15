package br.puc.mestrado.rebeca.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import br.puc.mestrado.rebeca.calc.ProcessTask;
import br.puc.mestrado.rebeca.calc.Task;
import br.puc.mestrado.rebeca.exception.ConvergenceException;
import br.puc.mestrado.rebeca.utils.TextBuilder;

public class Excel implements Task {
	private static Logger logger = Logger.getLogger(Excel.class);
	private FileOutputStream file;
	private String filename;
	private SXSSFWorkbook wb;
	private List<ExcelWS> list;
	private ProcessTask pt;
	private int maxProgress;

	public Excel(String filename) {
		list = new ArrayList<ExcelWS>();
		this.filename = filename;

		open(filename);
	}

	public void add(ExcelWS excelWS) {
		list.add(excelWS);
		maxProgress += excelWS.getMaxProgress();
	}

	private void create() {
		logger.info("Creating workbook [" + filename + "]");

		for (ExcelWS excelWS : list) {
			excelWS.create(pt);
		}

		close();
		logger.info("Workbook [" + filename + "] has been created.");
	}

	public Sheet createSheet(String name) {
		return wb.createSheet(name);
	}

	private void open(String filename) {
		try {
			file = new FileOutputStream(filename);
			wb = new SXSSFWorkbook(1000);
		} catch (FileNotFoundException e) {
			logger.error(TextBuilder.getFistFrameOfThrowable(e));
		}
	}

	private void close() {
		try {
			wb.write(file);
			file.close();
			wb.dispose();
		} catch (IOException e) {
			logger.error(TextBuilder.getFistFrameOfThrowable(e));
		}
	}

	public void doJob() throws ConvergenceException {
		create();
	}

	public void doInit(ProcessTask pt) {
		this.pt = pt;
	}

	public int getMaxProgress() {
		return maxProgress;
	}

	public String taskName() {
		return "Exporting data to Excel...";
	}

}
