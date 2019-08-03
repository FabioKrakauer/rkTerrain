package br.com.rkDev.terrain.events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import br.com.rkDev.terrain.MinecraftTerrain;
import br.com.rkDev.terrain.manage.Terrain;
import br.com.rkDev.terrain.user.User;

public class CallCustomEvents implements Listener{
	
	public CallCustomEvents() {
		Bukkit.getPluginManager().registerEvents(this, MinecraftTerrain.getInstance());
	}
	@EventHandler
	public void callJoinTerrain(PlayerMoveEvent e) {
		Terrain instTerrain = MinecraftTerrain.getInstance().getTerrainManager().getTerrain(e.getFrom());
		Terrain terrain = MinecraftTerrain.getInstance().getTerrainManager().getTerrain(e.getTo());
		if(terrain != null && instTerrain == null) {
			User user = MinecraftTerrain.getInstance().getUserManager().getUser(e.getPlayer().getUniqueId());
			UserJoinInTerrainEvent event = new UserJoinInTerrainEvent(user, terrain);
			Bukkit.getPluginManager().callEvent(event);
			e.setCancelled(event.isCancelled());
		}
	}
}
