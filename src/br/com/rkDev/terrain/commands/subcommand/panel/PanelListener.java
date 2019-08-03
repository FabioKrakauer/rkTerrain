package br.com.rkDev.terrain.commands.subcommand.panel;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.google.gson.JsonObject;

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
			if(e.getCurrentItem() == null) {
				return;
			}
			User user = MinecraftTerrain.getInstance().getUserManager().getUser(e.getWhoClicked().getUniqueId());
			Terrain terrain = MinecraftTerrain.getInstance().getTerrainManager().getTerrain(e.getWhoClicked().getLocation());
			if(terrain == null) {
				e.getWhoClicked().sendMessage(Lang.NULL_TERRAIN.build());
				return;
			}
			if(terrain.getOwner().getId() != user.getId()) {
				e.getWhoClicked().sendMessage(Lang.NOT_YOUR_TERRAIN.build());
				return;
			}
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
				Location loc = e.getWhoClicked().getLocation();
				JsonObject json = new JsonObject();
				json.addProperty("x", loc.getX());
				json.addProperty("y", loc.getY());
				json.addProperty("z", loc.getZ());
				json.addProperty("yaw", loc.getYaw());
				json.addProperty("pitch", loc.getPitch());
				json.addProperty("world", loc.getWorld().getName());
				Query update = new Query("UPDATE `terrain` SET `spawn`='"+json.toString()+"' WHERE `id`='"+terrain.getId()+"'");
				update.execute();
				MinecraftTerrain.getInstance().getTerrainManager().getCache().downloadTerrain(terrain.getId());
				e.getWhoClicked().sendMessage(Lang.TERRAIN_SPAWN_ALTERNED.build());
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
		Terrain terrain = MinecraftTerrain.getInstance().getTerrainManager().getTerrain(p.getLocation());
		
		if(terrain == null) {
			p.sendMessage(Lang.NULL_TERRAIN.build());
			return;
		}
		
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
		
		Item doorOption = new Item(Material.WOOL);
		doorOption.setData((short) 14);
		doorOption.setName("§bClique para ativar a entrada!");
		if(terrain.canJoin()) {
			doorOption.setData((short) 5);
			doorOption.setName("§bClique para desativar a entrada!");
		}
		Item pvpOption = new Item(Material.WOOL);
		pvpOption.setData((short) 14);
		pvpOption.setName("§bClique para ativar o pvp!");
		if(terrain.canPvP()) {
			pvpOption.setData((short) 5);
			pvpOption.setName("§bClique para desativar o pvp!");
		}
		
		Item buildOption = new Item(Material.WOOL);
		buildOption.setData((short) 14);
		buildOption.setName("§bClique para ativar a construção!");
		if(terrain.canBuid()) {
			buildOption.setData((short) 5);
			buildOption.setName("§bClique para desativar a construção!");
		}
		
		inv.setItem(11, sword.getItemStack());
		inv.setItem(13, door.getItemStack());
		inv.setItem(15, anvil.getItemStack());
		
		inv.setItem(29, pvpOption.getItemStack());
		inv.setItem(31, doorOption.getItemStack());
		inv.setItem(33, buildOption.getItemStack());
		
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
					if(terrain.getOwner().getId() != user.getId()) {
						p.sendMessage(Lang.NOT_YOUR_TERRAIN.build());
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
	@EventHandler
	public void onChangeFlag(InventoryClickEvent e) {
		if(e.getInventory().getName().equalsIgnoreCase("Configurações do terreno")) {
			e.setCancelled(true);
			Player p = (Player)e.getWhoClicked();
			User user = MinecraftTerrain.getInstance().getUserManager().getUser(e.getWhoClicked().getUniqueId());
			Terrain terrain = MinecraftTerrain.getInstance().getTerrainManager().getTerrain(e.getWhoClicked().getLocation());
			if(terrain == null) {
				p.sendMessage(Lang.NULL_TERRAIN.build());
				return;
			}
			if(terrain.getOwner().getId() != user.getId()) {
				p.sendMessage(Lang.NOT_YOUR_TERRAIN.build());
				return;
			}
			if(e.getCurrentItem() == null) {
				return;
			}
			if(e.getCurrentItem().getType().equals(Material.WOOL)) {
				switch (e.getCurrentItem().getItemMeta().getDisplayName()) {
					case "§bClique para ativar a entrada!":
						Query setJoinOn = new Query("UPDATE `terrain_flags` SET `join`='1' WHERE `terrain_id`='"+terrain.getId()+"'");
						setJoinOn.execute();
						break;
					case "§bClique para desativar a entrada!":
						Query setJoinOff = new Query("UPDATE `terrain_flags` SET `join`='0' WHERE `terrain_id`='"+terrain.getId()+"'");
						setJoinOff.execute();
						break;
					case "§bClique para ativar o pvp!":
						Query setPvPOn = new Query("UPDATE `terrain_flags` SET `pvp`='1' WHERE `terrain_id`='"+terrain.getId()+"'");
						setPvPOn.execute();
						break;
					case "§bClique para desativar o pvp!":
						Query setPvPOff = new Query("UPDATE `terrain_flags` SET `pvp`='0' WHERE `terrain_id`='"+terrain.getId()+"'");
						setPvPOff.execute();
						break;
					case "§bClique para ativar a construção!":
						Query setBuildOn = new Query("UPDATE `terrain_flags` SET `construct`='1' WHERE `terrain_id`='"+terrain.getId()+"'");
						setBuildOn.execute();
						break;
					case "§bClique para desativar a construção!":
						Query setBuildOff = new Query("UPDATE `terrain_flags` SET `construct`='0' WHERE `terrain_id`='"+terrain.getId()+"'");
						setBuildOff.execute();
						break;
				}
				MinecraftTerrain.getInstance().getTerrainManager().getCache().downloadTerrain(terrain.getId());
				openConfigMenu(user);
			}
		}
	}
}
