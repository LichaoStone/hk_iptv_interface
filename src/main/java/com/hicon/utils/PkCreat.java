package com.hicon.utils;

import java.util.Random;
import java.util.UUID;

/**
 * 32位UUID
 * @作者 栗超
 * @时间 2018年5月11日 下午3:08:18
 * @说明
 */
public class PkCreat {
	/**
	 * 主键生成方法
	 * @return 32位字符串主键
	 */
	public static String getTablePk() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 主键生成方法(纯英文字符串)
	 * @return 32位字符串主键
	 */
	public static String getTablePk2() {
		return UUID.randomUUID().toString().replaceAll("-", "")
				.replaceAll("0", "o").replaceAll("1", "p").replaceAll("2", "q")
				.replaceAll("3", "r").replaceAll("4", "s").replaceAll("5", "t")
				.replaceAll("6", "u").replaceAll("7", "v").replaceAll("8", "w")
				.replaceAll("9", "x");
	}

	/**
	 * 生成密码
	 * @return 返回生成的密码
	 */
	public static String createUserPass() {
		String[] arrLower = new String[] { "a", "b", "c", "d", "e", "f", "g",
				"h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
				"t", "u", "v", "w", "x", "y", "z" };
		String[] arrUpper = new String[] { "A", "B", "C", "D", "E", "F", "G",
				"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
				"T", "U", "V", "W", "X", "Y", "Z" };
		String[] arrNum = new String[] { "0", "1", "2", "3", "4", "5", "6",
				"7", "8", "9" };
		String[] arrSpecial = new String[] { "~", "@", "#", "$", "%", "&", "*",
				"(", ")", ",", ".", "/" };

		int lowerRandomArr[] = new int[] { 3, 4, 5 };
		int upperRandomArr[] = new int[] { 3, 4, 5 };
		int numRandomArr[] = new int[] { 3, 4, 5 };
		int specialRandomArr[] = new int[] { 3, 4, 5 };

		Random lowerRan = new Random();
		int lowerLen = lowerRandomArr[lowerRan.nextInt(3)];

		Random upperRan = new Random();
		int upperLen = upperRandomArr[upperRan.nextInt(3)];

		Random numRan = new Random();
		int numLen = numRandomArr[numRan.nextInt(3)];

		Random specialRan = new Random();
		int speciaLen = specialRandomArr[specialRan.nextInt(3)];

		String userPass = "";

		String lowerStr = "";
		String upperStr = "";
		String numStr = "";
		String specialStr = "";

		for (int i = 0; i < lowerLen; i++) {
			int sign = lowerRan.nextInt(arrLower.length);
			lowerStr = lowerStr + arrLower[sign];
		}

		for (int i = 0; i < upperLen; i++) {
			int sign = upperRan.nextInt(arrUpper.length);
			upperStr = upperStr + arrUpper[sign];
		}

		for (int i = 0; i < numLen; i++) {
			int sign = numRan.nextInt(arrNum.length);
			numStr = numStr + arrNum[sign];
		}

		for (int i = 0; i < speciaLen; i++) {
			int sign = specialRan.nextInt(arrSpecial.length);
			specialStr = specialStr + arrSpecial[sign];
		}

		Random orderRandom = new Random();
		int rs = orderRandom.nextInt(3) + 1;

		if (rs == 1) {
			userPass = lowerStr + upperStr + numStr + specialStr;
		} else if (rs == 2) {
			userPass = upperStr + lowerStr + specialStr + numStr;
		} else if (rs == 3) {
			userPass = numStr + upperStr + specialStr + lowerStr;
		} else if (rs == 4) {
			userPass = specialStr + upperStr + numStr + lowerStr;
		} else {
			userPass = lowerStr + specialStr + numStr + upperStr;
		}

		return userPass;
	}

	public static void main(String[] args) {
		System.out.println(PkCreat.getTablePk());
	}
}
