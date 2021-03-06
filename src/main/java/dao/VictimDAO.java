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
	
	public boolean checkIfPersonExistAsVictim(Victim victim) {
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call checkIfPersonExistAsVictim(?,?)}");
			)
		{
			ps.setInt(1, victim.getPersonalId());
			ps.setInt(2, victim.getComplaintID());
			ResultSet rs = ps.executeQuery();
			if (!rs.isBeforeFirst()) {
				return false;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
		return true;
	}
	
	public void linkNewVictim(Victim victim, String userId) {
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call linkNewVictim(?,?,?,?,?,?,?)}");
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
			ps.setString(7, userId);
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Successfully link the victim to incident", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void removeVictimbyPersonalId(int personalId) {
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call removeVictimbyPersonalId(?)}");
			) 
		{
			ps.setInt(1, personalId);
			ps.executeUpdate();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
	}
}
