package br.com.rkDev.terrain;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.rkDev.terrain.config.ConfigFile;
import net.milkbowl.vault.economy.Economy;

public class MinecraftTerrain extends JavaPlugin{
	
	public static MinecraftTerrain INSTACE;
	private Economy economy;
	
	private FileConfiguration config;
	private ConfigFile messages;
	
	public void onEnable() {
		INSTACE = this;
		
		if(!setupEconomy()) {
			Bukkit.getConsoleSender().sendMessage("§c==========================================");
			System.out.println(" ");
			Bukkit.getConsoleSender().sendMessage("§cNao foi possível encontrar um plugin de economia compatível");
			System.out.println(" ");
			Bukkit.getConsoleSender().sendMessage("§c==========================================");
		}
		//Start de config
		System.out.println("[rkTerrain] Carregando configurações...");
		config = getConfig();
		saveDefaultConfig();
		
		messages = new ConfigFile(this, "messages.yml");
		setConfigMessages();
		messages.saveConfig();
		
	}
	
	public boolean setupEconomy() {
		RegisteredServiceProvider<Economy> provider = getServer().getServicesManager().getRegistration(Economy.class);
		if(provider != null) {
			economy = provider.getProvider();
		}
		return economy != null;
	}
	public static MinecraftTerrain getInstance() {
		return INSTACE;
	}
	
	public FileConfiguration getConfig() {
		return config;
	}
	
	public ConfigFile getMessages() {
		return messages;
	}
	
	public void setConfigMessages() {
		if(getMessages().getConfig().getString("SemPermissao") == null) {
			getMessages().getConfig().set("SemPermissao", "&cVoce nao possui permissao!");
			getMessages().saveConfig();
		}
	}
	
}