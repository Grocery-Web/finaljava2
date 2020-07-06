package dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import common.ConnectToProperties;
import entity.Incident;

public class IncidentDAO {
	public List<Incident> getAllIncidents() {
		List<Incident> list = new ArrayList<Incident>();

		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call getAllIncidents}");
				ResultSet rs = ps.executeQuery();
			) 
		{
			while (rs.next()) {
				Incident incident = new Incident();
				incident.setId(rs.getInt("id"));
				incident.setDatetime(rs.getDate("datetime"));
				incident.setPlace(rs.getString("place"));
				incident.setDetail(rs.getString("detail"));
				
				list.add(incident);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return list;
	}
	
	public void addIncident(Incident inc) {
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call addIncident(?,?,?)}");
			) 
		{
			ps.setDate(1, new java.sql.Date(inc.getDatetime().getTime()));
			ps.setString(2, inc.getPlace());
			ps.setString(3, inc.getDetail());
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Insert new incident successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
	}
}
