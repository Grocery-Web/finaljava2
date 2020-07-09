package view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.ComplaintDAO;
import dao.PersonDAO;
import entity.Complaint;
import entity.Criminal;
import entity.Gender;
import entity.Person;

public class CriminalTableModel extends AbstractTableModel{
	private List<Criminal> db;
	PersonDAO perDAO = new PersonDAO();
	ComplaintDAO cplDAO = new ComplaintDAO();
	
	private String[] colNames = {"CriminalId", "PersonalId", "Name" , "Gender", "Date Of Birth", "Address", "Nationality", "Relevant Complaint", "Punishment", "Rating"};
	
	public CriminalTableModel() {}
	
	public void setData(List<Criminal> db) {
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
		Criminal cri = db.get(rowIndex);
		
//		Person in case
		int personalId = cri.getPersonalId();
		Person per = perDAO.findPersonById(personalId);
//		Complaint in case
		int complainId = cri.getComplaintId();
		Complaint cpl = cplDAO.findComplaintById(complainId);
		
		switch (columnIndex) {
		case 0: {
			return cri.getCriminalId();
		}
		case 1: {
			return per.getPersonalId();
		}
		case 2: {
			return per.getName();
		}
		case 3: {
			return per.getGender().toString();
		}
		case 4: {
			return per.getDob();
		}
		case 5: {
			return per.getAddress();
		}
		case 6: {
			return per.getNationality();
		}
		case 7: {
			return per.getAddress();
		}
		case 8: {
			return cpl.getName();
		}
		case 9: {
			return cri.getPunishment();
		}
		case 10: {
			return cri.getRating();
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + columnIndex);
		}
	}
}
