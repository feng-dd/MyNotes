/*
 * ==================================================================
 * The Huateng Software License
 *
 * Copyright (c) 2004-2005 Huateng Software System.  All rights
 * reserved.
 * ==================================================================
 */

package com.ruimin.ifs.bhjob.core.common;

import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataFormat {

	private static final String DATE_PATTERN = "yyyy-MM-dd";

	private static final String DATE_NUMBER_PATTERN = "yyyyMMdd";

	private static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	private static final String TIME_ONLY_PATTERN = "HHmmss";

	private static final String MONEY_PATTERN = "############0.00";

	private static final String CURRENCY_PATTERN = "#,###,###,###,##0.00";

	private static final String DAY_PATTERN = "00";

	/**
	 *
	 */
	public DataFormat() {
		super();
	}

	/**
	 * 由日期返回yyyy-MM-dd格式的字符串
	 *
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		if (date == null)
			return null;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				DataFormat.DATE_PATTERN);
		return simpleDateFormat.format(date);
	}

	/**
	 * 由日期返回yyyyMMdd格式的字符串
	 *
	 * @param date
	 * @return
	 */
	public static String dateToNumber(Date date) {
		if (date == null)
			return null;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				DataFormat.DATE_NUMBER_PATTERN);
		return simpleDateFormat.format(date);
	}

	public static String dateToNumberOrEmpty(Date date) {
		String d = null;
		try {
			d = dateToNumber(date);
		} catch (Exception ex) {
		}
		if (null == d) {
			return "";
		}
		return d;
	}

	/**
	 * 由日期返回 dd 格式的字符串
	 *
	 * @param date
	 * @return
	 */
	public static String getDay(Date date) {
		if (date == null)
			return null;
		return DataFormat.dateToNumber(date).substring(6, 8);
	}

	/**
	 * 由日期返回 mm 格式的字符串
	 *
	 * @param date
	 * @return
	 */
	public static String getMonth(Date date) {
		if (date == null)
			return null;
		return DataFormat.dateToNumber(date).substring(4, 6);
	}

	/**
	 * 由日期返回 yyyy 格式的字符串
	 *
	 * @param date
	 * @return
	 */
	public static String getYear(Date date) {
		if (date == null)
			return null;
		return DataFormat.dateToNumber(date).substring(0, 4);
	}

	/**
	 * 由日期返回 yy 格式的字符串
	 *
	 * @param date
	 * @return
	 */
	public static String getYear2(Date date) {
		if (date == null)
			return null;
		return DataFormat.dateToNumber(date).substring(2, 4);
	}

	/**
	 * 由日期返回yyyy-MM-dd HH:mm:ss格式的字符串
	 *
	 * @param time
	 * @return
	 */
	public static String timeToString(Date time) {
		if (time == null)
			return null;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				DataFormat.TIME_PATTERN);
		return simpleDateFormat.format(time);
	}

	/**
	 * 返回HHmmss格式字符串
	 *
	 * @param time
	 * @return
	 */
	public static String onlyTimeToString(Date time) {
		if (time == null)
			return null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				DataFormat.TIME_ONLY_PATTERN);
		return simpleDateFormat.format(time);
	}

	/**
	 * 由日期返回yyyyMMddHHmmss格式的字符串
	 *
	 * @param time
	 * @return
	 */
	public static String timeToNumber(Date time) {
		if (time == null)
			return null;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyyMMddHHmmss");
		return simpleDateFormat.format(time);
	}

	/**
	 * 由日期返回yyyy-MM-dd-HH.mm.ss.SSSSSS格式的字符串
	 *
	 * @param date
	 *            Date格式日期
	 * @return String yyyy-MM-dd-HH.mm.ss.SSSSSS格式 的字符串
	 */
	public static String getTimeStampFormat(Date date) {
		if (date == null)
			return null;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd-HH.mm.ss.SSSSSS");
		return simpleDateFormat.format(date);
	}

	/**
	 * 由yyyy-MM-dd HH:mm:ss格式的字符串返回日期时间
	 *
	 * @param String
	 *            时间
	 * @return
	 */
	public static Date stringToTime(String string) throws Exception {
		if (string == null)
			return null;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				DataFormat.TIME_PATTERN);
		try {
			return simpleDateFormat.parse(string);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 由yyyy-MM-dd格式的字符串返回日期
	 *
	 * @param date
	 * @return
	 */
	public static Date stringToDate(String string) throws Exception {
		if (string == null)
			return null;

		if (string.trim().length() == 0)
			return null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				DataFormat.DATE_PATTERN);
		try {
			return simpleDateFormat.parse(string);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 判断是否闰年
	 *
	 * @param year
	 * @return boolean
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	/**
	 * 取指定年、月的最大天数
	 *
	 * @param year,
	 *            month
	 * @return number
	 */
	public static int getMonthDay(int year, int month) {
		int[] numDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int n = numDays[month - 1];
		if (month == 2) {
			n = isLeapYear(year) ? 29 : 28;
		}
		return n;
	}

	/**
	 * 由yyyyMMdd格式的字符串返回日期
	 *
	 * @param date
	 * @return
	 */
	public static Date numberToDate(String string) throws Exception {
		if (string == null)
			return null;

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				DataFormat.DATE_NUMBER_PATTERN);
		try {
			int year = Integer.parseInt(string.substring(0, 4));
			int month = Integer.parseInt(string.substring(4, 6));
			int day = Integer.parseInt(string.substring(6, 8));
			if (month < 1 || month > 12)
				throw new ParseException("ERROR", 1);
			if (day < 1 || day > getMonthDay(year, month))
				throw new ParseException("ERROR", 1);
			return simpleDateFormat.parse(string);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 字符串转日期(total length 2bit, prefix 0)
	 *
	 * @param day
	 * @return
	 */

	public static String stringToDay(String day) {
		if (day == null)
			return DataFormat.DAY_PATTERN;
		DecimalFormat decimalFormat = new DecimalFormat(DataFormat.DAY_PATTERN);
		return decimalFormat.format(Integer.parseInt(day));
	}

	/**
	 * 将整数转换为指定长度的右对齐，前补0的字符串
	 *
	 * @param n
	 *            整数
	 * @param l
	 *            长度
	 * @return
	 */
	public static String intToString(long n, int l) {
		DecimalFormat decimalFormat = new DecimalFormat(DataFormat.initString(
				'0', l));
		return decimalFormat.format(n);
	}

	/**
	 * 以元为单位的金额转换为带分节符的右对齐的字符串
	 *
	 * @param money
	 * @return
	 */
	public static String doubleToCurrencyRA(double money) {
		DecimalFormat decimalFormat = new DecimalFormat(
				DataFormat.CURRENCY_PATTERN);
		String rtnValue = decimalFormat.format(money);
		return initString(' ', 12 - rtnValue.length()) + rtnValue;
	}

	/**
	 * 以元为单位的金额转换为带分节符的左对齐的字符串
	 *
	 * @param money
	 * @return
	 */
	public static String doubleToCurrencyLA(double money) {
		DecimalFormat decimalFormat = new DecimalFormat(
				DataFormat.CURRENCY_PATTERN);
		return decimalFormat.format(money);
	}

	/**
	 * 以元为单位的金额转换为不带分节符的右对齐的字符串
	 *
	 * @param money
	 * @return
	 */
	public static String doubleToMoneyRA(double money) {
		DecimalFormat decimalFormat = new DecimalFormat(
				DataFormat.MONEY_PATTERN);
		String rtnValue = decimalFormat.format(money);
		return initString(' ', 10 - rtnValue.length()) + rtnValue;
	}

	/**
	 * 以元为单位的金额转换为不带分节符的左对齐的字符串
	 *
	 * @param money
	 * @return
	 */
	public static String doubleToMoneyLA(double money) {
		DecimalFormat decimalFormat = new DecimalFormat(
				DataFormat.MONEY_PATTERN);
		return decimalFormat.format(money);
	}

	/**
	 * 不带分节符的字符串类型的以元为单位的金额转换为以元为单位的金额
	 *
	 * @param money
	 * @return
	 */
	public static double moneyToDouble(String money) {
		if (money == null)
			return 0.0;
		return Double.parseDouble(money);
	}

	/**
	 * 带分节符的字符串类型的以元为单位的金额转换为以元为单位的金额
	 *
	 * @param money
	 * @return
	 */
	public static double currencyToDouble(String money) {
		if (money == null)
			return 0.0;
		return Double.parseDouble(money.replaceAll(",", ""));
	}

	/**
	 * 将金额转换为大写人民币
	 *
	 * @param money
	 * @return
	 */
	public static String getRMBCapitalMoney(double money) {
		if (money == 0)
			return "零元整";

		String szChMoney = "", szNum = "";
		int iAddZero = 0;
		String mnUnit[] = { "分", "角", "元" };
		String hzUnit[] = { "拾", "佰", "仟", "万", "拾", "佰", "仟", "亿" };
		String hzNum[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		szNum = DataFormat.doubleToMoneyLA(money).replaceAll("[.]", "");
		if (szNum.charAt(0) == '-') {
			szChMoney = "负";
			szNum = szNum.substring(1);
		}
		int iLen = szNum.length();

		if (iLen > 18 || iLen == 0)
			return "";

		for (int i = 1; i <= iLen; i++) {
			int iNum = Integer.parseInt(szNum.substring(i - 1, i));
			if (iNum == 0) {
				if ((iLen - i - 2) % 4 == 0 && iLen - i - 3 > 0
						&& (iLen - i >= 8 || iAddZero < 3))
					szChMoney += hzUnit[(iLen - i - 3) % 8];
				iAddZero++;
			} else {
				if ((iAddZero != 0 && iLen - i >= 2) && (iLen - i - 1) % 4 != 0
						|| iAddZero >= 4)
					szChMoney += hzNum[0];
				szChMoney += hzNum[iNum];
				iAddZero = 0;
			}

			if (iAddZero < 1 || iLen - i == 2)
				if (iLen - i >= 3) {
					szChMoney += hzUnit[(iLen - i - 3) % 8];
				} else
					szChMoney += mnUnit[(iLen - i) % 3];
		}

		if (szChMoney.trim() == "")
			return "零元整";

		if (szNum.endsWith("0"))
			szChMoney += "整";

		if (szChMoney.startsWith("元"))
			szChMoney = szChMoney.substring(1);

		return szChMoney;
	}

	/**
	 * 返回指定长度的，由指定字符组成的字符串(相当于c语言的memset)
	 *
	 * @param ch
	 * @param length
	 * @return
	 */
	public static String initString(char ch, int length) {
		if (length < 0)
			return "";
		char chars[] = new char[length];
		for (int i = 0; i < length; i++)
			chars[i] = ch;
		return new String(chars);
	}

	/**
	 * 修改字符串指定位
	 *
	 * @param string
	 * @param idx
	 * @param ch
	 * @return
	 */
	public static String setCharAt(String string, int idx, char ch) {
		StringBuffer sb = new StringBuffer(string);

		sb.setCharAt(idx, ch);
		return new String(sb);
	}

	/**
	 * 判断string是否为空
	 *
	 * @param string
	 * @return 如果为空则返回true，否则返回true
	 */
	public static boolean isEmpty(String string) {
		if (string == null || string.trim().length() == 0)
			return true;
		else
			return false;
	}

	/**
	 * 判断Object是否为null
	 *
	 * @param object
	 * @return 如果为空则返回true，否则返回true
	 */
	public static boolean isNull(Object object) {
		if (object == null)
			return true;
		else
			return false;
	}

	/**
	 * 判断date是否为空
	 *
	 * @param date
	 * @return 如果为空则返回true，否则返回true
	 */
	public static boolean isEmpty(Date date) {
		if (date == null || DataFormat.dateToString(date).equals("1900-01-01"))
			return true;
		else
			return false;
	}

	/**
	 * trim
	 *
	 * @param string
	 * @return
	 */
	public static String trim(String string) {
		if (string == null)
			return "";
		else
			return string.trim();
	}

	/**
	 * 字符串加后缀
	 *
	 * @param string
	 * @param ch
	 * @param length
	 * @return string
	 */
	public static String suffixString(String string, char ch, int length) {

		int tmplen = length - string.trim().length();

		if (tmplen <= 0) {
			return string.trim();
		} else {
			String tmpStr = string.trim() + initString(ch, tmplen);
			return tmpStr;
		}
	}

	/**
	 * 将double转换为右对齐，左补0的String(相当于c语言的printf("%0m.nf",dbl))
	 *
	 * @param dbl
	 *            要转换的double
	 * @param length
	 *            小数点前长度（不包括符号位）
	 * @param precision
	 *            小数点后长度
	 * @return
	 * @throws Exception
	 */
	public static String dblRightAlign(double dbl, int length, int precision)
			throws Exception {
		if (length <= 0 || precision < 0)
			throw new Exception("数据格式错误");

		String format = initString('0', length);
		if (precision > 0)
			format = format + "." + initString('0', precision);
		DecimalFormat decimalFormat = new DecimalFormat(format);
		return decimalFormat.format(dbl);
	}

	/**
	 * 将double转换为右对齐，左补0的String，小数四舍五入(相当于c语言的printf("%0m.0f",dbl))
	 *
	 * @param dbl
	 *            要转换的double
	 * @param length
	 *            整数长度（不包括符号位）
	 * @return
	 * @throws Exception
	 */
	public static String dblRightAlign(double dbl, int length) throws Exception {
		if (length <= 0)
			throw new Exception("数据格式错误");

		String format = initString('0', length);
		DecimalFormat decimalFormat = new DecimalFormat(format);
		return decimalFormat.format(dbl);
	}

	/**
	 * 根据输入的字符串，返回指定长度的String(相当于c语言的printf("%-n.ns",str))
	 * 如果length小于str的长度，则截取指定长度； 如果length大于str的长度，则不足部分用空格补齐
	 *
	 * @param str
	 * @param length
	 *            长度
	 * @return
	 */
	public static String strLeftAlign(String str, int length) {
		if (str == null)
			return initString(' ', length);

		int len = str.length();
		if (length < len)
			return str.substring(0, length);
		else if (length == len)
			return str;
		else
			return str + initString(' ', length - len);
	}

	/**
	 * 将List转换为以逗号分割的String
	 *
	 * @param strList
	 * @return
	 */
	public static String listToString(List strList) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < strList.size(); i++) {
			if (i != 0)
				buf = buf.append(",");
			buf = buf.append((String) strList.get(i));
		}
		return buf.toString();
	}

	/**
	 * 将以逗号分割的String转换为List
	 *
	 * @param str
	 * @return
	 */
	public static List stringToList(String str) {
		List list = new ArrayList();
		if (DataFormat.isEmpty(str))
			return list;
		int startPos = 0;
		int endPos = str.indexOf(',', startPos);
		while (endPos >= 0) {
			list.add(str.substring(startPos, endPos));
			startPos = endPos + 1;
			endPos = str.indexOf(',', startPos);
		}
		if (startPos < str.length())
			list.add(str.substring(startPos));
		return list;
	}

	/**
	 * 将整型的贷款期限转换为String
	 *
	 * @param str
	 * @return
	 */
	public static String termToString(int year, int month, int day) {
		return DataFormat.intToString(year, 2)
				+ DataFormat.intToString(month, 2)
				+ DataFormat.intToString(day, 2);
	}

	/**
	 * 将YYMMDD格式的期限转换为n年n月n日的汉字描述
	 *
	 * @param term
	 * @return
	 */
	public static String termToDesc(String term) {
		int years = Integer.parseInt(term.substring(0, 2));
		int months = Integer.parseInt(term.substring(2, 4));
		int days = Integer.parseInt(term.substring(4, 6));
		StringBuffer buffer = new StringBuffer();
		if (years > 0)
			buffer.append(years).append("年");
		if (months > 0)
			buffer.append(months).append("月");
		if (days > 0)
			buffer.append(days).append("日");
		return buffer.toString();
	}

	/**
	 * 将MMDD格式的期限转换为n月n日的汉字描述
	 *
	 * @param term
	 * @return
	 */
	public static String termToDesc1(String term) {

		int months = Integer.parseInt(term.substring(0, 2));
		int days = Integer.parseInt(term.substring(2, 4));
		StringBuffer buffer = new StringBuffer();

		if (months > 0)
			buffer.append(months).append("月");
		if (days > 0)
			buffer.append(days).append("日");
		return buffer.toString();
	}

	/**
	 * 将整型的利率调整日期转换为String
	 *
	 * @param month
	 * @param day
	 * @return
	 */
	public static String adjToString(int month, int day) {
		return DataFormat.intToString(month, 2)
				+ DataFormat.intToString(day, 2);
	}

	/**
	 * 将布尔值的选项转换为字符串类型
	 *
	 * @param
	 * @return 5位的字符串
	 */
	public static String getGuatType(boolean credit, boolean grarantee,
			boolean pledge, boolean impawn, boolean perform) {
		String str1 = credit ? "1" : "0";
		String str2 = grarantee ? "1" : "0";
		String str3 = pledge ? "1" : "0";
		String str4 = impawn ? "1" : "0";
		String str5 = perform ? "1" : "0";
		return str1 + str2 + str3 + str4 + str5;
	}

	/**
	 * 将6位TERM的字符串转换为整形
	 *
	 * @param index -
	 *            如果为1则返回年，2返回月，3返回天
	 * @return
	 */
	public static int termToInt(String term, int index) {
		int value = 0;

		try {
			if (index == 1) {
				value = Integer.parseInt(term.substring(0, 2));
			} else if (index == 2) {
				value = Integer.parseInt(term.substring(2, 4));
			} else if (index == 3) {
				value = Integer.parseInt(term.substring(4));
			}
		} catch (Exception e) {

		}
		return value;
	}

	/**
	 * 计算费率
	 */
	public static float calIntrate(float basicIntrate, float percent) {
		long lb = (Math.round(basicIntrate * 1E6));
		long lp = (Math.round(percent * 1E4));
		double dIntrate = lb * (1E6 + lp) / 1E7; // 保证5位精度
		long lIntrate = Math.round(dIntrate);
		float fIntrate = (float) (lIntrate / 1E5);

		return fIntrate;
	}

	/**
	 * 金额大小比较。精度控制为0.00001
	 */
	public static boolean equalsMoeny(double moeny1, double moeny2) {
		double result = moeny1 - moeny2;
		if (Math.abs(result) > 0.00001) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 金额大小比较。精度控制为0.00001
	 */
	// public static boolean equalsMoeny1(java.math.BigDecimal moeny1,
	// java.math.BigDecimal moeny2) {
	// java.math.BigDecimal result = moeny1 - moeny2;
	// if (Math.abs(result) > 0.00001) {
	// return true;
	// } else {
	// return false;
	// }
	// }
	public static String byte2hex(byte[] b) {

		String hs = "";
		String stmp = "";

		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
			if (n < b.length - 1)
				hs = hs + ",";
		}

		return hs.toUpperCase();
	}

	// add by edison 20060515-------------------------------------
	/**
	 * string's length is 5 eg: 15.01 to 01501 1.1 to 00110
	 */
	public static String float2String(float f) {
		return intToString((int) (f * 100 + 0.5), 5);
	}

	/**
	 * string's length is 5 eg: 01501 to 15.01 00110 to 1.1
	 */
	public static float String2float(String s) {
		return Float.parseFloat(s) / 100.00f;
	}

	public static String array2String(String[] sa) {
		String result = "";
		for (int i = 0; i < sa.length; i++) {
			result += sa[i];
			if (i < sa.length - 1) {
				result += ":";
			}
		}
		return result;
	}

	public static String extendStringWithZero(String instr, int length) {
		if (instr.length() < length) {
			for (int i = instr.length(); i <= length; i++) {
				instr += "0";
			}
		}
		return instr;
	}

	// ************************************************************************************************
	// 以下为交行方法。
	// ************************************************************************************************
	/**
	 * 由java.util.Date日期转换为 java.sql.Date
	 *
	 * @param date
	 * @return
	 */
	public static java.sql.Date trsUtilDateToSqlDate(java.util.Date date)
			throws Exception {
		if (date == null)
			return null;

		return new java.sql.Date(date.getTime());
	}

	/**
	 * 由日期返回yyyy-MM-dd HH:mm:ss格式的字符串，为空返回空串
	 *
	 * @param time
	 * @return
	 */
	public static String timeToStringEx1(Date time) {
		if (time == null)
			return "";

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				DataFormat.TIME_PATTERN);
		return simpleDateFormat.format(time);
	}

	/**
	 * 带分节符的字符串类型的以元为单位的小写金额转换为大写金额
	 *
	 * @param money
	 * @return
	 */
	public static String currencyToChineseCurrency(double money) {
		if (money == 0.0) {
			return "零圆";
		}
		if (money > 9999999999999.99)
			return null;
		if (money < 0)
			return null;
		System.out.println(money);

		String number[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String monetary_unit[] = { "万", "仟", "佰", "拾", "亿", "仟", "佰", "拾", "万",
				"仟", "佰", "拾", "圆", "角", "分" };
		// 初始化数组
		String returnMoney = "";
		int length;
		int flag = 1;
		int i;

		Double dblMoney = new Double(money);
		String strMoney = new Double(dblMoney.doubleValue()).toString();
		String strMoney1 = new Integer(dblMoney.intValue()).toString();

		String p = strMoney;
		// p="1642342342315";
		// //---------------------------------
		// p=doubleToMoneyLANoDiv100Test(money);
		// //----------------------------------

		length = p.length();
		if (p.indexOf('E') != -1) {

			int postfix = p.substring(2, p.indexOf('E')).length(); // 得到小数点后数字个数
			String sdecimal = p.substring(0, p.indexOf('E')); // E值前的小数值
			String pre = sdecimal.substring(0, 1); // 小数点前的
			String post = sdecimal.substring(2, 2 + postfix); // 小数点后的
			int last = Integer
					.parseInt(p.substring(p.indexOf('E') + 1, length));// 科学计数法的位数E9的9
			p = pre.concat(post);

			// 不齐后面补零
			if (last > postfix) {
				for (i = 0; i < last - postfix; i++) {
					p = p.concat("0");
				}
			}
			if (last < postfix) {
				// endPoint:小数点后一位的值
				int endPoint = (new Integer(post.substring(last, last + 1)))
						.intValue();
				// 取p的整数部分
				p = p.substring(0, 1 + last);
				// 比五大进位
				if (endPoint >= 5) {
					char[] c = p.toCharArray();
					int all9Flag = 1;
					for (int j = 0; j < c.length; j++) {
						if (c[j] != '9') {
							all9Flag = 0;
							break;
						}
					}
					// 如果所有的数字都是九则变为1000000...
					if (all9Flag == 1) {
						String _p = "1";
						for (int j = p.length(); j > 0; j--) {
							_p = _p + "0";
						}
						p = _p;
					}
					// 进位加(非全九)
					else {
						String _p = "";
						int addFlag = 1; // 进位标志
						for (int j = p.length(); j > 0; j--) {
							// 有进位
							if (addFlag == 1) {
								if ((new Integer(p.substring(j - 1, j)))
										.intValue() == 9) {
									_p = "0" + _p;
									addFlag = 1;
								} else {
									_p = (new Integer(p.substring(j - 1, j))
											.intValue() + 1)
											+ _p;
									addFlag = 0;
								}

							}
							// 无进位
							else {
								_p = _p + p.substring(j - 1, j);
							}

						}
						p = _p;
					}
				}
			}

			System.out.println(p);
			// String d = DataFormat.suffixString(c,'0',last-postfix);
			length = p.length();
			p = p.concat("/");

		} else {
			p = strMoney1;
			length = p.length();
			p = p.concat("/");
		}

		for (i = 15 - length; i < 15; i++) {
			if (p.charAt(i - 15 + length) != '0') {
				returnMoney = returnMoney.concat(number[Integer.parseInt(p
						.substring(i - 15 + length, i - 15 + length + 1))]);

				returnMoney = returnMoney.concat(monetary_unit[i]);

			} else {
				if ((i != 4) && (i != 8) && (i != 12))
					if ((p.charAt(i - 15 + length + 1) != '0')
							&& (p.charAt(i - 15 + length + 1) != '/'))
						returnMoney = returnMoney.concat(number[0]);
				if (i == 5) {
					if ((p.charAt(i - 15 + length + 1) == '0')
							&& (p.charAt(i - 15 + length + 2) == '0')
							&& (p.charAt(i - 15 + length + 3) == '0'))
						flag = 0;
				} else {
					if (i == 12 || (i == 8 && flag == 1) || i == 4)
						returnMoney = returnMoney.concat(monetary_unit[i]);
				}
				if ((p.charAt(i - 15 + length) != '0')
						&& (p.charAt(i - 15 + length + 1) != '0'))
					returnMoney = returnMoney.concat(number[Integer.parseInt(p
							.substring(i - 15 + length, i - 15 + length + 1))]);
			}
		}
		if ((p.charAt(length - 1) == '0') && (p.charAt(length - 2) == '0'))
			returnMoney = returnMoney.concat("整");
		return returnMoney;
	}

	/**
	 * 带分节符的字符串类型的以元为单位的小写金额转换为大写金额
	 *
	 * @param money
	 * @return
	 */
	public static String currencyToChineseCurrency(String money) {
		// 去掉逗号
		money = money.replaceAll(",", "");
		if (money.equals("0")) {
			money = "0.0";
		}
		money = money.substring(0, money.length() - 3).concat(
				money.substring(money.length() - 2));
		return currencyToChineseCurrency(Double.parseDouble(money));
	}

	/**
	 *
	 * @param string
	 * @return
	 */
	public static String null2String(String string) {
		if (string == null)
			return "";
		return string;
	}

	// for test
	public static void main(String[] a) {
		System.out.println(float2String(12.10f));
		System.out.println(float2String(12.09f));
		System.out.println(float2String(12.01f));
		System.out.println(float2String(100.00f));
		System.out.println(float2String(2.1f));
		System.out.println(float2String(12f));
		System.out.println(float2String(12.0f));

		System.out.println(String2float("00000"));
		System.out.println(String2float("00110"));
		System.out.println(String2float("10000"));
		System.out.println(String2float("00100"));
		System.out.println(String2float("00002"));

		System.out.println("a".substring(3, 5));
	}
	// -------------------------------------add by edison 20060515
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 将日期格式 转为 Date格式 YYYY-MM-DD 2005-02-01
	 *
	 * @author wuzhiwei
	 * @param Date
	 * @return String
	 */
	public static String ConvertCVTDate(String param) {
		if (param == null || param.trim().length() < 1) {
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // 规定日期格式
		return (param.indexOf("CST") != -1 || param.indexOf(":") != -1) ? formatter.format(Date.parse(param)):param;

	}
	
	/**
	 * 按目标长度，右补指定字节
	 * @param str
	 * @param length　目标长度
	 * @param inbyte 填充字节
	 * @param catchFlag　截取标志，当原byte数组长度大于length时，是否截取
	 * @return
	 */
	public static String rightPadByte(String str,int length,char chr,boolean catchFlag){
		byte[] byteArg = str.getBytes();
		byte inByte = String.valueOf(chr).getBytes()[0];
		byte[] reByteArg = rightPadByte( byteArg,length,inByte,catchFlag);
		return new String(reByteArg);
	}
	
	/**
	 * 按目标长度，右补指定字节
	 * @param byteArg 原byte数组
	 * @param length　目标长度
	 * @param inbyte 填充字节
	 * @param catchFlag　截取标志，当原byte数组长度大于length时，是否截取
	 * @return
	 */
	public static byte[] rightPadByte(byte[] byteArg,int length,byte inbyte,boolean catchFlag){
		int origLength = byteArg.length;
		if( origLength > length ){
			if(catchFlag){
				return ByteBuffer.wrap(byteArg, 0, length).array();
			}else{
				return byteArg;
			}
		}else if( origLength == length ){
			return byteArg;
		}else{
			//填充字节数组
			ByteBuffer byteBuffer = ByteBuffer.allocate(length);
			byteBuffer.put(byteArg);
			for( int i=0;i<length-origLength;i++){
				byteBuffer.put(inbyte);
			}
			return byteBuffer.array();
		}
	}
	
	/**
     * 全角转半角
     * @param input String.
     * @return 半角字符串
     */
	public static String toDBC(String input) {
		char c[] = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '\u3000') {
				c[i] = ' ';
			} else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
				c[i] = (char) (c[i] - 65248);
			}
		}
		return new String(c);
	}
}