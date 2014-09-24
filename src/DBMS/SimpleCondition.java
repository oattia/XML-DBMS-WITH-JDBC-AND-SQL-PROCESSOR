package DBMS;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SimpleCondition {

	String op1AttrName, op2AttrName;
	String operator;
	String condition;

	public String toString() {
		return condition;
		// op1AttrName + " " + operator + " " + op2AttrName;
	}

	public SimpleCondition(String cond, Table table) {
		condition = "";
		
		// temporary for this version 
		// not supporting arithmatic operations on cells into the condition
		for(int i=0; i<cond.length(); i++)		// remove spaces and braces
			if(cond.charAt(i)!=' ' && cond.charAt(i)!='(' && cond.charAt(i)!=')')
				condition += cond.charAt(i);

		String op1 = condition.substring(0, 
			(condition.contains("<")) ? condition.indexOf('<') :
			(condition.contains(">")) ? condition.indexOf('>') :
			(condition.contains("!")) ? condition.indexOf('!') :
			(condition.contains("=")) ? condition.indexOf('=') : 1) ;
		String op2;

		if (condition.contains("\'"))
			op2 = condition
					.substring(condition.indexOf('\'') + 1, condition.lastIndexOf('\''));
		else
			op2 = condition.substring(	1 + 
				((condition.contains("=")) ? condition.indexOf('=') :
				(condition.contains("<")) ? condition.indexOf('<') :
				(condition.contains(">")) ? condition.indexOf('>') : 1), condition.length());

		List<String> attrNames = new ArrayList<String>();
		ColumnIdentifier[] colIDs = null;
		if (table != null)
			colIDs = table.getColIDs();
		else {
			//System.out.println("ERROR: NO SUCH TABLE");	//no table sent
			return;
		}
		for (int i = 0; i < colIDs.length; i++)
			attrNames.add(colIDs[i].getColumnName());

		op1AttrName = null;
		op2AttrName = null;

		for (String s : attrNames) {
			if (op1.compareTo(s) == 0)
				op1AttrName = new String(s);
			if (op2.compareTo(s) == 0)
				op2AttrName = new String(s);
		}
		operator = (condition.contains("!=")) ? "!=" : (condition.contains("<=")) ? "<=" :
			(condition.contains(">=")) ? ">=" : (condition.contains("=")) ? "=" :
			(condition.contains("<")) ? "<" : (condition.contains(">")) ? ">" : null;
	}

	public boolean meetsCondition(Record rec) {
		Object LHS = getLHSValue(rec);
		Object RHS = getRHSValue(rec);

		if (LHS == null || RHS == null || !(LHS instanceof Comparable)
				|| !(RHS instanceof Comparable)
				|| !(LHS.getClass().equals(RHS.getClass())))
			return false;

		@SuppressWarnings({ "unchecked", "rawtypes" })
		int comparisonResult = ((Comparable) LHS).compareTo((Comparable) RHS);

		if (operator.contains("<="))
			return (comparisonResult == -1 || comparisonResult == 0);
		else if (operator.contains(">="))
			return (comparisonResult == 1 || comparisonResult == 0);
		else if (operator.contains("!="))
			return (comparisonResult == 1 || comparisonResult == -1);
		else if (operator.contains("<"))
			return (comparisonResult == -1);
		else if (operator.contains(">"))
			return (comparisonResult == 1);
		else if (operator.contains("="))
			return (comparisonResult == 0);
		return true;
	}
	
	
	
	
	
	
	

	private Object getLHSValue(Record rec) {
		if (op1AttrName != null)
			return rec.getValue(op1AttrName);
		else
			return condition.substring(0, 
					(condition.contains("<")) ? condition.indexOf('<') :
					(condition.contains(">")) ? condition.indexOf('>') :
					(condition.contains("!")) ? condition.indexOf('!') :
					(condition.contains("=")) ? condition.indexOf('=') : 1) ;
	}

	private Object getRHSValue(Record rec) {
		if (op2AttrName != null)
			return rec.getValue(op2AttrName);
		else
			return RHSConstValue(getLHSValue(rec));
	}

	private Object RHSConstValue(Object LHS) { // parse to same type as LHS
		String op2;
		if (condition.contains("\'"))
			op2 = condition.substring(condition.indexOf('\'') + 1,
					condition.lastIndexOf('\''));
		else
			op2 = condition.substring(	1 + 
				((condition.contains("=")) ? condition.indexOf('=') :
				(condition.contains("<")) ? condition.indexOf('<') :
				(condition.contains(">")) ? condition.indexOf('>') : 1), condition.length());

		if (LHS == null)
			return null;

		try {
			if (LHS instanceof Integer)
				return Integer.valueOf(op2);
			if (LHS instanceof Double)
				return Double.valueOf(op2);
			if (LHS instanceof Float)
				return Float.valueOf(op2);
			if (LHS instanceof Long)
				return Long.valueOf(op2);
			if (LHS instanceof String)
				return String.valueOf(op2);
			if (LHS instanceof Date)
				return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(op2);
			if (LHS instanceof Boolean)
				return Boolean.parseBoolean(op2);
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	
}