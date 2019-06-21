package br.com.rkDev.terrain.database;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class Database {
	
	private String host;
	private Integer port;
	private String database;
	private String user;
	
	private Connection connection;
	
	public Database(String host, Integer port, String database, String user, String password) {
		this.host = host;
		this.port = port;
		this.database = database;
		this.user = user;
		String dsn = "jdbc:mysql://" + host + ":" + port + "/" + database;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = (Connection) DriverManager.getConnection(dsn, user, password);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public String getHost() {
		return this.host;
	}
	public Integer getPort() {
		return port;
	}
	public String getDatabase() {
		return database;
	}
	public String getUser() {
		return user;
	}
	public Connection getConnection() {
		return connection;
	}

}
