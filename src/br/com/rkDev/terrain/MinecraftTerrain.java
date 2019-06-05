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
		config = getConfig();
		saveDefaultConfig();
		
		messages = new ConfigFile(this, "messages.yml");
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
	
}