package view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Complaint;
import entity.Person;
import entity.Suspect;
import entity.ComplaintDetail;

public class ComplaintDetailTableModel extends AbstractTableModel {
	private List<Suspect> db;
	private String[] colNames = {"Person ID", "Name", "Dob", "Gender", "Address", "Nationality", "Job", "Crime Type"};
	
	public ComplaintDetailTableModel() {}
	
	public void setData(List<Suspect> db) {
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
		Suspect suspect = db.get(rowIndex);
		
		switch (columnIndex) {
		case 0: {
			return suspect.getPerson().getId();
		}
		case 1: {
			return suspect.getPerson().getName();
		}
		case 2: {
			return suspect.getPerson().getDob();
		}
		case 3: {
			return suspect.getPerson().getGender();
		}
		case 4: {
			return suspect.getPerson().getAddress();
		}
		case 5: {
			return suspect.getPerson().getNationality();
		}
		case 6: {
			return suspect.getPerson().getJob();
		}
		case 7: {
			return suspect.getCrimeType();
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + columnIndex);
		}
	}
	
	@Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
		Suspect suspect = db.get(rowIndex);
        if (columnIndex == 0) {
        	suspect.getPerson().setId((int) value);
        }      
    }
}
