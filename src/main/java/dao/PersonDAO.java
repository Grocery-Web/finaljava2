package dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import common.ConnectToProperties;
import entity.Gender;
import entity.Person;

public class PersonDAO {
	public List<Integer> getAllID() {
		List<Integer> listID = new ArrayList<Integer>();
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call getAllID}");
				ResultSet rs = ps.executeQuery();
			) 
		{
			while (rs.next()) {
				listID.add(rs.getInt(1));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
		return listID;
	}
	
	public List<Person> getAlivePeople() {
		List<Person> list = new ArrayList<Person>();
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call getAlivePeople}");
				ResultSet rs = ps.executeQuery();
			) 
		{
			while (rs.next()) {
				Person per = new Person();
				per.setPersonalId(rs.getInt("id"));
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
				PreparedStatement ps = connect.prepareCall("{call findLivePersonById(?)}");
		)
		{
			ps.setInt(1, id);
			var rs = ps.executeQuery();
			while (rs.next()) {
				per.setPersonalId(rs.getInt("id"));
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
				per.setAlive(rs.getBoolean("alive"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return per;
	}
	
	public void addPerson(Person per,String userId) {
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call insertPerson(?,?,?,?,?,?,?,?,?,?)}");
			) 
		{
			ps.setInt(1, per.getPersonalId());
			ps.setString(2, per.getName());
			if(per.getGender().name().equals("male")) {
				ps.setBoolean(3, true);
			}else {
				ps.setBoolean(3, false);
			}
			ps.setDate(4, new java.sql.Date(per.getDob().getTime()));
			ps.setString(5, per.getAddress());
			ps.setString(6, per.getImage());
			ps.setString(7, per.getNationality());
			ps.setString(8, per.getJob());
			ps.setString(9, userId);
			ps.setBoolean(10, per.getAlive());
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Insert new person successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void deletePerson(int id,String userId) {
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call deletePerson(?,?)}");
			) 
		{
			ps.setInt(1, id);
			ps.setString(2, userId);
			if (ps.executeUpdate() > 0) {
				JOptionPane.showMessageDialog(null, "Delete person successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "PersonalID not found", "Failed", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
	    return new java.sql.Date(date.getTime());
	}
	
	public void updatePersonByID (Person acc,String userId) {
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call updatePerson(?,?,?,?,?,?,?,?,?,?)}");
			) 
		{
			ps.setString(1, acc.getName());
			if (acc.getGender().toString() == "male") {
				ps.setBoolean(2, true);
			}else {
				ps.setBoolean(2, false);
			}
			ps.setDate(3, convertJavaDateToSqlDate(acc.getDob()));
			ps.setString(4, acc.getAddress());
			ps.setString(5, acc.getImage());
			ps.setString(6, acc.getNationality());
			ps.setString(7, acc.getJob());	
			ps.setBoolean(8, acc.getAlive());
			ps.setString(9, userId);
			ps.setInt(10, acc.getPersonalId());
			
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Update account successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void deleteByID (int id) {
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call deleteById(?)}");
			) 
		{
			ps.setInt(1, id);
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Deleted account successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public int checkPersonInJail (int personalId) {
		int count = 0;
		
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call checkPersonInJail(?)}");
			) 
		{
			ps.setInt(1, personalId);
			var rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
		
		return count;
	}
	
	public int checkPersonIsCriminal (int personalId) {
		int count = 0;
		
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call checkPersonIsCriminal(?)}");
			) 
		{
			ps.setInt(1, personalId);
			var rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
		
		return count;
	}
	
	public int checkPersonExistedInComplaint (int personalId, int compId) {
		int count = 0;
		
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call checkPersonExistedInComplaint(?,?)}");
			) 
		{
			ps.setInt(1, compId);
			ps.setInt(2, personalId);
			var rs = ps.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
		
		return count;
	}
	
	public Person getPersonById(int id) {
		Person per = new Person();
		
		try(
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call getPersonById(?)}");
		)
		{
			ps.setInt(1, id);
			var rs = ps.executeQuery();
			while (rs.next()) {
				per.setPersonalId(rs.getInt("id"));
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
				per.setAlive(rs.getBoolean("alive"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return per;
	}
}
