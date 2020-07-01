package view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Complaint;
import entity.Person;

public class ComplaintTableModel extends AbstractTableModel{
	private List<Complaint> db;
	private String[] colNames = {"Id", "Datetime", "Place", "Declaration People", "Detail", "Status"};
	
	public ComplaintTableModel() {}
	
	public void setData(List<Complaint> db) {
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
		return 6;
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
		Complaint complaint = db.get(rowIndex);
		
		switch (columnIndex) {
		case 0: {
			return complaint.getId();
		}
		case 1: {
			return complaint.getDatetime();
		}
		case 2: {
			return complaint.getPlace();
		}
		case 3: {
			return complaint.getDeclarantName();
		}
		case 4: {
			return complaint.getDetail();
		}
		case 5: {
			return complaint.isStatus();
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + columnIndex);
		}
	}
	
	@Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Complaint com = db.get(rowIndex);
        if (columnIndex == 0) {
        	com.setId((int) value);
        }      
    }
}
