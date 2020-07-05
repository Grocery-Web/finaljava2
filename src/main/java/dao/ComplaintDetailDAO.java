package dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import common.ConnectToProperties;
import entity.ComplaintDetail;
import entity.Gender;
import entity.Person;

public class ComplaintDetailDAO {
	public List<Integer> findAllPersonByComplaintId(int id) {
		List<Integer> personIdList = new ArrayList<Integer>();

		try (var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call findAllPersonByComplaintId(?)}");) {
			ps.setInt(1, id);

			var rs = ps.executeQuery();
			while (rs.next()) {
				personIdList.add(rs.getInt("personId"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return personIdList;
	}
	
	public HashMap<Person, String> getPeopleListByComplaintId(int id) {
		HashMap<Person, String> map = new HashMap<Person, String>();
		

		try (var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call getComplaintDetailByComplaintId(?)}");) {
			ps.setInt(1, id);

			var rs = ps.executeQuery();
			while (rs.next()) {
				PersonDAO personDAO = new PersonDAO();
				int personId = rs.getInt("personId");
				Person person = personDAO.findPersonById(personId);		
				map.put(person, rs.getString("crimeType"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return map;
	}

	public void setComplaintDetail(ComplaintDetail comDetail) {
		try (var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call setComplaintDetail(?,?,?)}");) {
			ps.setInt(1, comDetail.getPersonId());
			ps.setInt(2, comDetail.getCompId());
			ps.setString(3, comDetail.getCrimeType());
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Set Details Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public HashMap<Person, String> getComplaintDetails() {
		HashMap<Person, String> complaintDetails = new HashMap<Person, String>();
		PersonDAO perDAO = new PersonDAO();
		try(
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call getAllComplaintDetail}");
				ResultSet rs = ps.executeQuery();
		)
		{
			while (rs.next()) {
				Person per = perDAO.findPersonById(rs.getInt("personId"));
				complaintDetails.put(per,rs.getString("crimeType"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return complaintDetails;
	}
	
	public void removePerson(int personId, int compId) {
		try(
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call removePerson(?,?)}");
		)
		{
			ps.setInt(1, personId);
			ps.setInt(2, compId);
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Delete Sucessfully", "Success", JOptionPane.OK_OPTION|JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
}
