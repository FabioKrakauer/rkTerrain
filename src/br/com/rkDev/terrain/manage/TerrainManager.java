package br.com.rkDev.terrain.manage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import org.bukkit.Location;

import br.com.rkDev.terrain.user.User;

public class TerrainManager {
	
	private HashMap<Integer, Terrain> terrains;
	private TerrainCache cache;
	
	public TerrainManager() {
		terrains = new HashMap<Integer, Terrain>();
		cache = new TerrainCache(this);
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
	public void removeTerrain(Terrain terrain) {
		if(terrains.containsKey(terrain.getId())) {
			terrains.remove(terrain.getId());
		}
	}
	public void addTerrain(Terrain terrain) {
		removeTerrain(terrain);
		terrains.put(terrain.getId(), terrain);
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
	
	public TerrainCache getCache() {
		return cache;
	}
	
	public Cuboid generateCuboid(Location location, int radius) {
		int size = radius;
		double x = location.getX();
		double z = location.getZ();
		
		double x1 = x + (size / 2);
		double y1 = 0;
		double z1 = z + (size / 2);
		
		double x2 = x - (size / 2);
		double y2 = location.getWorld().getMaxHeight();
		double z2 = z - (size / 2);
		
		Location loc1 = new Location(location.getWorld(), x1, y1, z1);
		Location loc2 = new Location(location.getWorld(), x2, y2, z2);
		return new Cuboid(loc1, loc2);
	}
}
