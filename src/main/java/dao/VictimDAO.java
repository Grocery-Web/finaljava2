package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import common.ConnectToProperties;
import entity.*;

public class VictimDAO {
	public List<Victim> getAllVictims() {
		List<Victim> list = new ArrayList<Victim>();
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call getAllVictims}");
				ResultSet rs = ps.executeQuery();
			) 
		{
			while (rs.next()) {
				Victim victim = new Victim();
				victim.setPersonalId(rs.getInt("personalID"));
				victim.setStatus(rs.getBoolean("status"));
				victim.setDeathTime(rs.getDate("deathTime"));
				victim.setDeathPlace(rs.getString("deathPlace"));
				victim.setDeathReason(rs.getString("deathReason"));
				victim.setComplaintID(rs.getInt("complaintID"));
				victim.setName(rs.getString("name"));
				victim.setNationality(rs.getString("nationality"));
				victim.setIncidentName(rs.getString("complaintName"));
				
				Gender gender;
				if(rs.getBoolean("gender")) {
					gender = Gender.male;
				}else {
					gender = Gender.female;
				}
				victim.setGender(gender);
				
				list.add(victim);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return list;
	}
	
	public void linkNewVictim(Victim victim) {
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call linkNewVictim(?,?,?,?,?,?)}");
			) 
		{
			ps.setInt(1, victim.getPersonalId());
			ps.setBoolean(2, victim.getAlive());
			if (victim.getDeathTime() == null) {
				ps.setNull(3, java.sql.Types.TIMESTAMP);
			} else {
				ps.setTimestamp(3, new java.sql.Timestamp(victim.getDeathTime().getTime()));
			}
			ps.setString(4, victim.getDeathPlace());
			ps.setString(5, victim.getDeathReason());
			ps.setInt(6, victim.getComplaintID());
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Successfully link the victim to incident", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
