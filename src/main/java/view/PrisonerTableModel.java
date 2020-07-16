package view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.ComplaintDAO;
import dao.PersonDAO;
import entity.Complaint;
import entity.Criminal;
import entity.Person;
import entity.Prisoner;


public class PrisonerTableModel extends AbstractTableModel{
	private List<Prisoner> db;

	private String[] colNames = {"PrisonerId", "Name" , "Gender", "Nationality", "Type", "Date in Jail", "End Date", "Term (days)", "Prison Name"};
	
	public PrisonerTableModel() {}
	
	public void setData(List<Prisoner> db) {
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

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Prisoner prisoner = db.get(rowIndex);
			
		switch (columnIndex) {
		case 0: {
			return prisoner.getPrisonerId();
		}
		case 1: {
			return prisoner.getName();
		}
		case 2: {
			return prisoner.getGender().toString();
		}
		case 3: {
			return prisoner.getNationality();
		}
		case 4: {
			return prisoner.getType();
		}
		case 5: {
			return prisoner.getStartDate();
		}
		case 6: {
			return prisoner.getEndDate();
		}
		case 7: {
			return prisoner.getDuration();
		}
		case 8: {
			return prisoner.getPrisonName();
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + columnIndex);
		}
	}
}
