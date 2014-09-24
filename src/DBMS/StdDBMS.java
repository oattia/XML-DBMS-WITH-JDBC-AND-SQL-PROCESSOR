package DBMS;

import java.io.File;

public class StdDBMS implements DBMS {

	private StdDatabase usedDatabase;
	private String path;
	private String slash;
	private File DBDir;
	private Config config;
	
	public StdDBMS() throws Exception {

		// read configuration file:
		config = new Config();
		
		// get path
		slash = File.separatorChar + "";
		path = System.getProperty("user.home") + slash + "Databases" + slash;
		
		// overload path from file:
		if (config.get("path") != null) {
			path = config.get("path");
			if (!path.endsWith(slash))
				path += slash;
			System.out.println("new path:" + path);
		}
		
		// make sure the database directory exists
		DBDir = new File(path);
		if (!DBDir.exists()) {
			if (DBDir.mkdir() == false) {
				throw new Exception("Cannot open/make Database directory");
			}
		}
		
		// no database is loaded
		usedDatabase = null;
		
	}
	
	public boolean isValidUserName(String username) {
		String s_username = config.get("username");
		if (s_username == null)
			s_username = "";
		if (username == null)
			username = "";
		return s_username.equals(username);
	}
	
	public boolean isValidPassword(String password) {
		String s_password = config.get("password");
		if (s_password == null)
			s_password = "";
		if (password == null)
			password = "";
		return s_password.equals(password);
	}

	@Override
	public void createDB(String DBName) throws Exception {
		File newDB = new File(path + DBName);
		if (newDB.exists())
			throw new Exception("Database exists!");
		
		// create DB directory
		newDB.mkdir();
	}

	@Override
	public void removeDB(String DBName) {
		
	}
	
	@Override
	public void setUsedDB(String dbName) throws Exception {
		File usedDB = new File(path + dbName);
		if (!usedDB.exists()) {
			throw new Exception("Database doesn't exist!");
		}
		usedDatabase = new StdDatabase(usedDB);
	}
	
	@Override
	public StdDatabase getUsedDB() {
		return usedDatabase;
	}
	
}
