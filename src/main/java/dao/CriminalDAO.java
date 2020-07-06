package dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import common.ConnectToProperties;
import entity.Criminal;

public class CriminalDAO {
	public List<Criminal> getAllCriminals() {
		List<Criminal> list = new ArrayList<Criminal>();

		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call getAllCriminals}");
				ResultSet rs = ps.executeQuery();
			) 
		{
			while (rs.next()) {
				Criminal cri = new Criminal();
				cri.setId(rs.getInt("id"));
				cri.setCatchStatus(rs.getBoolean("catchStatus"));
				cri.setPersonId(rs.getInt("personId"));
				cri.setIncidentId(rs.getInt("IncidentId"));
				cri.setRating(rs.getInt("rating"));
				
				list.add(cri);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return list;
	}
	
	public void addCriminal(Criminal cri) {
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call addCriminal(?,?,?,?)}");
			) 
		{
			ps.setBoolean(1, cri.isCatchStatus());
			ps.setInt(2, cri.getPersonId());
			ps.setInt(3, cri.getIncidentId());
			ps.setInt(4, cri.getRating());
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Insert new criminal successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
	}
}
