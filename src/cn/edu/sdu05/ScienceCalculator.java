package cn.edu.sdu05;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.Utilities;
import org.mariuszgromada.math.mxparser.License;
import com.formdev.flatlaf.intellijthemes.FlatSolarizedLightIJTheme;
import cn.edu.sdu05B.CalculatePattern;
import cn.edu.sdu05B.CalculatorPattern;
import cn.edu.sdu05B.ConvertSystemPattern;
import cn.edu.sdu05B.EquationPattern;
import cn.edu.sdu05B.PlotFunctionPattern;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingConstants;

/**
 * ScienceCalculator类的简介
 * <p>TODO(这里用一句话描述这个类的作用)<br>
 *
 * @author Zhang Jinghui
 * <p>created on 2023/11/23<br>
 */
public class ScienceCalculator {
	// 初始模式为计算模式
	CalculatorPattern currentPattern = new CalculatePattern();

	JFrame frmZjhsCalculator;
	// 类控件，JTextPane
	public static JTextPane textPane = new JTextPane();
	// 算式样式，作为算式输入时的显示样式
	Style equationStyle = textPane.addStyle("equationStyle", null);   
	// 计算后样式，点击'='按钮计算后，原算式改为灰色并缩小
	Style calculatedStyle = textPane.addStyle("calculatedStyle", null);
	// resultLabel用于显示计算结果
	public static JLabel resultLabel = new JLabel("");
	// 表征是否处于SHIFT状态
	private boolean isShift;
	// 在功能菜单中用于表征是否处于模式转换状态
	private boolean isSwitch = false;
	// 用于表征模式转换时是否进入二级菜单
	private boolean secondIsSwitch = false;
	JLabel isShiftLabel = new JLabel("");

	/**
	 * 创建窗体
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {	
			public void run() {
				try {
					ScienceCalculator window = new ScienceCalculator();
					//在屏幕中央显示窗体
					window.frmZjhsCalculator.setLocationRelativeTo(null);
					window.frmZjhsCalculator.setVisible(true);
					/*确认mXparser包为非商用*/
					License.iConfirmNonCommercialUse("Zhang Jinghui");
					// 对当前模式进行初始化
					window.currentPattern.initialize();
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
	 * 构造函数
	 */
	public ScienceCalculator() {
		initialize();
	}

