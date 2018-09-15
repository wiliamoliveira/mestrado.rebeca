package br.puc.mestrado.rebeca.utils;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class LogUtils {	
	private static AtomicLong count = new AtomicLong(); 
	
	public static StringBuilder generateLogEventID() {
		StringBuilder sb = new StringBuilder();
		sb.append(new BigInteger(30, new Random()).toString(Character.MAX_RADIX));
//		sb.append('.');
//		sb.append(Thread.currentThread().getId());
		sb.append('.');
		sb.append(count.incrementAndGet());
		return sb;
	}
}
