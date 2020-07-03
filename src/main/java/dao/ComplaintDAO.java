package dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
				com.setName(rs.getString("complaintName"));
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
	
	public void addComplaint(Complaint cpt) {
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call addComplaint(?,?,?,?,?,?)}");
			) 
		{
			ps.setString(1, cpt.getName());
			ps.setDate(2, new java.sql.Date(cpt.getDatetime().getTime()));
			ps.setString(3, cpt.getPlace());
			ps.setString(4, cpt.getDeclarantName());
			ps.setString(5, cpt.getDetail());
			ps.setBoolean(6, cpt.isStatus());
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Insert new complaint successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void deleteComplaint(int id) {
		try(
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call deleteComplaint(?)}");
		)
		{
			ps.setInt(1, id);
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Delete Sucessfully", "Success", JOptionPane.OK_OPTION|JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	public Complaint findComplaintById(int id) {
		Complaint com = new Complaint();
		
		try(
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call findComplaintById(?)}");
		)
		{
			ps.setInt(1, id);
			var rs = ps.executeQuery();
			while (rs.next()) {
				com.setId(rs.getInt("id"));
				com.setDatetime(rs.getDate("datetime"));
				com.setPlace(rs.getString("place"));
				com.setDeclarantName(rs.getString("declarantName"));
				com.setDetail(rs.getString("detail"));
				com.setStatus(rs.getBoolean("verifyStatus"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return com;
	}
}
