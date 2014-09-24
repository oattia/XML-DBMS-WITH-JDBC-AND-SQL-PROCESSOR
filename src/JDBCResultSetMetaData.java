import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import DBMS.RecordSet;

public class JDBCResultSetMetaData implements ResultSetMetaData {
	private RecordSet recordSet;

	/**
	 * constructor
	 */
	public JDBCResultSetMetaData(RecordSet recordSet) {
		this.recordSet = recordSet;
	}

	/**
	 * Returns the number of columns in this ResultSet object.
	 */
	@Override
	public int getColumnCount() throws SQLException {
		return recordSet.get(0).getRecordSize();
	}

	/**
	 * Gets the designated column's suggested title for use in printouts and
	 * displays. The suggested title is usually specified by the SQL AS clause.
	 * If a SQL AS is not specified, the value returned from getColumnLabel will
	 * be the same as the value returned by the getColumnName method.
	 */
	@Override
	public String getColumnLabel(int column) throws SQLException {
		return recordSet.getAttributeName(column);
	}

	/**
	 * Get the designated column's name.
	 */
	@Override
	public String getColumnName(int column) throws SQLException {
		return recordSet.getAttributeName(column);
	}

	/**
	 * Retrieves the designated column's SQL type.
	 */
	@Override
	public int getColumnType(int column) throws SQLException {
		String columnName = recordSet.getAttributeName(column);
		Object classType = recordSet.get(column).getValue(columnName);
		String classTypeName = classType.getClass().getSimpleName();
		int columnTypeInt = Types.getTypeInt(classTypeName);

		if (columnTypeInt == -10000) {
			throw new SQLException("Undefined Data Type");
		} else {
			return columnTypeInt;
		}
	}

	/**
	 * Gets the designated column's table name.
	 */
	@Override
	public String getTableName(int column) throws SQLException {
		return recordSet.get(column).getTableName();
	}

	/**
	 * Indicates whether the designated column is automatically numbered.
	 */
	@Override
	public boolean isAutoIncrement(int column) throws SQLException {
		return false;
	}

	/**
	 * Indicates the nullability of values in the designated column.
	 */
	@Override
	public int isNullable(int column) throws SQLException {
		return 0;
	}

	/**
	 * Indicates whether the designated column is definitely not writable.
	 */
	@Override
	public boolean isReadOnly(int column) throws SQLException {
		return false;
	}

	/**
	 * Indicates whether the designated column can be used in a where clause.
	 */
	@Override
	public boolean isSearchable(int column) throws SQLException {
		return false;
	}

	/**
	 * Indicates whether it is possible for a write on the designated column to
	 * succeed.
	 */
	@Override
	public boolean isWritable(int column) throws SQLException {
		return true;
	}

	// ------------------------------------------------------\\
	// /////////--------- UNUSED METHODS ----------\\\\\\\\\\\\
	// ------------------------------------------------------\\

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {

		return false;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {

		return null;
	}

	@Override
	public String getCatalogName(int arg0) throws SQLException {

		return null;
	}

	@Override
	public String getColumnClassName(int arg0) throws SQLException {

		return null;
	}

	@Override
	public int getColumnDisplaySize(int arg0) throws SQLException {

		return 0;
	}

	@Override
	public String getColumnTypeName(int arg0) throws SQLException {

		return null;
	}

	@Override
	public int getPrecision(int arg0) throws SQLException {

		return 0;
	}

	@Override
	public int getScale(int arg0) throws SQLException {

		return 0;
	}

	@Override
	public String getSchemaName(int arg0) throws SQLException {

		return null;
	}

	@Override
	public boolean isCaseSensitive(int arg0) throws SQLException {

		return false;
	}

	@Override
	public boolean isCurrency(int arg0) throws SQLException {

		return false;
	}

	@Override
	public boolean isDefinitelyWritable(int arg0) throws SQLException {

		return false;
	}

	@Override
	public boolean isSigned(int arg0) throws SQLException {

		return false;
	}
}
