package br.com.rkDev.terrain.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import br.com.rkDev.terrain.MinecraftTerrain;
import br.com.rkDev.terrain.manage.Terrain;
import br.com.rkDev.terrain.user.User;

public class CallCustomEvents implements Listener{
	
	public CallCustomEvents() {
		Bukkit.getPluginManager().registerEvents(this, MinecraftTerrain.getInstance());
	}
	@EventHandler
	public void callJoinTerrainEvent(PlayerMoveEvent e) {
		Terrain isntTerrain = MinecraftTerrain.getInstance().getTerrainManager().getTerrain(e.getFrom());
		Terrain terrain = MinecraftTerrain.getInstance().getTerrainManager().getTerrain(e.getTo());
		if(terrain != null && isntTerrain == null) {
			User user = MinecraftTerrain.getInstance().getUserManager().getUser(e.getPlayer().getUniqueId());
			UserJoinInTerrainEvent event = new UserJoinInTerrainEvent(user, terrain);
			Bukkit.getPluginManager().callEvent(event);
			e.setCancelled(event.isCancelled());
		}
	}
	@EventHandler
	public void callPvPTerrainEvent(EntityDamageByEntityEvent e) {
		if(!(e.getDamager() instanceof Player)) {
			return;
		}
		Terrain terrain = MinecraftTerrain.getInstance().getTerrainManager().getTerrain(e.getEntity().getLocation());
		if(terrain != null) {
			User user = MinecraftTerrain.getInstance().getUserManager().getUser(e.getDamager().getUniqueId());
			UserHitInTerrainEvent event = new UserHitInTerrainEvent(user, terrain);
			Bukkit.getPluginManager().callEvent(event);
			e.setCancelled(event.isCancelled());
		}
	}
	@EventHandler
	public void callBuildTerrainEvent(BlockPlaceEvent e) {
		Terrain terrain = MinecraftTerrain.getInstance().getTerrainManager().getTerrain(e.getBlock().getLocation());
		if(terrain != null) {
			User user = MinecraftTerrain.getInstance().getUserManager().getUser(e.getPlayer().getUniqueId());
			UserBuildInTerrainEvent event = new UserBuildInTerrainEvent(user, terrain);
			Bukkit.getPluginManager().callEvent(event);
			e.setCancelled(event.isCancelled());
		}
	}
	@EventHandler
	public void callBuildTerrainEvent(BlockBreakEvent e) {
		Terrain terrain = MinecraftTerrain.getInstance().getTerrainManager().getTerrain(e.getBlock().getLocation());
		if(terrain != null) {
			User user = MinecraftTerrain.getInstance().getUserManager().getUser(e.getPlayer().getUniqueId());
			UserBuildInTerrainEvent event = new UserBuildInTerrainEvent(user, terrain);
			Bukkit.getPluginManager().callEvent(event);
			e.setCancelled(event.isCancelled());
		}
	}
}
