package com.arbiva.apfgc.invoice.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author srinivasa
 *
 */
public class DateUtil {
	private static final Logger LOGGER =Logger.getLogger(DateUtil.class);
	
	public static final String OPERATOR_ADD = "add";
	public static final String OPERATOR_MINUS = "minus";
	public static final String HOURS = "hours";
	public static final String MINUTES = "minutes";
	public static final String SECONDS = "seconds";

	private static DateFormat formatter;
	
	

	/**
	 * 
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static boolean isDateBetween(String minTextDate, String maxTextDate,
			String compareTextDate) {
		boolean isValid = false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date minDate = sdf.parse(minTextDate);
			Date maxDate = sdf.parse(maxTextDate);

			Date date = sdf.parse(compareTextDate);
			isValid = (date.after(minDate) && date.before(maxDate));

		} catch (Exception ex) {
			LOGGER.error("Exception @isDateBetween: " + ex);
		}
		return isValid;
	}

	/**
	 * 
	 * @param format
	 * @param date
	 * @return
	 */
	public static String getDateString(String format, Date date) {
		return new SimpleDateFormat(format).format(date);
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public static int getCurrentMonth() {
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}

	/**
	 * 
	 * @return
	 */
	public static int getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	/**
	 * 
	 * @return
	 */
	public static int getLastDayOfMonth() {
		return Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 
	 * @return
	 */
	public static Date getCurrentTime() {
		return Calendar.getInstance().getTime();
	}
	
	/**
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getCurrentMonth(String pattern) {
		return new SimpleDateFormat(pattern).format(Calendar.getInstance()
				.getTime());
	}

	/**
	 * 
	 * @return
	 */
	public static Date getStartDateOfCurrentMonth() {
		String currentMonth = getCurrentMonth(InvoiceEngineConstants.MONTH_PATTERN_IN_VALUE);
		Calendar gc = new GregorianCalendar();
        gc.set(Calendar.MONTH, Integer.valueOf(currentMonth)-1);
        gc.set(Calendar.DAY_OF_MONTH, 1);
        return gc.getTime();
	}
	
	/**
	 * 
	 * @return
	 */
	public static Date getEndDateOfCurrentMonth() {
		String currentMonth = getCurrentMonth(InvoiceEngineConstants.MONTH_PATTERN_IN_VALUE);
		Calendar gc = new GregorianCalendar();
        gc.set(Calendar.MONTH, Integer.valueOf(currentMonth)-1);
        gc.set(Calendar.DAY_OF_MONTH, 1);
        gc.add(Calendar.MONTH, 1);
        gc.add(Calendar.DAY_OF_MONTH, -1);
        return gc.getTime();
	}
	
	/**
	 * 
	 * @param month
	 * @return
	 */
	public static Date getStartDateOfMonth(int month) {
		Calendar gc = new GregorianCalendar();
        gc.set(Calendar.MONTH, month-1);
        gc.set(Calendar.DAY_OF_MONTH, 1);
        return gc.getTime();
	}
	
	/**
	 * 
	 * @param month
	 * @return
	 */
	public static Date getEndDateOfMonth(int month) {
		Calendar gc = new GregorianCalendar();
        gc.set(Calendar.MONTH, month-1);
        gc.set(Calendar.DAY_OF_MONTH, 1);
        gc.add(Calendar.MONTH, 1);
        gc.add(Calendar.DAY_OF_MONTH, -1);
        return gc.getTime();
	}
	
	/**
	 * 
	 * @param type
	 * @param value
	 * @param prefix
	 * @return
	 */
	public static Date getTime(String type, int value, String operator) {
		Calendar cal = Calendar.getInstance();
		switch (type) {
		case HOURS:
			cal.add(Calendar.HOUR_OF_DAY, StringUtils.equals(operator,
					OPERATOR_ADD) ? +value : -value);
			break;
		case MINUTES:
			cal.add(Calendar.MINUTE,
					StringUtils.equals(operator, OPERATOR_ADD) ? +value
							: -value);
			break;
		case SECONDS:
			cal.add(Calendar.SECOND,
					StringUtils.equals(operator, OPERATOR_ADD) ? +value
							: -value);
			break;
		default:
			break;
		}
		return cal.getTime();
	}
	
	public static String getMMMYY(String date){
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date d;
		String tdate = "";
		try {
			d = formatter.parse(date);
			SimpleDateFormat formatter1 = new SimpleDateFormat("MMM yy");  
			tdate = formatter1.format(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		
		return tdate;   
	}
	
	public static void main(String[] args) {
		String currentMonth = new SimpleDateFormat("M").format(Calendar.getInstance().getTime());
		System.out.println(currentMonth);
		System.out.println(Calendar.getInstance().get(Calendar.MONTH) + 1);
		System.out.println(getStartDateOfMonth(Integer.valueOf(currentMonth)));
		System.out.println(getEndDateOfMonth(Integer.valueOf(currentMonth)));
		
		System.out.println(getMMMYY("19-04-2017"));
		
	}
	
	public static String toDateTime(String dateTime) throws ParseException {
	     SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     Date date=format.parse(dateTime);
	     return format.format(date);
	}
	
	public static String toTime(String dateTime) throws ParseException {
	     SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     Date date=format.parse(dateTime);
	     //return format.format(date);
	     return new SimpleDateFormat("HH:mm:ss").format(date);
	}
	
	public static String getDateFromMillis(String millis) throws ParseException {
	    SimpleDateFormat formatter = new SimpleDateFormat("ss");
	    Date date=formatter.parse(millis);
	    return new SimpleDateFormat("HH:mm:ss").format(date);
	}
}
