package DBMS;

import java.util.Vector;

@SuppressWarnings("serial")
public class RecordSet extends Vector<Record>{		//changed from hashset to vector

	private String[] attributesNames;

	public RecordSet(String[] attributes) {
		attributesNames = attributes;
	}
	
	public RecordSet(RecordSet rs) {
		attributesNames = new String[rs.attributesNames.length];
		for(int i=0; i<rs.attributesNames.length; i++)
			this.attributesNames[i] = new String(rs.attributesNames[i]);
		for(int i=0; i<rs.size(); i++)
			add(new Record(rs.get(i)));
	}

	public String[] getAttributesNames() {
		return attributesNames;
	}

	public void setAttributesNames(String[] attributesNames) {
		this.attributesNames = attributesNames;
	}
	
	public String getAttributeName(int columnInd) {		// numbering starts 1->n
		if(columnInd>0 && columnInd<=attributesNames.length)
			return attributesNames[columnInd-1];
		return null;
	}
	
	public int getAttributeIndex(String columnName) {	// numbering starts 1->n
		for(int i=0; i<attributesNames.length; i++){
			if(attributesNames[i].equalsIgnoreCase(columnName))
				return i+1;
		}
		return 0;
	}
}