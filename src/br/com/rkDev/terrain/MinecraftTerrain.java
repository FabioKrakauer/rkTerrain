package br.com.rkDev.terrain;

import org.bukkit.plugin.java.JavaPlugin;

public class MinecraftTerrain extends JavaPlugin{
	
	public static MinecraftTerrain INSTACE;
	
	public void onEnable() {
		INSTACE = this;
	}
	
	public static MinecraftTerrain getInstance() {
		return INSTACE;
	}
	

}
