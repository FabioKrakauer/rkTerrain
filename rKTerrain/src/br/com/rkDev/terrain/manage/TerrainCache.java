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
				Query getFlags = new Query("SELECT * FROM `terrain_flags` WHERE `terrain_id`='"+terrain.getInt("id")+"'");
				ResultSet flagsSet = getFlags.getResultSet();
				boolean[] flags = {false, false, false};
				if(flagsSet.next()) {
					flags[0] = flagsSet.getInt("join") == 0 ? false : true;
					flags[1] = flagsSet.getInt("pvp") == 0 ? false : true;
					flags[2] = flagsSet.getInt("construct") == 0 ? false : true;
				}
				Terrain terrainMap = new Terrain(terrain, friends, flags);
				this.manager.addTerrain(terrainMap);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void downloadTerrain(Integer terrain_id) {
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
				Query getFlags = new Query("SELECT * FROM `terrain_flags` WHERE `terrain_id`='"+terrain.getInt("id")+"'");
				ResultSet flagsSet = getFlags.getResultSet();
				boolean[] flags = {false, false, false};
				if(flagsSet.next()) {
					flags[0] = flagsSet.getInt("join") == 0 ? false : true;
					flags[1] = flagsSet.getInt("pvp") == 0 ? false : true;
					flags[2] = flagsSet.getInt("construct") == 0 ? false : true;
				}
				Terrain terrainMap = new Terrain(terrain, friends, flags);
				this.manager.addTerrain(terrainMap);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
