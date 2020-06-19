package test;

import java.sql.*;

import common.ConnectToProperties;

public class TestConnection {

	public static void main(String[] args) {
		Connection connect = null;
		try {
			connect = DriverManager.getConnection(ConnectToProperties.getConnection());
			System.out.println("Success");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
