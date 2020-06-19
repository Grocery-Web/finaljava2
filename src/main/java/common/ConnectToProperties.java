package common;

import java.io.*;
import java.util.Properties;

public class ConnectToProperties {
	public static String getConnection() {
		String url = null;
		try (
				var stream = new FileInputStream("db.properties");
			) 
		{
				var prop = new Properties();
				prop.load(stream);
				url = prop.getProperty("url") + prop.getProperty("serverName") + ":" + prop.getProperty("portNumber") + "; databaseName=" + prop.getProperty("databaseName") + "; user=" + prop.getProperty("username") + "; password=" + prop.getProperty("password");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return url;
	}
}
