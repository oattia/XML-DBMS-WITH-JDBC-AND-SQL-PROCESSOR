public class Types {
	// final private static int INTEGER = 1;
	// final private static int VARCHAR = 2;
	// final private static int BOOLEAN = 3;
	// final private static int FLOAT = 4;
	// final private static int DOUBLE = 5;
	// final private static int DATE = 6;
	// final private static int TIME = 7;

	public static int getTypeInt(String ColumnType) {
		if (ColumnType.equalsIgnoreCase("String")) {
			return java.sql.Types.VARCHAR;
		} else if (ColumnType.equalsIgnoreCase("BOOLEAN")) {
			return java.sql.Types.BOOLEAN;
		} else if (ColumnType.equalsIgnoreCase("INTEGER")) {
			return java.sql.Types.INTEGER;
		} else if (ColumnType.equalsIgnoreCase("FLOAT")) {
			return java.sql.Types.FLOAT;
		} else if (ColumnType.equalsIgnoreCase("DOUBLE")) {
			return java.sql.Types.DOUBLE;
		} else if (ColumnType.equalsIgnoreCase("DATE")) {
			return java.sql.Types.DATE;
		} else if (ColumnType.equalsIgnoreCase("TIME")) {
			return java.sql.Types.TIME;
		} else {
			return -10000;
		}
	}
}
