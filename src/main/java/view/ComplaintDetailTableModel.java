package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.table.AbstractTableModel;

import entity.Person;

public class ComplaintDetailTableModel extends AbstractTableModel{

	private HashMap<Person, String> db;
	private String[] colNames = {"Id", "Name", "Gender", "Date Of Birth", "Occupation", "Nationality", "Address", "Crime Type"};
	
	public ComplaintDetailTableModel() {}
	
	public void setData(HashMap<Person, String> db) {
		this.db = db;
	}
	
	@Override
	public String getColumnName(int column) {
		return colNames[column];
	}

	@Override
	public int getRowCount() {
		if(db != null) {
			return db.size();
		}
		return 0;
	}

	@Override
	public int getColumnCount() {
		return 8;
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
	    if (db.isEmpty()) {
	        return Object.class;
	    }
	    return getValueAt(0, columnIndex).getClass();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		
		if(rowIndex > getRowCount() || columnIndex > getColumnCount()) {
			return "";
		}
		
		String crimeType = (new ArrayList<String>(db.values())).get(rowIndex);
		Person person = (new ArrayList<Person>(db.keySet())).get(rowIndex);
		
		switch (columnIndex) {
		case 0: {
			return person.getPersonalId();
		}
		case 1: {
			return person.getName();
		}
		case 2: {
			return person.getGender();
		}
		case 3: {
			return person.getDob();
		}
		case 4: {
			return person.getJob();
		}
		case 5: {
			return person.getNationality();
		}
		case 6: {
			return person.getAddress();
		}
		case 7: {
			return crimeType;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + columnIndex);
		}
	}
}
