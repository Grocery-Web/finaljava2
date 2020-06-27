package dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import common.ConnectToProperties;
import entity.Complaint;

public class ComplaintDAO {
	public List<Complaint> getAllComplaints() {
		List<Complaint> list = new ArrayList<Complaint>();

		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call getAllComplaints}");
				ResultSet rs = ps.executeQuery();
			) 
		{
			while (rs.next()) {
				Complaint com = new Complaint();
				com.setId(rs.getInt("id"));
				com.setDatetime(rs.getDate("datetime"));
				com.setPlace(rs.getString("place"));
				com.setDeclarantName(rs.getString("declarantName"));
				com.setDetail(rs.getString("detail"));
				com.setStatus(rs.getBoolean("verifyStatus"));
				
				list.add(com);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return list;
	}
}
