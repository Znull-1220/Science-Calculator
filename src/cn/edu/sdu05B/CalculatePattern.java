package cn.edu.sdu05B;

import org.mariuszgromada.math.mxparser.Expression;
import cn.edu.sdu05.ScienceCalculator;

/**
 * CalculatePattern类的简介
 * <p>数值计算模式<br>
 *
 * @author Zhang Jinghui
 * <p>created on 2023/11/25<br>
 */
public class CalculatePattern implements CalculatorPattern {
	@Override
	public void execute() {
		// 调用mxparser包的Expression类，创建新表达式并计算
		Expression expression = new Expression(ScienceCalculator.textPane.getText());
		Double result = expression.calculate();
		/* 判断计算结果是否为NaN，若为NaN，则清空resultLabel
		 * 并在textPane中显示提示信息，若不为NaN则将计算结果显示在resultLabel上*/
		if(Double.isNaN(result)) {
			ScienceCalculator.textPane.setText("数学错误");
			ScienceCalculator.resultLabel.setText(null);
		}
		else {
			// 并将计算结果设置为resultLabel的内容以显示结果
			ScienceCalculator.resultLabel.setText(String.valueOf(result));
		}
	};
	
	@Override
	public void initialize() {
		// 清空textPane与resultLabel
		ScienceCalculator.textPane.setText(null);
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
