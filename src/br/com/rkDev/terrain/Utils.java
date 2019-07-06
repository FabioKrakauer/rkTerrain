package br.com.rkDev.terrain;

import java.text.DecimalFormat;

public class Utils {
	
	public static boolean isInteger(String number) {
		try {
			Integer.parseInt(number);
			return true;
		}catch (NumberFormatException e) {
			return false;
		}
	}
	public static boolean isDobule(String number) {
		try {
			Double.parseDouble(number);
			return true;
		}catch (NumberFormatException e) {
			return false;
		}
	}
	public static String formatDouble(double value) {
		return new DecimalFormat("#,###.##").format(value);
	}
}
