package cn.edu.sdu05;

import javax.swing.*;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.License;
import java.awt.*;

/**
 * PlotFunction类的简介
 * <p>函数绘图类<br>
 *
 * @author Zhang Jinghui
 * <p>created on 2023/11/16<br>
 */
@SuppressWarnings("serial")
public class PlotFunction extends JFrame {
	private static final int WIDTH = 800;  // 窗口宽度
	private static final int HEIGHT = 600; // 窗口高度
	private static final double X_MIN = -5.0; // x轴最小值
	private static final double X_MAX = 5.0;  // x轴最大值
	private static final double Y_MIN = -5.0; // y轴最小值
	private static final double Y_MAX = 5.0;  // y轴最大值
	private static final int NUM_POINTS = 5000; // 绘制点数
	// 初始化函数表达式
	String function = null; 

	/**
	 * 创建一个新的实例 PlotFunction
	 *
	 * @param function 函数表达式
	 */
	public PlotFunction(String function) {
		this.function = function;
		setTitle("函数绘图"); // 设置窗口标题
		setSize(WIDTH, HEIGHT); // 设置窗口大小
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭
		JPanel plotPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				// 绘制网格
				drawGrid(g);
				// 绘制坐标轴
				drawAxis(g);
				// 绘制函数曲线和标签
				drawFunction(g);
			}
		};
		// 将绘图面板设置为内容面板
		setContentPane(plotPanel); 
		//在屏幕中央显示窗体
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * drawGrid方法
	 * <p>绘制网格线<br>
	 *
	 * @param g 参数
	 * @return void 返回类型
	 */
	private void drawGrid(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		// 绘制垂直网格线
		double xStep = (X_MAX - X_MIN) / 10.0;
		double x = X_MIN;
		int xPixelStep = WIDTH / 10;
		for (int i = 0; i <= 10; i++) {
			int xPixel = i * xPixelStep;
			g.drawLine(xPixel, 0, xPixel, HEIGHT);
			x += xStep;
		}
		// 绘制水平网格线
		double yStep = (Y_MAX - Y_MIN) / 10.0;
		double y = Y_MIN;
		int yPixelStep = HEIGHT / 10;
		for (int i = 0; i <= 10; i++) {
			int yPixel = i * yPixelStep;
			g.drawLine(0, yPixel, WIDTH, yPixel);
			y += yStep;
		}
	}

	/**
	 * drawAxis方法
	 * <p>绘制坐标<br>
	 *
	 * @param g 参数
	 * @return void 返回类型
	 */
	private void drawAxis(Graphics g) {
		// x轴在窗口中的y坐标
		int xAxisY = (int) (HEIGHT * (Y_MAX / (Y_MAX - Y_MIN))); 
		g.setColor(Color.BLACK);
		g.drawLine(0, xAxisY, WIDTH, xAxisY); // 绘制x轴
		// y轴在窗口中的x坐标
		int yAxisX = (int) (-X_MIN * (WIDTH / (X_MAX - X_MIN))); 
		g.drawLine(yAxisX, 0, yAxisX, HEIGHT); // 绘制y轴
		// 绘制x轴刻度
		double xStep = (X_MAX - X_MIN) / 10.0;
		double x = X_MIN;
		int xPixelStep = WIDTH / 10;
		for (int i = 0; i <= 10; i++) {
			int xPixel = i * xPixelStep;
			g.drawLine(xPixel, xAxisY, xPixel, xAxisY + 5); // 绘制刻度线
			g.drawString(String.format("%.1f", x), xPixel, xAxisY + 20); // 绘制刻度值
			x += xStep;
		}
		// 绘制y轴刻度
		double yStep = (Y_MAX - Y_MIN) / 10.0;
		double y = Y_MIN;
		int yPixelStep = HEIGHT / 10;
		for (int i = 0; i <= 10; i++) {
			int yPixel = i * yPixelStep;
			g.drawLine(yAxisX, yPixel, yAxisX - 5, yPixel); // 绘制刻度线
			g.drawString(String.format("%.1f", y), yAxisX - 40, yPixel + 5); // 绘制刻度值
			y += yStep;
		}
	}

	/**
	 * drawFunction方法
	 * <p>绘制函数<br>
	 *
	 * @param g 参数
	 * @return void 返回类型
	 */
	private void drawFunction(Graphics g) {
		// 绘制时点的间距
		double dx = (X_MAX - X_MIN) / NUM_POINTS;
		Graphics2D g2d = (Graphics2D) g;
		// 设置线条粗细为2，红色
		g2d.setStroke(new BasicStroke(2)); 
		g.setColor(Color.RED);
		/* 确认mxparser非商用 */
		License.iConfirmNonCommercialUse("Zhang Jinghui");
		// 新建参数xArg,在表达式中的符号为x
		Argument xArg = new Argument("x");     			
		// 创建函数表达式，x为自变量
		Expression expression = new Expression(function, xArg); 

		for (int i = 0; i < NUM_POINTS; i++) {
			double x = X_MIN + i * dx;
			// 将x的值传入参数xArg
			xArg.setArgumentValue(x); 
			// 计算函数的值，并赋值给y
			double y = expression.calculate(); 
			// 计算当前横纵坐标
			int xPixel = (int) (WIDTH * ((x - X_MIN) / (X_MAX - X_MIN)));
			int yPixel = (int) (HEIGHT * (1 - (y - Y_MIN) / (Y_MAX - Y_MIN)));
			// 若y不为NaN并且为有限值，则绘制当前函数点，大小为一个像素
			if(!Double.isNaN(y) && Double.isFinite(y)) {
				g.fillRect(xPixel, yPixel, 2, 2); 
			}	
		}
		// 设置解析式字体样式
		g.setColor(Color.BLUE);
		g.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 25)); 
		// 拼接字符串，绘制函数解析式标签
		g.drawString("f(x)=" + function, 10, 20);
	}
}
