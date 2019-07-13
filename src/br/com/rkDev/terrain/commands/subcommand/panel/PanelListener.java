package br.com.rkDev.terrain.commands.subcommand.panel;

import java.util.ArrayList;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import br.com.rkDev.terrain.MinecraftTerrain;
import br.com.rkDev.terrain.manage.Terrain;
import br.com.rkDev.terrain.menu.Item;
import br.com.rkDev.terrain.menu.PageableMenu;
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
				openMembersMenu(user);
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
		Player p = Bukkit.getPlayer(user.getName());
		Terrain terrain = MinecraftTerrain.getInstance().getTerrainManager().getTerrain(p.getLocation());
		ArrayList<ItemStack> heads = new ArrayList<ItemStack>();
		for(User friend : terrain.getFriends()) {
			ItemStack stack = new ItemStack(Material.SKULL_ITEM);
			stack.setDurability((short) 3);
			SkullMeta meta = (SkullMeta)stack.getItemMeta();
			meta.setOwner(friend.getName());
			meta.setDisplayName("§a" + friend.getName());
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(" ");
			lore.add("§e- Clique para remover!");
			meta.setLore(lore);
			stack.setItemMeta(meta);
			heads.add(stack);
		}
		PageableMenu pm = new PageableMenu(heads, "Membros Terreno", user, user.getSkull("§eAdicionar Membro"), 0);
		pm.setType("Adicionar membro");
		pm.openInventoryOnPage(1);
		MinecraftTerrain.getInstance().getPageableMenuManager().setPageableMenuUser(user, pm);
	}
}
