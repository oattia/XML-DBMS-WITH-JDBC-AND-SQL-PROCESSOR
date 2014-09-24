package DBMS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

@SuppressWarnings("serial")
public class Config extends HashMap<String,String> {

	private String filename = "JDBC.config";
	
	Config() {
		// path of configuration file:
		String slash = File.separatorChar + "";
		String path = System.getProperty("user.home") + slash + filename;
		File configFile = new File(path);
		// read configuration file:
		try {
			BufferedReader reader = new BufferedReader(new FileReader(configFile));
			String line = null;
			while ((line = reader.readLine()) != null) {
			    String[] array = line.split("=");
			    if (array.length == 2) {
			    	this.put(array[0], array[1]);
			    }
			}
			reader.close();
		} catch (IOException e1) {
		}
	}
	
}
