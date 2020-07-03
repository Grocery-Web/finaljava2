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
	public List<Integer> findAllPersonByComplaintId(int id) {
		List<Integer> personIdList = new ArrayList<Integer>(); 
		
		try(
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call findAllPersonByComplaintId(?)}");
		)
		{
			ps.setInt(1, id);
	
			var rs = ps.executeQuery();
			while (rs.next()) {
				personIdList.add(rs.getInt("personId"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return personIdList;
	}
}
