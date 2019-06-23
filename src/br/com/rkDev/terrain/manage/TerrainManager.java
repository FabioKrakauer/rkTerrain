package br.com.rkDev.terrain.manage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import org.bukkit.Location;

import br.com.rkDev.terrain.user.User;

public class TerrainManager {
	
	private HashMap<Integer, Terrain> terrains;
	
	public TerrainManager() {
		terrains = new HashMap<Integer, Terrain>();
	}
	
	public Terrain getTerrain(Integer id) {
		return terrains.get(id);
	}
	
	public Terrain getTerrain(Location location) {
		for(Entry<Integer, Terrain> entry : terrains.entrySet()) {
			if(entry.getValue().getCuboid().contains(location)) {
				return entry.getValue();
			}
		}
		return null;
	}
	
	public HashSet<Terrain> getUserTerrains(User user){
		HashSet<Terrain> terrain = new HashSet<Terrain>();
		for(Entry<Integer, Terrain> entry : terrains.entrySet()) {
			if(entry.getValue().getOwner().getId() == user.getId()) {
				terrain.add(entry.getValue());
			}
		}
		return terrain;
	}
	public HashSet<Terrain> getUserFriendsTerrains(User user){
		HashSet<Terrain> terrain = new HashSet<Terrain>();
		for(Entry<Integer, Terrain> entry : terrains.entrySet()) {
			if(entry.getValue().getFriends().contains(user)) {
				terrain.add(entry.getValue());
			}
		}
		return terrain;
	}
}
