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
				
				cri.setCriminalId(rs.getInt("id"));
				cri.setPersonalId(rs.getInt("personId"));
				cri.setComplaintId(rs.getInt("complaintID"));
				cri.setPunishment(rs.getString("punishment"));
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
}
