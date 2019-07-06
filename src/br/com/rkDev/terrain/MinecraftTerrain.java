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
		if(getMessages().getConfig().getString("ComandoNaoEncontrado") == null) {
			getMessages().getConfig().set("ComandoNaoEncontrado", "&cComando não encontrado!");
			getMessages().saveConfig();
		}
		if(getMessages().getConfig().getString("Somente_Players") == null) {
			getMessages().getConfig().set("Somente_Players", "&cSomentes players podem digitar este comando!");
			getMessages().saveConfig();
		}
		if(getMessages().getConfig().getString("Uso_Correto") == null) {
			getMessages().getConfig().set("Uso_Correto", "&eForma incorreta! Digite: &f%comando%");
			getMessages().saveConfig();
		}
		if(getMessages().getConfig().getString("TerrenoNaoAVenda") == null) {
			getMessages().getConfig().set("TerrenoNaoAVenda", "&eEste terreno nao esta a venda!");
			getMessages().saveConfig();
		}
		if(getMessages().getConfig().getString("Maximo_Terreno") == null) {
			getMessages().getConfig().set("Maximo_Terreno", "&cVoce ja possui o maximo terrenos de %quantidade%");
			getMessages().saveConfig();
		}
		if(getMessages().getConfig().getString("Saldo_Insuficiente") == null) {
			getMessages().getConfig().set("Saldo_Insuficiente", "&eSaldo insuficiente! Este terreno custa %valor%!");
			getMessages().saveConfig();
		}
		if(getMessages().getConfig().getString("TerrenoNaoEncontrado") == null) {
			getMessages().getConfig().set("TerrenoNaoEncontrado", "&cVoce nao esta sobre nenhum terreno!");
			getMessages().saveConfig();
		}
		if(getMessages().getConfig().getString("Terreno_Comprado") == null) {
			getMessages().getConfig().set("Terreno_Comprado", "&aVocê comprou o terreno por %valor%!");
			getMessages().saveConfig();
		}
		if(getMessages().getConfig().getString("DigiteSomenteNumeros") == null) {
			getMessages().getConfig().set("DigiteSomenteNumeros", "&cDigite somente números!");
			getMessages().saveConfig();
		}
		if(getMessages().getConfig().getString("DigiteSomenteNumerosDecimal") == null) {
			getMessages().getConfig().set("DigiteSomenteNumerosDecimal", "&cDigite somente números com somente um ponto(.) para casa decimal!");
			getMessages().saveConfig();
		}
		if(getMessages().getConfig().getString("VoceEsteSobreUmTerreno") == null) {
			getMessages().getConfig().set("VoceEsteSobreUmTerreno", "&eVoce esta sobre um terreno!");
			getMessages().saveConfig();
		}
		if(getMessages().getConfig().getString("Tamanho_Minimo") == null) {
			getMessages().getConfig().set("Tamanho_Minimo", "&cO tamanho do terreno deve ser no minimo %tamanho%");
			getMessages().saveConfig();
		}
		if(getMessages().getConfig().getString("Tamanho_Maximo") == null) {
			getMessages().getConfig().set("Tamanho_Maximo", "&cO tamanho do terreno deve ser no maximo %tamanho%");
			getMessages().saveConfig();
		}
		if(getMessages().getConfig().getString("ComprarProprioTerreno") == null) {
			getMessages().getConfig().set("ComprarProprioTerreno", "&eVocê não pode comprar seu proprio terreno!");
			getMessages().saveConfig();
		}
		if(getMessages().getConfig().getString("Tamanho_Par") == null) {
			getMessages().getConfig().set("Tamanho_Par", "&eO tamanho do terreno deve ser um numero par!");
			getMessages().saveConfig();
		}
		if(getMessages().getConfig().getString("Terreno_Intersecao") == null) {
			getMessages().getConfig().set("Terreno_Intersecao", "&eExiste um terreno que intercede o seu novo terreno.");
			getMessages().saveConfig();
		}
		if(getMessages().getConfig().getString("NaoESeuTerreno") == null) {
			getMessages().getConfig().set("NaoESeuTerreno", "&eEste terreno não é seu!");
			getMessages().saveConfig();
		}
		if(getMessages().getConfig().getString("TerrenoJaAVenda") == null) {
			getMessages().getConfig().set("TerrenoJaAVenda", "&eEste terreno ja esta a venda!");
			getMessages().saveConfig();
		}
		if(getMessages().getConfig().getString("TerrenoAVenda") == null) {
			getMessages().getConfig().set("TerrenoAVenda", "&aVocê anunciou seu terreno por %valor% com sucesso!");
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
//			getConfig().set("Terreno.BlocoParede", "188:0");
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