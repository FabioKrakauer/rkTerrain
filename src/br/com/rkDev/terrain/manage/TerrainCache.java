package br.com.rkDev.terrain.manage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import br.com.rkDev.terrain.MinecraftTerrain;
import br.com.rkDev.terrain.database.Query;
import br.com.rkDev.terrain.user.User;

public class TerrainCache {
	
	private TerrainManager manager;
	
	public TerrainCache(TerrainManager manager) {
		this.manager = manager;
		downloadAllTerrains();
	}
	
	private void downloadAllTerrains() {
		try {
			Query selectTerrains = new Query("SELECT * FROM `terrain`");
			ResultSet terrain = selectTerrains.getResultSet();
			while(terrain.next()) {
				HashSet<User> friends = new HashSet<User>();
				Query selectFriends = new Query("SELECT * FROM `user_terrain` WHERE `terrain_id`='"+terrain.getInt("id")+"'");
				ResultSet friendsSet = selectFriends.getResultSet();
				while(friendsSet.next()) {
					friends.add(MinecraftTerrain.getInstance().getUserManager().getUser(friendsSet.getInt("user_id")));
				}
				Terrain terrainMap = new Terrain(terrain, friends);
				this.manager.addTerrain(terrainMap);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void downloadTerrains(Integer terrain_id) {
		try {
			Query selectTerrains = new Query("SELECT * FROM `terrain` WHERE `id`='" + terrain_id + "'");
			ResultSet terrain = selectTerrains.getResultSet();
			if(terrain.next()) {
				HashSet<User> friends = new HashSet<User>();
				Query selectFriends = new Query("SELECT * FROM `user_terrain` WHERE `terrain_id`='"+terrain_id+"'");
				ResultSet friendsSet = selectFriends.getResultSet();
				while(friendsSet.next()) {
					friends.add(MinecraftTerrain.getInstance().getUserManager().getUser(friendsSet.getInt("user_id")));
				}
				Terrain terrainMap = new Terrain(terrain, friends);
				this.manager.addTerrain(terrainMap);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
