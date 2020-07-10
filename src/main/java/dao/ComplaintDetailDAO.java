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

				// Verify person with more than one crimeType and add they into Map
				if (map.size() > 0) {
					boolean flag = false;
					for (Person perInMap : map.keySet()) {
						if(perInMap.getPersonalId() == personId) {
							flag = true;
							String crimeType = map.get(perInMap) + "|" + rs.getString("crimeType");
							map.put(perInMap, crimeType);
							break;
						}
					}
					
					if(flag != true) {
						map.put(person, rs.getString("crimeType"));
					}
				}else {
					map.put(person, rs.getString("crimeType"));
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
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
	
	public void removePerson(int personId, int compId) {
		try(
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call removePersoninComplaintDetail(?,?)}");
		)
		{
			ps.setInt(1, personId);
			ps.setInt(2, compId);
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Remove Person in Complaint Sucessfully", "Success", JOptionPane.OK_OPTION|JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public List<String> getCrimeTypeOfPerson(int personId, int compId) {
		List<String> crimeTypeList = new ArrayList<String>();
		
		try (var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call getCrimeTypeOfPerson(?,?)}");) {
			ps.setInt(1, personId);
			ps.setInt(2, compId);
			
			var rs = ps.executeQuery();
			while (rs.next()) {
				crimeTypeList.add(rs.getString("crimeType"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
		
		return crimeTypeList;
	}
}
