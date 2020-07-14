package dao;

import java.sql.*;

import javax.swing.JOptionPane;

import common.ConnectToProperties;
import entity.*;

public class VictimDAO {
	public void linkNewVictim(Victim victim, Complaint cpl) {
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call linkNewVictim}");
			) 
		{
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
	}
}
