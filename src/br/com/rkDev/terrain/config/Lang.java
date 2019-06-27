package br.com.rkDev.terrain.config;

import br.com.rkDev.terrain.MinecraftTerrain;

public enum Lang {

	NO_PERMISSION(MinecraftTerrain.getInstance().getMessages().getConfig().get("SemPermissao").toString()),
	ONLY_PLAYER(MinecraftTerrain.getInstance().getMessages().getConfig().getString("Somente_Players")),
	INVALID_ARGS(MinecraftTerrain.getInstance().getMessages().getConfig().getString("Uso_Correto"));
	
	public String message;
	Lang(String messag){
		message = messag;
	}
	public String build() {
		return message.replace("&", "§");
	}
	
}
