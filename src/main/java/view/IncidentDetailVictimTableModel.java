package view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import dao.PersonDAO;
import entity.Criminal;
import entity.Person;
import entity.Victim;

public class IncidentDetailVictimTableModel extends AbstractTableModel {

	private List<Victim> db;
	private String[] colNames = {"Id", "Name", "Gender", "Date Of Birth", "Occupation", "Nationality", "Address", "Status"};
	
	public IncidentDetailVictimTableModel() {}
	
	public void setData(List<Victim> db) {
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
	public Object getValueAt(int rowIndex, int columnIndex) {
		Victim victim = db.get(rowIndex);
		PersonDAO personDAO = new PersonDAO();
		Person per = new Person();
		per = personDAO.findPersonById(victim.getPersonalId());
		
		switch (columnIndex) {
		case 0: {
			return victim.getId();
		}
		case 1: {
			return per.getName();
		}
		case 2: {
			return per.getGender();
		}
		case 3: {
			return per.getDob();
		}
		case 4: {
			return per.getJob();
		}
		case 5: {
			return per.getNationality();
		}
		case 6: {
			return per.getAddress();
		}
		case 7: {
			if (victim.isStatus() == true) {
				return "Alive";
			} else {
				return "Dead";
			}
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + columnIndex);
		}
	}

}
