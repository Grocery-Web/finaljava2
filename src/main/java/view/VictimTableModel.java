package view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import entity.Victim;

public class VictimTableModel extends AbstractTableModel{
	private List<Victim> db;

	private String[] colNames = {"Personal ID", "Name" , "Gender", "Nationality", "Status", "DeathTime", "DeathPlace", "DeathReason", "Incident Name"};
	
	public VictimTableModel() {}
	
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
		return 9;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Victim victim = db.get(rowIndex);
		
		switch (columnIndex) {
		case 0: {
			return victim.getPersonalId();
		}
		case 1: {
			return victim.getName();
		}
		case 2: {
			return victim.getGender().toString();
		}
		case 3: {
			return victim.getNationality();
		}
		case 4: {
			if(victim.isStatus()) {
				return "Alive";
			}else {
				return "Dead";
			}
		}
		case 5: {
			return victim.getDeathTime();
		}
		case 6: {
			return victim.getDeathPlace();
		}
		case 7: {
			return victim.getDeathReason();
		}
		case 8: {
			return victim.getIncidentName();
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + columnIndex);
		}
	}
}
