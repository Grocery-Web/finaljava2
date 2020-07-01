package dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import common.ConnectToProperties;
import entity.Account;
import entity.PersonDetail;

public class PersonDetailDAO {
	public PersonDetail getInfoDetail (int idUser) {
		PersonDetail personDetail = new PersonDetail();
		try (
				var connect = DriverManager.getConnection(ConnectToProperties.getConnection());
				PreparedStatement ps = connect.prepareCall("{call getPersonDetail(?)}");
			) 
		{
			ps.setInt(1, idUser);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				personDetail.setId(rs.getInt(1));
				personDetail.setName(rs.getString(2));
				personDetail.setGender(rs.getBoolean(3));
				personDetail.setDob(rs.getDate(4));
				personDetail.setAddress(rs.getString(5));
				personDetail.setImg(rs.getString(6));
				personDetail.setNational(rs.getString(7));
				personDetail.setJob(rs.getString(8));
				personDetail.setBlood(rs.getString(9));
				personDetail.setHeight(rs.getInt(10));
				personDetail.setNote(rs.getString(11));
				return personDetail;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	return null;
	}

}
