package br.com.rkDev.terrain.commands.subcommand.panel;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import br.com.rkDev.terrain.MinecraftTerrain;
import br.com.rkDev.terrain.config.Lang;
import br.com.rkDev.terrain.database.Query;
import br.com.rkDev.terrain.manage.Terrain;
import br.com.rkDev.terrain.menu.AnvilGUI;
import br.com.rkDev.terrain.menu.AnvilGUI.AnvilClickEvent;
import br.com.rkDev.terrain.menu.AnvilGUI.AnvilSlot;
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
				openConfigMenu(user);
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
	
	public void openConfigMenu(User user) {
		Player p = Bukkit.getPlayer(user.getName());
		Inventory inv = Bukkit.createInventory(p, 9*5, "Configurações do terreno");
		
		Item door = new Item(Material.IRON_DOOR, "§bEntrada");
		door.addLore(" ");
		door.addLore("§aPermite ou bloqueia a entrada no seu terreno!");
		door.constructItem();
		
		Item sword = new Item(Material.DIAMOND_SWORD, "§bPvP");
		sword.addLore(" ");
		sword.addLore("§aPermite ou bloqueia o PvP no terreno!");
		sword.constructItem();
		
		Item anvil = new Item(Material.ANVIL, "§bConstruções");
		anvil.addLore(" ");
		anvil.addLore("§aPermite ou bloqueia o construção no terreno!");
		anvil.constructItem();
		
		inv.setItem(11, sword.getItemStack());
		inv.setItem(13, door.getItemStack());
		inv.setItem(15, anvil.getItemStack());
		
		p.closeInventory();
		p.openInventory(inv);
	}
	@EventHandler
	public void membersPanelController(InventoryClickEvent e) {
		if(e.getInventory().getName().contains("Membros Terreno - Pag(")) {
			e.setCancelled(true);
			Player p = (Player)e.getWhoClicked();
			User user = MinecraftTerrain.getInstance().getUserManager().getUser(e.getWhoClicked().getUniqueId());
			if(e.getCurrentItem() == null) {
				return;
			}
			if(e.getCurrentItem().getType().equals(Material.ARROW)) {
				PageableMenu pm = MinecraftTerrain.getInstance().getPageableMenuManager().getPageableMenuUser(user);
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aAvancar ->")) {
					pm.nextPage();
					return;
				}else {
					pm.backPage();
					return;
				}
			}
			if(e.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
				Terrain terrain = MinecraftTerrain.getInstance().getTerrainManager().getTerrain(p.getLocation());
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eAdicionar Membro")) {
					float exp = p.getExp();
					AnvilGUI gui = new AnvilGUI((Player) e.getWhoClicked(), new AnvilGUI.AnvilClickEventHandler() {

						@Override
						public void onAnvilClick(AnvilClickEvent event) {
							p.setExp(exp);
							User friend = MinecraftTerrain.getInstance().getUserManager().getUser(event.getName());
							if(friend == null) {
								p.sendMessage(Lang.USER_NOT_FOUND.build().replace("%jogador%", event.getName()));
								return;
							}
							if(terrain == null) {
								p.sendMessage(Lang.NULL_TERRAIN.build());
								return;
							}
							if(user.getId() != terrain.getOwner().getId()) {
								p.sendMessage(Lang.NOT_YOUR_TERRAIN.build());
								return;
							}
							if(terrain.getFriends().contains(friend) || user.getId() == friend.getId()) {
								p.sendMessage(Lang.USER_ALREADY_ADD.build());
								return;
							}
							Query query = new Query("INSERT INTO `user_terrain` (`id`, `user_id`, `terrain_id`) VALUES (NULL, '"+friend.getId()+"', '"+terrain.getId()+"')");
							query.execute();
							MinecraftTerrain.getInstance().getTerrainManager().getCache().downloadTerrain(terrain.getId());
							p.sendMessage(Lang.USER_ADD.build().replace("%jogador%", friend.getName()));
						}
						
					});
					gui.setSlot(AnvilSlot.INPUT_LEFT, user.getSkull("Digite o nome"));
					gui.open();
				}else {
					String name = e.getCurrentItem().getItemMeta().getDisplayName().split("§a")[1];
					User friendToRemove = MinecraftTerrain.getInstance().getUserManager().getUser(name);
					if(friendToRemove == null) {
						p.sendMessage(Lang.USER_NOT_FOUND.build());
						return;
					}
					if(terrain == null) {
						p.sendMessage(Lang.NULL_TERRAIN.build());
						return;
					}
					Query removeUser = new Query("DELETE FROM `user_terrain` WHERE `user_id`='"+friendToRemove.getId()+"' AND `terrain_id`='"+terrain.getId()+"'");
					removeUser.execute();
					MinecraftTerrain.getInstance().getTerrainManager().getCache().downloadTerrain(terrain.getId());
					p.sendMessage(Lang.USER_REMOVED.build().replace("%jogador%", friendToRemove.getName()));
					p.closeInventory();
				}
			}
		}
	}
}
