//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class Main {
//	
//	public static void main(String[] args) {
//
//		// This is to be CMD main interface required in the problem statement
//		// All of the "Try to"s are necessary
//
//		// Steps :
//		// --------
//		// 1- Try to check for existence of the JDBC driver
//		// 2- Try to obtain and register the JDBC driver to the DriverManager
//		// the previous two steps can be merged into one
//		// 3- Try to obtain the connection from the DriverManager
//		// 4- Try to obtain a statement instance
//		// 5- Try to direct all SQL statements to the statement instance
//		
//		// 1,2: load the JDBC driver (it registers itself automatically :D)
//		// ------------------------------------------------------------------
//		try {
//			Class.forName("JDBCDriver"); // our DBMS Driver :D
//		} catch(ClassNotFoundException e) {
//		    System.err.println("Error loading driver: " + e);
//		}
//		
//		// 3: obtain connection:
//		// ----------------------
//		try {
//			DriverManager.getConnection("jdbc:dana:localhost:foe", "cutepuppy", "69696969");
//		} catch (SQLException e) {
//			System.err.println("Error creating connection: " + e);
//		}
//		
//	}
//}

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Main {

	public static void main(String[] args) {

		// This is to be CMD main interface required in the problem statement
		// All of the "Try to"s are necessary

		// Steps :
		// --------
		// 1- Try to check for existence of the JDBC driver
		// 2- Try to obtain and register the JDBC driver to the DriverManager
		// the previous two steps can be merged into one
		// 3- Try to obtain the connection from the DriverManager
		// 4- Try to obtain a statement instance
		// 5- Try to direct all SQL statements to the statement instance

		// 1,2: load the JDBC driver (it registers itself automatically :D)
		// ------------------------------------------------------------------

		try {
			Class.forName("JDBCDriver"); // our DBMS Driver :D
		} catch (ClassNotFoundException e) {
			System.err.println("Error loading driver: " + e);
			System.err.println("Exit");
			System.exit(0);
		}

		Connection con = null;
		Statement stmt = null;

		// 3: obtain connection:
		// ----------------------
		try {
			String url = "jdbc:JDBC:localhost";
			con = DriverManager.getConnection(url, "sudouser", "123456");
			stmt = con.createStatement();
			Scanner in = new Scanner(System.in);
			while (true) {
				try {
					System.out.print("SQL >>> ");
					String s = in.nextLine();
					stmt.execute(s);
					if (s.trim().equals("#"))
						break;
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
			stmt.close();
			con.close();
			in.close();
		} catch (SQLException e) {
			System.err.println("Error creating connection: " + e);
		}
	}

	public static void test() {
		Result result = JUnitCore.runClasses(TestUnit.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		if (result.wasSuccessful())
			System.out.println("test is successfully done");
		else
			System.out.println("there is an error please re-check ur code");
	}
}
