package br.com.rkDev.terrain.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import br.com.rkDev.terrain.MinecraftTerrain;
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
			}
		}
	}

}
