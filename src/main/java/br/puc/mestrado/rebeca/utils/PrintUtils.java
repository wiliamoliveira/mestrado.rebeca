package br.puc.mestrado.rebeca.utils;

import br.puc.mestrado.rebeca.config.Config;

public class PrintUtils {

	public static StringBuilder printConfig(Config c) {
		int max = 10;
		StringBuilder sb = new StringBuilder();

		sb.append("\nConfig:\n");
		sb.append(PrintUtils.ident("accuracy", c.getAccuracy(), max, true));
		sb.append(PrintUtils.ident("convYield", c.getConvYield(), max, true));
		sb.append(PrintUtils.ident("cf", c.getC2(), max, true));
		sb.append(PrintUtils.ident("cv", c.getC1(), max, true));
		sb.append(PrintUtils.ident("E0", c.getE0(), max, true));
		sb.append(PrintUtils.ident("Ebarra", c.getEbarra(), max, true));
		sb.append(PrintUtils.ident("P0", c.getP0(), max, true));
		sb.append(PrintUtils.ident("Pmax", c.getPmax(), max, true));
		sb.append(PrintUtils.ident("R", c.getR(), max, true));
		sb.append(PrintUtils.ident("sigma", c.getSigma(), max, true));
		sb.append(PrintUtils.ident("T", c.getT(), max, true));
		sb.append(PrintUtils.ident("tau", c.getTau(), max, true));
		sb.append(PrintUtils.ident("velRev", c.getVelRev(), max, false));

		return sb;
	}

	public static StringBuilder ident(String text, double value, int max, boolean newLine) {
		return ident(text, (new StringBuilder()).append(value), max, newLine);
	}

	public static StringBuilder ident(String text, StringBuilder value, int max, boolean newLine) {
		StringBuilder sb = new StringBuilder();

		if (text.length() < max) {
			for (int i = 0; i < max - text.length(); i++) {
				sb.append(" ");
			}
		}
		sb.append(text);
		sb.append(" = [");
		sb.append(value);
		sb.append("]");

		if (newLine)
			sb.append("\n");
		return sb;
	}
}
