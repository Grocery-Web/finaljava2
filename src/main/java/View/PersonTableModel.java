package View;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import Entity.Person;


public class PersonTableModel extends AbstractTableModel {
	
	private List<Person> db;
	private String[] colNames = {"Id", "Name", "Gender", "Date Of Birth", "Occupation", "Nationality", "Address"};
	
	public PersonTableModel() {}
	
	public void setData(List<Person> db) {
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
		return 7;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Person person = db.get(rowIndex);
		
		switch (columnIndex) {
		case 0: {
			return person.getId();
		}
		case 1: {
			return person.getName();
		}
		case 2: {
			return person.getGender();
		}
		case 3: {
			return person.getDob();
		}
		case 4: {
			return person.getJob();
		}
		case 5: {
			return person.getNationality();
		}
		case 6: {
			return person.getAddress();
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + columnIndex);
		}
	}

}
