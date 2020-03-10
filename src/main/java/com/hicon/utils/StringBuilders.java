package com.hicon.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * 字符处理器
 * 
 * @Title: 字符处理器
 * @Description: 本类的作用是处理各种类型的字符串
 * @Copyright: Copyright (c) 2011
 * @Company: allook
 * @author zhengxi
 * @version 1.0
 */
public class StringBuilders {

	/**
	 * 判断字符串是否为null， 如果为null，返回空字符串； 如果不是null，去掉空格
	 * 
	 * @param str String
	 * @return String
	 */
	public static String trim(String str) {
		return str == null ? "" : str.trim();
	}

	/**
	 * 该方法计算字符串(包括中文)的实际长度. 单个中文为2个字节，单个英文为1个字节
	 * 
	 * @param str String 需要计算长度的字符串
	 * @return int 字符串的实际长度
	 */
	public static int length(String str) {
		if (str == null) {
			return 0;
		}
		try {
			return new String(str.getBytes("GBK"), "8859_1").length();
		} catch (UnsupportedEncodingException e) {
			return -1;
		}
	}

	/**
	 * 按照固定长度截取字符串,整理成一个Vector返回
	 * 
	 * @param str String 待处理字符串
	 * @param lengthLimit int 固定长度
	 * @return Vector
	 */
	public static Vector<String> getMultiRow(String str, int lengthLimit) {
		Vector<String> v = new Vector<String>(); // 处理后返回的数组
		String optStr = ""; // 截取成的每段小于optLenLimit的字符串
		int lengthLimitTmp = lengthLimit;
		int countChineseBytes = 0; // 一个字符串中双字节字符的字节数
		byte[] bMsg = str.getBytes();
		byte[] bMsgTmp;
		int optLen = bMsg.length; // 待处理字符总长度
		// 长度限制超过输入字符长度直接返回
		if (optLen <= lengthLimit) {
			v.add(str);
			return v;
		}
		for (int i = 0; i < optLen; i += lengthLimitTmp) {
			// 下次需要截取的字符串
			lengthLimitTmp = ((i + lengthLimit) > optLen) ? optLen - i : lengthLimit;
			bMsgTmp = new byte[lengthLimitTmp];
			System.arraycopy(bMsg, i, bMsgTmp, 0, lengthLimitTmp);
			countChineseBytes = 0;
			for (int m = 0; m < bMsgTmp.length; m++) {
				if (bMsgTmp[m] < 0) {
					countChineseBytes++;
				}
			}
			// 假如双字节字符的字节个数为奇数，就提前一个字节显示
			if (countChineseBytes % 2 == 1) {
				lengthLimitTmp -= 1;
			}
			optStr = new String(bMsg, i, lengthLimitTmp);
			v.add(optStr);
		}
		return v;
	}

	/**
	 * 得到一个格式化的字符串，按照'E'或者'.'，如果字符串里面有字母E就截取首字母到字母E， 如果有字符'.',就将其去掉 如果没有字母E，有'.'，就截取首字母到'.'
	 * 
	 * @param str String 待处理字符串
	 * @param num int 固定长度
	 * @return String
	 */
	public static String getFormatStr(String str, int num) {
		if (str == null || str.equals("")) {
			return "";
		}
		int posE = str.indexOf("E");
		if (posE < 0) {
			int pos = str.indexOf(".");
			if (pos < 0) {
				return str;
			}
			str = str.substring(0, pos);
		} else {
			str = str.substring(0, posE);
			int pos = str.indexOf(".");
			str = str.substring(0, pos) + str.substring(pos + 1, str.length());
			int length = str.length();
			for (int i = 0; i < num - length; i++) {
				str = str + "0";
			}
		}
		return str;
	}

	/**
	 * 把以";"间隔的字符串放到数组里面
	 * 
	 * @param nameStr String 待处理字符串
	 * @return String[]
	 */
	public static String[] getNameArg(String nameStr) {
		StringTokenizer st = new StringTokenizer(nameStr, ";");
		int num = st.countTokens();
		String[] name = new String[num];
		for (int i = 0; i < num; i++) {
			name[i] = st.nextToken();
		}
		return name;
	}

