package dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import common.ConnectToProperties;
import entity.PrisonList;

public class PrisonListDAO {
	public List<PrisonList> getAllPrisonList() {
		List<PrisonList> prisonList = new ArrayList<PrisonList>();
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call getAllPrisonList}");
				ResultSet rs = ps.executeQuery();
			) 
		{
			while (rs.next()) {
				PrisonList pl = new PrisonList();
				pl.setId(rs.getInt("id"));
				pl.setName(rs.getString("name"));
				pl.setAddress(rs.getString("address"));
				pl.setCapacity(rs.getInt("limit"));
				pl.setQuantity(rs.getInt("prisonerNum"));	
				
				prisonList.add(pl);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
		return prisonList;
	}
}
