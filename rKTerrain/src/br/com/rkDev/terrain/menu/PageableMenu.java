package br.com.rkDev.terrain.menu;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import br.com.rkDev.terrain.user.User;

public class PageableMenu {
	
	private List<ItemStack> itens;
	private String name;
	private int currentPage;
	private User user;
	private String type;
	private Inventory inv;
	
	private ItemStack customItem;
	private Integer customSlot;
	
	public PageableMenu(List<ItemStack> itens, String name, User user) {
		this.itens = itens;
		this.name = name;
		this.currentPage = 1;
		this.user = user;
		
		this.customItem = null;
		this.customSlot = 0;
	}
	public PageableMenu(List<ItemStack> itens, String name, User user, ItemStack item, int slot) {
		this.itens = itens;
		this.name = name;
		this.currentPage = 1;
		this.user = user;
		
		this.customItem = item;
		this.customSlot = slot;
	}
	public int getMaxPages() {
		int pages = itens.size() / 35;
		pages++;
		return pages;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void openInventoryOnPage(int page) {
		this.inv = Bukkit.createInventory(Bukkit.getPlayer(user.getName()), 9*6, this.name + " - Pag(" + page + "/" + getMaxPages()+")");
		int inicial = (page - 1) * 35;
		int MaxStopAt = page * 35 - 1;
		int stopAt = itens.size() > MaxStopAt ? MaxStopAt : itens.size() -1;
		int counter = inicial;
		int startInvSet = 9;
		Item back = new Item(Material.ARROW, "§a<- Voltar");
		Item total = new Item(Material.COMPASS, "§aTotal: " + itens.size());
		Item next = new Item(Material.ARROW, "§aAvancar ->");
		Item invType = new Item(Material.BARRIER, "§bTipo: " + this.type);
		invType.constructItem();
		back.constructItem();
		total.constructItem();
		next.constructItem();
		
		
		if(page > 1) {
			inv.setItem(48, back.getItemStack());
		}
		inv.setItem(49, total.getItemStack());
		if(page < getMaxPages()) {
			inv.setItem(50, next.getItemStack());
		}
		if(this.type != null) {
			inv.setItem(4, invType.getItemStack());
		}
		while(counter <= stopAt) {
			if(counter == itens.size()) {
				inv.setItem(startInvSet, itens.get(counter- 1));
			}else {
				inv.setItem(startInvSet, itens.get(counter));
			}
			startInvSet++;
			counter++;
		}
		if(this.customItem != null) {
			inv.setItem(this.customSlot, this.customItem);
		}
		Bukkit.getPlayer(user.getName()).openInventory(inv);
	}
	public int getCurrentPage() {
		return this.currentPage;
	}
	public void nextPage() {
		this.currentPage += 1;
		this.openInventoryOnPage(currentPage);
	}
	public void backPage() {
		this.currentPage -= 1;
		this.openInventoryOnPage(currentPage);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + currentPage;
		result = prime * result + ((itens == null) ? 0 : itens.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageableMenu other = (PageableMenu) obj;
		if (currentPage != other.currentPage)
			return false;
		if (itens == null) {
			if (other.itens != null)
				return false;
		} else if (!itens.equals(other.itens))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
}
