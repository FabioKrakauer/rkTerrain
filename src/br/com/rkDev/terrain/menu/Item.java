package br.com.rkDev.terrain.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Item {
	
	private Material material;
	private ItemStack stack;
	private String name;
	private ItemMeta meta;
	private ArrayList<String> lore;
	private Integer amount = 1;
	private HashMap<Enchantment, Integer> enchantment;
	private short data = 0;
	
	public Item(Material material) {
		this.material = material;
		this.lore = new ArrayList<>();
		this.enchantment = new HashMap<Enchantment, Integer>();
	}
	public Item() {
		this.lore = new ArrayList<>();
		this.enchantment = new HashMap<Enchantment, Integer>();
	}
	public Item(Material material, String name) {
		this.material = material;
		setName(name);
		this.lore = new ArrayList<>();
		this.enchantment = new HashMap<Enchantment, Integer>();
	}
	public Item(Material material, Integer amount) {
		this.material = material;
		this.amount = amount;
		this.enchantment = new HashMap<Enchantment, Integer>();
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	public Material getMaterial() {
		return this.material;
	}
	public ItemStack getItemStack() {
		constructItem();
		return this.stack;
	}
	public ItemMeta getItemMeta() {
		return this.meta;
	}
	public short getData() {
		return this.data;
	}
	public void setData(short data) {
		this.data = data;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}
	public ArrayList<String> getLore(){
		return this.lore;
	}
	public void addLore(String line) {
		this.lore.add(line);
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public int getAmount() {
		return this.amount;
	}
	public void addEnchantment(Enchantment enchantment, int level) {
		this.enchantment.put(enchantment, level);
	}
	public boolean hasEnchant() {
		return this.enchantment.size() > 0;
	}
	public void constructItem() {
		this.stack = new ItemStack(this.material, getAmount(), data);
		this.meta = this.stack.getItemMeta();
		this.meta.setDisplayName(this.name);
		this.meta.setLore(this.lore);
		if(hasEnchant()) {
			for(Entry<Enchantment, Integer> map : this.enchantment.entrySet()) {
				this.meta.addEnchant(map.getKey(), map.getValue(), true);
			}
		}
		this.stack.setItemMeta(this.meta);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + data;
		result = prime * result + ((enchantment == null) ? 0 : enchantment.hashCode());
		result = prime * result + ((lore == null) ? 0 : lore.hashCode());
		result = prime * result + ((material == null) ? 0 : material.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Item other = (Item) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (data != other.data)
			return false;
		if (enchantment == null) {
			if (other.enchantment != null)
				return false;
		} else if (!enchantment.equals(other.enchantment))
			return false;
		if (lore == null) {
			if (other.lore != null)
				return false;
		} else if (!lore.equals(other.lore))
			return false;
		if (material != other.material)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	

}
