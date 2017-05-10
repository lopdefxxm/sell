package aero.framework.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class GeneralTypeConvert {
	
	/**
	 * 转化为String类型
	 * @author Wangtianhai
	 */
	public static String AnyToString(Object cellValue) {
		String strcellValue;
		if (cellValue == null) {
			//为空的话
			strcellValue = "";
		}else{
			if (cellValue instanceof Double){
				//获取得到的是Double型
					strcellValue = ""+cellValue;
					//strcellValue = strcellValue.substring(0,strcellValue.length()-2);
			}else if (cellValue instanceof String){
				//获取得到的是String型
				strcellValue = (String)cellValue;
			}else if(cellValue instanceof Date){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
				java.util.Date date=new java.util.Date();  
				strcellValue=sdf.format(date);  
			}else {
				//其他数字类型
				strcellValue = ""+cellValue;
			}
		}
		return strcellValue;
	}
	
	/**
	 * 转化为Integr类型
	 * @author Wangtianhai
	 */
	public static int AnyToInteger(Object cellValue) throws Exception {
		int intcellValue;
		if (cellValue == null) {
			//为空的话
			intcellValue = 0;
		}else{
			if (cellValue instanceof Double){
				//获取得到的是Double型
				double dblcellValue = (Double)cellValue;
				intcellValue = (int) dblcellValue;
			}else if (cellValue instanceof String){
				//获取得到的是String型
				try{
					intcellValue = Integer.parseInt((String)cellValue);
				}catch (Exception e) {
					throw new Exception("格式错误，应为数字");
				}
			}else {
				//其他数字类型
				intcellValue = (Integer)cellValue;
			}
		}
		return intcellValue;
	}
	
	/**
	 * 转化为long类型
	 * @author Wangtianhai
	 */
	public static long AnyToLong(Object cellValue) throws Exception {
		long lngcellValue;
		if (cellValue == null) {
			//为空的话
			lngcellValue = 0;
		}else{
			if (cellValue instanceof Double){
				//获取得到的是Double型
				double dblcellValue = (Double)cellValue;
				lngcellValue = (long) dblcellValue;
			}else if (cellValue instanceof String){
				//获取得到的是String型
				try{
					lngcellValue = Long.parseLong((String)cellValue);
				}catch (Exception e) {
					throw new Exception("格式错误，应为数字");
				}
			}else {
				//其他数字类型
				lngcellValue = (Long)cellValue;
			}
		}
		return lngcellValue;
	}
	
	/**
	 * 转化为Float类型
	 * @author Wangtianhai
	 */
	public static float AnyToFloat(Object cellValue) throws Exception {
		float fltcellValue;
		if (cellValue == null) {
			//为空的话
			fltcellValue =  0;
		}else{
			if (cellValue instanceof Double){
				//获取得到的是Double型
				fltcellValue = (Float)cellValue;
			}else if (cellValue instanceof String){
				//获取得到的是String型
				try {
					fltcellValue = Float.parseFloat((String)cellValue);
				} catch (Exception e) {
					throw new Exception("格式错误，应为数字");
				}
				
			}else {
				//其他数字类型
				fltcellValue = (Float)cellValue;
			}
		}
		return fltcellValue;
	}
	
	/**
	 * 转化为Double类型
	 * @author Wangtianhai
	 */
	public static double AnyToDouble(Object cellValue) throws Exception {
		double dblcellValue;
		if (cellValue == null) {
			//为空的话
			dblcellValue = 0;
		}else{
			if (cellValue instanceof Double){
				//获取得到的是Double型
				 dblcellValue = (Double)cellValue;
			}else if (cellValue instanceof String){
				//获取得到的是String型
				try {
					dblcellValue = Double.parseDouble((String)cellValue);
				} catch (Exception e) {
					throw new Exception("格式错误，应为数字");
				}
				
			}else {
				//其他数字类型
				dblcellValue = (Double)cellValue;
			}
		}
		return dblcellValue;
	}
	
	/**
	 * 转化为Date类型
	 * @author Wangtianhai
	 * @throws Exception 
	 */
	public static Date AnyToDate(String dataFormat, String dateValue) throws Exception {
		if (dateValue == null)
			return null;

		if (dataFormat == null)
			dataFormat = "yyyy/MM/dd";

		SimpleDateFormat dateFormat = new SimpleDateFormat(dataFormat);
		Date newDate = null;

		try {
			newDate = dateFormat.parse(dateValue);
		}  catch (java.text.ParseException e) {
			throw new Exception("格式错误，无法转换为时间");
		}

		return newDate;
	}

}
