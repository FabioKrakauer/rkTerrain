package br.com.rkDev.terrain;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.rkDev.terrain.config.ConfigFile;
import br.com.rkDev.terrain.config.ConfigManager;
import br.com.rkDev.terrain.database.DatabaseManager;
import br.com.rkDev.terrain.manage.TerrainManager;
import br.com.rkDev.terrain.user.UserManager;
import net.milkbowl.vault.economy.Economy;

public class MinecraftTerrain extends JavaPlugin{
	
	public static MinecraftTerrain INSTACE;
	private Economy economy;

	private ConfigFile messages;
	
	private ConfigManager configManager;
	private DatabaseManager databaseManager;
	private UserManager userManager;
	private TerrainManager terrainManager;
	
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
		saveDefaultConfig();
		setConfig();
		
		messages = new ConfigFile(this, "messages.yml");
		setConfigMessages();
		messages.saveConfig();
		
		System.out.println("[rkTerrain] Configurações carregadas com sucesso!");
		//managers
		System.out.println("[rkTerrain] Carregando managers...");
		
		configManager = new ConfigManager();
		databaseManager = new DatabaseManager();
		databaseManager.setup();
		userManager = new UserManager();
		terrainManager = new TerrainManager();
		
		System.out.println("[rkTerrain] Managers Carregados!");
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
	
	public Economy getEconomy() {
		return economy;
	}
	
	public ConfigFile getMessages() {
		return messages;
	}
	
	public void setConfigMessages() {
		if(getMessages().getConfig().getString("SemPermissao") == null) {
			getMessages().getConfig().set("SemPermissao", "&cVoce nao possui permissao!");
			getMessages().saveConfig();
		}
		if(getMessages().getConfig().getString("Somente_Players") == null) {
			getMessages().getConfig().set("Somente_Players", "&cSomentes player podem digitar este comando!");
			getMessages().saveConfig();
		}
	}
	
	public void setConfig() {
		if(getConfig().isConfigurationSection("MySQL") == false) {
			getConfig().set("MySQL.Host", "localhost");
			getConfig().set("MySQL.Port", 3306);
			getConfig().set("MySQL.Database", "terrain");
			getConfig().set("MySQL.User", "root");
			getConfig().set("MySQL.Password", "");
			saveConfig();
		}
		if(getConfig().isConfigurationSection("Terreno") == false) {
			getConfig().set("Terreno.Tamanho_Maximo", 100);
			getConfig().set("Terreno.Tamanho_Minimo", 10);
			getConfig().set("Terreno.PermissaoAdmin", "rkterrain.admin");
			getConfig().set("Terreno.BlocoParede", "188:0");
			getConfig().set("Terreno.PrecoQuadrado", 1000);
			getConfig().set("Terreno.TerrenoPorPlayerPadrao", 2);
			saveConfig();
		}
	}
	
	public DatabaseManager getDatabaseManager() {
		return databaseManager;
	}
	
	public UserManager getUserManager() {
		return userManager;
	}
	
	public TerrainManager getTerrainManager() {
		return terrainManager;
	}
	
	public ConfigManager getConfigManager() {
		return configManager;
	}
}