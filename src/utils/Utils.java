package src.utils;


public class Utils {
	public static int toNumeric(String s) {
		int value = 8;
		int i =0;
		while(i<s.length()){
			if(!isNumeric(s.charAt(i))) {
				if(i == 0) s = s.substring(i+1);
				else s = s.substring(0, i)+s.substring(i+1);
			}else {
				i++;
			}
		}
		if(s.length() != 0) value = Integer.parseInt(s);
		if(value <4) return 4;
		return value;
	}

	private static boolean isNumeric(char charAt) {
		String a = "0123456789";
		if(a.indexOf(charAt) != -1) return true;
		return false;
	}
	public static void printArray(int[] x) {
		for(int i=0;i<x.length;i++) {
			System.out.print("["+i+","+x[i]+"]");
		}
		System.out.print("\n");
	}
}