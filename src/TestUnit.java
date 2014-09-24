import java.io.File;
import java.sql.SQLException;
import org.junit.Test;
import DBMS.ColumnIdentifier;
import DBMS.DBMS;
import DBMS.Database;
import DBMS.Record;
import DBMS.RecordSet;
import DBMS.StdDBMS;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestUnit {
	JDBCResultSet resSet;

	// database creation abd el aziz part
	@Test
	public void creatDB() {
		String query = "create database a11";
		DBMS ddd;
		JDBCStatement s = null;
		try {
			ddd = new StdDBMS();
			s = new JDBCStatement(ddd, new JDBCConnection(ddd));
			s.execute(query);
			assertEquals("DataBase Created", s.getTestString());
			File file = new File("E:\\Databases\\a11");
			file.deleteOnExit();
			// file.deleteOnExit();
			s.setTestString(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void creatDB1() {
		String query = "create database n23";
		DBMS ddd;
		JDBCStatement s = null;
		try {
			ddd = new StdDBMS();
			s = new JDBCStatement(ddd, new JDBCConnection(ddd));
			s.execute(query);
			if (s != null)
				assertEquals("DataBase Created", not(s.getTestString()));
			else
				assertEquals("1", not("2"));
			File file = new File("E:\\Databases\\A123");
			file.deleteOnExit();
			// file.deleteOnExit();
			s.setTestString(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// all operations #omar_yousry part
	@Test
	public void operationTest() throws Exception {
		String query = "create database C123";
		File file = new File("O:\\Databases\\C123");
		//file.deleteOnExit();
		DBMS ddd;
		JDBCStatement s = null;
		try {
			ddd = new StdDBMS();
			s = new JDBCStatement(ddd, new JDBCConnection(ddd));
			s.execute(query);
			assertEquals("DataBase Created", s.getTestString());
			s.setTestString(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		query = "use C123";
		
		s.execute(query);

		assertEquals("used", s.getTestString());
		File file1 = new File("O:\\Databases\\C123\\newTb2");
//			s.execute("create table newTb2 (r1 Integer , r2 Varchar , r3 boolean)");

		s.setTestString(null);
	}

	@Test
	public void OperationTest1() throws Exception {
		JDBCStatement s = null;
		String query = "create database C123";
		File file = new File("O:\\Databases\\C123");
		DBMS ddd;
		try {
			ddd = new StdDBMS();
			s = new JDBCStatement(ddd, new JDBCConnection(ddd));
			s.setTestString(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		query = "use C123";
		s.execute(query);
		s.execute("insert into newTb2 (r1 , r2 , r3) values (10 , 'omar' , true)");
		assertEquals("inserted", s.getTestString());
		s.setTestString(null);

	}

	@Test
	public void OperationTest2() throws Exception {
		JDBCStatement s = null;
		String query = "create database C123";
		File file = new File("O:\\Databases\\C123");
		DBMS ddd;
		try {
			ddd = new StdDBMS();
			s = new JDBCStatement(ddd, new JDBCConnection(ddd));
		} catch (Exception e) {
			e.printStackTrace();
		}
		query = "use C123";
		s.execute(query);
		s.execute("select (c1, c2) from newTb2 where c3 = true");
		assertEquals("selected", s.getTestString());
		s.setTestString(null);

	}

	@Test
	public void OperationTest3() throws Exception {
		JDBCStatement s = null;
		String query = "create database C123";
		File file = new File("O:\\Databases\\C123");
		DBMS ddd;
		try {
			ddd = new StdDBMS();
			s = new JDBCStatement(ddd, new JDBCConnection(ddd));
			s.setTestString(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		query = "use C123";
		s.execute(query);
		s.execute("delete from newTb2 where c3 = true");
		assertEquals("deleted", s.getTestString());
		s.setTestString(null);

	}

	@Test
	public void OperationTest4() throws Exception {
		JDBCStatement s = null;
		String query = "create database C123";
		File file = new File("O:\\Databases\\C123");
		DBMS ddd;
		try {
			ddd = new StdDBMS();
			s = new JDBCStatement(ddd, new JDBCConnection(ddd));
			s.setTestString(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		query = "use C123";
		s.execute(query);
		s.execute("select (c1, c2) from newTb2 where c3 = true");
		assertEquals("selected", s.getTestString());
		s.setTestString(null);
		file = new File("O:\\Databases\\C123");
		file.deleteOnExit();
		

	}

	// omar barakat part

	public void test1() throws SQLException {
		RecordSet rs = new RecordSet(new String[] { "courseName", "courseCode",
				"maxGrade", "maxNumOfSt", "minNumOfSt" });
		testAddTable();
		if (getFoeDB() == null) {
			System.out.println("Error DB null!");
			return;
		}

		Record newValues = new Record(new String[] { "courseName",
				"courseCode", "maxGrade", "maxNumOfSt", "minNumOfSt" },
				new Object[] { "math", new Integer(1), new Integer(10),
						new Integer(50), new Integer(76) }, getFoeDB()
						.getTable("courses"));
		try {
			getFoeDB().getTable("courses").insert(newValues);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		rs.add(newValues);

		newValues = new Record(new String[] { "courseName", "courseCode",
				"maxGrade", "maxNumOfSt", "minNumOfSt" }, new Object[] {
				"digital", new Integer(2), new Integer(99), new Integer(56),
				new Integer(187) }, getFoeDB().getTable("courses"));
		try {
			getFoeDB().getTable("courses").insert(newValues);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		rs.add(newValues);

		newValues = new Record(new String[] { "courseName", "courseCode",
				"maxGrade", "maxNumOfSt", "minNumOfSt" },
				new Object[] { "SQL", new Integer(3), new Integer(5),
						new Integer(34), new Integer(0) }, getFoeDB().getTable(
						"courses"));
		try {
			getFoeDB().getTable("courses").insert(newValues);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		rs.add(newValues);
		resSet = new JDBCResultSet(rs, null);
		System.out.println(resSet.findColumn("courseName"));

	}

	@Test
	public void test2() throws SQLException {
		test1();
		try {
			assertEquals("true", resSet.last() + "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test3() throws SQLException {
		test1();
		try {
			System.out.println(resSet.findColumn("courseName"));
			assertEquals("1", "" + resSet.findColumn("courseName"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test4() throws SQLException {
		test1();
		try {
			assertEquals(false, resSet.isAfterLast());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void test5() throws SQLException {
		test1();
		try {
			assertEquals(true, resSet.isFirst());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void test7() throws SQLException {
		test1();
		try {
			resSet.afterLast();
			assertEquals(true, resSet.previous());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void test8() throws SQLException {
		test1();
		try {
			assertEquals(true, resSet.isLast());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void test9() throws SQLException {
		test1();
		try {
			assertEquals(false, resSet.isFirst());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void test10() throws SQLException {
		test1();
		try {
			assertEquals(true, resSet.first());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void test11() throws SQLException {
		test1();
		try {
			assertEquals(false, resSet.isLast());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void test12() throws SQLException {
		test1();
		try {
			assertEquals(true, resSet.isFirst());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void test13() throws SQLException {
		test1();
		resSet.absolute(-1);
		try {
			assertEquals(3, resSet.getInt(2));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void test14() throws SQLException {
		test1();
		try {
			assertEquals(3, resSet.getInt("courseCode"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void test15() throws SQLException {
		test1();
		try {
			assertEquals("SQL", resSet.getString(1));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void test16() throws SQLException {
		test1();
		try {
			assertEquals("SQL", resSet.getString("courseName"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Database getFoeDB() {

		DBMS mainDBMS = null;
		try {
			mainDBMS = new StdDBMS();
		} catch (Exception e1) {
			System.out.println("Error: can't make mainDBMS, " + e1);
			return null;
		}

		// make sure foe exists:
		try {
			mainDBMS.createDB("foe");
		} catch (Exception e) {
			// System.out.println("foe database already exists!");
		}

		// get foe
		try {
			mainDBMS.setUsedDB("foe");
		} catch (Exception e) {
			System.out.println("DB not found!");
		}

		return mainDBMS.getUsedDB();
	}

	public static void testAddTable() {
		Database foe = getFoeDB();
		try {
			ColumnIdentifier[] colIDs = new ColumnIdentifier[5];
			colIDs[0] = new ColumnIdentifier("courseName", String.class);
			colIDs[1] = new ColumnIdentifier("courseCode", Integer.class);
			colIDs[2] = new ColumnIdentifier("maxGrade", Integer.class);
			colIDs[3] = new ColumnIdentifier("maxNumOfSt", Integer.class);
			colIDs[4] = new ColumnIdentifier("minNumOfSt", Integer.class);
			foe.addTable("courses", colIDs);
		} catch (Exception e) {
			System.out.println("table courses exist!");
		}
	}

}
