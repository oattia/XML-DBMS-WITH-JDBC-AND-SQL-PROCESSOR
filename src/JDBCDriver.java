import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;
import DBMS.*;

public class JDBCDriver implements Driver {

	/**
	 * Static Initializer :v When the class is loaded, it registers itself
	 * automatically so that it can be accessible through DriverManager methods.
	 */
	static {
		// register the driver:
		try {
			DriverManager.registerDriver(new JDBCDriver());
		} catch (SQLException e1) {
			// cannot register :(
		}
	}

	/**
	 * This method parses the URL string to make sure it is valid. It only
	 * accepts the format: jdbc:dana:localhost:<dbname>, where dbname is
	 * optional. The method returns <dbname> if the URL is valid, or null
	 * otherwise.
	 */
	private String urlToDataBase(String url) {
		String[] tokens = url.split(":");

		if (!(tokens.length == 3 || tokens.length == 4))
			return null;

		if (!tokens[0].equals("jdbc"))
			return null;

		if (!tokens[1].equals("JDBC"))
			return null;

		if (!tokens[2].equals("localhost"))
			return null;

		return tokens.length == 4 ? tokens[3] : ""; // database name
	}

	/**
	 * Retrieves whether the driver thinks that it can open a connection to the
	 * given URL. Typically drivers will return true if they understand the
	 * subprotocol specified in the URL and false if they do not. Parameters:
	 * url - the URL of the database Returns: true if this driver understands
	 * the given URL; false otherwise Throws: SQLException - if a database
	 * access error occurs
	 */
	@Override
	public boolean acceptsURL(String url) throws SQLException {
		return urlToDataBase(url) != null;
	}

	/**
	 * Attempts to make a database connection to the given URL. The driver
	 * should return "null" if it realizes it is the wrong kind of driver to
	 * connect to the given URL. This will be common, as when the JDBC driver
	 * manager is asked to connect to a given URL it passes the URL to each
	 * loaded driver in turn. The driver should throw an SQLException if it is
	 * the right driver to connect to the given URL but has trouble connecting
	 * to the database. The java.util.Properties argument can be used to pass
	 * arbitrary string tag/value pairs as connection arguments. Normally at
	 * least "user" and "password" properties should be included in the
	 * Properties object. Parameters: url - the URL of the database to which to
	 * connect info - a list of arbitrary string tag/value pairs as connection
	 * arguments. Normally at least a "user" and "password" property should be
	 * included. Returns: a Connection object that represents a connection to
	 * the URL Throws: SQLException - if a database access error occurs
	 */
	@Override
	public Connection connect(String url, Properties info) throws SQLException {
		String username = info.getProperty("user");
		String password = info.getProperty("password");

		// instantiate DBMS:
		DBMS dbms;
		try {
			dbms = new StdDBMS();
		} catch (Exception e1) {
			throw new SQLException(e1.getMessage());
		}
		// check user name and password:
		if (!dbms.isValidUserName(username) || !dbms.isValidPassword(password))
			throw new SQLException("Username or password is not valid");

		// create new connection
		JDBCConnection c;
		try {
			c = new JDBCConnection(dbms);
		} catch (Exception e) {
			throw new SQLException("Unable to create new connection!");
		}
		// return the connection
		return c;
	}

	/**
	 * Gets information about the possible properties for this driver. The
	 * getPropertyInfo method is intended to allow a generic GUI tool to
	 * discover what properties it should prompt a human for in order to get
	 * enough information to connect to a database. Note that depending on the
	 * values the human has supplied so far, additional values may become
	 * necessary, so it may be necessary to iterate though several calls to the
	 * getPropertyInfo method. Parameters: url - the URL of the database to
	 * which to connect info - a proposed list of tag/value pairs that will be
	 * sent on connect open Returns: an array of DriverPropertyInfo objects
	 * describing possible properties. This array may be an empty array if no
	 * properties are required. Throws: SQLException - if a database access
	 * error occurs
	 */
	@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info)
			throws SQLException {

		return null;
	}

	// ------------------------------------------------------\\
	// /////////--------- UNUSED METHODS ----------\\\\\\\\\\\\
	// ------------------------------------------------------\\

	@Override
	public int getMajorVersion() {
		return 0;
	}

	@Override
	public int getMinorVersion() {
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	@Override
	public boolean jdbcCompliant() {
		return false;
	}

}
