package cn.edu.sdu05;

import java.util.EmptyStackException;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ManipulateEquation类的简介
 * <p>该类用于操作算式字符串，涵盖一系列操作算式字符串的方法
 * 包括将中缀表达式转化为后缀表达式，以及执行算式字符串计算的方法<br>
 *
 * @author Zhang Jinghui
 * <p>created on 2023/11/13<br>
 */
public class ManipulateEquation {
	/**
	 * getNumber方法
	 * <p>获取StringBuilder中的第一个数字<br>
	 *
	 * @param stringbuilder 原算式
	 * @return void 返回类型
	 */
	public static void getNumber(StringBuilder stringbuilder) {
		// 使用正则表达式匹配数字
		Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?"); 
		Matcher matcher = pattern.matcher(stringbuilder);

		if (matcher.find()) {
			// 获取数字
			String number = matcher.group();
			// 直接将数字入队
			CollectionUtil.expressionQueue.add(Double.parseDouble(number));
			// 将该数字从StringBuilder中移除
			while(true) {
				stringbuilder.replace(matcher.start(), matcher.end(), "");
				break;
			}
		} 
	}

	/**
	 * isDigit方法
	 * <p>判断当前算式的首字符是否为数字<br>
	 *
	 * @param stringbuilder 算式
	 * @return boolean 返回类型
	 */
	public static boolean isDigit(StringBuilder stringbuilder) {
		return Character.isDigit(stringbuilder.charAt(0));
	}


	/**
	 * getOperator方法
	 * <p>获取stringbuilder中第一个运算符<br>
	 *
	 * @param stringbuilder 参数
	 * @return void 返回类型
	 */
	public static void getOperator(StringBuilder stringbuilder) {
		// 使用正则表达式匹配运算符
		Pattern pattern = Pattern.compile("(\\^|\\+|\\×|\\-|\\÷|\\(|\\)|!)"); 
		Matcher matcher = pattern.matcher(stringbuilder);
		if (matcher.find()) {
			String operator = matcher.group();

			// 左括号直接入栈
			if(operator.equals("(")) {
				CollectionUtil.opStack.push(stringbuilder.charAt(0));
			}
			// 如果是右括号
			else if(operator.equals(")")) {
				// 当栈顶元素不为左括号时，循环入队并出栈
				while(CollectionUtil.opStack.peek() != '(') {
					CollectionUtil.expressionQueue.add(CollectionUtil.opStack.peek());
					CollectionUtil.opStack.pop();
				}
				// 最后将栈顶的左括号出栈丢弃
				CollectionUtil.opStack.pop();
			}
			// 如果是运算符
			else if(operator.length()<2) {
				// 获取当前运算符信息
				Operator currentOperator = Operator.operatorMap.get(stringbuilder.charAt(0));
				// 如果栈为空或栈顶为左括号，运算符直接入栈
				if(CollectionUtil.opStack.isEmpty() || CollectionUtil.opStack.peek() == '(') {
					CollectionUtil.opStack.push(stringbuilder.charAt(0));
				}
				else {
					// 获取栈顶运算符信息
					Operator topOperator = Operator.operatorMap.get(CollectionUtil.opStack.peek());
					// 判断当前运算符是否满足为左结合&当前运算符优先级小于等于栈顶运算符
					if (currentOperator.isLeftAssociative() == true && currentOperator.getPriotity() <= topOperator.getPriotity()) {
						while(currentOperator.getPriotity() <= topOperator.getPriotity()) {
							// 栈顶运算符入队
							CollectionUtil.expressionQueue.add(CollectionUtil.opStack.peek());
							// 栈顶运算符出栈
							CollectionUtil.opStack.pop();
							// 如果栈已空，跳出循环
							if(CollectionUtil.opStack.isEmpty())
								break;
							// 更新栈顶运算符，以便进行下次判断
							topOperator = Operator.operatorMap.get(CollectionUtil.opStack.peek());
						}
						// 当前运算符入栈
						CollectionUtil.opStack.push(currentOperator.getSymbol());
					}
					// 判断当前运算符是否满足为右结合&当前运算符优先级小于栈顶运算符
					else if (currentOperator.isLeftAssociative() == false && currentOperator.getPriotity() < topOperator.getPriotity()) {
						while(currentOperator.getPriotity() < topOperator.getPriotity()) {
							// 栈顶运算符入队
							CollectionUtil.expressionQueue.add(CollectionUtil.opStack.peek());
							// 栈顶运算符出栈
							CollectionUtil.opStack.pop();
							// 如果栈已空，跳出循环
							if(CollectionUtil.opStack.isEmpty())
								break;
							// 更新栈顶运算符，以便进行下次判断
							topOperator = Operator.operatorMap.get(CollectionUtil.opStack.peek());
						}
						// 当前运算符入栈
						CollectionUtil.opStack.push(currentOperator.getSymbol());
					}
					// 其余情况，当前运算符直接入栈
					else {
						CollectionUtil.opStack.push(currentOperator.getSymbol());
					}
				}
			}
			// 将该数字从StringBuilder中移除
			while(true) {
				stringbuilder.replace(matcher.start(), matcher.end(), "");
				break;
			}
		}
	} 

