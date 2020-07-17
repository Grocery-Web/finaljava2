package view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.PrisonListDAO;
import entity.PrisonList;
import entity.PrisonerInList;

public class TablePrisonInListModel extends AbstractTableModel {
	
	private List<PrisonerInList> db;
	PrisonListDAO pl = new PrisonListDAO();
	private String[] colNames = {"PersonID", "PrisonerID", "Name", "DOB", "Gender", "Start Date", "Duration (days)", "End","Nationality" };
	
	public TablePrisonInListModel() {}
	
	public void setData(List<PrisonerInList> db) {
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
		return 9;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		PrisonerInList pri = db.get(rowIndex);		
		switch (columnIndex) {
		case 0: {
			return pri.getPersonID();
		}
		case 1: {
			return pri.getPrisonID();
		}
		case 2: {
			return pri.getName();
		}
		case 3: {
			return pri.getDob();
		}
		case 4: {
			return pri.getGender();
		}
		case 5: {
			return pri.getStartDate();
		}
		case 6: {
			return pri.getDuration();
		}
		case 7: {
			return pri.getEndDate();
		}
		case 8: {
			return pri.getNationality();
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + columnIndex);
		}
	}

}
