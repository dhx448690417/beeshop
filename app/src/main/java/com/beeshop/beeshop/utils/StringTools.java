
package com.beeshop.beeshop.utils;

import android.text.TextUtils;
import android.widget.EditText;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTools {

	public static String formatAmountStr(String str)
	{
		String[] src = str.split("\\."); //点是特殊字符
		if(src.length <= 0)
			return "";
		if(src[0].length() <= 3)
			return str;
		
	    // 将传进数字反转
	    String reverseStr = new StringBuilder(src[0]).reverse().toString();
	 
	    String strTemp = "";
	    for (int i=0; i<reverseStr.length(); i++) {
	        if (i*3+3 > reverseStr.length()) {
	            strTemp += reverseStr.substring(i*3, reverseStr.length());
	            break;
	        }
	        strTemp += reverseStr.substring(i*3, i*3+3)+",";
	    }
	    // 将 【789,456,】 中最后一个【,】去除
	    if (strTemp.endsWith(",")) {
	        strTemp = strTemp.substring(0, strTemp.length()-1);
	    }
	 
	    // 将数字重新反转
	    String resultStr = new StringBuilder(strTemp).reverse().toString();
	    if(src.length > 1 )
	    	return resultStr + "." + src[1];
	    return resultStr;
	
	}
	
	public static void floatNumberSplit(CharSequence s, EditText editText) {
		if (s.toString().contains(".")) {
			if (s.length() - 1 - s.toString().indexOf(".") > 2) {
				s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
				editText.setText(s);
				editText.setSelection(s.length());
			}
		}
		if (s.toString().trim().substring(0).equals(".")) {
			s = "0" + s;
			editText.setText(s);
			editText.setSelection(2);
		}

		if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
			if (!s.toString().substring(1, 2).equals(".")) {
				editText.setText(s.subSequence(0, 1));
				editText.setSelection(1);
				return;
			}
		}
	}

	/**
	 * 将完整手机号转换成中间带*的手机号
	 * @param phone
	 * @return
	 */
	public static String getStarPhoneNum(String phone) {
		if (!TextUtils.isEmpty(phone)) {
			return phone.replaceAll("(?<=\\d{3})\\d(?=\\d{4})", "*");
		} else {
			return "";
		}
	}

	/**
	 * 拆分字符串，用于显示整数部分和小数部分
	 *
	 * @param data
	 * @return
	 */
	public static String[] splitStr(String data) {
		return data.split("\\.");
	}

	/**
	 * 转换文件大小
	 * @param fileS
	 * @return
	 */
	public static String FormetFileSize(long fileS)
	{
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		String wrongSize="0B";
		if(fileS==0){
			return wrongSize;
		}
		if (fileS < 1024){
			fileSizeString = df.format((double) fileS) + "B";
		}
		else if (fileS < 1048576){
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		}
		else if (fileS < 1073741824){
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		}
		else{
			fileSizeString = df.format((double) fileS / 1073741824) + "GB";
		}
		return fileSizeString;
	}

	/**
	 * 将毫秒转化成固定格式的时间
	 * 时间格式: yyyy-MM-dd HH:mm:ss
	 *
	 * @param millisecond
	 * @return
	 */
	public static String getDateTimeFromMillisecond(Long millisecond){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(millisecond);
		String dateStr = simpleDateFormat.format(date);
		return dateStr;
	}

	/**
	 * 截取后台返回cookie
	 * @param str
	 * @return
	 */
	public static String splitCookie(String str) {
		String[] temp1 = str.split(";");
		if (temp1.length > 0 && !TextUtils.isEmpty(temp1[0])) {
			return temp1[0].trim();
		} else {
			return "";
		}
	}

	/**
	 * 去除字符串中的空格、回车、换行符、制表符
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 去掉%
	 * @param str
	 * @return
	 */
	public static String removePercent(String str) {
		String s = "";
		s = str.substring(0, str.length() - 1);
//		if (!TextUtils.isEmpty(str)) {
//		}
		return s;
	}

}
