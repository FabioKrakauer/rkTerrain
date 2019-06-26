package br.com.rkDev.terrain.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionAttachmentInfo;

import br.com.rkDev.terrain.MinecraftTerrain;

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
	
	public Integer getMaxTerrain() {
		for(PermissionAttachmentInfo info : Bukkit.getPlayer(getName()).getEffectivePermissions()) {
			if(info.getPermission().contains("rkterrain.maxterrain.")) {
				String[] terrains = info.getPermission().split("rkterrain.maxterrain.");
				if(terrains.length < 2) {
					return MinecraftTerrain.getInstance().getConfigManager().getDefaultPlayerTerrainQuantity();
				}
				if(terrains[1] != null) {
					int terrainQuantity = MinecraftTerrain.getInstance().getConfigManager().getDefaultPlayerTerrainQuantity();
					try {
						terrainQuantity = Integer.parseInt(terrains[1]);
						return terrainQuantity;
					}catch (NumberFormatException e) {
						return terrainQuantity;
					}
				}
			}
		}
		return MinecraftTerrain.getInstance().getConfigManager().getDefaultPlayerTerrainQuantity();
	}
}
