package br.puc.mestrado.rebeca.utils;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

public class ProgressLogIndicator {

	private DecimalFormat formatter = new DecimalFormat("0");
	private Logger logger;
	private int loopCur;
	private int max;
	private double last;
	private String name;

	public ProgressLogIndicator(Logger logger, String name, int max) {
		this.logger = logger;
		this.loopCur = 0;
		this.max = max;
		this.last = 0;
		this.name = name;
	}

	public void next() {
		double percent = ((double) loopCur++ / max) * 100;
		if ((((int) percent) % 1 == 0) && ((int) percent != (int) last))
			logger.info(name + " - " + formatter.format(percent) + "% completed");

		last = percent;
	}
}
