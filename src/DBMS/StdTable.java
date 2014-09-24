package DBMS;

import java.io.File;
import java.nio.file.Files;

public class StdTable implements Table {

	private String tableName;
	private File tableFile;
	private File tmpFile;
	private File dtdFile;
	private ColumnIdentifier[] columnsId;

	public StdTable(String tableName, File tableFile,
			ColumnIdentifier[] columnsId) {
		this.tableName = tableName;
		this.tableFile = tableFile;
		this.columnsId = columnsId;
		this.tmpFile = new File(tableFile.getParentFile().getAbsolutePath()
				+ File.separatorChar + tableName + ".tmp");
		this.dtdFile = new File(tableFile.getParentFile().getAbsolutePath()
				+ File.separatorChar + tableName + ".dtd");
	}

	public String getTableName() {
		return tableName;
	}

	public ColumnIdentifier[] getColIDs() {
		return columnsId;
	}

	public String[] getColNames() {
		// return array of names of all columns of the table
		// of the database of the project.
		String[] ret = new String[columnsId.length];
		for (int i = 0; i < columnsId.length; i++)
			ret[i] = columnsId[i].getColumnName();
		return ret;
	}

	public void insert(Record newValues) throws Exception {

		// rename table file to a "tableName.tmp":
		Files.copy(tableFile.toPath(), tmpFile.toPath());

		// open tmpFile for read
		XMLHandler tempFileXMLHandler = new XMLHandler(tmpFile, columnsId,
				this, false);

		// open tableFile for write
		XMLHandler tableFileXMLHandler = new XMLHandler(tableFile, columnsId,
				this, true);
		// read all elements of tmpFile and write them into tableFile
		Record readRecord;
		while ((readRecord = tempFileXMLHandler.readNextRecord()) != null) {
			tableFileXMLHandler.writeNextRecord(readRecord);
		}

		// write the new record inside tableFile
		tableFileXMLHandler.writeNextRecord(newValues);
		// close tmpFile
		tempFileXMLHandler.close();
		// close tableFile
		tableFileXMLHandler.close();
		// delete tmpFile
		tmpFile.delete();

		DTDGenerator.main(new String[] { tableFile.getAbsolutePath(),
				dtdFile.getAbsolutePath() });
	}

	public RecordSet select(String[] columnsNames, Condition condition) {

		// make new instance
		RecordSet ret = new RecordSet(columnsNames);

		// open tableFile for read
		XMLHandler hndl = new XMLHandler(tableFile, columnsId, this, false);

		// loop
		Record readRecord;
		try {
			while ((readRecord = hndl.readNextRecord()) != null) {
				if (condition == null || condition.meetsCondition(readRecord)) {
					Record r = new Record(this);
					for (String col : columnsNames)
						r.setCell(col, readRecord.getValue(col));
					ret.add(r);
				}
			}
		} catch (Exception e) {
			// do nothing.
		}

		// close hndl:
		hndl.close();

		// return the record set
		return ret;
	}

	public int delete(Condition condition) throws Exception {
		int matched = 0;
		Record readRecord;

		// rename table file to a "tableName.tmp":
		Files.copy(tableFile.toPath(), tmpFile.toPath());

		// open tmpFile for read
		XMLHandler tempFileXMLHandler = new XMLHandler(tmpFile, columnsId,
				this, false);
		// open tableFile for write
		XMLHandler tableFileXMLHandler = new XMLHandler(tableFile, columnsId,
				this, true);
		// read all elements of tmpFile and write them into tableFile while
		// !meet condition
		while ((readRecord = tempFileXMLHandler.readNextRecord()) != null) {
			if (condition != null && !condition.meetsCondition(readRecord)) {
				tableFileXMLHandler.writeNextRecord(readRecord);
			} else {
				// delete:
				matched++;
			}
		}
		// close tmpFile
		tempFileXMLHandler.close();
		// close tableFile
		tableFileXMLHandler.close();
		// delete tmpFile
		tmpFile.delete();
		// return number of matched rows:
		return matched;
	}

	public int update(String[] columnsNames, Object[] values,
			Condition condition) throws Exception {

		int matched = 0;
		Record readRecord;

		// rename table file to a "tableName.tmp":
		Files.copy(tableFile.toPath(), tmpFile.toPath());

		// open tmpFile for read
		XMLHandler tempFileXMLHandler = new XMLHandler(tmpFile, columnsId,
				this, false);
		// open tableFile for write
		XMLHandler tableFileXMLHandler = new XMLHandler(tableFile, columnsId,
				this, true);
		// read all elements of tmpFile and write them into tableFile and update
		// records if it meet the condition
		while ((readRecord = tempFileXMLHandler.readNextRecord()) != null) {
			if (condition == null || condition.meetsCondition(readRecord)) {
				matched++;
				for (int i = 0; i < columnsNames.length; i++) {
					readRecord.setCell(columnsNames[i], values[i]);
				}
			}
			tableFileXMLHandler.writeNextRecord(readRecord);
		}
		// close tmpFile
		tempFileXMLHandler.close();
		// close tableFile
		tableFileXMLHandler.close();
		// delete tmpFile
		tmpFile.delete();
		// return number of matched rows:
		return matched;
	}

	public int update(String[] columnsNames, String[] values,
			Condition condition) throws Exception {

		Record r = new Record(columnsNames, values, this);
		Object[] obj = new Object[columnsNames.length];
		for (int i = 0; i < columnsNames.length; i++)
			obj[i] = r.getValue(columnsNames[i]);
		return update(columnsNames, obj, condition);

	}

	@Override
	public int deleteAll() throws Exception {
		return 0;
	}

}
