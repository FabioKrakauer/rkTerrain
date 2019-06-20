package br.com.rkDev.terrain.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class User {
	
	private int id;
	private String name;
	private UUID uuid;
	
	public User(ResultSet user) throws SQLException {
		id = user.getInt("id");
		name = user.getString("name");
		uuid = UUID.fromString(user.getString("uuid"));
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public UUID getUUID() {
		return uuid;
	}
}
