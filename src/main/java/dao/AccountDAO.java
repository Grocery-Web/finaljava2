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
					acc.setStatus(rs.getBoolean(6));
				}
				if (!acc.isStatus()) {
					return -2;
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
}
