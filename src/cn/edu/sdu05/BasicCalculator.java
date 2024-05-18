package cn.edu.sdu05;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.Utilities;
import com.formdev.flatlaf.intellijthemes.FlatSolarizedLightIJTheme;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JCheckBox;
import java.awt.Font;

/**
 * BasicCalculator类的简介
 * <p>基础计算功能GUI<br>
 *
 * @author Zhang Jinghui
 * <p>created on 2023/11/18<br>
 */
public class BasicCalculator {
	JFrame frmZjhsCalculator;
	// 静态控件，JTextPane
	static JTextPane textPane = new JTextPane();
	// 算式样式，作为算式输入时的显示样式
	Style equationStyle = textPane.addStyle("equationStyle", null);   
	// 计算后样式，点击'='按钮计算后，原算式改为灰色并缩小
	Style calculatedStyle = textPane.addStyle("calculatedStyle", null);
	// resultLabel，用于显示计算结果
	static JLabel resultLabel = new JLabel("");

	/**
	 * 启动应用程序
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BasicCalculator window = new BasicCalculator();
					//在屏幕中央显示窗体
					window.frmZjhsCalculator.setLocationRelativeTo(null);
					window.frmZjhsCalculator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
		});
		// 设置UI
		try {
			UIManager.put("Button.arc", 25);
			UIManager.setLookAndFeel(new FlatSolarizedLightIJTheme());
		} catch (UnsupportedLookAndFeelException e) {
			// 不做处理
		}
	}

	/**
	 * 创建应用程序
	 */
	public BasicCalculator() {
		initialize();
	}

