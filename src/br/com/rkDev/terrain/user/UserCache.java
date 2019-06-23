package br.com.rkDev.terrain.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.rkDev.terrain.database.Query;

public class UserCache {
	
	private UserManager manager;
	
	public UserCache(UserManager manager) {
		this.manager = manager;
		downloadAll();
	}
	
	private void downloadAll() {
		try {
			Query download = new Query("SELECT * FROM `user`");
			ResultSet rs = download.getResultSet();
			while(rs.next()) {
				User user = new User(rs);
				this.manager.addUser(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void downloadUser(Integer id) {
		try {
			Query download = new Query("SELECT * FROM `user` WHERE `id`='" + id + "'");
			ResultSet rs = download.getResultSet();
			if(rs.next()) {
				User user = new User(rs);
				this.manager.addUser(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
