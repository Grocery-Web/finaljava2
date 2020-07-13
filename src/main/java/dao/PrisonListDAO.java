package dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import common.ConnectToProperties;
import entity.Gender;
import entity.PrisonList;
import entity.Prisoner;
import entity.PrisonerInList;

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
				pl.setImg(rs.getString("img"));
				pl.setCapacity(rs.getInt("limit"));
				pl.setQuantity(rs.getInt("prisonerNum"));	
				
				prisonList.add(pl);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
		return prisonList;
	}
	
	public PrisonList getPrisonListByID(int id) {
		
		PrisonList pr = new PrisonList();
		
		try(
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call getPrisonListByID(?)}");
		)
		{
			ps.setInt(1, id);
			var rs = ps.executeQuery();
			while (rs.next()) {
				
				pr.setId(rs.getInt("id"));
				pr.setName(rs.getString("name"));
				pr.setCapacity(rs.getInt("limit"));
				pr.setQuantity(rs.getInt("prisonerNum"));
				pr.setImg(rs.getString("img"));				
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return pr;
		
	}
	
	public List<PrisonerInList> getAllPrisonerByPrisonListID(int id){
		
		List<PrisonerInList> prisoners = new ArrayList<PrisonerInList>();
		PrisonerInList prisoner = new PrisonerInList();
		
		try(
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call getAllPrisonerByPrisonListID(?)}");
		)
		{
			ps.setInt(1, id);
			var rs = ps.executeQuery();
			while (rs.next()) {
				
				prisoner.setPersonID(rs.getInt("personId"));
				prisoner.setPrisonID(rs.getInt("id"));
				prisoner.setName(rs.getString("name"));
				prisoner.setDob(rs.getDate("dob"));
				if (rs.getBoolean("gender" )) {
					prisoner.setGender(Gender.female);
				}
				else {
					prisoner.setGender(Gender.male);
				}
				prisoner.setStartDate(rs.getDate("startDate"));	
				prisoner.setDuration(rs.getInt("duration"));
				prisoner.setNationality(rs.getString("nationality"));
				
				prisoners.add(prisoner);
				
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return prisoners;
	}
}
