package DBMS;

import java.util.StringTokenizer;

public class SQLProcessor {

	private DBMS dbms;
	private StringTokenizer tokens;

	public SQLProcessor(DBMS dbms) {
		this.dbms = dbms;
	}

	public void processSQL(String toProcess) {
		toProcess = prepareSQLStatement(toProcess);
		tokens = new StringTokenizer(toProcess, " ");
		try {
			String operation = tokens.nextToken();
			if (operation.equalsIgnoreCase("create")) {
				handleCreateStatement();
			} else if (operation.equalsIgnoreCase("insert")) {
				handleInsertStatement();
			} else if (operation.equalsIgnoreCase("delete")) {
				handleDeleteStatement();
			} else if (operation.equalsIgnoreCase("select")) {
				handleSelectStatement();
			} else if (operation.equalsIgnoreCase("update")) {
				handleUpdateStatement();
			} else if (operation.equalsIgnoreCase("use")) {
				handleUseStatement();
			} else {
				System.out.println("ERROR: \"" + operation
						+ "\" IS NOT RECOGNIZED AS SQL QUERY");
			}
		} catch (Exception e) {
			System.out.println("ERROR: WRONG INPUT FORMAT!");
		}
	}

	private String prepareSQLStatement(String sql) {
		if (sql == null || sql.trim().equals("")) {
			System.out.println("ERROR : THIS IS NOT A SQL STATEMENT");
		}
		sql = sql.replaceAll("\\*", " * ");
		sql = sql.replaceAll("\\(", " ( ");
		sql = sql.replaceAll("\\)", " ) ");
		sql = sql.replaceAll("\\,", " , ");
		sql = sql.replaceAll("\\=", " = ");
		while (sql.contains("  "))
			sql = sql.replaceAll("  ", " ");
		return sql.trim();
	}

