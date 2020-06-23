package dao;

import java.sql.*;
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
}
