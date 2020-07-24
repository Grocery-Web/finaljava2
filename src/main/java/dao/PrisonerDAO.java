package dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import common.ConnectToProperties;
import entity.Gender;
import entity.PrisonList;
import entity.Prisoner;

public class PrisonerDAO {
	public List<Prisoner> getUnreleasedPrisoners() {
		List<Prisoner> list = new ArrayList<Prisoner>();
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call findUnreleasedPrisoners}");
				ResultSet rs = ps.executeQuery();
			) 
		{
			while (rs.next()) {
				Prisoner prisoner = new Prisoner();
				prisoner.setPrisonerId(rs.getInt("id"));
				prisoner.setPrisonId(rs.getInt("prisonId"));
				prisoner.setCriminalId(rs.getInt("criminalID"));
				prisoner.setStartDate(rs.getDate("startDate"));
				prisoner.setEndDate(rs.getDate("endDate"));
				prisoner.setDuration(rs.getInt("duration"));
				prisoner.setReleasedStatus(rs.getBoolean("releaseStatus"));
				prisoner.setType(rs.getString("type"));
				prisoner.setName(rs.getString("personName"));
				prisoner.setNationality(rs.getString("nationality"));
				prisoner.setPrisonName(rs.getString("prisonName"));
				
				Gender gender;
				if(rs.getBoolean("gender")) {
					gender = Gender.male;
				}else {
					gender = Gender.female;
				}
				prisoner.setGender(gender);
				
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
	
	public Prisoner findPrisonerByID(int prisonerID) {
		
		Prisoner prisoner = new Prisoner();
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call findPrisonerByID(?)}");
			) 
		{
			ps.setInt(1, prisonerID);
			var rs = ps.executeQuery();
			while (rs.next()) {
				prisoner.setPrisonerId(rs.getInt("id"));
				prisoner.setPrisonId(rs.getInt("prisonId"));
				prisoner.setCriminalId(rs.getInt("CriminalID"));
				prisoner.setStartDate(rs.getDate("startDate"));
				prisoner.setEndDate(rs.getDate("endDate"));
				prisoner.setDuration(rs.getInt("duration"));
				prisoner.setReleasedStatus(rs.getBoolean("releaseStatus"));
				prisoner.setType(rs.getString("type"));
				prisoner.setName(rs.getString("personName"));
				prisoner.setDob(rs.getDate("dob"));
				prisoner.setNationality(rs.getString("nationality"));
				prisoner.setImage(rs.getString("image"));
				prisoner.setPrisonName(rs.getString("prisonName"));
				prisoner.setHisOfViolent(rs.getString("hisOfViolent"));
				Gender gender;
				if(rs.getBoolean("gender")) {
					gender = Gender.male;
				} else {
					gender = Gender.female;
				}
				prisoner.setGender(gender);
			}			
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
		return prisoner;	
	}
	
	public void releasePrisoner(int PrisonerID) {
		
		Date date = new Date();
		Prisoner prisoner = new Prisoner();
		prisoner = findPrisonerByID(PrisonerID);
		
		if (prisoner.getType().equals("Death penalty")) {
			JOptionPane.showMessageDialog(null, "Cant release dead person! Please check again");
		} else if (prisoner.getType().equals("Life-sentence")) {
			JOptionPane.showMessageDialog(null, "Cant release life-sentenced prisoner! Please check again");
		}
		else {
			
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
				JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
	
	public void releaseListPrisoners(List<Prisoner> listReleasedPrisoners) {
		
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call releaseListPrisonerByID(?,?,?)}");
				)
		{
			for (Prisoner prisoner : listReleasedPrisoners) {
				ps.setInt(1, prisoner.getPrisonerId());
				ps.setDate(2, new java.sql.Date(prisoner.getEndDate().getTime()));
				ps.setInt(3, prisoner.getDuration());
				ps.addBatch();
			}
			ps.executeBatch();
			connect.commit();
			ps.clearBatch();
			connect.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void transferPrisoner(int PrisonerID, int prisonId) {
			
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call transferPrisonerByID(?,?)}");
				)
		{
			ps.setInt(1, PrisonerID);
			ps.setInt(2, prisonId);
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Transfer successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void transferListPrisoner(List<Prisoner> listTransferedPrisoners) {
		int count = 0;
		ArrayList<String> list = new ArrayList<String>();
		PrisonListDAO prisonDAO = new PrisonListDAO();
		
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call transferPrisonerByID(?,?)}");
				)
		{
			for (Prisoner prisoner : listTransferedPrisoners) {
				PrisonList prl = prisonDAO.getPrisonListByID(prisoner.getPrisonId());
				if(prl.getQuantity() >= prl.getCapacity()) {
					if(list.contains(prl.getName()) == false) {
						JOptionPane.showMessageDialog(null, prl.getName() + " is full capacity, prisoners transfered to this prison will be halted. "
								+ " Choose other prisons!", "Oops!", JOptionPane.ERROR_MESSAGE);
						list.add(prl.getName());
					}
				}else {
					ps.setInt(1, prisoner.getPrisonerId());
					ps.setInt(2, prisoner.getPrisonId());
					ps.addBatch();
					++count;
				}
			}
			
			if(count > 0) {
				ps.executeBatch();
				connect.commit();
				ps.clearBatch();
				connect.close();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public void updateDurationByPrisonerID(int prisonerID, int duration) {
		 try (
				 var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				 PreparedStatement ps = connect.prepareCall("{call updateDurationByPrisonerID(?,?)}");
				 )
		 {
			 ps.setInt(1, prisonerID);
			 ps.setInt(2, duration);
			 ps.executeUpdate();
		 } catch (Exception e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		 }
	}
}
