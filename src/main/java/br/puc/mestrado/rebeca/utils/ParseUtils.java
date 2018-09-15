package br.puc.mestrado.rebeca.utils;

import org.apache.commons.lang3.math.NumberUtils;

public class ParseUtils {
//	private DecimalFormat formatter = new DecimalFormat("0");
	
	public static String toString(double d) {
		return Double.toString(d);
	}
	
	public static double toDouble(String s) {
		return NumberUtils.toDouble(s);
	}
	
	public static int toInteger(String s) {
		return NumberUtils.toInt(s);
	}
}
