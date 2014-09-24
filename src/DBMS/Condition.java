package DBMS;

import java.util.Stack;

public class Condition {

	String condition;
	Table table;

	public Condition(String cond, Table t) {
		condition = cond.replace(" AND ", "&").replace(" OR ", "|")
				.replace(" NOT ", "!").replace(" and ", "&")
				.replace(" or ", "|").replace(" not ", "!")
				.replace(")AND(", ")&(").replace(")OR(", ")|(")
				.replace("NOT(", "!(").replace(")and(", ")&(")
				.replace(")or(", ")|(").replace("not(", "!(");
		if (condition.startsWith("not "))
			condition = condition.replaceFirst("not", "!");
		if (condition.startsWith("NOT "))
			condition = condition.replaceFirst("NOT", "!");
		condition = condition.replace(" ", "");
		table = t;
	}

	public boolean meetsCondition(Record rec) {
		String substitute = new String(condition);

		for (int i = 0; i < condition.length(); i++) {
			int j = i;
			String simpleCond = "";
			int bracesCounter = 0;
			while (i < condition.length() && condition.charAt(i) != '&'
					&& condition.charAt(i) != '|' && condition.charAt(i) != '!') {
				simpleCond += condition.charAt(i);
				if (condition.charAt(i) == '(')
					bracesCounter++;
				if (condition.charAt(i) == ')')
					if (--bracesCounter == 0)
						break;
				i++;
			}

			if (simpleCond.length() != 0 && bracesCounter == 0) {
				if (simpleCond.contains("=") || simpleCond.contains("<")
						|| simpleCond.contains(">")) {
					substitute = substitute.replaceFirst(simpleCond,
							(new SimpleCondition(simpleCond, table)
									.meetsCondition(rec)) ? "T" : "F");
				} else if (rec.getValue(simpleCond) instanceof Boolean) { // the
																			// cell
																			// it
																			// self
																			// might
																			// be
																			// of
																			// boolean
																			// type
					condition = condition.replaceFirst(simpleCond,
							((boolean) rec.getValue(simpleCond)) ? "T" : "F");
				}
			} else if (simpleCond.length() != 0) {
				i = j;
			}
		}
		return valueOfExplicitExp(substitute);
	}

	private boolean valueOfExplicitExp(String exp) {
		Stack<Character> operations = new Stack<Character>();
		Stack<Boolean> values = new Stack<Boolean>();

		boolean increaseFlag = true;
		for (int i = 0; i < exp.length(); i = (increaseFlag) ? i + 1 : i) {
			if (exp.charAt(i) == 'T' || exp.charAt(i) == 'F') {
				values.push((exp.charAt(i) == 'T') ? true : false);
				increaseFlag = true;
			} else if (operations.isEmpty() || exp.charAt(i) == '('
					|| comparePeriority(exp.charAt(i), operations.peek()) != -1) {
				operations.push(exp.charAt(i));
				increaseFlag = true;
			} else // evaluate the top operation in stack
			{
				increaseFlag = false;
				if (operations.peek() == '!')
					values.push(evaluateSimpleExp(values.pop(),
							operations.pop(), true));
				else if (operations.peek() == '(' && exp.charAt(i) == ')') {
					operations.pop();
					increaseFlag = true;
				} else
					values.push(evaluateSimpleExp(values.pop(),
							operations.pop(), values.pop()));
			}
		}
		while (operations.size() != 0) {
			if (operations.peek() == '!')
				values.push(evaluateSimpleExp(values.pop(), operations.pop(),
						true));
			else
				values.push(evaluateSimpleExp(values.pop(), operations.pop(),
						values.pop()));
		}
		if (values.size() == 1)
			return values.pop();
		// through exception
		return false;
	}

	private int comparePeriority(char opr1, char opr2) {
		if (opr1 == opr2)
			return 0;
		if (opr1 == '!')
			return 1;
		if (opr2 == '!')
			return -1;
		if (opr1 == '&')
			return 1;
		if (opr2 == '&')
			return -1;
		if (opr1 == '|')
			return 1;
		if (opr2 == '|')
			return -1;
		if (opr1 == '(')
			return 1;
		if (opr2 == '(')
			return -1;
		if (opr1 == ')')
			return 1;
		if (opr2 == ')')
			return -1;
		return 0;
	}

	private boolean evaluateSimpleExp(boolean op1, char operator, boolean op2) {
		switch (operator) {
		case '&':
			return op1 & op2;
		case '|':
			return op1 | op2;
		case '!':
			return !op1;
		}
		return false;
	}
}