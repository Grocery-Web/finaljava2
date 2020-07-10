package view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.PrisonListDAO;
import entity.PrisonList;

public class PrisonListTableModel extends AbstractTableModel{
	
	private List<PrisonList> db;
	PrisonListDAO pl = new PrisonListDAO();
	private String[] colNames = {"ID", "Prison Name", "Address", "Quantity", "Capacity"};
	
	public PrisonListTableModel() {}
	
	public void setData(List<PrisonList> db) {
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
		return 5;
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		PrisonList pri = db.get(rowIndex);		
		switch (columnIndex) {
		case 0: {
			return pri.getId();
		}
		case 1: {
			return pri.getName();
		}
		case 2: {
			return pri.getAddress();
		}
		case 3: {
			return pri.getQuantity();
		}
		case 4: {
			return pri.getCapacity();
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + columnIndex);
		}
	}

}
