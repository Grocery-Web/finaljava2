package dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import common.ConnectToProperties;
import entity.ComplaintDetail;
import entity.Person;
import dao.PersonDAO;

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
		HashMap<Person, String> personMap = new HashMap<Person, String>();
		

		try (var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call getComplaintDetailByComplaintId(?)}");) {
			ps.setInt(1, id);

			var rs = ps.executeQuery();
			while (rs.next()) {
				PersonDAO personDAO = new PersonDAO();
				int personId = rs.getInt("personId");
				String crimeType = rs.getString("crimeType");
				Person person = PersonDAO.findPersonById(personId);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return personMap;
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
}
