package com.arbiva.apfgc.invoice.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormate {
	
	public static String DateFormateDDMMYYY(String date) throws ParseException{
		
		String finaldate=new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		return finaldate;
	}
	
public static String DateFormateDDMMYYYwithslash(String date) throws ParseException{
		
		String finaldate=new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		return finaldate;
	}

public static String DateFormateYYYYMMwithslash(String date) throws ParseException{
	
	String finaldate=new SimpleDateFormat("yyyy/MM").format(new SimpleDateFormat("yyyy-MM-dd").parse(date));
	return finaldate;
}

	public static void main(String[] args) throws ParseException {
		String ans= DateFormateDDMMYYY("2017-05-25");
		System.out.println(ans);
		
	}

}
