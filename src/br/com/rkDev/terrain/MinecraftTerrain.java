package br.com.rkDev.terrain;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.rkDev.terrain.config.ConfigFile;

public class MinecraftTerrain extends JavaPlugin{
	
	public static MinecraftTerrain INSTACE;
	
	private FileConfiguration config;
	private ConfigFile messages;
	
	public void onEnable() {
		INSTACE = this;
		
		//Start de config
		System.out.println("[rkMining] Carregando configurações...");
		config = getConfig();
		saveDefaultConfig();
		
		messages = new ConfigFile(this, "messages.yml");
		setConfigMessages();
		messages.saveConfig();
		
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