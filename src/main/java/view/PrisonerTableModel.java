package view;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.table.AbstractTableModel;

import dao.ComplaintDAO;
import dao.PersonDAO;
import entity.Complaint;
import entity.Criminal;
import entity.Person;
import entity.Prisoner;


public class PrisonerTableModel extends AbstractTableModel{
	private List<Prisoner> db;
	private int remaindays;
	
	// CURRENT DATE
	long millis = System.currentTimeMillis();
	java.sql.Date today = new java.sql.Date(millis);

	private String[] colNames = {"PrisonerId", "Name" , "Gender", "Nationality", "Type", "Date in Jail", "End Date", "Term (days)", "Prison Name", "Remain Days"};
	
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
		return 10;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Prisoner prisoner = db.get(rowIndex);
		if(prisoner.getEndDate() != null) {
			remaindays = (int) getDateDiff(today, prisoner.getEndDate(), TimeUnit.DAYS);
		}
		
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
		case 9: {
			return remaindays;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + columnIndex);
		}
	}
	
//	@Override
//	public Class<?> getColumnClass(int columnIndex) {
//	    Class retVal = Object.class;
//
//	    if(getRowCount() > 0)
//	    	System.out.println("0k");
//	        retVal =  getValueAt(0, columnIndex).getClass();
//
//	    return retVal;
//	}
	
	public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
	    long diffInMillies = date2.getTime() - date1.getTime();
	    return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
	}
}
