package view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Criminal;
import entity.Person;

import dao.PersonDAO;

public class IncidentDetailTableModel extends AbstractTableModel {
	
	private List<Criminal> db;
	
	private String[] colNames = {"ID", "Name" , "Gender", "DOB", "Address", "Nationality", "Punishment", "Rating", "Status"};
	
	public IncidentDetailTableModel() {}
	
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
		return 9;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Criminal criminal = db.get(rowIndex);
		
		switch (columnIndex) {
		case 0: {
			return criminal.getCriminalId();
		}
		case 1: {
			return criminal.getName();
		}
		case 2: {
			return criminal.getGender();
		}
		case 3: {
			return criminal.getDob();
		}
		case 4: {
			return criminal.getAddress();
		}
		case 5: {
			return criminal.getNationality();
		}
		case 6: {
			return criminal.getPunishment();
		}
		case 7: {
			return criminal.getRating();
		}
		case 8: {
			if(criminal.isAlive()) {
				return "Alive";
			}else {
				return "Dead";
			}
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + columnIndex);
		}
	}

}