	/**
	 * infixToPostfix方法
	 * <p>中缀表达式转后缀表达式(RPN,逆波兰表达式)<br>
	 *
	 * @param stringbuilder 输入算式
	 * @return void 返回类型
	 */
	public static void infixToPostfix(StringBuilder stringbuilder) {
		while(stringbuilder.length() != 0) {
			if(isDigit(stringbuilder)) {
				getNumber(stringbuilder);
			}
			else {
				getOperator(stringbuilder);
			}

		}
		// 循环结束后，把栈中的剩余运算符都添加到队列中
		while(!CollectionUtil.opStack.isEmpty()) {
			// 栈顶元素入队
			CollectionUtil.expressionQueue.add(CollectionUtil.opStack.peek());
			// 栈顶元素出栈
			CollectionUtil.opStack.pop();
		}
	}

	/**
	 * factorial方法
	 * <p>递归计算阶乘<br>
	 *
	 * @param n 阶乘数字
	 * @return 阶乘结果
	 */
	private static Double factorial(Double n) {
		if (n <= 1) {
			return (double) 1;
		} else {
			return n * factorial(n - 1);
		}
	}

	/**
	 * calculate方法
	 * <p>对运算符执行计算<br>
	 *
	 * @param operator 运算符
	 * @return void 返回类型
	 */
	public static void calculate(char operator) {
		switch (operator) {
		case '+': {
			Double operand2 = CollectionUtil.numStack.pop();
			Double operand1 = CollectionUtil.numStack.pop();
			Double result = operand1 + operand2;
			CollectionUtil.numStack.push(result);
			break;
		}
		case '-': {
			Double operand2 = CollectionUtil.numStack.pop();
			Double operand1 = CollectionUtil.numStack.pop();
			Double result = operand1 - operand2;
			CollectionUtil.numStack.push(result);
			break;
		}			
		case '×': {
			Double operand2 = CollectionUtil.numStack.pop();
			Double operand1 = CollectionUtil.numStack.pop();
			Double result = operand1 * operand2;
			CollectionUtil.numStack.push(result);
			break;
		}
		case '÷': {
			Double operand2 = CollectionUtil.numStack.pop();
			Double operand1 = CollectionUtil.numStack.pop();
			Double result = operand1 / operand2;
			CollectionUtil.numStack.push(result);
			break;
		}
		case '^': {
			Double operand2 = CollectionUtil.numStack.pop();
			Double operand1 = CollectionUtil.numStack.pop();
			Double result = Math.pow(operand1, operand2);
			CollectionUtil.numStack.push(result);
			break;
		}
		case '!': {
			Double result = factorial(CollectionUtil.numStack.pop());		
			CollectionUtil.numStack.push(Double.valueOf(result));
			break;
		}
		}
	}

	/**
	 * executeOperation方法
	 * <p>执行后缀表达式的计算<br>
	 *
	 * @return Double 返回类型
	 */
	public static Double executeOperation() {
		while (!CollectionUtil.expressionQueue.isEmpty()) {
			Object token = CollectionUtil.expressionQueue.remove();
			if (token instanceof Double) {
				// 如果是数字，直接进入数字栈
				CollectionUtil.numStack.push((double) token);
			} 
			else if (token instanceof Character) {
				// 如果是运算符，则调用calculate函数进行计算
				char operator = (char) token;
				calculate(operator);
			}
		}
		// 运算完毕后，数字栈中数字即为运算结果
		return CollectionUtil.numStack.pop();
	}

	/**
	 * calculate方法
	 * <p>实现完整计算过程，并返回计算结果<br>
	 *
	 * @param equation 算式
	 * @return Double 返回类型
	 */
	public static Double calculate(String equation) {
		// 并创建一个StringBuilder对象,参数为算式字符串
		StringBuilder stringBuilder= new StringBuilder(equation);
		/* 若集合中没有更多元素可以获取，会引发NoSuchElementException异常
		 * 当栈为空时，会引发EmptyStackException异常
		 * 发生以上异常时，将返回值设为NaN，以便后续处理*/
		try {
			// 中缀表达式转后缀表达式
			infixToPostfix(stringBuilder);
			// 执行计算
			Double result = executeOperation();
			return result;
		} catch (NoSuchElementException e) {
			return Double.NaN;
		} catch (EmptyStackException e) {
			return Double.NaN;
		}
	}

	/**
	 * reset方法
	 * <p>对集合进行重置，以便下次计算<br>
	 * 
	 * @return void 返回类型
	 */
	public static void reset() {
		CollectionUtil.clearCollection();
	}
}
