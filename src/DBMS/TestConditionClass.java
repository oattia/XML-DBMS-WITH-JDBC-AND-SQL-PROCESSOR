package DBMS;

public class TestConditionClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		testAddTable();
		if (getFoeDB() == null) {
			System.out.println("Error DB null!");
			return;
		}
		Record newValues = new Record(new String[] {"courseName", "courseCode", "maxGrade", 
				"maxNumOfSt", "minNumOfSt"}, 
				new Object[]{"math", new Integer(10), new Integer(10), new Integer(50), new Integer(10)},
				getFoeDB().getTable("courses"));
		try {
			getFoeDB().getTable("courses").insert(newValues);
		} catch (Exception e) {
			System.out.println("Error: " + e);
		}
		testSelect(getFoeDB().getTable("courses"));
	}
	
	
	public static void testSelect(Table tbl) {
		Condition cond = new Condition("not((   ((courseCode= 10))) AND ((courseName =\'math\'))) |  courseCode =maxNumOfSt", tbl);
		RecordSet rs = tbl.select(new String[]{"courseName"}, cond);
		for (Record r : rs) {
			System.out.println(r);
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
			//System.out.println("foe database already exists!");
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
		
		// add new table:
		/*try {
			ColumnIdentifier[] colIDs = new ColumnIdentifier[7];
			colIDs[0] = new ColumnIdentifier("name", String.class);
			colIDs[1] = new ColumnIdentifier("integ", Integer.class);
			colIDs[2] = new ColumnIdentifier("longer", Long.class);
			colIDs[3] = new ColumnIdentifier("floating", Float.class);
			colIDs[4] = new ColumnIdentifier("doubler", Double.class);
			colIDs[5] = new ColumnIdentifier("birth", Date.class);
			colIDs[6] = new ColumnIdentifier("logic", Boolean.class);
			foe.addTable("users", colIDs);
		} catch (Exception e) {
			System.out.println("table users exist!");
		}
		
		try {
			ColumnIdentifier[] colIDs = new ColumnIdentifier[5];
			colIDs[0] = new ColumnIdentifier("tarek", String.class);
			colIDs[1] = new ColumnIdentifier("omar", String.class);
			colIDs[2] = new ColumnIdentifier("mostafa", Float.class);
			colIDs[3] = new ColumnIdentifier("iocoder", Date.class);
			colIDs[4] = new ColumnIdentifier("mostafa2", Boolean.class);
			foe.addTable("students", colIDs);
		} catch (Exception e) {
			System.out.println("table students exist!");
		}*/
		
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

	
	public static void testDelete() {
		//Table tbl = getUsersTable();
		//Condition cond = new Condition("name = tito", tbl);
		/*try {
			//tbl.delete(cond);
		} catch (Exception e) {
			fail("some error in delete");
		}*/
	}

	
	public static void testUpdate() {
		/*Table tbl = getUsersTable();
		Condition cond = new Condition("longer > 114", tbl);
		String cols[] = {"name", "logic"};
		Object vals[] = {"toko", "false" };
		try {
			tbl.update(cols, vals, cond);
		} catch (Exception e) {
			fail("some error in update");
		}*/
	}

}