	/**
	 * 初始化窗体内容
	 */
	private void initialize() {
		frmZjhsCalculator = new JFrame();
		frmZjhsCalculator.setResizable(false);
		frmZjhsCalculator.setTitle("ZJH‘s Calculator");
		frmZjhsCalculator.setBounds(100, 100, 500, 696);
		frmZjhsCalculator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frmZjhsCalculator.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JCheckBox checkBox1 = new JCheckBox("\u57FA\u7840\u8BA1\u7B97");
		checkBox1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 通过BasicCalculator类的主函数进入基础计算界面
				BasicCalculator.main(null);
				// 关闭当前窗体
				frmZjhsCalculator.setVisible(false);
			}
		});
		checkBox1.setBounds(10, 133, 101, 23);
		panel.add(checkBox1);

		JCheckBox checkBox2 = new JCheckBox("科学计算");
		checkBox2.setBounds(113, 133, 109, 23);
		panel.add(checkBox2);
		checkBox2.setSelected(true);;
		textPane.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		textPane.setBackground(new Color(255, 248, 220));
		textPane.setBounds(10, 10, 466, 117);
		panel.add(textPane);
		/*---------textPane样式设置----------*/
		// 算式样式，设置字体为粗体，字号20
		StyleConstants.setBold(equationStyle, true);
		StyleConstants.setFontSize(equationStyle, 22);
		// 计算后样式，设置字体为粗体，字号18,灰色
		StyleConstants.setBold(calculatedStyle, true);
		StyleConstants.setFontSize(calculatedStyle, 20);
		StyleConstants.setForeground(calculatedStyle, Color.GRAY);
		// 设置初始样式为算式样式
		StyledDocument doc = textPane.getStyledDocument();
		doc.setCharacterAttributes(0, 0, equationStyle, true);
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
					button1Pressed();
					break; 
				case '2' :
					button2Pressed();
					break; 
				case '3' :
					button3Pressed();
					break; 
				case '4' :
					button4Pressed();
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
					addString("/");
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
		scrollPane.setBounds(10, 10, 466, 117);
		panel.add(scrollPane);

		JButton upButton = new JButton("▲");
		upButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				upButtonPressed();
			}
		});
		upButton.setBounds(205, 162, 59, 23);
		panel.add(upButton);

		JButton backwardButton = new JButton("◀");
		backwardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backwardButtonPressed();
			}
		});
		backwardButton.setBounds(143, 187, 59, 23);
		panel.add(backwardButton);

		JButton forwardButton = new JButton("▶");
		forwardButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				forwardButtonPressed();
			}
		});
		forwardButton.setBounds(267, 187, 59, 23);
		panel.add(forwardButton);

		JButton downButton = new JButton("▼");
		downButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				downButtonPressed();
			}
		});
		downButton.setBounds(205, 211, 59, 23);
		panel.add(downButton);

		JButton xButton = new JButton("x");
		xButton.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 18));
		xButton.setBounds(330, 244, 67, 23);
		panel.add(xButton);
		xButton.addActionListener(e -> addString("x"));

		JButton button1 = new JButton("1");
		button1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		button1.setBounds(10, 547, 73, 30);
		panel.add(button1);
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				button1Pressed();
			};
		});

		JButton button2 = new JButton("2");
		button2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		button2.setBounds(105, 547, 73, 30);
		panel.add(button2);
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button2Pressed();
			};
		});

		JButton button3 = new JButton("3");
		button3.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		button3.setBounds(200, 547, 73, 30);
		panel.add(button3);
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button3Pressed();
			};
		});

		JButton button4 = new JButton("4");
		button4.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		button4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				button4Pressed();
			};
		});
		button4.setBounds(10, 494, 73, 30);
		panel.add(button4);

		JButton button5 = new JButton("5");
		button5.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		button5.setBounds(105, 494, 73, 30);
		panel.add(button5);
		button5.addActionListener(e -> addString("5", "E"));

		JButton button6 = new JButton("6");
		button6.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		button6.setBounds(200, 494, 73, 30);
		panel.add(button6);
		button6.addActionListener(e -> addString("6", "F"));

		JButton button7 = new JButton("7");
		button7.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		button7.setBounds(10, 441, 73, 30);
		panel.add(button7);
		button7.addActionListener(e -> addString("7"));

		JButton button8 = new JButton("8");
		button8.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		button8.setBounds(105, 441, 73, 30);
		panel.add(button8);
		button8.addActionListener(e -> addString("8"));

		JButton button9 = new JButton("9");
		button9.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		button9.setBounds(200, 441, 73, 30);
		panel.add(button9);
		button9.addActionListener(e -> addString("9"));

		JButton button0 = new JButton("0");
		button0.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		button0.setBounds(10, 600, 73, 30);
		panel.add(button0);
		button0.addActionListener(e -> addString("0"));

		JButton plusButton = new JButton("+");
		plusButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		plusButton.setBounds(295, 547, 86, 30);
		panel.add(plusButton);
		plusButton.addActionListener(e -> addString("+"));

		JButton minusButton = new JButton("-");
		minusButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		minusButton.setBounds(390, 547, 86, 30);
		panel.add(minusButton);
		minusButton.addActionListener(e -> addString("-"));

		JButton multiplyButton = new JButton("\u00D7");
		multiplyButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		multiplyButton.setBounds(295, 494, 86, 30);
		panel.add(multiplyButton);
		multiplyButton.addActionListener(e -> addString("×"));

		JButton btnNewButton_30 = new JButton("(");
		btnNewButton_30.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnNewButton_30.addActionListener(e -> addString("(", "sec("));
		btnNewButton_30.setBounds(248, 350, 67, 23);
		panel.add(btnNewButton_30);

		JButton switchButton = new JButton("\u529F\u80FD\u83DC\u5355");
		switchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 清空textPane与resultLabel
				textPane.setText(null);
				resultLabel.setText(null);
				// 加载菜单
				textPane.setText("按数字键选择工作模式\n1: 数值计算\n2: 方程求解\n3: 进制转换\n4: 函数绘图");
				// 设置初始样式为算式样式
				StyledDocument doc = textPane.getStyledDocument();
				doc.setCharacterAttributes(0, doc.getLength(), equationStyle, true);
				// 更新标志,表征正处于模式切换状态
				isSwitch = true; 
				// 光标返回textPane
				textPane.requestFocus();
			}
		});
		switchButton.setBounds(367, 142, 109, 30);
		panel.add(switchButton);

		JButton integralButton = new JButton("\u222B");
		integralButton.setFont(new Font("Sylfaen", Font.BOLD | Font.ITALIC, 15));
		integralButton.setBounds(10, 246, 67, 23);
		panel.add(integralButton);
		integralButton.addActionListener(e -> addString("∫(expression, x, from, to)")); 

		JButton derivativeButton = new JButton("\u2202");
		derivativeButton.setFont(new Font("Sylfaen", Font.BOLD, 15));
		derivativeButton.setBounds(90, 246, 67, 23);
		panel.add(derivativeButton);
		derivativeButton.addActionListener(e -> addString("der(expression, x, point)")); 

		JButton sumButton = new JButton("\u2211");
		sumButton.setFont(new Font("Sylfaen", Font.BOLD, 15));
		sumButton.setBounds(171, 244, 67, 23);
		panel.add(sumButton);
		sumButton.addActionListener(e -> addString("∑(x, from, to, function)")); 

		JButton productButton = new JButton("\u220F");
		productButton.setFont(new Font("Sylfaen", Font.BOLD, 15));
		productButton.setBounds(248, 244, 67, 23);
		panel.add(productButton);
		productButton.addActionListener(e -> addString("∏(x, from, to, function)")); 

		JButton lgButton = new JButton("lg");
		lgButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lgButton.setBounds(409, 244, 67, 23);
		panel.add(lgButton);
		lgButton.addActionListener(e -> addString("lg(", "log2("));

		JButton lnButton = new JButton("ln");
		lnButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lnButton.setBounds(10, 297, 67, 23);
		panel.add(lnButton);
		lnButton.addActionListener(e -> addString("ln(", "e"));

		JButton sinButton = new JButton("sin");
		sinButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		sinButton.setBounds(90, 296, 67, 23);
		panel.add(sinButton);
		sinButton.addActionListener(e -> addString("sin(", "arcsin("));

		JButton cosButton = new JButton("cos");
		cosButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		cosButton.setBounds(171, 297, 67, 23);
		panel.add(cosButton);
		cosButton.addActionListener(e -> addString("cos(", "arccos("));

		JButton tanButton = new JButton("tan");
		tanButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		tanButton.setBounds(248, 297, 67, 23);
		panel.add(tanButton);
		tanButton.addActionListener(e -> addString("tan(", "arctan("));

		JButton dotButton = new JButton("\u00B7");
		dotButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		dotButton.setBounds(105, 600, 73, 30);
		panel.add(dotButton);
		dotButton.addActionListener(e -> addString(".", "π"));

		JButton squareRootButton = new JButton("√x");
		squareRootButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		squareRootButton.setBounds(330, 297, 67, 23);
		panel.add(squareRootButton);
		squareRootButton.addActionListener(e -> addString("√","root(n,x)"));

		JButton divideButton = new JButton("\u00F7");
		divideButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		divideButton.setBounds(390, 494, 86, 30);
		panel.add(divideButton);
		divideButton.addActionListener(e -> addString("÷"));

		JButton executeButton = new JButton("=");
		executeButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		executeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executeButtonPressed();
			}
		});
		executeButton.setBounds(295, 600, 181, 30);
		panel.add(executeButton);

		JButton acButton = new JButton("AC");
		acButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
		acButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 调用当前模式的initialize方法进行初始化
				currentPattern.initialize();
				// 设置初始样式为算式样式
				StyledDocument doc = textPane.getStyledDocument();
				doc.setCharacterAttributes(0, doc.getLength(), equationStyle, true);
			}
		});
		acButton.setBounds(390, 441, 86, 30);
		panel.add(acButton);

		JButton delButton = new JButton("DEL");
		delButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
		// 使用lambda表达式创建匿名监听器，指定调用deleteChar()方法来删除一个字符
		delButton.addActionListener(e -> deleteChar());
		delButton.setBounds(295, 441, 86, 30);
		panel.add(delButton);

		JButton btnNewButton = new JButton(")");
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnNewButton.addActionListener(e -> addString(")", "csc("));
		btnNewButton.setBounds(330, 350, 67, 23);
		panel.add(btnNewButton);

		JButton shiftButton = new JButton("SHIFT");
		shiftButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		shiftButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 点击按钮切换isShift的bool值
				if(isShift) {
					isShift = false;
					isShiftLabel.setText("");
				}
				else {
					isShift = true;
					isShiftLabel.setText("Shifting");
				}
				// 光标返回textPane
				textPane.requestFocus();
			}
		});
		shiftButton.setBounds(10, 198, 93, 23);
		panel.add(shiftButton);
		isShiftLabel.setFont(new Font("Monotype Corsiva", Font.BOLD | Font.ITALIC, 18));
		isShiftLabel.setForeground(new Color(255, 0, 255));
		isShiftLabel.setHorizontalAlignment(SwingConstants.CENTER);

		isShiftLabel.setBounds(10, 173, 82, 23);
		panel.add(isShiftLabel);

		JButton btnNewButton_1 = new JButton("HEX");
		btnNewButton_1.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentPattern.displayHex();
				StyledDocument doc = textPane.getStyledDocument();
				// 将textPane的文字样式设置为算式样式
				doc.setCharacterAttributes(0, doc.getLength(), equationStyle, true);
			}
		});
		btnNewButton_1.setBounds(248, 403, 67, 23);
		panel.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("OCT");
		btnNewButton_2.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentPattern.displayOct();
				StyledDocument doc = textPane.getStyledDocument();
				// 将textPane的文字样式设置为算式样式
				doc.setCharacterAttributes(0, doc.getLength(), equationStyle, true);
			}
		});
		btnNewButton_2.setBounds(90, 403, 67, 23);
		panel.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("DEC");
		btnNewButton_3.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentPattern.displayDec();
				StyledDocument doc = textPane.getStyledDocument();
				// 将textPane的文字样式设置为算式样式
				doc.setCharacterAttributes(0, doc.getLength(), equationStyle, true);
			}
		});
		btnNewButton_3.setBounds(171, 403, 67, 23);
		panel.add(btnNewButton_3);

		JButton btnNewButton_4 = new JButton("BIN");
		btnNewButton_4.setFont(new Font("Times New Roman", Font.PLAIN, 10));
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentPattern.displayBin();
				StyledDocument doc = textPane.getStyledDocument();
				// 将textPane的文字样式设置为算式样式
				doc.setCharacterAttributes(0, doc.getLength(), equationStyle, true);
			}
		});
		btnNewButton_4.setBounds(10, 403, 67, 23);
		panel.add(btnNewButton_4);

		JLabel lblNewLabel = new JLabel("arcsin");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel.setForeground(new Color(255, 0, 255));
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setBounds(90, 279, 58, 15);
		panel.add(lblNewLabel);

		JLabel lblArccos = new JLabel("arccos");
		lblArccos.setForeground(new Color(255, 0, 255));
		lblArccos.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblArccos.setBounds(171, 279, 58, 15);
		panel.add(lblArccos);

		JLabel lblArctan = new JLabel("arctan");
		lblArctan.setForeground(new Color(255, 0, 255));
		lblArctan.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblArctan.setBounds(253, 279, 58, 15);
		panel.add(lblArctan);

		JLabel lblx = new JLabel("ⁿ√x");
		lblx.setForeground(new Color(255, 0, 255));
		lblx.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblx.setBounds(330, 279, 58, 15);
		panel.add(lblx);

		JLabel lblArctan_1_1 = new JLabel("e");
		lblArctan_1_1.setForeground(new Color(255, 0, 255));
		lblArctan_1_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		lblArctan_1_1.setBounds(10, 279, 58, 15);
		panel.add(lblArctan_1_1);

		JButton btnNewButton_5 = new JButton("x!");
		btnNewButton_5.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 18));
		btnNewButton_5.addActionListener(e -> addString("!", "C(m,n)"));
		btnNewButton_5.setBounds(409, 297, 67, 23);
		panel.add(btnNewButton_5);

		JLabel lblCmn = new JLabel("C(m,n)");
		lblCmn.setForeground(new Color(255, 0, 255));
		lblCmn.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblCmn.setBounds(409, 279, 58, 15);
		panel.add(lblCmn);

		JButton btnNewButton_6 = new JButton("abs");
		btnNewButton_6.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnNewButton_6.addActionListener(e -> addString("abs(", "sinh("));
		btnNewButton_6.setBounds(10, 350, 67, 23);
		panel.add(btnNewButton_6);

		JButton btnNewButton_7 = new JButton("^");
		btnNewButton_7.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnNewButton_7.addActionListener(e -> addString("^", "tanh("));
		btnNewButton_7.setBounds(171, 350, 67, 23);
		panel.add(btnNewButton_7);

		JButton btnNewButton_8 = new JButton("/");
		btnNewButton_8.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnNewButton_8.addActionListener(e -> addString("/", "cosh("));
		btnNewButton_8.setBounds(90, 350, 67, 23);
		panel.add(btnNewButton_8);

		JButton btnNewButton_9 = new JButton("rad");
		btnNewButton_9.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnNewButton_9.addActionListener(e -> addString("rad(", "deg("));
		btnNewButton_9.setBounds(200, 600, 73, 30);
		panel.add(btnNewButton_9);

		JButton btnNewButton_10 = new JButton("max");
		btnNewButton_10.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		btnNewButton_10.addActionListener(e -> addString("max(", "min("));
		btnNewButton_10.setBounds(330, 403, 67, 23);
		panel.add(btnNewButton_10);

		JButton btnNewButton_11 = new JButton(",");
		btnNewButton_11.setFont(new Font("Times New Roman", Font.BOLD, 15));
		btnNewButton_11.addActionListener(e -> addString(",", "%"));
		btnNewButton_11.setBounds(409, 403, 67, 23);
		panel.add(btnNewButton_11);

		JLabel lblArccos_1 = new JLabel("进制转换");
		lblArccos_1.setFont(new Font("宋体", Font.PLAIN, 12));
		lblArccos_1.setForeground(new Color(0, 0, 255));
		lblArccos_1.setBounds(10, 383, 82, 15);
		panel.add(lblArccos_1);

		JButton btnNewButton_11_1 = new JButton("exp");
		btnNewButton_11_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		btnNewButton_11_1.addActionListener(e -> addString("exp(", "cot("));
		btnNewButton_11_1.setBounds(409, 350, 67, 23);
		panel.add(btnNewButton_11_1);

		JLabel lblDeg = new JLabel("deg");
		lblDeg.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblDeg.setForeground(new Color(255, 0, 255));
		lblDeg.setBackground(Color.WHITE);
		lblDeg.setBounds(200, 580, 58, 15);
		panel.add(lblDeg);

		JLabel lblLog = new JLabel("log₂");
		lblLog.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblLog.setForeground(new Color(255, 0, 255));
		lblLog.setBackground(Color.WHITE);
		lblLog.setBounds(409, 225, 58, 15);
		panel.add(lblLog);

		JLabel lblLog_1 = new JLabel("≈");
		lblLog_1.setForeground(new Color(255, 0, 255));
		lblLog_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblLog_1.setBackground(Color.WHITE);
		lblLog_1.setBounds(295, 580, 58, 15);
		panel.add(lblLog_1);

		JLabel lblSinh = new JLabel("sinh");
		lblSinh.setForeground(new Color(255, 0, 255));
		lblSinh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblSinh.setBounds(10, 330, 58, 15);
		panel.add(lblSinh);

		JLabel lblCosh = new JLabel("cosh");
		lblCosh.setForeground(new Color(255, 0, 255));
		lblCosh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblCosh.setBounds(90, 330, 58, 15);
		panel.add(lblCosh);

		JLabel lblTanh = new JLabel("tanh");
		lblTanh.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblTanh.setForeground(new Color(255, 0, 255));
		lblTanh.setBounds(171, 330, 58, 15);
		panel.add(lblTanh);

		JLabel lblDeg_1 = new JLabel("π");
		lblDeg_1.setForeground(new Color(255, 0, 255));
		lblDeg_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		lblDeg_1.setBackground(Color.WHITE);
		lblDeg_1.setBounds(105, 580, 58, 15);
		panel.add(lblDeg_1);

		JLabel lblSec = new JLabel("sec");
		lblSec.setForeground(new Color(255, 0, 255));
		lblSec.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblSec.setBounds(248, 330, 58, 15);
		panel.add(lblSec);

		JLabel lblCsc = new JLabel("csc");
		lblCsc.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblCsc.setForeground(new Color(255, 0, 255));
		lblCsc.setBounds(330, 330, 58, 15);
		panel.add(lblCsc);

		JLabel lblCot = new JLabel("cot");
		lblCot.setForeground(new Color(255, 0, 255));
		lblCot.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblCot.setBounds(409, 330, 58, 15);
		panel.add(lblCot);

		JLabel lblMin = new JLabel("min");
		lblMin.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblMin.setForeground(new Color(255, 0, 255));
		lblMin.setBounds(330, 383, 58, 15);
		panel.add(lblMin);

		JLabel lblMin_1 = new JLabel("%");
		lblMin_1.setForeground(new Color(255, 0, 255));
		lblMin_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblMin_1.setBounds(409, 383, 58, 15);
		panel.add(lblMin_1);

		JLabel lblDeg_1_1 = new JLabel("A");
		lblDeg_1_1.setForeground(new Color(255, 0, 255));
		lblDeg_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblDeg_1_1.setBackground(Color.WHITE);
		lblDeg_1_1.setBounds(10, 530, 58, 15);
		panel.add(lblDeg_1_1);

		JLabel lblDeg_1_1_1 = new JLabel("B");
		lblDeg_1_1_1.setForeground(new Color(255, 0, 255));
		lblDeg_1_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblDeg_1_1_1.setBackground(Color.WHITE);
		lblDeg_1_1_1.setBounds(105, 530, 58, 15);
		panel.add(lblDeg_1_1_1);

		JLabel lblDeg_1_1_2 = new JLabel("C");
		lblDeg_1_1_2.setForeground(new Color(255, 0, 255));
		lblDeg_1_1_2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblDeg_1_1_2.setBackground(Color.WHITE);
		lblDeg_1_1_2.setBounds(200, 530, 58, 15);
		panel.add(lblDeg_1_1_2);

		JLabel lblDeg_1_1_3 = new JLabel("D");
		lblDeg_1_1_3.setForeground(new Color(255, 0, 255));
		lblDeg_1_1_3.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblDeg_1_1_3.setBackground(Color.WHITE);
		lblDeg_1_1_3.setBounds(10, 477, 58, 15);
		panel.add(lblDeg_1_1_3);

		JLabel lblDeg_1_1_4 = new JLabel("E");
		lblDeg_1_1_4.setForeground(new Color(255, 0, 255));
		lblDeg_1_1_4.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblDeg_1_1_4.setBackground(Color.WHITE);
		lblDeg_1_1_4.setBounds(105, 477, 58, 15);
		panel.add(lblDeg_1_1_4);

		JLabel lblDeg_1_1_5 = new JLabel("F");
		lblDeg_1_1_5.setForeground(new Color(255, 0, 255));
		lblDeg_1_1_5.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblDeg_1_1_5.setBackground(Color.WHITE);
		lblDeg_1_1_5.setBounds(200, 477, 58, 15);
		panel.add(lblDeg_1_1_5);

		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(265, 88, 205, 39);
		// 将layeredPane添加到textPane中
		textPane.add(layeredPane, 0);
		resultLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		resultLabel.setBounds(0, 0, 180, 23);
		resultLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
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
		// 按下按钮后，均将isShift置为false
		isShift = false;
		isShiftLabel.setText("");
	}

	/**
	 * addString方法
	 * <p>在textPane中追加字符串。当isShift为{@code true}时追加string2
	 * ,为{@code false}时追加string1<br>
	 *
	 * @param string1 isShift为假时添加的字符串
	 * @param string2 isShift为真时添加的字符串
	 * @return void 返回类型
	 */
	private void addString(String string1, String string2) {
		StyledDocument doc = textPane.getStyledDocument();
		try {
			// 获取光标当前位置
			int caretPosition = textPane.getCaretPosition();  
			if (isShift) {
				// 若isShift为真，则在光标位置追加字符串string2
				doc.insertString(caretPosition, string2, null);
				// 结束后将isShift置为false
				isShift = false;
				isShiftLabel.setText("");
			}
			else {
				// isShift为假时，在光标位置追加字符串string1
				doc.insertString(caretPosition, string1, null);
			}
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
		// 将isShift置为false，关闭SHIFT状态
		isShift = false;
	}

	/**
	 * forwardButtonPressed方法
	 * <p>光标前移<br>
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
		// 清空resultLabel
		resultLabel.setText(null);
	}

	/**
	 * backwardButtonPressed方法
	 * <p>光标后移<br>
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
	 * downButtonPressed方法
	 * <p>光标下移<br>
	 * 参数
	 * @return void 返回类型
	 */
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
			// 获取光标在该行下一行的起始位置
			int startOfNextLine = Utilities.getRowStart(textPane, endOfCurrentLine + 1);
			// 获取算式总长度
			int endPosition = textPane.getDocument().getLength(); 
			// 判断偏差值是否小于下一行的长度
			if(deviation < endPosition - startOfNextLine) {
				// 设置光标位置为下一行起始位+偏移值，即当前光标下方的位置
				textPane.setCaretPosition(startOfNextLine + deviation);
			}
			else {
				// 若下一行长度小于偏差，则将光标移动到算式末尾
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

	/**
	 * upButtonPressed方法
	 * <p>光标上移<br>
	 * 
	 * @return void 返回类型
	 */
	private void upButtonPressed() {
		// 获取光标当前位置
		int caretPosition = textPane.getCaretPosition();  
		try {
			// 获取光标当前行的起始位置
			int startOfCurrentLine = Utilities.getRowStart(textPane, caretPosition);
			// 计算光标当前位置与当前行起始位置的插值
			int deviation = caretPosition - startOfCurrentLine;
			// 获取光标在该行上一行的起始位置
			int startOfPreviousLine = Utilities.getRowStart(textPane, startOfCurrentLine - 1);
			// 设置光标位置为上一行起始位 + 偏移值，即当前光标上方的位置
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

	/**
	 * button1Pressed方法
	 * <p>按键1按下<br>
	 * 
	 * @return void 返回类型
	 */
	private void button1Pressed() {
		if(isSwitch) {
			currentPattern = new CalculatePattern();
			currentPattern.initialize();
			// 设置初始样式为算式样式
			StyledDocument doc = textPane.getStyledDocument();
			doc.setCharacterAttributes(0, doc.getLength(), equationStyle, true);
			isSwitch = false;
		}
		else if (secondIsSwitch) {
			// 更新当前模式为二元一次方程求解，并初始化界面
			currentPattern = new EquationPattern.SolveLinearEquationPattern();
			currentPattern.initialize();
			// 设置初始样式为算式样式
			StyledDocument doc = textPane.getStyledDocument();
			doc.setCharacterAttributes(0, doc.getLength(), equationStyle, true);
			// 更新secondIsSwitch标志，退出二级菜单
			secondIsSwitch = false;
		}
		else {
			addString("1", "A");
		}
	}

	/**
	 * button2Pressed方法
	 * <p>按键2按下<br>
	 * 
	 * @return void 返回类型
	 */
	private void button2Pressed() {
		if(isSwitch) {
			textPane.setText("1: 二元一次方程组\n2: 一元二次方程");
			// 设置初始样式为算式样式
			StyledDocument doc = textPane.getStyledDocument();
			doc.setCharacterAttributes(0, doc.getLength(), equationStyle, true);
			// 进入二级菜单，选择要求解方程的类别，更新secondIsSwitch
			secondIsSwitch = true;
			// 关闭isSwitch状态
			isSwitch = false;
		}
		else if (secondIsSwitch) {
			// 更新当前模式为一元二次方程求解，并初始化界面
			currentPattern = new EquationPattern.SolveEquationPattern();
			currentPattern.initialize();
			// 设置初始样式为算式样式
			StyledDocument doc = textPane.getStyledDocument();
			doc.setCharacterAttributes(0, doc.getLength(), equationStyle, true);
			secondIsSwitch = false;
		}
		else {
			addString("2", "B");
		}
	}

	/**
	 * button3Pressed方法
	 * <p>按键3按下<br>
	 * 
	 * @return void 返回类型
	 */
	private void button3Pressed() {
		if(isSwitch) {
			currentPattern = new ConvertSystemPattern();
			currentPattern.initialize();
			// 设置初始样式为算式样式
			StyledDocument doc = textPane.getStyledDocument();
			doc.setCharacterAttributes(0, doc.getLength(), equationStyle, true);
			isSwitch = false;
		}
		else {
			addString("3", "C");
		}
	}

	/**
	 * button4Pressed方法
	 * <p>按键4按下<br>
	 * 
	 * @return void 返回类型
	 */
	private void button4Pressed() {
		if(isSwitch) {
			currentPattern = new PlotFunctionPattern();
			currentPattern.initialize();
			// 设置初始样式为算式样式
			StyledDocument doc = textPane.getStyledDocument();
			doc.setCharacterAttributes(0, doc.getLength(), equationStyle, true);
			isSwitch = false;
		}
		else {
			addString("4", "D");
		}
	}

	/**
	 * executeButtonPressed方法
	 * <p>'='按键按下的方法，执行运算操作<br>
	 * 
	 * @return void 返回类型
	 */
	private void executeButtonPressed() {
		if(isShift) {
			String result = resultLabel.getText();
			// 使用String.format方法保留两位小数并显示
			result = String.format("%.2f", Double.valueOf(result));
			resultLabel.setText(result);
		}
		else {
			currentPattern.execute();
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
	}
}
