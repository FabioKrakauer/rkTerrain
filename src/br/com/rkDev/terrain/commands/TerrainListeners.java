package br.com.rkDev.terrain.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import br.com.rkDev.terrain.MinecraftTerrain;
import br.com.rkDev.terrain.config.Lang;
import br.com.rkDev.terrain.events.UserBuildInTerrainEvent;
import br.com.rkDev.terrain.events.UserHitInTerrainEvent;
import br.com.rkDev.terrain.events.UserJoinInTerrainEvent;
import br.com.rkDev.terrain.manage.Terrain;
import br.com.rkDev.terrain.menu.PageableMenu;
import br.com.rkDev.terrain.user.User;

public class TerrainListeners implements Listener{
	
	public TerrainListeners() {
		Bukkit.getPluginManager().registerEvents(this, MinecraftTerrain.getInstance());
	}
	
	@EventHandler
	public void GoCommandMenu(InventoryClickEvent e) {
		if(e.getInventory().getName().contains("Terrenos - Pag(")) {
			e.setCancelled(true);
			User user = MinecraftTerrain.getInstance().getUserManager().getUser(e.getWhoClicked().getUniqueId());
			Player p = (Player)e.getWhoClicked();
			if(e.getCurrentItem().getType().equals(Material.ARROW)) {
				PageableMenu pm = MinecraftTerrain.getInstance().getPageableMenuManager().getPageableMenuUser(user);
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aAvancar ->")) {
					pm.nextPage();
					return;
				}else {
					pm.backPage();
					return;
				}
			}else if(e.getCurrentItem().getType().equals(Material.FENCE) || e.getCurrentItem().getType().equals(Material.DARK_OAK_FENCE)) {
				Terrain t = MinecraftTerrain.getInstance().getTerrainManager().getTerrain(Integer.parseInt(e.getCurrentItem().getItemMeta().getLore().get(0).replace("§8", "")));
				p.teleport(t.getSpawn());
				p.sendMessage(Lang.TELEPORTED_TERRAIN.build());
			}
		}
	}
	@EventHandler()
	public void userJoinInTerrain(UserJoinInTerrainEvent e) {
		Terrain terrain = e.getTerrain();
		User user = e.getUser();
		if(!terrain.getFlags()[0]) {
			if(terrain.getOwner().getId() != user.getId() && (!terrain.getFriends().contains(user))) {
				if(MinecraftTerrain.getInstance().getUserManager().isWithBypass(user)) {
					return;
				}
				e.setCancelled(true);
			}
		}
	}
	@EventHandler()
	public void usePvPInTerrain(UserHitInTerrainEvent e) {
		Terrain terrain = e.getTerrain();
		if(!terrain.getFlags()[1]) {
			e.setCancelled(true);
		}
	}
	@EventHandler()
	public void userBuildInTerrain(UserBuildInTerrainEvent e) {
		Terrain terrain = e.getTerrain();
		User user = e.getUser();
		if(!terrain.getFlags()[2]) {
			if(terrain.getOwner().getId() != user.getId() && (!terrain.getFriends().contains(user))) {
				if(MinecraftTerrain.getInstance().getUserManager().isWithBypass(user)) {
					return;
				}
				e.setCancelled(true);
			}
		}
	}
}
