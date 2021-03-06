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
	public List<Complaint> getAllUnverifiedComplaints() {
		List<Complaint> list = new ArrayList<Complaint>();

		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call getAllUnverifiedComplaints}");
				ResultSet rs = ps.executeQuery();
			) 
		{
			while (rs.next()) {
				Complaint com = new Complaint();
				com.setId(rs.getInt("id"));
				com.setName(rs.getString("complaintName"));
				com.setDatetime(rs.getTimestamp("datetime"));
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
	
	public List<Complaint> getAllApprovedComplaints() {
		List<Complaint> list = new ArrayList<Complaint>();

		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call getAllApprovedComplaints}");
				ResultSet rs = ps.executeQuery();
			) 
		{
			while (rs.next()) {
				Complaint com = new Complaint();
				com.setId(rs.getInt("id"));
				com.setName(rs.getString("complaintName"));
				com.setDatetime(rs.getTimestamp("datetime"));
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
	
	public List<Integer> findIncidentsCommitedByPerson(int id) {
		List<Integer> list = new ArrayList<Integer>();
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call findIncidentsCommitedByPerson(?)}");
			)
		{
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				list.add(rs.getInt(1));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
		return list;
	}
	
	public void addComplaint(Complaint cpt,String userId) {
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call addComplaint(?,?,?,?,?,?,?)}");
			) 
		{
			ps.setString(1, cpt.getName());
			ps.setTimestamp(2, new java.sql.Timestamp(cpt.getDatetime().getTime()));
			ps.setString(3, cpt.getPlace());
			ps.setString(4, cpt.getDeclarantName());
			ps.setString(5, cpt.getDetail());
			ps.setBoolean(6, cpt.isStatus());
			ps.setString(7, userId);
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
				com.setName(rs.getString("complaintName"));
				com.setDatetime(rs.getTimestamp("datetime"));
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
	
	public void updateComplaintById(int id, Complaint cpl, String userId) {
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call updateComplaintById(?,?,?,?,?,?,?,?)}");
			) 
		{
			ps.setInt(1, id);
			ps.setString(2, cpl.getName());
			ps.setTimestamp(3, new java.sql.Timestamp(cpl.getDatetime().getTime()));
			ps.setString(4, cpl.getPlace());
			ps.setString(5, cpl.getDeclarantName());
			ps.setString(6, cpl.getDetail());
			ps.setBoolean(7, cpl.isStatus());	
			ps.setString(8, userId);
		
			ps.executeUpdate();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
	    return new java.sql.Date(date.getTime());
	}
}
