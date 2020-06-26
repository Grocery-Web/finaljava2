package View;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import Entity.Complaint;

public class ComplaintTableModel extends AbstractTableModel{
	private List<Complaint> db;
	private String[] colNames = {"Id", "Datetime", "Place", "Declaration People", "Detail", "Status"};
	
	public ComplaintTableModel() {}
	
	public void setData(List<Complaint> db) {
		this.db = db;
	}
	
	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return colNames[column];
	}

	@Override
	public int getRowCount() {
//		return db.size();
		return 1;
	}

	@Override
	public int getColumnCount() {
		return 6;
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
			return complaint.getVictimName();
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
}
