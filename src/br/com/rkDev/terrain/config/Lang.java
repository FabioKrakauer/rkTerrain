package br.com.rkDev.terrain.config;

import br.com.rkDev.terrain.MinecraftTerrain;

public enum Lang {

	NO_PERMISSION(MinecraftTerrain.getInstance().getMessages().getConfig().get("SemPermissao").toString()),
	
	ONLY_PLAYER(MinecraftTerrain.getInstance().getMessages().getConfig().getString("Somente_Players")),
	INVALID_ARGS(MinecraftTerrain.getInstance().getMessages().getConfig().getString("Uso_Correto")),
	
	NO_CREDITS(MinecraftTerrain.getInstance().getMessages().getConfig().getString("Saldo_Insuficiente")),
	
	TERRAIN_NOTSALE(MinecraftTerrain.getInstance().getMessages().getConfig().getString("TerrenoNaoAVenda")),
	MAX_TERRAINS(MinecraftTerrain.getInstance().getMessages().getConfig().getString("Maximo_Terrenos")),
	NULL_TERRAIN(MinecraftTerrain.getInstance().getMessages().getConfig().getString("TerrenoNaoEncontrado")),
	;
	
//	
	public String message;
	Lang(String messag){
		message = messag;
	}
	public String build() {
		return message.replace("&", "§");
	}
	
}
