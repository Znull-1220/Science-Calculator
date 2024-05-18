package cn.edu.sdu05;

import java.math.BigDecimal;

/**
 * SolveEquation类的简介
 * <p>该类用于求解方程，包括一元二次方程与二元一次方程组<br>
 *
 * @author Zhang Jinghui
 * <p>created on 2023/11/13<br>
 */
public class SolveEquation {

	/**
	 * solveEquation方法
	 * <p>求解一元二次方程的方法，包括虚数解。返回的字符串数组包含两个元素，
	 * 若该方程有两个相同解，则第一个元素为方程的解，第二个元素为NaN。
	 * 其余情况两个元素分别为x1与x2的值。<br>
	 *
	 * @param a 二次项系数
	 * @param b 一次项系数
	 * @param c 常数项
	 * @return String[] 返回类型
	 */
	public static String[] solveEquation(double a, double b, double c) {
		String[] string = {"0" ,"NaN"};
		// 判别式
		double discriminant = b * b - 4 * a * c;
		BigDecimal bigDecimal = new BigDecimal(discriminant);
		// 判别式大于0时
		if(bigDecimal.compareTo(BigDecimal.ZERO) == 1) {
			string[0] = String.valueOf((-b + Math.sqrt(discriminant)) / 2 * a);
			string[1] = String.valueOf((-b - Math.sqrt(discriminant)) / 2 * a);
		}
		// 如果判别式等于0，则有两个相同解
		else if (bigDecimal.compareTo(BigDecimal.ZERO) == 0) {
			string[0]= String.valueOf(- b / 2 * a);
		}
		else {
			// 虚部
			double im = Math.sqrt(Math.abs(discriminant)) / 2 * a;
			// 实部
			double re = - b / 2 * a;
			string[0] = String.valueOf(re) + String.valueOf('+') + String.valueOf(im) + String.valueOf('i');
			string[1] = String.valueOf(re) + String.valueOf('-') + String.valueOf(im) + String.valueOf('i');
		}
		return string;
	}

	/**
	 * solveLinearEquations方法
	 * <p>求解二元一次方程组的方法。返回的字符串数组包含两个元素，
	 * 若该方程有无穷解或无解，则第一个元素为元素为解的情况(无解或无穷解)，
	 * 第二个元素为NaN。若有唯一解，则第一个元素与第二个元素分别为x与y的值。<br>
	 *
	 * @param a1 方程1中x的系数
	 * @param b1 方程1中yx的系数
	 * @param c1 方程1中等号后的常数
	 * @param a2 方程2中x的系数
	 * @param b2 方程2中y的系数
	 * @param c2 方程2中等号后的常数
	 * @return String[] 返回类型
	 */
	public static String[] solveLinearEquations(double a1, double b1, double c1, double a2, double b2, double c2) {
		String[] solution = {"0" ,"NaN"};
		// 判断方程对应系数是否成比例
		double determinant = a1 * b2 - a2 * b1;
		BigDecimal bigDecimal = new BigDecimal(determinant);	
		if (bigDecimal.compareTo(BigDecimal.ZERO) == 0) {
			// 系数的比值
			BigDecimal proportion = new BigDecimal(a1 / a2);
			// 如果常数与系数比例相同，则有无穷解
			if(proportion.compareTo(new BigDecimal(c1 / c2)) == 0)
				solution[0] = "无穷解";
			else {
				solution[0] = "无解";
			}
		}
		else {
			// x
			solution[0] = String.valueOf((b2 * c1 - b1 * c2) / determinant);
			// y
			solution[1] = String.valueOf((a1 * c2 - a2 * c1) / determinant);
		}
		return solution;
	}

}
