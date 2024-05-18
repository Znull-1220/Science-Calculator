package cn.edu.sdu05B;

import javax.swing.JFrame;
import cn.edu.sdu05.PlotFunction;
import cn.edu.sdu05.ScienceCalculator;

/**
 * PlotFunctionPattern类的简介
 * <p>函数绘图模式<br>
 *
 * @author Zhang Jinghui
 * <p>created on 2023/11/28<br>
 */
public class PlotFunctionPattern implements CalculatorPattern{
	/**
	 * execute
	 * <p>执行计算方法，这里是绘制函数图象<br> 
	 * @see cn.edu.sdu05B.CalculatorPattern#execute() 
	 */
	@Override
	public void execute() {
		// 获取函数表达式
		String funcStr = ScienceCalculator.textPane.getText();
		// 获取等号后面的子字符串，即去掉"f(x)="
		String function = funcStr.substring(funcStr.indexOf('=') + 1);
		// 将函数字符串作为参数创建PlotFunction的对象进行绘图
		PlotFunction plotFunction = new PlotFunction(function);
		// 使窗体可见
		plotFunction.setVisible(true);
		// 设置关闭行为为仅关闭当前窗体，不退出程序
		plotFunction.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	};
	
	/**
	 * initialize
	 * <p>界面初始化<br> 
	 * @see cn.edu.sdu05B.CalculatorPattern#initialize() 
	 */
	@Override
	public void initialize() {
		// 清空textPane与resultLabel
		ScienceCalculator.textPane.setText("f(x)=");
		ScienceCalculator.resultLabel.setText(null);
	};
		
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