	/**
	 * when called, checks for validity of the Use statement and calls the
	 * method for the specified database if exists
	 * 
	 * @param tokens
	 */
	private void handleUseStatement() {
		String dbName = tokens.nextToken();
		try {
			dbms.setUsedDB(dbName);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * when called, checks for validity of the handle statement and calls the
	 * method for the specified database if exists
	 * 
	 * @param tokens
	 */

	private void handleUpdateStatement() {
		String tableName = tokens.nextToken();
		if (!verifyName(tableName)) {
			System.out.println("ERROR: WRONG INPUT FORMAT");
			return;
		}
		if (tokens.nextToken().equalsIgnoreCase("set")) {
			invokeUpdateTable(tableName);
		} else {
			System.out.println("ERROR: Wrong SQL Statement");
		}
	}

	private void invokeUpdateTable(String tableName) {
		Database db = dbms.getUsedDB();
		if (db == null) {
			System.out.println("ERROR: YOU MUST CHOOSE AN EXISTING DATABASE");
			return;
		}
		Table tb = db.getTable(tableName);
		if (tb == null) {
			System.out.println("ERROR: \"" + tableName
					+ "\" IS NOT DEFINED AS TABLE IN THIS DATABASE");
		}
		createRecordToUpdate(tb);
	}

	private void createRecordToUpdate(Table tb) {
		StringBuffer toReturn = new StringBuffer();
		String piece;
		while (!(piece = tokens.nextToken()).equalsIgnoreCase("where")) {
			toReturn.append(piece);
			toReturn.append(" ");
		}
		StringTokenizer updates = new StringTokenizer(toReturn.toString(), ",");

		String[] columnsNames = new String[updates.countTokens()];
		String[] values = new String[updates.countTokens()];

		int i = 0;
		while (updates.hasMoreTokens()) {
			StringTokenizer x = new StringTokenizer(updates.nextToken(), "=");
			if (x.countTokens() != 2) {
				System.out.println("ERROR: WRONG INPUT");
				return;
			}
			String name = x.nextToken();
			String value = x.nextToken();
			if (!verifyName(name)) {
				System.out.println("ERROR: WRONG INPUT");
				return;
			}
			columnsNames[i] = name.trim();
			values[i] = value.trim();
			i++;
		}

		Condition condition = extractCondition(tb);
		if (condition == null) {
			System.out.println("ERROR: WRONG INPUT");
			return;
		}

		try {
			tb.update(columnsNames, values, condition);
		} catch (Exception e) {
			System.out.println("ERROR: UPDATING FAILED");
		}
	}

	private void handleSelectStatement() {
		String columnsNames = getBetweenBraces();
		if (!tokens.nextToken().equalsIgnoreCase("from")) {
			System.out.println("ERROR: WRONG INPUT");
			return;
		}

		String tableName = tokens.nextToken();
		if (!verifyName(tableName)) {
			System.out.println("ERROR: WRONG INPUT");
			return;
		}

		Database db = dbms.getUsedDB();
		if (db == null) {
			System.out.println("ERROR: WRONG INPUT");
			return;
		}

		Table tb = db.getTable(tableName);

		if (tb == null) {
			System.out.println("ERROR: WRONG INPUT");
			return;
		}
		if (!tokens.nextToken().equalsIgnoreCase("where")) {
			System.out.println("ERROR: WRONG INPUT");
			return;
		}

		Condition cond = extractCondition(tb);
		String[] arr = columnsNames.split(",");
		String[] toBeTrimmed = new String[arr.length];
		for (int i = 0; i < toBeTrimmed.length; i++) {
			toBeTrimmed[i] = arr[i].trim();
		}

		RecordSet selected = tb.select(toBeTrimmed, cond);
		for (Record r : selected) {
			System.out.println(r.toString());
		}
	}

	private void handleDeleteStatement() {
		String toCheck = tokens.nextToken();
		boolean deleteAll;
		if (toCheck.equalsIgnoreCase("from")) {
			deleteAll = false;
		} else if (toCheck.equals("*")) {
			deleteAll = true;
		} else {
			System.out.println("ERROR: WRONG SQL STATEMENT, MISSING \"FROM\" ");
			return;
		}
		Database db = dbms.getUsedDB();
		if (db == null) {
			System.out.println("ERROR: NO SUCH DATABASE");
			return;
		}

		String tableName = tokens.nextToken();
		Table tb = db.getTable(tableName);
		if (tb == null) {
			System.out.println("ERROR: NO SUCH TABLE");
			return;
		}

		if (deleteAll) {
			try {
				tb.deleteAll();
			} catch (Exception e) {
				System.out.println("ERROR: " + e.getMessage());
			}
			return;
		}

		if (!tokens.nextToken().equalsIgnoreCase("where")) {
			System.out.println("ERROR: WRONG INPUT FORMAT");
			return;
		}

		Condition cond = extractCondition(tb);
		if (cond == null) {
			System.out.println("ERROR: WRONG INPUT FORMAT");
			return;
		}

		try {
			tb.delete(cond);
		} catch (Exception e) {
			System.out.println("ERROR: DELETION FAILED");
		}
	}

	private void handleInsertStatement() {
		if (!tokens.nextToken().equalsIgnoreCase("into")) {
			System.out.println("ERROR: Wrong INPUT FORMAT");
			return;
		}

		Database db = dbms.getUsedDB();

		if (db == null) {
			System.out.println("ERROR: CANNOT FIND DATABASE");
			return;
		}

		String tableName = tokens.nextToken();
		if (!verifyName(tableName)) {
			System.out.println("ERROR: Wrong INPUT FORMAT");
			return;
		}
		Table tb = db.getTable(tableName);
		if (tb == null) {
			System.out.println("ERROR: CANNOT FIND TABLE");
			return;
		}
		invokeInsertInto(tb);
	}

	private void invokeInsertInto(Table tb) {
		String columnsNames = getBetweenBraces();
		if (!tokens.nextToken().equalsIgnoreCase("values")) {
			System.out.println("ERROR: Wrong INPUT FORMAT");
			return;
		}
		String columnsValues = getBetweenBraces();
		if (columnsNames == null || columnsValues == null) {
			System.out.println("ERROR: Wrong INPUT FORMAT");
			return;
		}
		String[] colNames = columnsNames.split(",");
		String[] colVals = columnsValues.split(",");
		String[] trimmedCN = new String[colNames.length];
		String[] trimmedCV = new String[colVals.length];

		for (int i = 0; i < colNames.length; i++) {
			trimmedCN[i] = colNames[i].trim();
			trimmedCV[i] = colVals[i].trim();
		}
		try {
			Record newValues = new Record(trimmedCN, trimmedCV, tb);
			tb.insert(newValues);
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}

	private void handleCreateStatement() {
		String toBeCreated = tokens.nextToken();
		if (toBeCreated.equalsIgnoreCase("table")) {
			invokeCreateTable(tokens);
		} else if (toBeCreated.equalsIgnoreCase("database")) {
			invokeCreateDatabase(tokens);
		} else {
			System.out.println("ERROR: Wrong SQL Statemnet \"" + toBeCreated
					+ "\" CANNOT BE CREATED");
		}
	}

	private void invokeCreateTable(StringTokenizer tokens) {
		String tableName = tokens.nextToken();
		if (!verifyName(tableName)) {
			System.out.println("ERROR: TABLE NAME IS NOT ALLOWED");
			return;
		}
		Database db = dbms.getUsedDB();
		if (db == null) {
			System.out.println("ERROR: DATABASE IS NOT SPECIFIED");
			return;
		}
		try {
			ColumnIdentifier[] columnIdentifiers = extractColumnIdentifiers();
			db.addTable(tableName, columnIdentifiers);
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}

	private void invokeCreateDatabase(StringTokenizer tokens) {
		String dbName = tokens.nextToken();
		if (!verifyName(dbName)) {
			System.out.println("ERROR: WRONG INPUT FORMAT");
		}
		try {
			dbms.createDB(dbName);
		} catch (Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}

	private Condition extractCondition(Table tb) {
		String all = depleteTokens();
		if (!validateCondition(all)) {
			System.out.println("ERROR: INVALID CONDITION FORMAT");
			return null;
		}
		Condition cond = new Condition(all, tb);
		return cond;
	}

	private String depleteTokens() {
		StringBuffer all = new StringBuffer();
		int i = 0;
		while (tokens.hasMoreTokens()) {
			all.append(tokens.nextToken());
			all.append(" ");
			i++;
		}
		if (i == 0)
			return null;
		else
			return all.toString();
	}

	private boolean validateCondition(String all) {
		if (all == null)
			return false;
		String nameReq = "[A-Za-z][A-Za-z0-9]*";
		String operatorPat = "(\\*|\\<\\=|\\>\\=|\\=|\\<|\\>)";
		String intPat = "(-){0,1}[0-9]+";
		String RealPat = "(-){0,1}[0-9]+(\\.){0,1}[0-9]+";
		String stringPat = ".*";
		String datePat = "[1-2]([0-9]){3}-((0[1-9])|1[0-2])-([0-2][1-9]|3[0-1])T([0-2][0-3])\\:([0-5][0-9])\\:([0-5][0-9])";
		String booleanPat = "(((?i)true)|((?i)false))";

		String parPatCV = intPat + "|" + RealPat + "|" + booleanPat + "|"
				+ stringPat + "|" + datePat;

		String conditionPat = nameReq + "\\s*" + operatorPat + "\\s*" + "("
				+ nameReq + "|" + parPatCV + ")";
		return all.matches(conditionPat);
	}

	private String getBetweenBraces() {
		StringBuffer toReturn = new StringBuffer();

		if (!tokens.nextToken().equals("("))
			return null;

		String x;
		while (!(x = tokens.nextToken()).equals(")")) {
			toReturn.append(x);
			toReturn.append(" ");
		}

		return toReturn.toString().trim();
	}

	private String removeBraces(String string) {
		int indexOfOpenPar = string.indexOf('(');
		int indexOfClosedPar = string.indexOf(')');
		if (indexOfOpenPar < 0 || indexOfClosedPar < 0)
			return null;
		String toBeSplitted = string.substring(indexOfOpenPar + 1,
				indexOfClosedPar);
		return toBeSplitted.trim();
	}

	private ColumnIdentifier[] extractColumnIdentifiers() {
		String toSplit = depleteTokens();
		toSplit = removeBraces(toSplit);
		if (toSplit == null) {
			System.out.println("ERROR: WRONG INPUT FORMAT");
			return null;
		}
		StringTokenizer tt = new StringTokenizer(toSplit, ",");
		ColumnIdentifier[] ids = new ColumnIdentifier[tt.countTokens()];
		int i = 0;
		while (tt.hasMoreTokens()) {
			StringTokenizer ff = new StringTokenizer(tt.nextToken(), " ");
			if (ff.countTokens() != 2) {
				System.out.println("ERROR: WRONG INPUT FORMAT");
				return null;
			}
			String name = ff.nextToken();
			String type = ff.nextToken();
			if (!verifyName(name)) {
				System.out.println("ERROR: WRONG INPUT FORMAT");
				return null;
			}
			String[] del = { name.trim(), type.trim() };
			try {
				ids[i] = new ColumnIdentifier(del);
				i++;
			} catch (Exception e) {
				System.out.println("ERROR: " + e.getMessage());
			}
		}
		return ids;
	}

	public DBMS getDbms() {
		return dbms;
	}

	public void setDbms(DBMS dbms) {
		this.dbms = dbms;
	}

	private boolean verifyName(String objectName) {
		String nameReq = "[A-Za-z][A-Za-z0-9]*";
		return objectName.trim().matches(nameReq);
	}
}