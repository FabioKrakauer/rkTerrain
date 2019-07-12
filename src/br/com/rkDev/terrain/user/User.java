package br.com.rkDev.terrain.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
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
	
	public boolean isAdmin() {
		return Bukkit.getPlayer(getName()).hasPermission(MinecraftTerrain.getInstance().getConfigManager().getAdminPermission());
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
	
	public ItemStack getSkull(String itemName) {
		ItemStack stack = new ItemStack(Material.SKULL_ITEM);
		SkullMeta meta = (SkullMeta) stack.getItemMeta();
		stack.setDurability((short) 3);
		meta.setOwner(getName());
		meta.setDisplayName(itemName);
		stack.setItemMeta(meta);
		return stack;
	}
	public double getMoney() {
		return MinecraftTerrain.getInstance().getEconomy().getBalance(Bukkit.getOfflinePlayer(getUUID()));
	}
	public void removeMoney(double quantity) {
		MinecraftTerrain.getInstance().getEconomy().withdrawPlayer(Bukkit.getOfflinePlayer(getUUID()), quantity);
	}
	public void addMoney(double quantity) {
		MinecraftTerrain.getInstance().getEconomy().depositPlayer(Bukkit.getOfflinePlayer(getUUID()), quantity);
	}
}
