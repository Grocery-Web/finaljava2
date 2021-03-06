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
	
	public List<PrisonList> getAllPrisonListExceptPrisonID( int id) {
		List<PrisonList> prisonList = new ArrayList<PrisonList>();
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call getAllPrisonListExceptPrisonID(?)}");
			) 
		{
			ps.setInt(1, id);
			var rs = ps.executeQuery();
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
				pr.setAddress(rs.getString("address"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return pr;
		
	}
	
	public List<Prisoner> getAllPrisonerByPrisonListID(int id){
		
		List<Prisoner> listPrisoners = new ArrayList<Prisoner>();
		
		
		try(
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call getAllPrisonerByPrisonListID(?)}");
		)
		{
			ps.setInt(1, id);
			var rs = ps.executeQuery();
			while (rs.next()) {
				Prisoner prisoner = new Prisoner();
				prisoner.setPersonalId(rs.getInt("personId"));
				prisoner.setPrisonerId(rs.getInt("id"));
				prisoner.setName(rs.getString("name"));
				prisoner.setDob(rs.getDate("dob"));
				if (rs.getBoolean("gender" ) == true) {
					prisoner.setGender(Gender.male);
				}
				else {
					prisoner.setGender(Gender.female);
				}
				prisoner.setStartDate(rs.getDate("startDate"));	
				prisoner.setDuration(rs.getInt("duration"));
				prisoner.setNationality(rs.getString("nationality"));
				prisoner.setEndDate(rs.getDate("endDate"));
				prisoner.setType(rs.getString("type"));
				prisoner.setPrisonId(rs.getInt("prisonId"));
				
				listPrisoners.add(prisoner);
				
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return listPrisoners;
	}
	
	public void transferPrisoner(int idFrom, int idTo, int prisonerID) {
		
		PrisonList prisonFrom = getPrisonListByID(idFrom);
		PrisonList prisonTo = getPrisonListByID(idTo);
		
		if (prisonTo.getCapacity()  > prisonTo.getQuantity() + 1) {
			try (
					var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
					PreparedStatement ps = connect.prepareCall("{call transferPrisonerByID(?,?)}");
				) 
			{
				ps.setInt(1, prisonerID);
				ps.setInt(2, idTo);
				
				if ( ps.executeUpdate() > 0 ) {
					JOptionPane.showMessageDialog(null, "Update account successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
			}
		}
		else JOptionPane.showMessageDialog(null, "Prison reach limitations. Please choose another prison");
	}
	
	public void updatePrisonInfo(PrisonList prison) {
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call updatePrisonList(?,?,?)}");
			) 
		{
			ps.setString(1, prison.getName());
			ps.setString(2, prison.getAddress());
			ps.setInt(3, prison.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public List<PrisonList> getAllAvailablePrisons() {
		List<PrisonList> prisonList = new ArrayList<PrisonList>();
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call getAllPrisonList}");
				ResultSet rs = ps.executeQuery();
			) 
		{
			while (rs.next()) {
				if (rs.getInt("limit") == rs.getInt("prisonerNum")) {
					continue;
				} else {
					PrisonList pl = new PrisonList();
					pl.setId(rs.getInt("id"));
					pl.setName(rs.getString("name"));
					pl.setAddress(rs.getString("address"));
					pl.setImg(rs.getString("img"));
					pl.setCapacity(rs.getInt("limit"));
					pl.setQuantity(rs.getInt("prisonerNum"));	
					
					prisonList.add(pl);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
		return prisonList;
	}
}
