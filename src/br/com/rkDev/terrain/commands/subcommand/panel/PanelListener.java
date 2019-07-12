package br.com.rkDev.terrain.commands.subcommand.panel;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import br.com.rkDev.terrain.MinecraftTerrain;
import br.com.rkDev.terrain.user.User;

public class PanelListener implements Listener{
	
	public PanelListener() {
		Bukkit.getPluginManager().registerEvents(this, MinecraftTerrain.getInstance());
	}
	
	@EventHandler
	public void selectOptionMainMenu(InventoryClickEvent e) {
		if(e.getInventory().getName().equalsIgnoreCase("Painel do terreno")) {
			e.setCancelled(true);
			User user = MinecraftTerrain.getInstance().getUserManager().getUser(e.getWhoClicked().getUniqueId());
			if(e.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
				return;
			}
			if(e.getCurrentItem().getType().equals(Material.REDSTONE_COMPARATOR)) {
				return;
			}
			if(e.getCurrentItem().getType().equals(Material.ITEM_FRAME)) {
				return;
			}
			if(e.getCurrentItem().getType().equals(Material.EYE_OF_ENDER)) {
				return;
			}
		}
	}
	
	public void openMembersMenu(User user) {
		
	}
}
