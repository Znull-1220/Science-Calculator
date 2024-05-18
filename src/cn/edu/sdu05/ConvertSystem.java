package cn.edu.sdu05;

/**
 * ConvertSystem类的简介
 * <p>进制转换类<br>
 *
 * @author Zhang Jinghui
 * <p>created on 2023/11/16<br>
 */
public class ConvertSystem {
	/**
	 * hexToDec方法
	 * <p>十六进制转十进制<br>
	 *
	 * @param hex 输入的十六进制数字
	 * @return String 返回类型
	 */
	public static String hexToDec(String hex) {
		return String.valueOf(Integer.parseInt(hex, 16));
	}
	
	/**
	 * decToHex方法
	 * <p>10进制转16进制<br>
	 *
	 * @param dec 输入10进制数
	 * @return String 返回类型
	 */
	public static String decToHex(String dec) {
		return Integer.toHexString(Integer.valueOf(dec));
	}
	
	/**
	 * binToDec方法
	 * <p>二进制转十进制<br>
	 *
	 * @param bin 输入二进制数
	 * @return String 返回类型
	 */
	public static String binToDec(String bin) {
		return String.valueOf(Integer.parseInt(bin, 2));
	}
	
	/**
	 * decToBin方法
	 * <p>十进制转二进制<br>
	 *
	 * @param dec 输入十进制数
	 * @return String 返回类型
	 */
	public static String decToBin(String dec) {
		return Integer.toBinaryString(Integer.valueOf(dec));
	}
	
	/**
	 * octToDec方法
	 * <p>八进制转十进制<br>
	 *
	 * @param oct 输入八进制数

	 * @return String 返回类型
	 */
	public static String octToDec(String oct) {
		return String.valueOf(Integer.parseInt(oct, 8));
	}
	
	/**
	 * decToOct方法
	 * <p>十进制转八进制<br>
	 *
	 * @param dec 输入十进制数
	 * @return String 返回类型
	 */
	public static String decToOct(String dec) {
		return Integer.toOctalString(Integer.valueOf(dec));
	}

	/**
	 * octToHex方法
	 * <p>八进制转十六进制<br>
	 *
	 * @param oct 输入八进制数
	 * @return String 返回类型
	 */
	public static String octToHex(String oct) {
		return String.valueOf(Integer.toHexString(Integer.parseInt(oct, 8)));

	}
	
	/**
	 * hexToOct方法
	 * <p>十六进制转八进制<br>
	 *
	 * @param hex 输入十六进制数
	 * @return String 返回类型
	 */
	public static String hexToOct(String hex) {
		return String.valueOf(Integer.toOctalString(Integer.parseInt(hex, 16)));
	}
	
	/**
	 * binToHex方法
	 * <p>二进制转十六进制<br>
	 *
	 * @param oct 输入二进制数
	 * @return String 返回类型
	 */
	public static String binToHex(String bin) {
		return String.valueOf(Integer.toHexString(Integer.parseInt(bin, 2)));

	}
	
	/**
	 * hexToBin方法
	 * <p>十六进制转二进制<br>
	 *
	 * @param hex 输入十六进制数
	 * @return String 返回类型
	 */
	public static String hexToBin(String hex) {
		return String.valueOf(Integer.toBinaryString(Integer.parseInt(hex, 16)));
	}
	
	/**
	 * binToOct方法
	 * <p>二进制转八进制<br>
	 *
	 * @param oct 输入二进制数
	 * @return String 返回类型
	 */
	public static String binToOct(String bin) {
		return String.valueOf(Integer.toOctalString(Integer.parseInt(bin, 2)));

	}
	
	/**
	 * octToBin方法
	 * <p>八进制转二进制<br>
	 *
	 * @param hex 输入八进制数
	 * @return String 返回类型
	 */
	public static String octToBin(String hex) {
		return String.valueOf(Integer.toBinaryString(Integer.parseInt(hex, 8)));
	}
}
