package br.com.rkDev.terrain.commands.subcommand.panel;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import br.com.rkDev.terrain.MinecraftTerrain;
import br.com.rkDev.terrain.Utils;
import br.com.rkDev.terrain.config.Lang;
import br.com.rkDev.terrain.manage.Terrain;
import br.com.rkDev.terrain.menu.Item;
import br.com.rkDev.terrain.user.User;

public class PanelCommand {
	
	private User user;
	
	public PanelCommand(User user) {
		this.user = user;
		execute();
	}
	
	private void execute() {
		Player p = Bukkit.getPlayer(user.getName());
		Terrain terrain = MinecraftTerrain.getInstance().getTerrainManager().getTerrain(p.getLocation());
		if(terrain == null) {
			p.sendMessage(Lang.NULL_TERRAIN.build());
			return;
		}
		if(terrain.getOwner().getId() != user.getId()) {
			p.sendMessage(Lang.NOT_YOUR_TERRAIN.build());
			return;
		}
		
		Item config = new Item(Material.REDSTONE_COMPARATOR);
		config.setName("§aConfigurações");
		config.addLore(" ");
		config.addLore("§e- Muda as configurações do terreno!");
		config.constructItem();
		
		Item sale = new Item(Material.ITEM_FRAME);
		String onSale = "§7A venda: §c§lNAO";
		if(terrain.isOnSale()) {
			onSale = "§7A venda: §a§lSIM";
			sale.addLore(" ");
			sale.addLore("§7Valor: §e" + Utils.formatDouble(terrain.getSaleValue()));
		}
		sale.setName(onSale);
		sale.constructItem();
		
		Item spawn = new Item(Material.EYE_OF_ENDER);
		spawn.setName("§aDefinir spawn");
		spawn.addLore(" ");
		spawn.addLore("§e- Defini spawn do terreno!");
		spawn.constructItem();
		
		Inventory inv = Bukkit.createInventory(p, 9*3, "Painel do terreno");
		
		inv.setItem(10, user.getSkull("§aMembros"));
		inv.setItem(12, config.getItemStack());
		inv.setItem(14, sale.getItemStack());
		inv.setItem(16, spawn.getItemStack());
		
		p.closeInventory();
		p.openInventory(inv);
	}

}
