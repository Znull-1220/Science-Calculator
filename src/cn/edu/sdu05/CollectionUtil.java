package cn.edu.sdu05;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * CollectionUtil类的简介
 * <p>集合工具类,用于将中缀表达式转后缀表达式并计算<br>
 *
 * @author Zhang Jinghui
 * <p>created on 2023/11/13<br>
 */
public class CollectionUtil {
	// 运算符栈
	public static Stack<Character> opStack = new Stack<>();
	// 双向队列，便于更高效实现赋值并出栈的操作
	public static Deque<Double> numStack = new ArrayDeque<>();
	// 泛型队列
	public static Queue<Object> expressionQueue = new LinkedList<>();

	/**
	 * clearCollection方法
	 * <p>该方法用于清空如上定义的集合<br>
	 * 
	 * @return void 返回类型
	 */
	public static void clearCollection() {
		opStack.clear();
		numStack.clear();
		expressionQueue.clear();	
	}
}
