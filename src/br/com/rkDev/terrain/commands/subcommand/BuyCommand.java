package br.com.rkDev.terrain.commands.subcommand;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.google.gson.JsonObject;

import br.com.rkDev.terrain.MinecraftTerrain;
import br.com.rkDev.terrain.Utils;
import br.com.rkDev.terrain.config.Lang;
import br.com.rkDev.terrain.database.Query;
import br.com.rkDev.terrain.manage.Cuboid;
import br.com.rkDev.terrain.manage.Terrain;
import br.com.rkDev.terrain.user.User;

public class BuyCommand {

	private User user;
	private String[] args;

	public BuyCommand(User user, String[] args) {
		this.user = user;
		this.args = args;
		execute();
	}

	private void execute() {
		Player p = Bukkit.getPlayer(user.getName());
		if(args.length < 2) {
			Terrain terrain = MinecraftTerrain.getInstance().getTerrainManager().getTerrain(p.getLocation());
			
			if(terrain == null) {
				p.sendMessage(Lang.NULL_TERRAIN.build());
				return;
			}
			if(terrain.getOwner().getId() == user.getId()) {
				p.sendMessage(Lang.NOT_BUY_OWN_TERRAIN.build());
				return;
			}
			if(!terrain.isOnSale()) {
				p.sendMessage(Lang.TERRAIN_NOTSALE.build());
				return;
			}
			if(MinecraftTerrain.getInstance().getTerrainManager().getUserTerrains(user).size() >= user.getMaxTerrain()) {
				p.sendMessage(Lang.MAX_TERRAINS.build().replace("%quantidade%", user.getMaxTerrain().toString()));
				return;
			}
			if(terrain.getSaleValue() > user.getMoney()) {
				p.sendMessage(Lang.NO_CREDITS.build().replace("%valor%", Utils.formatDouble(terrain.getSaleValue()) + ""));
				return;
			}
			Query updateOwner = new Query("UPDATE `terrain` SET `user_id`='"+user.getId()+"', `sale`='0' WHERE `id`='"+terrain.getId()+"'");
			Query removeFriends = new Query("DELETE FROM `user_terrain` WHERE `terrain_id`='"+terrain.getId()+"'");
			updateOwner.execute();
			removeFriends.execute();
			terrain.getOwner().addMoney(terrain.getSaleValue());
			user.removeMoney(terrain.getSaleValue());
			
			p.sendMessage(Lang.BROUGHT_TERRAIN.build().replace("%valor%", Utils.formatDouble(terrain.getSaleValue()) + ""));
			MinecraftTerrain.getInstance().getTerrainManager().getCache().downloadTerrain(terrain.getId());
			return;
		}
		if(!Utils.isInteger(args[1])) {
			p.sendMessage(Lang.ONLY_NUMBERS.build());
			return;
		}
		double value = MinecraftTerrain.getInstance().getConfigManager().getPriceBlock() * Integer.parseInt(args[1]);
		if(user.getMoney() < value) {
			p.sendMessage(Lang.NO_CREDITS.build().replace("%valor%", Utils.formatDouble(value) + ""));
			return;
		}
		if(MinecraftTerrain.getInstance().getTerrainManager().getTerrain(p.getLocation()) != null) {
			p.sendMessage(Lang.IN_TERRAIN.build());
			return;
		}
		if(MinecraftTerrain.getInstance().getTerrainManager().getUserTerrains(user).size() >= user.getMaxTerrain() ) {
			p.sendMessage(Lang.MAX_TERRAINS.build().replace("%quantidade%", user.getMaxTerrain().toString()));
			return;
		}
		if(Integer.parseInt(args[1]) < MinecraftTerrain.getInstance().getConfigManager().getTerrainMinSize()) {
			p.sendMessage(Lang.MIN_TERRAIN_SIZE.build().replace("%tamanho%", MinecraftTerrain.getInstance().getConfigManager().getTerrainMinSize().toString()));
			return;
		}
		if(Integer.parseInt(args[1]) > MinecraftTerrain.getInstance().getConfigManager().getTerrainMaxSize()) {
			p.sendMessage(Lang.MAX_TERRAIN_SIZE.build().replace("%tamanho%", MinecraftTerrain.getInstance().getConfigManager().getTerrainMaxSize().toString()));
			return;
		}
		if(Integer.parseInt(args[1]) % 2 != 0) {
			p.sendMessage(Lang.PAR_SIZE.build());
			return;
		}
		Cuboid cuboid = MinecraftTerrain.getInstance().getTerrainManager().generateCuboid(p.getLocation(), Integer.parseInt(args[1]));
		
		if(MinecraftTerrain.getInstance().getTerrainManager().intersectTerrain(cuboid) != null) {
			p.sendMessage(Lang.TERRAIN_INTERSECT.build());
			return;
		}
		String json = cuboid.toJson().toString();
		
		JsonObject spawnObject = new JsonObject();
		
		spawnObject.addProperty("x", cuboid.getCenter().getX());
		spawnObject.addProperty("y", cuboid.getCenter().getY());
		spawnObject.addProperty("z", cuboid.getCenter().getZ());
		spawnObject.addProperty("yaw", cuboid.getCenter().getYaw());
		spawnObject.addProperty("pitch", cuboid.getCenter().getPitch());
		spawnObject.addProperty("world", cuboid.getCenter().getWorld().getName());
		
		String spawn = spawnObject.toString();
		user.removeMoney(value);
		Query insertTerrain = new Query("INSERT INTO `terrain` (`id`, `user_id`, `cuboid`, `spawn`, `sale`) VALUES (NULL, '"+user.getId()+"', '"+json+"', '"+spawn+"', '0')");
		int terrainId = insertTerrain.insertGetID("id");
		
		Query insertFlags = new Query("INSERT INTO `terrain_flags` (`id`, `terrain_id`, `join`, `pvp`, `construct`) VALUES (NULL, '"+terrainId+"', '0', '0', '0')");
		insertFlags.execute();
		MinecraftTerrain.getInstance().getTerrainManager().getCache().downloadTerrain(terrainId);
		for(Location loc : cuboid.getWalls((int)p.getLocation().getY())) {
			loc.getBlock().setType(Material.FENCE);
		}
		
		p.sendMessage(Lang.BROUGHT_TERRAIN.build().replace("%valor%", Utils.formatDouble(value) + ""));
	}
}