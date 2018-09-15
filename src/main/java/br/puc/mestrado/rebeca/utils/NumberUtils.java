package br.puc.mestrado.rebeca.utils;

public class NumberUtils {

	public static int roundUp(double d) {
		return d > (int) d ? (int) d + 1 : (int) d;
	}
}
