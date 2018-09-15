package br.puc.mestrado.rebeca.utils;

public class ArrayUtils {

	public static Integer find(double[] array, double value) {
		Integer found = null;

		for (int i = 0; i < array.length; i++)
			if (array[i] == value)
				found = i;

		return found;
	}
}
