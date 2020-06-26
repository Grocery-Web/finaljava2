package dao;

import java.sql.*;
import java.util.*;

import javax.swing.JOptionPane;
import common.ConnectToProperties;
import entity.Account;

public class AccountDAO {
	public int checkAcc(Account acc) {
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call checkAcc(?, ?)}");
			) 
		{
			ps.setString(1, acc.getUserID());
			ps.setString(2, acc.getPassword());
			ResultSet rs = ps.executeQuery();
			if (!rs.isBeforeFirst()) {
				return -1;
			} else {
				while (rs.next()) {
					acc.setPrivilege(rs.getInt(5));
				}
				return acc.getPrivilege();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.INFORMATION_MESSAGE);
		}
		
		return -1;
	}
	
	public List<Account> getAllAccount() {
		List<Account> list = new ArrayList<Account>();
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call getAllAcc}");
				ResultSet rs = ps.executeQuery();
			) 
		{
			while (rs.next()) {
				Account acc = new Account();
				acc.setUserID(rs.getString(1));
				acc.setFullName(rs.getString(2));
				acc.setEmail(rs.getString(3));
				acc.setPrivilege(rs.getInt(4));
				list.add(acc);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return list;
	}
	
	public void addAccount(Account acc) {
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call addAccount(?,?,?,?,?)}");
			) 
		{
			ps.setString(1, acc.getUserID());
			ps.setString(2, acc.getFullName());
			ps.setString(3, acc.getEmail());
			ps.setString(4, acc.getPassword());
			ps.setInt(5, acc.getPrivilege());
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Insert new account successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void deleteAccount(Account acc) {
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call deleteAcc(?)}");
			) 
		{
			ps.setString(1, acc.getUserID());
			if (ps.executeUpdate() > 0) {
				JOptionPane.showMessageDialog(null, "Delete account successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "UserID not found", "Failed", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void updateAccount(Account acc) {
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call updateAcc(?,?,?,?,?)}");
			) 
		{
			ps.setString(1, acc.getFullName());
			ps.setString(2, acc.getEmail());
			if (acc.getPassword() == null) {
				ps.setNull(3, java.sql.Types.VARCHAR);
			} else {
				ps.setString(3, acc.getPassword());
			}
			ps.setInt(4, acc.getPrivilege());
			ps.setString(5, acc.getUserID());
			if (ps.executeUpdate() > 0) {
				JOptionPane.showMessageDialog(null, "Update account successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "UserID not found", "Failed", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "info", JOptionPane.ERROR_MESSAGE);
		}
	}
}
