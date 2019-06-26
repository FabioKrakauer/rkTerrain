package br.com.rkDev.terrain.config;

import org.bukkit.Bukkit;

import br.com.rkDev.terrain.MinecraftTerrain;

public class ConfigManager {
	
	private MinecraftTerrain main;
	
	public ConfigManager() {
		this.main = MinecraftTerrain.getInstance();
		validateConfig();
	}
	public void validateConfig() {
		boolean error = false;
		String erro = "Nao indentificado";
		
		if(getTerrainMaxSize() == 0) {
			error = true;
			erro = "Tamanho maximo do terreno";
		}
		if(getTerrainMinSize() == 0) {
			error = true;
			erro = "Tamanho minimo do terreno";
		}
		if(getAdminPermission() == null) {
			error = true;
			erro = "Permissao do administrador";
		}
		if(getWallBlock() == null) {
			error = true;
			erro = "Bloco da parede";
		}
		if(getPriceBlock() == 0) {
			error = true;
			erro = "Pre�o por bloco ao quadrado";
		}
		
		if(error) {
			Bukkit.getConsoleSender().sendMessage("�c----------------------------------------------");
			Bukkit.getConsoleSender().sendMessage("�cErro na CONFIG do plugin! Apague o arquivo e reinicie o servidor!");
			Bukkit.getConsoleSender().sendMessage("�cErro: " + erro + "!");
			Bukkit.getConsoleSender().sendMessage("�c----------------------------------------------");
			Bukkit.shutdown();
		}
	}
	
	public Integer getTerrainMaxSize() {
		return getMain().getConfig().getInt("Terreno.Tamanho_Maximo");
	}
	
	public Integer getTerrainMinSize() {
		return getMain().getConfig().getInt("Terreno.Tamanho_Minimo");
	}
	
	public String getAdminPermission() {
		return getMain().getConfig().getString("Terreno.PermissaoAdmin");
	}
	
	public String getWallBlock() {
		return getMain().getConfig().getString("Terreno.BlocoParede");
	}
	
	public Integer getPriceBlock() {
		return getMain().getConfig().getInt("Terreno.Pre�oQuadrado");
	}
	
	private MinecraftTerrain getMain() {
		return main;
	}
}