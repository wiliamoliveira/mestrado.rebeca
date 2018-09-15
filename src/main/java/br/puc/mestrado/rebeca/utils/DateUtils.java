package br.puc.mestrado.rebeca.utils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

	public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");

	public static final String getStringNow() {
		return sdf.format(Calendar.getInstance().getTime());
	}

	public static final String format(Date d) {
		if (d == null) {
			return "null";
		} else {
			return sdf.format(d);
		}
	}

	public static final String format(java.util.Date d) {
		if (d == null) {
			return "null";
		} else {
			return sdf.format(d);
		}
	}

	public static double getElapsedTime(long start) {
		 return ((new Double(System.nanoTime())-new Double(start))/((double)1000000));
//		return (System.nanoTime() - start) / 1000000;
	}

	public static double getFormatNanoTime(long start) {
		return (new Double(start) / ((double) 1000000));
	}

	public static int compareNow(int hourOfDay, int minute) {
		Calendar now = Calendar.getInstance();
		Calendar tmp = Calendar.getInstance();

		tmp.set(Calendar.HOUR_OF_DAY, hourOfDay);
		tmp.set(Calendar.MINUTE, minute);

		return now.compareTo(tmp);
	}

}
