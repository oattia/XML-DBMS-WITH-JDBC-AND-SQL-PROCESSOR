package DBMS;

public interface Table {

	public String getTableName();

	public ColumnIdentifier[] getColIDs();
	
	public String[] getColNames();

	public RecordSet select(String[] columnsNames, Condition condition);

	public void insert(Record newValues) throws Exception;

	public int delete(Condition condition) throws Exception;
	
	public int deleteAll() throws Exception;

	public int update(String[] columnsNames, Object[] values,
			Condition condition) throws Exception;
	
	public int update(String[] columnsNames, String[] values,
			Condition condition) throws Exception;

}