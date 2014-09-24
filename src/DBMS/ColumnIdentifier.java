package DBMS;

public class ColumnIdentifier {

	private String columnName;
	private Class<?> columnType;

	public ColumnIdentifier() {

	}

	public ColumnIdentifier(String[] bySpace) throws Exception {
		if (bySpace.length != 2) {
			throw new Exception("ERROR: CANNOT IDENTIFY COLUMN TYPE");
		}
		columnName = bySpace[0];

		if (bySpace[1].equalsIgnoreCase("VARCHAR")) {
			columnType = String.class;
		} else if (bySpace[1].equalsIgnoreCase("BOOLEAN")) {
			columnType = Boolean.class;
		} else if (bySpace[1].equalsIgnoreCase("INTEGER")) {
			columnType = Integer.class;
		} else if (bySpace[1].equalsIgnoreCase("FLOAT")) {
			columnType = Float.class;
		} else if (bySpace[1].equalsIgnoreCase("DOUBLE")) {
			columnType = Double.class;
		} else if (bySpace[1].equalsIgnoreCase("DATE")) {
			columnType = java.util.Date.class;
		} else if (bySpace[1].equalsIgnoreCase("TIME")) {
			columnType = java.util.Date.class;
		} else {
			throw new Exception("ERROR: THIS TYPE \"" + bySpace[1]
					+ "\" IS NOT SUPPORTED");
		}

	}

	public ColumnIdentifier(String name, Class<?> type) {
		columnName = name;
		columnType = type;
	}

	public ColumnIdentifier(String strColID) {
		String strs[] = strColID.split(":");
		try {
			columnName = strs[0];
			columnType = this.getClass().getClassLoader().loadClass(strs[1]);
		} catch (ClassNotFoundException e) {
			columnType = String.class;
		}
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Class<?> getColumnType() {
		return columnType;
	}

	public void setColumnType(Class<?> columnType) {
		this.columnType = columnType;
	}

}
