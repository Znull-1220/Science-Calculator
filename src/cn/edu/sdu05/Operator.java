package cn.edu.sdu05;

import java.util.HashMap;
import java.util.Map;

/**
 * Operator类的简介
 * <p>运算符类，用于初始化运算符及其优先级与结合性，并用Map静态存储其与符号的对应关系<br>
 *
 * @author Zhang Jinghui
 * <p>created on 2023/11/13<br>
 */
public class Operator {
	// 运算符
	private final char symbol;
	// 运算符优先级
	private final int priority;
	// 是否左结合
	private final boolean isLeftAssociative;

	/**
	 * 创建一个新的实例 Operator
	 *
	 * @param symbol 运算符
	 * @param priority 优先级
	 * @param isLeftAssociative 结合性是否为左结合
	 */
	public Operator(char symbol, int priority, boolean isLeftAssociative) {
		this.symbol = symbol;
		this.priority = priority;
		this.isLeftAssociative = isLeftAssociative;
	}

	/**
	 * getSymbol方法
	 * <p>获取运算符<br>
	 *
	 * @return char 返回类型
	 */
	public char getSymbol() {
		return symbol;
	}

	/**
	 * getPriotity方法
	 * <p>获取运算符的优先级<br>
	 *
	 * @return int 返回类型
	 */
	public int getPriotity() {
		return priority;
	}

	/**
	 * isLeftAssociative方法
	 * <p>获取运算符是否为左结合，若为左结合则返回{@code true}，否则返回{@code false}<br>
	 *
	 * @return boolean 返回类型
	 */
	public boolean isLeftAssociative() {
		return isLeftAssociative;
	}

	// 该映射存储运算符字符及对应运算符类的映射关系
	public static final Map<Character, Operator> operatorMap = new HashMap<>();
	static {
		operatorMap.put('(', new Operator('(', 0, true));
		operatorMap.put('+', new Operator('+', 1, true));
		operatorMap.put('-', new Operator('-', 1, true));
		operatorMap.put('×', new Operator('×', 2, true));
		operatorMap.put('÷', new Operator('÷', 2, true));
		operatorMap.put('^', new Operator('^', 4, false));  // 右结合,指数运算符
		operatorMap.put('!', new Operator('!', 3, false));  // 右结合,阶乘运算符
	}
}
