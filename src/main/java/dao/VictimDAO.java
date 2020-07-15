package dao;

import java.sql.*;

import javax.swing.JOptionPane;

import common.ConnectToProperties;
import entity.*;

public class VictimDAO {
	public void linkNewVictim(Victim victim) {
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call linkNewVictim(?,?,?,?,?,?)}");
			) 
		{
			ps.setInt(1, victim.getPersonalId());
			ps.setBoolean(2, victim.getAlive());
			ps.setTimestamp(3, new java.sql.Timestamp(victim.getDeathTime().getTime()));
			ps.setString(4, victim.getDeathPlace());
			ps.setString(5, victim.getDeathReason());
			ps.setInt(6, victim.getComplaintID());
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Successfully link the victim to incident", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
	}
}
