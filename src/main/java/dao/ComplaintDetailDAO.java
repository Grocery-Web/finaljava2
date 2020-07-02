package dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

import common.ConnectToProperties;
import entity.Complaint;
import entity.ComplaintDetailEntity;

public class ComplaintDetailDAO {
	public void setComplaintDetail(ComplaintDetailEntity comDetail) {
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call setComplaintDetail(?,?,?)}");
			) 
		{
			ps.setInt(1, comDetail.getPersonId());
			ps.setInt(2, comDetail.getCompId());
			ps.setString(3, comDetail.getCrimeType());
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Set Details Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
	}
}
