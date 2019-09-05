package br.com.rkDev.terrain.commands.subcommand;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.com.rkDev.terrain.MinecraftTerrain;
import br.com.rkDev.terrain.manage.Terrain;
import br.com.rkDev.terrain.menu.Item;
import br.com.rkDev.terrain.menu.PageableMenu;
import br.com.rkDev.terrain.user.User;

public class GoCommand {
	
	private User user;
	
	public GoCommand(User user) {
		this.user = user;
		execute();
	}
	
	private void execute() {
		List<ItemStack> itens = new ArrayList<ItemStack>();
		
		int quantity = 0;
		for(Terrain terrain : MinecraftTerrain.getInstance().getTerrainManager().getUserTerrains(user)) {
			quantity++;
			Item item = new Item(Material.FENCE, "§aTerreno #" + quantity);
			item.addLore("§8" + terrain.getId());
			itens.add(item.getItemStack());
		}
		for(Terrain terrain : MinecraftTerrain.getInstance().getTerrainManager().getUserFriendsTerrains(user)) {
			quantity++;
			Item item = new Item(Material.DARK_OAK_FENCE, "§aTerreno #" + quantity);
			item.addLore("§8" + terrain.getId());
			itens.add(item.getItemStack());
		}
		PageableMenu pm = new PageableMenu(itens, "Terrenos", user);
		pm.openInventoryOnPage(1);
		MinecraftTerrain.getInstance().getPageableMenuManager().setPageableMenuUser(user, pm);
	}

}
