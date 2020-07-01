package dao;

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
}
