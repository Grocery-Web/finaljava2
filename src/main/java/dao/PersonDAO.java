package dao;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import common.ConnectToProperties;
import entity.Complaint;
import entity.Gender;
import entity.Person;

public class PersonDAO {
	public List<Person> getAllAccount() {
		List<Person> list = new ArrayList<Person>();
		boolean gen;
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call getAllPerson}");
				ResultSet rs = ps.executeQuery();
			) 
		{
			while (rs.next()) {
				Person per = new Person();
				per.setId(rs.getInt("id"));
				per.setName(rs.getString("name"));
				
				Gender gender;
				if(rs.getBoolean("gender")) {
					gender = Gender.male;
				}else {
					gender = Gender.female;
				}
				per.setGender(gender);
				per.setDob(rs.getDate("dob"));
				per.setAddress(rs.getString("address"));
				per.setNationality(rs.getString("nationality"));
				per.setImage(rs.getString("image"));
				per.setJob(rs.getString("job"));
				
				list.add(per);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return list;
	}
	
	public Person findPersonById(int id) {
		Person per = new Person();
		
		try(
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call findPersonById(?)}");
		)
		{
			ps.setInt(1, id);
			var rs = ps.executeQuery();
			while (rs.next()) {
				per.setId(rs.getInt("id"));
				per.setName(rs.getString("name"));
				
				Gender gender;
				if(rs.getBoolean("gender")) {
					gender = Gender.male;
				}else {
					gender = Gender.female;
				}
				
				per.setGender(gender);
				per.setDob(rs.getDate("dob"));
				per.setAddress(rs.getString("address"));
				per.setNationality(rs.getString("nationality"));
				per.setImage(rs.getString("image"));
				per.setJob(rs.getString("job"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return per;
	}
	
	private java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
	    return new java.sql.Date(date.getTime());
	}
	
	public void updatePersonByID (int id, Person acc) {
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call updatePersonById(?,?,?,?,?,?,?,?)}");
			) 
		{
			ps.setInt(1, acc.getId());
			ps.setString(2, acc.getName());
			if (acc.getGender().toString() == "male") {
				ps.setBoolean(3, true);
			}
			else ps.setBoolean(3, false);
			ps.setDate(4, convertJavaDateToSqlDate(acc.getDob()));
			ps.setString(5, acc.getAddress());
			ps.setString(6, acc.getImage());
			ps.setString(7, acc.getNationality());
			ps.setString(8, acc.getJob());			
		
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Update account successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
	}
}