	/**
	 * 把以";"为一级间隔、","为二级间隔的字符串放到数组里面
	 * 
	 * @param key String 键值
	 * @return String[][]
	 */
	public static String[][] getNameArg2(String key) {
		String[] name_pre = getNameArg(key);
		int num = name_pre.length;

		StringTokenizer st = new StringTokenizer(name_pre[0], ",");
		int num_son = st.countTokens();

		String[][] name = new String[num][num_son];
		for (int i = 0; i < num; i++) {
			st = new StringTokenizer(name_pre[i], ",");
			for (int j = 0; j < num_son; j++) {
				name[i][j] = st.nextToken();
			}
		}
		return name;
	}

	/**
	 * 按照标志字符串，将初始字符串的前j个字符，截成一个数组，并返回
	 * 
	 * @param preStr String 初始字符串
	 * @param flag String 标志字符串
	 * @param j int
	 * @return String[]
	 */

	public static String[] getArg(String preStr, int j, String flag) {
		String[] s = new String[j];
		for (int i = 0; i < j; i++) {
			if (i < (j - 1)) {
				s[i] = preStr.substring(0, preStr.indexOf(flag));
				preStr = preStr.substring(preStr.indexOf(flag) + 1, preStr.length());
			} else {
				s[i] = preStr;
			}
		}
		return s;
	}

	/**
	 * 把flag分隔的字符串分开放到数组中，flag可以是任何想要的标志
	 * 
	 * @param str String 待处理的字符串
	 * @param flag String 分隔标志
	 * @return String[] 处理好的数组
	 */
	public static String[] splitStrFlag(String str, String flag) {
		// 把以flag分隔的字符串进行拆分
		StringTokenizer st = new StringTokenizer(str, flag);
		// 得到拆分的字符串的个数
		int num = st.countTokens();
		// 定义存放拆分好的字符串的数组
		String[] name = new String[num];
		for (int i = 0; i < num; i++) {
			// 把各个拆分好的数组放入数组中
			name[i] = st.nextToken();
		}
		// 返回处理好的数组
		return name;
	}

	/**
	 * 把以flag1为一级间隔、flag2为二级间隔的字符串放到数组里面
	 * 
	 * @param key String 键值
	 * @return String[][]
	 */
	public static String[][] getNameArg3(String key, String flag1, String flag2) {
		String[] name_pre = splitStrFlag(key, flag1);
		int num = name_pre.length;

		StringTokenizer st = new StringTokenizer(name_pre[0], flag2);
		int num_son = st.countTokens();

		String[][] name = new String[num][num_son];
		for (int i = 0; i < num; i++) {
			st = new StringTokenizer(name_pre[i], flag2);
			for (int j = 0; j < num_son; j++) {
				name[i][j] = st.nextToken();
			}
		}
		return name;
	}

	/**
	 * 以replace替换str中的problemStr
	 * 
	 * @param str String 待处理的字符串
	 * @param problemStr String 被替换字符串
	 * @param replace String 替换字符串
	 * @return String
	 */
	public static String replaceStr(String str, String problemStr, String replace) {

		for (int i = str.lastIndexOf(problemStr); i >= 0; i = str.lastIndexOf(problemStr, i - 1)) {
			if (i == 0) {
				str = replace + str.substring(i + 1, str.length());
			} else {
				str = str.substring(0, i) + replace + str.substring(i + 1, str.length());
			}// end if
		}// end for
		return str;
	}

	/**
	 * 字符串是否为空白字符，为NULL长度为0时，返回true。
	 * 
	 * @param s String
	 * @return boolean
	 */
	public static final boolean isBlankString(String s) {
		return (s == null || s.length() < 1);
	}

	/**
	 * 字符串是否为一个包含有效信息的字符串， NULL或者长度为0或者仅包含空白字符时返回false
	 * 
	 * @param s String
	 * @return boolean
	 */
	public static final boolean isValidateString(String s) {
		return s != null && s.trim().length() > 0;
	}

	/**
	 * 字符串单词首字母大写
	 * 
	 * @param s String
	 * @return String
	 */
	public static final String toTitleCase(String s) {
		if (s == null || s.length() < 1) {
			return s;
		}

		StringBuffer sb = new StringBuffer(s.length());
		StringBuffer tb = new StringBuffer();
		StringTokenizer st = new StringTokenizer(s, " ");
		while (st.hasMoreTokens()) {
			tb.append(st.nextToken());
			tb.setCharAt(0, Character.toUpperCase(tb.charAt(0)));
			sb.append(tb.toString()).append(' ');
			tb.delete(0, tb.length());
		}
		return sb.toString();
	}