	/**
	 * 初始化窗体内容
	 */
	private void initialize() {
		frmZjhsCalculator = new JFrame();
		frmZjhsCalculator.setTitle("ZJH\u2018s Calculator");
		frmZjhsCalculator.setResizable(false);
		frmZjhsCalculator.setBounds(100, 100, 500, 595);
		frmZjhsCalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frmZjhsCalculator.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		textPane.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textPane.setBackground(new Color(255, 248, 220));
		textPane.setBounds(10, 10, 466, 132);
		panel.add(textPane);
		/*——————textPane样式设置———————*/
		// 算式样式，设置字体为粗体，字号20
		StyleConstants.setBold(equationStyle, true);
		StyleConstants.setFontSize(equationStyle, 22);
		// 计算后样式，设置字体为粗体，字号18,灰色
		StyleConstants.setBold(calculatedStyle, true);
		StyleConstants.setFontSize(calculatedStyle, 20);
		StyleConstants.setForeground(calculatedStyle, Color.GRAY);
		// 添加键盘监听器
		textPane.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				// 获取按键对应的字符(ASCII code)
				char keyChar = e.getKeyChar();
				// switch语句处理多分支，效率高于if-else
				switch(keyChar){
				case '0' :
					addString("0");
					break; 
				case '1' :
					addString("1");
					break; 
				case '2' :
					addString("2");
					break; 
				case '3' :
					addString("3");
					break; 
				case '4' :
					addString("4");
					break; 
				case '5' :
					addString("5");
					break; 
				case '6' :
					addString("6");
					break; 
				case '7' :
					addString("7");
					break; 
				case '8' :
					addString("8");
					break; 
				case '9' :
					addString("9");
					break; 
				case '\b' :  // 退格
					deleteChar();
					break; 
				case '=' :
					executeButtonPressed();
					break; 
				case '+' :
					addString("+");
					break; 
				case '-' :
					addString("-");
					break; 
				case '*' :
					addString("×");
					break; 
				case '/' :
					addString("÷");
					break; 
				case '^' :
					addString("^");
				case '!' :
					addString("!");
				case '(' :
					addString("(");
				case ')' :
					addString(")");
					break; 
				case 72 :	// 上方向键的ASCII码
					upButtonPressed();
					break; 
				case 80 :	// 下方向键的ASCII码
					downButtonPressed();
					break; 
				case 75 :	// 左方向键的ASCII码
					backwardButtonPressed();
					break; 
				case 77 :	// 右方向键的ASCII码
					forwardButtonPressed();
					break; 
				}
				// 取消键盘事件，阻止输入
				e.consume(); 
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// 按键按下不做处理

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// 按键释放不做处理
			}
		});
		// 将textPane添加到scrollPane中，以支持滚动
		JScrollPane scrollPane = new JScrollPane(textPane);
		scrollPane.setBounds(10, 10, 466, 132);
		panel.add(scrollPane);

		JButton button1 = new JButton("1");
		button1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		// 输入按钮均使用lambda表达式创建匿名监听器，调用addString方法并将对应字符作为参数传入，执行添加
		button1.addActionListener(e -> addString("1"));
		button1.setBounds(10, 319, 60, 33);
		panel.add(button1);

		JButton button2 = new JButton("2");
		button2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		button2.addActionListener(e -> addString("2"));
		button2.setBounds(88, 319, 60, 33);
		panel.add(button2);

		JButton button3 = new JButton("3");
		button3.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		button3.addActionListener(e -> addString("3"));
		button3.setBounds(166, 319, 60, 33);
		panel.add(button3);

		JButton button4 = new JButton("4");
		button4.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		button4.addActionListener(e -> addString("4"));
		button4.setBounds(10, 384, 60, 33);
		panel.add(button4);

		JButton button5 = new JButton("5");
		button5.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		button5.addActionListener(e -> addString("5"));
		button5.setBounds(88, 384, 60, 33);
		panel.add(button5);

		JButton button6 = new JButton("6");
		button6.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		button6.addActionListener(e -> addString("6"));
		button6.setBounds(166, 384, 60, 33);
		panel.add(button6);

		JButton button7 = new JButton("7");
		button7.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		button7.addActionListener(e -> addString("7"));
		button7.setBounds(10, 446, 60, 33);
		panel.add(button7);

		JButton button8 = new JButton("8");
		button8.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		button8.addActionListener(e -> addString("8"));
		button8.setBounds(88, 446, 60, 33);
		panel.add(button8);

		JButton button9 = new JButton("9");
		button9.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		button9.addActionListener(e -> addString("9"));
		button9.setBounds(166, 446, 60, 33);
		panel.add(button9);

		JButton plusButton = new JButton("+");
		plusButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		plusButton.addActionListener(e -> addString("+"));
		plusButton.setBounds(246, 384, 60, 33);
		panel.add(plusButton);

		JButton minusButton = new JButton("-");
		minusButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		minusButton.addActionListener(e -> addString("-"));
		minusButton.setBounds(327, 384, 60, 33);
		panel.add(minusButton);

		JButton multiplyButton = new JButton("\u00D7");
		multiplyButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		multiplyButton.addActionListener(e -> addString("×"));
		multiplyButton.setBounds(246, 446, 60, 33);
		panel.add(multiplyButton);

		JButton divideButton = new JButton("\u00F7");
		divideButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		divideButton.addActionListener(e -> addString("÷"));
		divideButton.setBounds(327, 446, 60, 33);
		panel.add(divideButton);

		JButton btnNewButton = new JButton("!");
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnNewButton.addActionListener(e -> addString("!"));
		btnNewButton.setBounds(166, 507, 60, 33);
		panel.add(btnNewButton);

		JButton btnNewButton_14 = new JButton("(");
		btnNewButton_14.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnNewButton_14.addActionListener(e -> addString("("));
		btnNewButton_14.setBounds(246, 319, 60, 33);
		panel.add(btnNewButton_14);

		JButton btnNewButton_15 = new JButton(")");
		btnNewButton_15.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnNewButton_15.addActionListener(e -> addString(")"));
		btnNewButton_15.setBounds(327, 320, 60, 31);
		panel.add(btnNewButton_15);

		JButton executeButton = new JButton("=");
		executeButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		executeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executeButtonPressed();
			}
		});
		executeButton.setBounds(327, 507, 149, 33);
		panel.add(executeButton);

		JButton acButton = new JButton("AC");
		acButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
		acButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 将textPane与resultLabel的显示内容全部清空
				textPane.setText(null);
				resultLabel.setText(null);
				// 重置计算状态
				ManipulateEquation.reset();
			}
		});
		acButton.setBounds(408, 319, 68, 76);
		panel.add(acButton);

		JButton delButton = new JButton("DEL");
		delButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
		// 使用lambda表达式创建匿名监听器，指定调用deleteChar()方法来删除一个字符
		delButton.addActionListener(e -> deleteChar());
		delButton.setBounds(408, 405, 68, 74);
		panel.add(delButton);

		JButton button0 = new JButton("0");
		button0.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		button0.addActionListener(e -> addString("0"));
		button0.setBounds(10, 507, 60, 33);
		panel.add(button0);

		JButton backwardButton = new JButton("\u25C0");
		backwardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backwardButtonPressed();
			}
		});
		backwardButton.setBounds(125, 220, 60, 33);
		panel.add(backwardButton);

		JButton upButton = new JButton("\u25B2");
		upButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				upButtonPressed();
			}
		});
		upButton.setBounds(198, 177, 60, 33);
		panel.add(upButton);

		JButton forwardButton = new JButton("\u25B6");
		forwardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				forwardButtonPressed();
			}
		});
		forwardButton.setBounds(271, 220, 60, 33);
		panel.add(forwardButton);

		JButton downButton = new JButton("\u25BC");
		downButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				downButtonPressed();
			}
		});
		downButton.setBounds(198, 263, 60, 33);
		panel.add(downButton);

		JCheckBox checkBox1 = new JCheckBox("\u57FA\u7840\u8BA1\u7B97");
		checkBox1.setBounds(10, 148, 109, 23);
		panel.add(checkBox1);
		// 设置基础计算器为选中状态
		checkBox1.setSelected(true);

		JCheckBox checkBox2 = new JCheckBox("\u79D1\u5B66\u8BA1\u7B97");
		checkBox2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 通过ScienceCalculator类的主函数显示科学计算器界面
				ScienceCalculator.main(null);
				// 关闭当前窗体
				frmZjhsCalculator.setVisible(false);
			}
		});
		checkBox2.setBounds(117, 148, 109, 23);
		panel.add(checkBox2);

		// 将两个CheckBox添加到buttonGroup中，使得只有一个CheckBox可以被选中
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(checkBox1);
		buttonGroup.add(checkBox2);

		JButton dotButton = new JButton("\u00B7");
		dotButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		dotButton.addActionListener(e -> addString("."));
		dotButton.setBounds(88, 507, 60, 33);
		panel.add(dotButton);

		JButton btnNewButton_1 = new JButton("^");
		btnNewButton_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnNewButton_1.addActionListener(e -> addString("^"));
		btnNewButton_1.setBounds(246, 507, 60, 33);
		panel.add(btnNewButton_1);

		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(208, 106, 259, 36);
		// 将layeredPane添加到textPane中，优先级设置为0
		textPane.add(layeredPane, 0);
		resultLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		resultLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		resultLabel.setBounds(0, 0, 235, 15);
		// 将resultLabel添加到layeredPane中，优先级设置为1，使其显示在最上层
		layeredPane.add(resultLabel, 1);
	}

	/**
	 * addString方法
	 * <p>在textPane中追加字符串<br>
	 *
	 * @param string 要添加的字符串
	 * @return void 返回类型
	 */
	private void addString(String string) {
		StyledDocument doc = textPane.getStyledDocument();
		try {
			// 获取光标当前位置
			int caretPosition = textPane.getCaretPosition();  
			// 在光标位置追加字符串
			doc.insertString(caretPosition, string, null);
			// 将textPane的文字样式设置为equationStyle
			doc.setCharacterAttributes(0, doc.getLength(), equationStyle, true);
			// 清空resultLabel的内容
			resultLabel.setText(null);
			// 将焦点返回textPane，以便持续显示光标
			textPane.requestFocusInWindow();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * deleteChar方法
	 * <p>删除textPane中的一个字符<br>
	 * 参数
	 * @return void 返回类型
	 */
	private void deleteChar() {
		// 获取textPane的Document对象
		Document document = textPane.getDocument();  
		// 获取光标当前位置
		int caretPosition = textPane.getCaretPosition();  

		try {
			// 删除当前光标前的一个字符
			document.remove(caretPosition - 1, 1); 
			StyledDocument doc = textPane.getStyledDocument();
			// 将textPane的文字样式设置为equationStyle
			doc.setCharacterAttributes(0, doc.getLength(), equationStyle, true);
			// 清空计算结果显示
			resultLabel.setText(null);
			// 将焦点返回textPane，以便持续显示光标
			textPane.requestFocusInWindow();
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * executeButtonPressed方法
	 * <p>'='按键按下的方法，执行运算操作<br>
	 * 
	 * @return void 返回类型
	 */
	private void executeButtonPressed() {
		// 将算式字符串作为参数传入，执行计算
		Double result = ManipulateEquation.calculate(textPane.getText());
		/* 当计算结果为NaN时，清空resultLabel并在textPane中显示"数学错误"
		 * 计算结果不为NaN时，将结果显示在resultLabel*/
		if (Double.isNaN(result)) {
			resultLabel.setText(null);
			textPane.setText("数学错误");
		}
		else {
			resultLabel.setText(String.valueOf(result));
		}
		StyledDocument doc = textPane.getStyledDocument();
		// 将textPane的文字样式设置为calculatedStyle
		doc.setCharacterAttributes(0, doc.getLength(), calculatedStyle, true);
		// 获取Caret对象
		Caret caret = textPane.getCaret();
		// 获取算式总长度
		int endPosition = textPane.getDocument().getLength(); 
		// 将光标移动到算式末尾，以便后续计算
		caret.setDot(endPosition);
	}

	/**
	 * backwardButtonPressed方法
	 * <p>左方向键按下后光标后退一个字符<br>
	 * 
	 * @return void 返回类型
	 */
	private void backwardButtonPressed() {
		// 获取Caret对象
		Caret caret = textPane.getCaret();
		// 获取光标当前位置
		int caretPosition = textPane.getCaretPosition();  
		// 光标左移一位
		caret.setDot(caretPosition - 1);  
		// 将焦点返回textPane，以便持续显示光标
		textPane.requestFocusInWindow();
		StyledDocument doc = textPane.getStyledDocument();
		// 将textPane的文字样式设置为equationStyle
		doc.setCharacterAttributes(0, doc.getLength(), equationStyle, true);
		// 清空resultLabel的内容
		resultLabel.setText(null);
	}

	/**
	 * forwardButtonPressed方法
	 * <p>右方向键按下后光标前进一个字符<br>
	 * 
	 * @return void 返回类型
	 */
	private void forwardButtonPressed() {
		// 获取Caret对象
		Caret caret = textPane.getCaret();
		// 获取光标当前位置
		int caretPosition = textPane.getCaretPosition();  
		// 光标右移一位
		caret.setDot(caretPosition + 1);
		// 将焦点返回textPane，以便持续显示光标
		textPane.requestFocusInWindow();
		StyledDocument doc = textPane.getStyledDocument();
		// 将textPane的文字样式设置为equationStyle
		doc.setCharacterAttributes(0, doc.getLength(), equationStyle, true);
		// 清空resultLabel的内容
		resultLabel.setText(null);
	}

	/**
	 * upButtonPressed方法
	 * <p>上方向键按下后，光标移动到该行上一行<br>
	 * 
	 * @return void 返回类型
	 */
	private void upButtonPressed() {
		// 获取光标当前位置
		int caretPosition = textPane.getCaretPosition();  
		try {
			// 获取光标当前行的起始位置
			int startOfCurrentLine = Utilities.getRowStart(textPane, caretPosition);
			// 计算光标当前位置与当前行起始位置的差值
			int deviation = caretPosition - startOfCurrentLine;
			// 获取光标所在行上一行的起始位置
			int startOfPreviousLine = Utilities.getRowStart(textPane, startOfCurrentLine - 1);
			// 设置光标位置为上一行起始位置 + 偏移值，即当前光标上方的位置
			textPane.setCaretPosition(startOfPreviousLine + deviation);
		} catch (BadLocationException ex) {
			// 不做处理
		}
		// 将焦点返回textPane，以便持续显示光标
		textPane.requestFocusInWindow(); 
		StyledDocument doc = textPane.getStyledDocument();
		// 将textPane的文字样式设置为equationStyle
		doc.setCharacterAttributes(0, doc.getLength(), equationStyle, true);
		// 清空resultLabel的内容
		resultLabel.setText(null);
	}

	private void downButtonPressed() {
		// 获取光标当前位置
		int caretPosition = textPane.getCaretPosition();  
		try {
			// 获取光标当前行的起始位置
			int startOfCurrentLine = Utilities.getRowStart(textPane, caretPosition);
			// 计算光标当前位置与当前行起始位置的差值
			int deviation = caretPosition - startOfCurrentLine;
			// 获取光标当前行的结束位置
			int endOfCurrentLine = Utilities.getRowEnd(textPane, caretPosition);         
			// 获取光标所在行下一行的起始位置
			int startOfNextLine = Utilities.getRowStart(textPane, endOfCurrentLine + 1);
			// 获取算式总长度
			int endPosition = textPane.getDocument().getLength(); 
			// 判断偏差值是否小于下一行的长度
			if(deviation < endPosition - startOfNextLine) {
				// 设置光标位置为下一行起始位置 + 偏移值，即当前光标下方的位置
				textPane.setCaretPosition(startOfNextLine + deviation);
			}
			else {
				// 若下一行长度小于偏差值，则将光标移动到算式末尾
				textPane.setCaretPosition(endPosition);
			}
		} catch (BadLocationException ex) {
			// 不做处理
		}
		// 将焦点返回textPane，以便持续显示光标
		textPane.requestFocusInWindow();
		StyledDocument doc = textPane.getStyledDocument();
		// 将textPane的文字样式设置为equationStyle
		doc.setCharacterAttributes(0, doc.getLength(), equationStyle, true);
		// 清空resultLabel的内容
		resultLabel.setText(null);
	}
}
