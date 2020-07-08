package view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Complaint;

public class ComplaintTableModel extends AbstractTableModel{
	private List<Complaint> db;
	private String[] colNames = {"Id", "Name" , "Datetime", "Place", "Declaration People", "Detail"};
	
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
			return complaint.getName();
		}
		case 2: {
			Date getDateTime = new java.sql.Timestamp(complaint.getDatetime().getTime());
			System.out.println("1 " + getDateTime);
			String dateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a").format(getDateTime);
			System.out.println("2 " + dateTime);
			return dateTime;
		}
		case 3: {
			return complaint.getPlace();
		}
		case 4: {
			return complaint.getDeclarantName();
		}
		case 5: {
			return complaint.getDetail();
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
