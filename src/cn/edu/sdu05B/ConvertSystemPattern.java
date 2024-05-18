package cn.edu.sdu05B;

import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import cn.edu.sdu05.ConvertSystem;
import cn.edu.sdu05.ScienceCalculator;

/**
 * ConvertSystemPattern类的简介
 * <p>进制转换模式<br>
 *
 * @author Zhang Jinghui
 * <p>created on 2023/11/26<br>
 */
public class ConvertSystemPattern implements CalculatorPattern{
	// 表征当前数字的进制
	String currentSystem = null;

	/**  
	 * initialize
	 * <p>界面初始化<br> 
	 * @see cn.edu.sdu05B.CalculatorPattern#initialize() 
	 */ 
	@Override
	public void initialize() {
		// 清空当前进制状态
		currentSystem = null;
		// 设置提示信息,初始状态为十进制
		String info = "[DEC]\n";
		ScienceCalculator.textPane.setText(info);
		// 将光标设置到文本末尾，这样光标就在下一行第一个字符处
		ScienceCalculator.textPane.setCaretPosition(ScienceCalculator.textPane.getDocument().getLength());

	}

	/**  
	 * execute
	 * <p>执行计算<br> 
	 * @see cn.edu.sdu05B.CalculatorPattern#execute() 
	 */ 
	@Override
	public void execute() {
		// 获取textPane中的文本
		String input = ScienceCalculator.textPane.getText();
		// 由于插入提示信息后用"\n"换行,故可用换行符分割当前进制标志与输入数字
		String[] lines = input.split("\n"); 
		// 第一行为当前进制标志,赋值给currentSystem
		currentSystem = lines[0];
		StyledDocument doc = ScienceCalculator.textPane.getStyledDocument();
		// 在下一行显示当前数字
		try {
			doc.insertString(doc.getLength(), "\n" + lines[1], null);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * displayHex方法
	 * 
	 * @see cn.edu.sdu05B.CalculatorPattern#displayHex() 
	 */
	@Override
	public void displayHex() {
		if(currentSystem == null) {
			// 设置提示信息为16进制
			String info = "[HEX]\n";
			ScienceCalculator.textPane.setText(info);
			// 将光标设置到文本末尾，这样光标就在下一行第一个字符处
			ScienceCalculator.textPane.setCaretPosition(ScienceCalculator.textPane.getDocument().getLength());
		}
		else {
			// 获取textPane中的文本
			String input = ScienceCalculator.textPane.getText();
			// 由于插入提示信息后用"\n"换行,故可用换行符分割当前进制标志与输入数字
			String[] lines = input.split("\n"); 
			// 输入数字处于第二行，为lines[1]
			String number = lines[1];
			// 初始化转换后的16进制数hex
			String hex = null;
			// 判断初始数字的进制，调用对应的方法转换为十六进制
			if(currentSystem.equals("[DEC]")) {
				hex = ConvertSystem.decToHex(number);
			}
			else if (currentSystem.equals("[OCT]")) {
				hex = ConvertSystem.octToHex(number);
			}
			else if (currentSystem.equals("[BIN]")) {
				hex = ConvertSystem.binToHex(number);
			}
			else {
				hex = number;
			}
			// 显示转换结果
			ScienceCalculator.textPane.setText("[HEX]\n" + lines[1] + "\n" + hex);
		}
	}
	
	/**
	 * displayDec方法
	 * 
	 * @see cn.edu.sdu05B.CalculatorPattern#displayDec() 
	 */
	@Override
	public void displayDec() {
		if(currentSystem == null) {
			// 设置提示信息为10进制
			String info = "[DEC]\n";
			ScienceCalculator.textPane.setText(info);
			// 将光标设置到文本末尾，这样光标就在下一行第一个字符处
			ScienceCalculator.textPane.setCaretPosition(ScienceCalculator.textPane.getDocument().getLength());
		}
		else {
			// 获取textPane中的文本
			String input = ScienceCalculator.textPane.getText();
			// 由于插入提示信息后用"\n"换行,故可用换行符分割当前进制标志与输入数字
			String[] lines = input.split("\n"); 
			// 输入数字处于第二行，为lines[1]
			String number = lines[1];
			// 初始化转换后的10进制数dec
			String dec = null;
			// 判断初始数字的进制，调用对应的方法转换为十进制
			if(currentSystem.equals("[DEC]")) {
				dec = number;
			}
			else if (currentSystem.equals("[OCT]")) {
				dec = ConvertSystem.octToDec(number);
			}
			else if (currentSystem.equals("[BIN]")) {
				dec = ConvertSystem.binToDec(number);
			}
			else {
				dec = ConvertSystem.hexToDec(number);
			}
			// 显示转换结果
			ScienceCalculator.textPane.setText("[DEC]\n" + lines[1] + "\n" + dec);
		}
	}
	
	/**
	 * displayOct方法
	 * 
	 * @see cn.edu.sdu05B.CalculatorPattern#displayOct() 
	 */
	@Override
	public void displayOct() {
		if(currentSystem == null) {
			// 设置提示信息为8进制
			String info = "[OCT]\n";
			ScienceCalculator.textPane.setText(info);
			// 将光标设置到文本末尾，这样光标就在下一行第一个字符处
			ScienceCalculator.textPane.setCaretPosition(ScienceCalculator.textPane.getDocument().getLength());
		}
		else {
			// 获取textPane中的文本
			String input = ScienceCalculator.textPane.getText();
			// 由于插入提示信息后用"\n"换行,故可用换行符分割当前进制标志与输入数字
			String[] lines = input.split("\n"); 
			// 输入数字处于第二行，为lines[1]
			String number = lines[1];
			// 初始化转换后的8进制数oct
			String oct = null;
			// 判断初始数字的进制，调用对应的方法转换为八进制
			if(currentSystem.equals("[DEC]")) {
				oct = ConvertSystem.decToOct(number);
			}
			else if (currentSystem.equals("[OCT]")) {
				oct = number;
			}
			else if (currentSystem.equals("[BIN]")) {
				oct = ConvertSystem.binToOct(number);
			}
			else {
				oct = ConvertSystem.hexToOct(number);
			}
			// 显示转换结果
			ScienceCalculator.textPane.setText("[OCT]\n" + lines[1] + "\n" + oct);
		}
	}
	
	/**
	 * displayBin方法
	 * 
	 * @see cn.edu.sdu05B.CalculatorPattern#displayBin() 
	 */
	@Override
	public void displayBin() {
		if(currentSystem == null) {
			// 设置提示信息为2进制
			String info = "[BIN]\n";
			ScienceCalculator.textPane.setText(info);
			// 将光标设置到文本末尾，这样光标就在下一行第一个字符处
			ScienceCalculator.textPane.setCaretPosition(ScienceCalculator.textPane.getDocument().getLength());
		}
		else {
			// 获取textPane中的文本
			String input = ScienceCalculator.textPane.getText();
			// 由于插入提示信息后用"\n"换行,故可用换行符分割当前进制标志与输入数字
			String[] lines = input.split("\n"); 
			// 输入数字处于第二行，为lines[1]
			String number = lines[1];
			// 初始化转换后的2进制数bin
			String bin = null;
			// 判断初始数字的进制，调用对应的方法转换为二进制
			if(currentSystem.equals("[DEC]")) {
				bin = ConvertSystem.decToBin(number);
			}
			else if (currentSystem.equals("[OCT]")) {
				bin = ConvertSystem.octToBin(number);
			}
			else if (currentSystem.equals("[BIN]")) {
				bin = number;
			}
			else {
				bin = ConvertSystem.hexToBin(number);
			}
			// 显示转换结果
			ScienceCalculator.textPane.setText("[BIN]\n" + lines[1] + "\n" + bin);
		}
	}
	
}
