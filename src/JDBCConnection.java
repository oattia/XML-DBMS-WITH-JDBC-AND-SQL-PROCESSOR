import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import org.apache.log4j.Logger;

import DBMS.*;

public class JDBCConnection implements Connection {

	private static Logger logger = Logger.getLogger(JDBCConnection.class);
	private DBMS dbms;

	/**
	 * The constructor.
	 */
	public JDBCConnection(DBMS dbms) throws Exception {
		if (dbms == null) {
			logger.error("Null database manager!");
			throw new Exception("Null database manager!");
		}

		this.dbms = dbms;
		logger.info("Conncetion with this DBMS has been made");
	}

	/**
	 * Creates a Statement object for sending SQL statements to the database.
	 * SQL statements without parameters are normally executed using Statement
	 * objects. If the same SQL statement is executed many times, it may be more
	 * efficient to use a PreparedStatement object. Result sets created using
	 * the returned Statement object will by default be type TYPE_FORWARD_ONLY
	 * and have a concurrency level of CONCUR_READ_ONLY. The holdability of the
	 * created result sets can be determined by calling getHoldability().
	 * Returns: a new default Statement object Throws: SQLException - if a
	 * database access error occurs or this method is called on a closed
	 * connection
	 */
	@Override
	public Statement createStatement() throws SQLException {
		if (dbms == null) {
			logger.error("This connection is closed!");
			throw new SQLException("This connection is closed!");
		}
		return new JDBCStatement(dbms, this);
	}

	/**
	 * Releases this Connection object's database and JDBC resources immediately
	 * instead of waiting for them to be automatically released. Calling the
	 * method close on a Connection object that is already closed is a no-op. It
	 * is strongly recommended that an application explicitly commits or rolls
	 * back an active transaction prior to calling the close method. If the
	 * close method is called and there is an active transaction, the results
	 * are implementation-defined. Throws: SQLException - SQLException if a
	 * database access error occurs
	 */
	@Override
	public void close() throws SQLException {
		dbms = null; // release the DBMS object.
		logger.info("Connection Has been closed");
	}

	// ------------------------------------------------------\\
	// /////////--------- UNUSED METHODS ----------\\\\\\\\\\\\
	// ------------------------------------------------------\\

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		return null;
	}

	@Override
	public void abort(Executor executor) throws SQLException {

	}

	@Override
	public void clearWarnings() throws SQLException {

	}

	@Override
	public void commit() throws SQLException {

	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements)
			throws SQLException {
		return null;
	}

	@Override
	public Blob createBlob() throws SQLException {
		return null;
	}

	@Override
	public Clob createClob() throws SQLException {
		return null;
	}

	@Override
	public NClob createNClob() throws SQLException {
		return null;
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		return null;
	}

	@Override
	public Statement createStatement(int resultSetType, int resultSetConcurrency)
			throws SQLException {
		return null;
	}

	@Override
	public Statement createStatement(int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return null;
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes)
			throws SQLException {
		return null;
	}

	@Override
	public boolean getAutoCommit() throws SQLException {
		return false;
	}

	@Override
	public String getCatalog() throws SQLException {
		return null;
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		return null;
	}

	@Override
	public String getClientInfo(String name) throws SQLException {
		return null;
	}

	@Override
	public int getHoldability() throws SQLException {
		return 0;
	}

	@Override
	public DatabaseMetaData getMetaData() throws SQLException {
		return null;
	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		return 0;
	}

	@Override
	public String getSchema() throws SQLException {
		return null;
	}

	@Override
	public int getTransactionIsolation() throws SQLException {
		return 0;
	}

	@Override
	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return null;
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		return null;
	}

	@Override
	public boolean isClosed() throws SQLException {
		return false;
	}

	@Override
	public boolean isReadOnly() throws SQLException {
		return false;
	}

	@Override
	public boolean isValid(int timeout) throws SQLException {
		return false;
	}

	@Override
	public String nativeSQL(String sql) throws SQLException {
		return null;
	}

	@Override
	public CallableStatement prepareCall(String sql) throws SQLException {
		return null;
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		return null;
	}

	@Override
	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
			throws SQLException {
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
			throws SQLException {
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String sql, String[] columnNames)
			throws SQLException {
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		return null;
	}

	@Override
	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		return null;
	}

	@Override
	public void releaseSavepoint(Savepoint savepoint) throws SQLException {

	}

	@Override
	public void rollback() throws SQLException {

	}

	@Override
	public void rollback(Savepoint savepoint) throws SQLException {

	}

	@Override
	public void setAutoCommit(boolean autoCommit) throws SQLException {

	}

	@Override
	public void setCatalog(String catalog) throws SQLException {

	}

	@Override
	public void setClientInfo(Properties properties)
			throws SQLClientInfoException {

	}

	@Override
	public void setClientInfo(String name, String value)
			throws SQLClientInfoException {

	}

	@Override
	public void setHoldability(int holdability) throws SQLException {

	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds)
			throws SQLException {

	}

	@Override
	public void setReadOnly(boolean readOnly) throws SQLException {

	}

	@Override
	public Savepoint setSavepoint() throws SQLException {
		return null;
	}

	@Override
	public Savepoint setSavepoint(String name) throws SQLException {

		return null;
	}

	@Override
	public void setSchema(String schema) throws SQLException {

	}

	@Override
	public void setTransactionIsolation(int level) throws SQLException {

	}

	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {

	}

}
