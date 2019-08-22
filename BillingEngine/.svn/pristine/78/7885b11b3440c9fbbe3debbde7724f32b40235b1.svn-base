package com.arbiva.apfgc.invoice.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class ConvertingUtil {
	public static String formatKBsToMBs(String size) {
		String[] s = size.split("\\.");
	    double m = Double.parseDouble(s[0])/1024D;
	    DecimalFormat dec = new DecimalFormat("0.00");
	    return dec.format(m);
	}
	
	public static String formateBytesToGb(String size) {

		return (new BigDecimal(size).divide(new BigDecimal("1024")).divide(new BigDecimal("1024"))
				.divide(new BigDecimal("1024"))).setScale(2, RoundingMode.HALF_DOWN).toString();
	}
	
	public static String formateSecToTime(String duration){
			//String s=duration.substring(0, duration.length()-3);
			String[] s = duration.split("\\.");
			long seconds = Long.parseLong(s[0]); 
			long minutes ;
	        long hours;
			hours = seconds / 3600;
	        minutes = (seconds%3600)/60;
	        long seconds_output = (seconds% 3600)%60;
        return hours+":"+minutes+":"+seconds_output;
	}
	public static void main(String[] args) {
		//String size=ConvertingUtil.formatKBsToMBs("10000");
		String time=ConvertingUtil.formateSecToTime("6000000000");
		System.out.println(time);
		//System.out.println(size);
		
		System.out.println(formateBytesToGb("1212121211122"));
		
	}
}
