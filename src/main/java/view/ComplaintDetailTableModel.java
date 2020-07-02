package view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Complaint;
import entity.Person;
import entity.ComplaintDetail;

public class ComplaintDetailModelTable extends AbstractTableModel {
	private List<Person> db;
	private String[] colNames = {"Person ID", "Name", "Dob", "Gender", "Address", "Nationality", "Job", "Crime Type"};
	
	public ComplaintDetailModelTable() {}
	
	public void setData(List<Person> db) {
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
		Person person = db.get(rowIndex);
		
		switch (columnIndex) {
		case 0: {
			return person.getId();
		}
		case 1: {
			return person.getName();
		}
		case 2: {
			return person.getDob();
		}
		case 3: {
			return person.getGender();
		}
		case 4: {
			return person.getAddress();
		}
		case 5: {
			return person.getNationality();
		}
		case 6: {
			return person.getJob();
		}
		case 7: {
			return 1;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + columnIndex);
		}
	}
	
	@Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Person per = db.get(rowIndex);
        if (columnIndex == 0) {
        	per.setId((int) value);
        }      
    }
}
