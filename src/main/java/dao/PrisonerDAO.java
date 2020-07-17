package dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import common.ConnectToProperties;
import entity.Prisoner;

public class PrisonerDAO {
	public List<Prisoner> getAllPrisoners() {
		List<Prisoner> list = new ArrayList<Prisoner>();
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call getAllPrisoners}");
				ResultSet rs = ps.executeQuery();
			) 
		{
			while (rs.next()) {
				Prisoner prisoner = new Prisoner();
				prisoner.setPrisonId(rs.getInt("prisonId"));
				prisoner.setCriminalId(rs.getInt("criminalID"));
				prisoner.setStartDate(rs.getDate("startDate"));
				prisoner.setEndDate(rs.getDate("endDate"));
				prisoner.setDuration(rs.getInt("duration"));
				prisoner.setReleasedStatus(rs.getBoolean("releaseStatus"));
				prisoner.setType(rs.getString("type"));
				
				list.add(prisoner);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return list;
	}
	
	public void addPrisoner(Prisoner prisoner) {
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call addPrisoner(?,?,?,?,?,?,?)}");
			) 
		{
			ps.setInt(1, prisoner.getPrisonId());
			ps.setInt(2, prisoner.getCriminalId());
			ps.setDate(3, new java.sql.Date(prisoner.getStartDate().getTime()));
			if(prisoner.getEndDate() != null) {
				ps.setDate(4, new java.sql.Date(prisoner.getEndDate().getTime()));
			}else {
				ps.setDate(4,null);
			}
			ps.setInt(5, prisoner.getDuration());
			ps.setBoolean(6, prisoner.isReleasedStatus());
			ps.setString(7, prisoner.getType());
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "A new prisoner has been created", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void releasePrisoner(int PrisonerID) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call releasePrisonerByID(?,?)}");
		)
		{
			ps.setInt(1, PrisonerID);
			ps.setDate(2, new java.sql.Date(date.getTime()));
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Release Completed", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
	}
}