	/**
	 * 字符串中字符大小写状态反转
	 * 
	 * @param s String
	 * @return String
	 */
	public static final String toToggleCase(String s) {
		if (s == null || s.length() < 1) {
			return s;
		}

		char c;
		char[] temp = s.toCharArray();
		for (int i = 0; i < temp.length; i += 1) {
			c = temp[i];
			temp[i] = Character.isUpperCase(c) ? Character.toLowerCase(c) : Character.toUpperCase(c);
		}
		return new String(temp);
	}

	/**
	 * 判断输入参数是否为null返回一个非null的值
	 * 
	 * @param s String
	 * @return String
	 */
	public static final String toValidateString(String s) {
		return (s != null) ? s.trim() : "";
	}

	/**
	 * 将字符串转换成包含有效信息的非空字符串
	 * 
	 * @param s String
	 * @param d String
	 * @return String
	 */
	public static final String toValidateString(String s, String d) {
		return (s != null) ? s.trim() : d;
	}

	/**
	 * 取指定字符串的指定长度子字串
	 * 
	 * @param strAll String
	 * @param strLen int
	 * @return String
	 */
	public static final String subString(String strAll, int strLen) {
		String strNew = toValidateString(strAll);
		return strNew.length() >= strLen ? strNew.substring(0, strLen) : strNew;
	}

	/**
	 * 替换StringBuffer中的字符串为指定字符串
	 * 
	 * @param b StringBuffer
	 * @param s String
	 * @param t String
	 * @return StringBuffer
	 */
	public static final StringBuffer replace(StringBuffer b, String s, String t) {
		if (b == null || b.length() < 1 || isBlankString(s)) {
			return b;
		}

		int i = b.indexOf(s);
		int l = s.length();
		while (i >= 0) {
			b.replace(i, l, t);
		}
		return b;
	}

	/**
	 * 不区分大小写，替换字符串中的指定字符，
	 * 
	 * @param s String
	 * @param s1 String
	 * @param s2 String
	 * @return String
	 */
	public static final String replaceIgnoreCase(String s, String s1, String s2) {
		if (s == null || isBlankString(s1)) {
			return s;
		}

		String l1 = s.toLowerCase();
		String l2 = s1.toLowerCase();

		int i = 0;
		int j = l1.indexOf(l2, i);
		int l = l2.length();
		char[] c = s.toCharArray();
		StringBuffer sb = new StringBuffer(s.length());
		while (j >= 0) {
			sb.append(c, i, j - i).append(s2);
			i = j + l;
			j = l1.indexOf(l2, i);
		}
		sb.append(c, i, c.length - i);
		return sb.toString();
	}

	/**
	 * 将String数组转换为List
	 * 
	 * @param strArray String[]
	 * @return ArrayList
	 */
	public static final ArrayList<String> toList(String[] strArray) {
		ArrayList<String> list = new ArrayList<String>();
		if (strArray == null || strArray.length < 1) {
			return list;
		}

		for (int i = 0; i < strArray.length; i++) {
			String temp = strArray[i];
			list.add(toValidateString(temp));
		}
		return list;
	}

	/**
	 * 纯文件字符到HTML文章换行的转换
	 * 
	 * @param str String
	 * @return String
	 */
	public static final String toHtml(String str) {
		if (isBlankString(str)) {
			return ("");
		}

		char[] temp = str.toCharArray();
		StringBuffer buf = new StringBuffer(temp.length);

		for (int i = 0; i < temp.length; i++) {
			char chr = temp[i];
			if (chr == '\n') {
				buf.append("<br />");
				continue;
			}

			if (chr == '\t') {
				buf.append("&nbsp;&nbsp;&nbsp;&nbsp;");
				continue;
			}

			if (chr == ' ') {
				buf.append("&nbsp;");
				continue;
			}

			buf.append(chr);
		}

		return buf.toString();
	}

	/**
	 * 将字符串数组合并成一个以 delim 分隔的字符串
	 * 
	 * @param array String[] 字符串数组
	 * @param delim String 分隔符，为null的时候使用""作为分隔符（即没有分隔符）
	 * @return String 合并后的字符串
	 */
	public static final String combine(String[] array, String delim) {
		if (array == null || array.length == 0) {
			return "";
		}

		if (delim == null) {
			delim = "";
		}

		StringBuffer result = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			String temp = array[i];
			result.append(delim);
			result.append(temp);
		}

