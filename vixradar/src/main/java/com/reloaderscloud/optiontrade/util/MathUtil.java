package com.reloaderscloud.optiontrade.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

public class MathUtil {
	public static final long BILLION = 1000000000;
	public static final long MILLION = 1000000;
	public static final long K = 1000;
	
    public static boolean isExist(String s){
        if(s!=null && s.trim().length()>0){
          return true;
        }
        return false;
    }
	public static String roundDollar(double d){
		String s = "" + round(d);
		
		return s;
	}
	
	public static String treatString(String s){
		if(s!=null){
			return s.trim();
		}
		return "";
	}
	
	public static double round(double d){
		return Math.round(d * 100.0)/100.0;
	}
	
	
	
	public static String getServerURL(HttpServletRequest request){
		StringBuffer sbuff = new StringBuffer();
		sbuff.append("http://");
		sbuff.append(request.getServerName());
		sbuff.append(":");
		sbuff.append(request.getServerPort());
		sbuff.append(request.getContextPath());
		return sbuff.toString();
	}
	
	public static String getStraddleKey(String date, String strike){
		return date + strike;
	}
	
	public static List<ValuePair> hashToArray(HashMap hs){
		List<ValuePair> a = new ArrayList<ValuePair>();
		Iterator its = hs.keySet().iterator();
		while(its.hasNext()){
			String key = (String)its.next();
			ValuePair v = new ValuePair(key, key);
			a.add(v);
		}
		return a;
	}
	public static double parseDouble(String s){
		if(s==null) return 0.0;
		try{
			return Double.parseDouble(s);
		} catch (Exception e){
			
		}
		return 0.0;
	}
	public static double getDouble(String s){
		if(s==null) return 0.0;
		try{
			s = s.trim();
			s = s.replaceAll(",", "");
			boolean isM = false;
			boolean isK = false;
			if(s.charAt(s.length() - 1)=='M'){
				isM = true;
				s = s.substring(0, s.length()-1);
			}
			if(s.charAt(s.length() - 1)=='M'){
				s = s.substring(0, s.length()-1);
				isK = true;
			}
			double d = Double.parseDouble(s);
			if(isM){
				d = d * 1000000.0;
			}
			if(isK){
				d = d * 1000.0;
			}
			return d;
		}catch(Exception e){
			
		}
		return 0.0;
	}
	
	public static long getLong(String s){
		if(s==null) return 0;
		try{
			s = s.trim();
			s = s.replaceAll(",", "");
			return Long.parseLong(s.trim());
		}catch(Exception e){
			
		}	
		return 0;
	}
	
	public static int getInt(String s){
		if(s==null) return 0;
		try{
			s = s.trim();
			s = s.replaceAll(",", "");
			return Integer.parseInt(s.trim());
		}catch(Exception e){
			
		}
		return 0;
	}
	
	public static long parseLong(String s){
		long rs = 0;
		
		String unit = s.substring(s.length() - 1);
		String base = s.substring(0, s.length() - 1);
		long multi = 1;
		if("K".equalsIgnoreCase(unit)){
			multi = K;
		}
		if("M".equalsIgnoreCase(unit)){
			multi = MILLION;
		}
		if("B".equalsIgnoreCase(unit)){
			multi = BILLION;
		}
		double d = 0.0;
		try{
			d = Double.parseDouble(base);
		}catch(Exception e){
			
		}
		rs = (long)(d * multi);
		return rs;
	}
	
	public static String formatLong(long s){
		String rs = "" + s;
		
		if(s>BILLION){
			double d = (double)s/(double)BILLION;
			d = Math.round(d * 10000.)/10000.;
			rs = d + "B";
		}else if(s>MILLION){
			double d = (double)s/(double)MILLION;
			d = Math.round(d * 10000.)/10000.;
			rs = d + "M";
		}else if(s>K){
			double d = (double)s/(double)K;
			d = Math.round(d * 10000.)/10000.;
			rs = d + "K";
		}
		return rs;
	}
	
	public static double percentToDouble(String s){
		double rs = 0.0;
		
		String base = s.substring(s.length() - 1);
		if("%".equals(base)){
			try{
				rs = Double.parseDouble(s.substring(0, s.length()-1));
				rs = rs/100.0;
				rs = Math.round(rs * 10000.)/10000.;
			}catch(Exception e){
				
			}
		}
		return rs;
	}
	
	public static void main(String[] args){
		System.out.println(getOptionTimeStamp("2017-05-19"));
		System.out.println(getOptionDateString("1495152000"));
		System.out.println(percentToDouble("10.22%"));
	}
	
	/*
	 * Get the expiration string for Yahoo Finance
	 * from date format yyyy-MM-dd
	 */
	public static String getOptionTimeStamp(String expireDate) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = sf.parse(expireDate);
			Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
			c.setTime(d);
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			long t  = c.getTimeInMillis() / 1000;
			return "" + t;
		} catch (ParseException e) {
			
		}
		return null;
	}
	
	/*
	 * Get the expiration string for Yahoo Finance
	 * from it's seconds expression
	 */
	public static String getOptionDateString(String expireDate) {
		long time = Long.parseLong(expireDate) * 1000 + 8 * 3600000;
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date d = new Date(time);
			
			return sf.format(d);
		} catch (Exception e) {
			
		}
		return null;
	}
	
}
