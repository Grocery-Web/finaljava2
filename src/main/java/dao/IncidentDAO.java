package dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
	
	public Incident addIncident(Date datetime, String place, String detail) {
		Incident inc = new Incident();
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call addIncident(?,?,?)}");
			) 
		{
			ps.setDate(1, new java.sql.Date(datetime.getTime()));
			ps.setString(2, place);
			ps.setString(3, detail);
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Insert new incident successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
			
			int incId;
			Statement statement = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = statement.executeQuery("SELECT MAX(id) AS SmallestPrice FROM Incident");
			incId = ((Number) resultSet.getObject(1)).intValue();
			
			inc.setId(incId);
			inc.setDatetime(datetime);
			inc.setPlace(place);
			inc.setDetail(detail);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
		return inc;
	}
}