		if (result.length() > 0) {
			result.delete(0, delim.length());
		}
		return result.toString();
	}

	/**
	 * 字符串左部填充字符串f到定长l。
	 * 
	 * @param s String 指定的字符
	 * @param l int 指定的长度
	 * @return String 最终生成的字符串
	 */
	public static final String lPad(String s, int l, String f) {
		StringBuffer sb = new StringBuffer(l);
		int t = l - s.length();
		while (--t >= 0) {
			sb.append(f);
		}
		sb.append(s);
		t = sb.length() - l;
		return t > 0 ? sb.substring(t) : sb.toString();
	}

	/**
	 * 字符串右部填充字符串f到定长l
	 * 
	 * @param s String 原字符串
	 * @param f String 指定的字符
	 * @param l int 指定的长度
	 * @return String 最终生成的字符串
	 */
	public static final String rPad(String s, int l, String f) {
		StringBuffer sb = new StringBuffer(l);
		sb.append(s);
		int t = l - s.length();
		while (--t >= 0) {
			sb.append(f);
		}
		t = sb.length() - l;
		return t > 0 ? sb.substring(0, l) : sb.toString();
	}

	/**
	 * 左端匹配删除，将所有左端重复出现的空白字符（如\t\n\r\f\x0B）删除
	 * 
	 * @param s String
	 * @return String
	 */
	public static final String lTrim(String s) {
		return lTrim(s, "");
	}

	/**
	 * 左端匹配替换，将所有左端重复出现的空白字符（如\t\n\r\f\x0B）替换成一个c
	 * 
	 * @param s String
	 * @param c String
	 * @return String
	 */
	public static final String lTrim(String s, String c) {
		return (s != null) ? s.replaceAll("^[\\s]+", c) : s;
	}

	/**
	 * 右端匹配删除，将所有右端重复出现的空白字符（如\t\n\r\f\x0B）删除
	 * 
	 * @param s String
	 * @return String
	 */
	public static final String rTrim(String s) {
		return rTrim(s, "");
	}

	/**
	 * 右端匹配替换，将所有右端重复出现的空白字符（如\t\n\r\f\x0B）替换成一个c
	 * 
	 * @param s String
	 * @param c String
	 * @return String
	 */
	public static final String rTrim(String s, String c) {
		return (s != null) ? s.replaceAll("[\\s]+$", c) : s;
	}

	/**
	 * 左端匹配替换，将所有左端重复出现的字符串m替换成一个t
	 * 
	 * @param s String
	 * @param m String
	 * @param t String
	 * @return String
	 */
	public static final String lReplace(String s, String m, String t) {
		return (s != null) ? s.replaceAll("^(" + m + ")+", t) : s;
	}

	/**
	 * 右端匹配替换，将所有右端重复出现的字符串m替换成一个t
	 * 
	 * @param s String
	 * @param m String
	 * @param t String
	 * @return String
	 */
	public static final String rReplace(String s, String m, String t) {
		return (s != null) ? s.replaceAll("(" + m + ")+$", t) : s;
	}

	/**
	 * 匹配替换，将所有重复出现的字符串m替换成一个字符串t
	 * 
	 * @param s String
	 * @param m String
	 * @param t String
	 * @return String
	 */
	public static final String trim(String s, String m, String t) {
		return s.replaceAll('(' + m + ')' + '+', t);
	}

	/**
	 * 判断字符是否为双字节字符，如中文
	 * 
	 * @param c String
	 * @return boolean
	 */
	public static final boolean isDoubleByte(char c) {
		return (c >>> 8) == 0;
	}
	public static final boolean isDoubleByte(String s){
		char[] c = s.toCharArray();
		for(char t : c){
			return (t >>> 8) == 0;
		}
		return false;
	}
	/**
	 * 将文字转化成数字
	 * @param strInt
	 * @return
	 */
	public static final Integer str2Int(String strInt){
    	int rs = 0;
    	try{
    		rs = Integer.parseInt(strInt);
    	}catch(NumberFormatException nfe){
    	}
    	return rs;
    }
}
