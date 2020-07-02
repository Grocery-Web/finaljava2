package dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import common.ConnectToProperties;
import entity.ComplaintDetail;
import entity.Gender;

public class ComplaintDetailDAO {
	public ComplaintDetail findCompDetailByPersonId(int personId, int compId) {
		ComplaintDetail compDetail = new ComplaintDetail();
		
		try(
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call findCompDetailByPersonComplaintId(?,?)}");
		)
		{
			ps.setInt(1, personId);
			ps.setInt(2, compId);
			var rs = ps.executeQuery();
			while (rs.next()) {
				compDetail.setId(rs.getInt("id"));
				compDetail.setPersonId(rs.getInt("personId"));
				compDetail.setCompId(rs.getInt("compId"));
				compDetail.setCrimeType(rs.getString("crimeType"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return compDetail;
	}
}
