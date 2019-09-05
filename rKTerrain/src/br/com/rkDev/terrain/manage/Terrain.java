package br.com.rkDev.terrain.manage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.rkDev.terrain.MinecraftTerrain;
import br.com.rkDev.terrain.user.User;

public class Terrain {
	
	private Integer id;
	private User owner;
	private Cuboid cuboid;
	private Location spawn;
	private double sale;
	
	private HashSet<User> friends;
	
	private boolean canJoin;
	private boolean canBuild;
	private boolean canPvP;
	
	public Terrain(ResultSet terrain, HashSet<User> friends, boolean[] flags) throws SQLException {
		this.id = terrain.getInt("id");
		this.owner = MinecraftTerrain.getInstance().getUserManager().getUser(terrain.getInt("user_id"));
		JsonParser parser = new JsonParser();
		JsonObject json = (JsonObject) parser.parse(terrain.getString("cuboid"));
		this.cuboid = new Cuboid(json);
		JsonObject spawnJson = (JsonObject)parser.parse(terrain.getString("spawn"));
		this.spawn = new Location(Bukkit.getWorld(spawnJson.get("world").getAsString()), spawnJson.get("x").getAsDouble(), spawnJson.get("y").getAsDouble(), spawnJson.get("z").getAsDouble(), spawnJson.get("yaw").getAsFloat(), spawnJson.get("pitch").getAsFloat());
		this.sale = terrain.getInt("sale");
		this.friends = friends;
		canJoin = flags[0];
		canPvP = flags[1];
		canBuild = flags[2];
	}

	public Integer getId() {
		return id;
	}

	public User getOwner() {
		return owner;
	}

	public Cuboid getCuboid() {
		return cuboid;
	}

	public Location getSpawn() {
		return spawn;
	}

	public HashSet<User> getFriends() {
		return friends;
	}
	
	public boolean isOnSale() {
		return sale > 0;
	}
	
	public double getSaleValue() {
		return sale;
	}
	
	public boolean[] getFlags() {
		boolean[] flags = {canJoin, canPvP, canBuild};
		return flags;
	}
	public boolean canJoin() {
		return canJoin;
	}
	public boolean canPvP() {
		return canPvP;
	}
	public boolean canBuid() {
		return canBuild;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Terrain other = (Terrain) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
