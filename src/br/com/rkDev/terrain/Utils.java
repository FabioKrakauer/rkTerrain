package br.com.rkDev.terrain;

public class Utils {
	
	public static boolean isInteger(String number) {
		try {
			Integer.parseInt(number);
			return true;
		}catch (NumberFormatException e) {
			return false;
		}
	}
}
