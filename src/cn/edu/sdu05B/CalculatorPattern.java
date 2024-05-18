package cn.edu.sdu05B;

/**
 * CalculatorPattern类的简介
 * <p>计算器的模式接口，可扩展出多种工作模式<br>
 *
 * @author Zhang Jinghui
 * <p>created on 2023/11/25<br>
 */
public interface CalculatorPattern {
	/**
	 * execute方法
	 * <p>执行计算<br>
	 * 
	 * @return void 返回类型
	 */
	public void execute();
	
	/**
	 * initialize方法
	 * <p>计算模式初始化<br>
	 * 
	 * @return void 返回类型
	 */
	public void initialize();	
	
	/**
	 * displayHex方法
	 * <p>当前数字转16进制<br>
	 * 
	 * @return void 返回类型
	 */
	public void displayHex();
	
	/**
	 * displayDec方法
	 * <p>当前数字转10进制<br>
	 * 
	 * @return void 返回类型
	 */
	public void displayDec();
	
	/**
	 * displayOct方法
	 * <p>当前数字转8进制<br>
	 * 
	 * @return void 返回类型
	 */
	public void displayOct();
	
	/**
	 * displayBin方法
	 * <p>当前数字转2进制<br>
	 * 
	 * @return void 返回类型
	 */
	public void displayBin();
}

