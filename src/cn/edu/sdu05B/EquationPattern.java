package cn.edu.sdu05B;

import cn.edu.sdu05.ScienceCalculator;
import cn.edu.sdu05.SolveEquation;

/**
 * EquationPattern类的简介
 * <p>方程模式类，该类为抽象类，包括SolveEquationPattern与
 * SolveLinearEquationPattern两个子类(非抽象类)，分别为一
 * 元二次方程模式与二元一次方程组模式<br>
 *
 * @author Zhang Jinghui
 * <p>created on 2023/11/25<br>
 */
public abstract class EquationPattern implements CalculatorPattern {
	/**
	 * SolveEquationPattern类的简介
	 * <p>一元二次方程模式类<br>
	 *
	 * @author Zhang Jinghui
	 * <p>created on 2023/11/30<br>
	 */
	public static class SolveEquationPattern extends EquationPattern {
		/**
		 * execute
		 * <p>执行计算<br> 
		 * @see cn.edu.sdu05B.CalculatorPattern#execute() 
		 */
		@Override
		public void execute() {
			// 获取textPane中的文本
			String input = ScienceCalculator.textPane.getText();
			// 由于插入提示信息后用"\n"换行,故可用换行符分割提示信息与输入系数
			String[] lines = input.split("\n"); 
			// 第二行为系数,用","来分割，获取系数
			String[] coefficients = lines[1].split(","); // 获取下一行的文本，并按逗号分割为系数数组
			if (coefficients.length == 3) {
				// 将系数转换为double类型
				double a = Double.parseDouble(coefficients[0]);
				double b = Double.parseDouble(coefficients[1]);
				double c = Double.parseDouble(coefficients[2]);
				// 调用SolveEquation.solveEquation方法进行计算
				String[] result = SolveEquation.solveEquation(a, b, c);
				// 显示计算结果
				displayResult(coefficients, result);		
			}
			else {
				// 清空textPane并显示错误信息
				ScienceCalculator.textPane.setText(null);
				ScienceCalculator.textPane.setText("输入错误");
			}
		};
		
		/**
		 * displayResult方法
		 * <p>显示方程计算结果<br>
		 *
		 * @param coefficients 方程系数
		 * @param result 计算结果
		 * @return void 返回类型
		 */
		private void displayResult(String[] coefficients, String[] result) {
			// 清空textPane
			ScienceCalculator.textPane.setText(null);
			// 提示信息字符串
			String info = coefficients[0] + "x²+" + coefficients[1] + "x+" + coefficients[2] + "=0的解为:\n";
			// 以info为参数初始化StringBuilder，以便后续计算结果的添加
			StringBuilder sb = new StringBuilder(info);

			if(result[1] == "NaN") {
				// 如果result[1] == "NaN"，则该方程有两个相同解
				sb.append("x₁=x₂=" + result[0]).append("\n");
			}
			else {
				// 其余情况有两个不同解
				sb.append("x₁=" + result[0]).append("\n");
				sb.append("x₂=" + result[1]).append("\n");
			}
			ScienceCalculator.textPane.setText(sb.toString());
		}
		/**
		 * initialize
		 * <p>界面初始化<br> 
		 * @see cn.edu.sdu05B.CalculatorPattern#initialize() 
		 */
		@Override
		public void initialize() {
			// 设置提示信息
			String info = "请输入方程ax²+bx+c=0的系数,格式为a,b,c\n";
			ScienceCalculator.textPane.setText(info);
			// 将光标设置到文本末尾，这样光标就在下一行第一个字符处
			ScienceCalculator.textPane.setCaretPosition(ScienceCalculator.textPane.getDocument().getLength());
		}
	}
	
	/**
	 * SolveLinearEquationPattern类的简介
	 * <p>二元一次方程组模式类<br>
	 *
	 * @author Zhang Jinghui
	 * <p>created on 2023/11/30<br>
	 */
	public static class SolveLinearEquationPattern extends EquationPattern {
		/**
		 * execute
		 * <p>执行计算<br> 
		 * @see cn.edu.sdu05B.CalculatorPattern#execute() 
		 */
		@Override
		public void execute() {
			// 获取textPane中的文本
			String input = ScienceCalculator.textPane.getText();
			// 由于插入提示信息后用"\n"换行,故可用换行符分割提示信息与输入系数
			String[] lines = input.split("\n"); 
			// 第二行为系数,用","来分割，获取系数
			String[] coefficients = lines[1].split(","); // 获取下一行的文本，并按逗号分割为系数数组
			if (coefficients.length == 6) {
				// 将系数转换为double类型
				double a1 = Double.parseDouble(coefficients[0]);
				double b1 = Double.parseDouble(coefficients[1]);
				double c1 = Double.parseDouble(coefficients[2]);
				double a2 = Double.parseDouble(coefficients[3]);
				double b2 = Double.parseDouble(coefficients[4]);
				double c2 = Double.parseDouble(coefficients[5]);
				// 调用SolveEquation.solveLinearEquations方法计算二元一次方程组
				String[] result = SolveEquation.solveLinearEquations(a1, b1, c1, a2, b2, c2);
				// 显示结果
				displayResult(coefficients, result);		
			}
			else {
				// 清空textPane并显示错误信息
				ScienceCalculator.textPane.setText(null);
				ScienceCalculator.textPane.setText("输入错误");
			}
		};
		
		/**
		 * displayResult方法
		 * <p>显示方程计算结果<br>
		 *
		 * @param coefficients 方程系数
		 * @param result 计算结果
		 * @return void 返回类型
		 */
		private void displayResult(String[] coefficients, String[] result) {
			// 清空textPane
			ScienceCalculator.textPane.setText(null);
			// 提示信息字符串
			String info = coefficients[0] + "x+" + coefficients[1] + "y=" + coefficients[2] + "&&" 
			+ coefficients[3] + "x+" + coefficients[4] + "y=" + coefficients[5] + "的解为:\n";
			// 以info为参数初始化StringBuilder，以便后续计算结果的添加
			StringBuilder sb = new StringBuilder(info);

			if(result[1] == "NaN") {
				// 如果result[1] == "NaN"，则该方程无解或有无穷解
				sb.append(result[0]).append("\n");
			}
			else {
				// 其余情况有唯一解
				sb.append("x=" + result[0]).append("\n");
				sb.append("y=" + result[1]).append("\n");
			}
			ScienceCalculator.textPane.setText(sb.toString());
		}
		/**
		 * initialize
		 * <p>界面初始化<br> 
		 * @see cn.edu.sdu05B.CalculatorPattern#initialize() 
		 */
		@Override
		public void initialize() {
			// 设置提示信息
			String info = "请输入方程组a₁x+b₁y=c₁&&a₂x+b₂y=c₂的系数,格式为a₁,b₁,c₁,a₂,b₂,c₂\n";
			ScienceCalculator.textPane.setText(info);
			// 将光标设置到文本末尾，这样光标就在下一行第一个字符处
			ScienceCalculator.textPane.setCaretPosition(ScienceCalculator.textPane.getDocument().getLength());
		}
	}
	
	/*下面四个方法用于进制转换类,这里无需实现*/
	public void displayHex() {
		
	}
	public void displayDec() {
		
	}
	public void displayOct() {
		
	}
	public void displayBin() {
		
	}
}
