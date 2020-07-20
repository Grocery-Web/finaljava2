package dao;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import common.ConnectToProperties;
import entity.Criminal;
import entity.Gender;

public class CriminalDAO {
	public List<Criminal> getAllCriminals() {
		List<Criminal> list = new ArrayList<Criminal>();

		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call getCriminalsInProcess}");
				ResultSet rs = ps.executeQuery();
			) 
		{
			while (rs.next()) {
				Criminal cri = new Criminal();
				
				cri.setCriminalId(rs.getInt("criminalId"));
				cri.setPersonalId(rs.getInt("id"));
				cri.setName(rs.getString("name"));
				cri.setDob(rs.getDate("dob"));
				cri.setAddress(rs.getString("address"));
				cri.setImage(rs.getString("image"));
				cri.setNationality(rs.getString("nationality"));
				cri.setComplaintName(rs.getString("complaintName"));
				cri.setPunishment(rs.getString("punishment"));
				cri.setRating(rs.getInt("rating"));
				
				Gender gender;
				if(rs.getBoolean("gender")) {
					gender = Gender.male;
				}else {
					gender = Gender.female;
				}
				cri.setGender(gender);
				
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
				PreparedStatement ps = connect.prepareCall("{call addCriminal(?,?,?,?,?,?)}");
			) 
		{
			ps.setInt(1, cri.getPersonalId());
			ps.setInt(2, cri.getComplaintId());
			ps.setString(3, cri.getPunishment());
			ps.setInt(4, cri.getRating());
			ps.setDate(5, (Date) cri.getAppliedDate());
			ps.setString(6, cri.getHisOfViolent());
			ps.executeUpdate();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public Criminal findLastUpdatedByPersonalId(int personalId) {
		Criminal cri = new Criminal();
		
		try(
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call findLastUpdatedByPersonalId(?)}");
		)
		{
			ps.setInt(1, personalId);
			var rs = ps.executeQuery();
			while (rs.next()) {
				cri.setCriminalId(rs.getInt("id"));
				cri.setPersonalId(rs.getInt("personId"));
				cri.setComplaintId(rs.getInt("complaintID"));
				cri.setAppliedDate(rs.getDate("appliedDate"));
				cri.setHisOfViolent(rs.getString("hisOfViolent"));
				cri.setPunishment(rs.getString("punishment"));
				cri.setRating(rs.getInt("rating"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
		
		return cri;
	}
	
	public Criminal findCriminalbyId(int criminalId) {
		Criminal cri = new Criminal();
		
		try(
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call findCriminalbyId(?)}");
		)
		{
			ps.setInt(1, criminalId);
			var rs = ps.executeQuery();
			while (rs.next()) {
				cri.setCriminalId(rs.getInt("id"));
				cri.setPersonalId(rs.getInt("personId"));
				cri.setComplaintId(rs.getInt("complaintID"));
				cri.setAppliedDate(rs.getDate("appliedDate"));
				cri.setHisOfViolent(rs.getString("hisOfViolent"));
				cri.setPunishment(rs.getString("punishment"));
				cri.setRating(rs.getInt("rating"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
		
		return cri;
	}
	
	public void updateCriminal(Criminal cri) {
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call updateCriminal(?,?,?,?,?,?,?)}");
			) 
		{
			ps.setInt(1, cri.getPersonalId());
			ps.setInt(2, cri.getComplaintId());
			ps.setDate(3, new java.sql.Date(cri.getAppliedDate().getTime()));
			ps.setString(4, cri.getHisOfViolent());
			ps.setString(5, cri.getPunishment());
			ps.setInt(6, cri.getRating());
			ps.setInt(7, cri.getCriminalId());
			
			if (ps.executeUpdate() > 0) {
				JOptionPane.showMessageDialog(null, "Criminal has already updated", "Success", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Criminal not found", "Failed", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public Criminal findCriminalByPersonAndComplaintId(int personId, int complaintId) {
		Criminal cri = new Criminal();
		
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call findCriminalByPersonAndComplaintId(?,?)}");
			) 
		{
			ps.setInt(1, personId);
			ps.setInt(2, complaintId);
			var rs = ps.executeQuery();
			while (rs.next()) {
				cri.setCriminalId(rs.getInt("id"));
				cri.setPersonalId(rs.getInt("personId"));
				cri.setComplaintId(rs.getInt("complaintId"));
				cri.setPunishment(rs.getString("punishment"));
				cri.setRating(rs.getInt("rating"));
				cri.setName(rs.getString("personName"));
				cri.setDob(rs.getDate("dob"));
				cri.setAddress(rs.getString("address"));
				cri.setNationality(rs.getString("nationality"));
				Gender gender;
				if(rs.getBoolean("gender")) {
					gender = Gender.male;
				}else {
					gender = Gender.female;
				}
				cri.setGender(gender);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
		
		return cri;
	}
	
	public List<Criminal> findCriminalListByPersonId(int personId) {
		List<Criminal> list = new ArrayList<Criminal>();
		
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call findCriminalListByPersonId(?)}");
			)
		{
			ps.setInt(1, personId);
			var rs = ps.executeQuery();
			while (rs.next()) {
				Criminal cri = new Criminal();
				cri.setCriminalId(rs.getInt("id"));
				cri.setPersonalId(rs.getInt("personId"));
				cri.setComplaintId(rs.getInt("complaintId"));
				cri.setPunishment(rs.getString("punishment"));
				cri.setAppliedDate(rs.getDate("appliedDate"));
				cri.setHisOfViolent(rs.getString("hisOfViolent"));
				cri.setRating(rs.getInt("rating"));
				list.add(cri);
			}		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
		return list;
	}
}
