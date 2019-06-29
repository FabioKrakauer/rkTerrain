package br.com.rkDev.terrain.config;

import br.com.rkDev.terrain.MinecraftTerrain;

public enum Lang {

	NO_PERMISSION(MinecraftTerrain.getInstance().getMessages().getConfig().get("SemPermissao").toString()),
	
	ONLY_PLAYER(MinecraftTerrain.getInstance().getMessages().getConfig().getString("Somente_Players")),
	INVALID_ARGS(MinecraftTerrain.getInstance().getMessages().getConfig().getString("Uso_Correto")),
	ONLY_NUMBERS(MinecraftTerrain.getInstance().getMessages().getConfig().getString("DigiteSomenteNumeros")),
	
	NO_CREDITS(MinecraftTerrain.getInstance().getMessages().getConfig().getString("Saldo_Insuficiente")),
	
	TERRAIN_NOTSALE(MinecraftTerrain.getInstance().getMessages().getConfig().getString("TerrenoNaoAVenda")),
	MAX_TERRAINS(MinecraftTerrain.getInstance().getMessages().getConfig().getString("Maximo_Terreno")),
	NULL_TERRAIN(MinecraftTerrain.getInstance().getMessages().getConfig().getString("TerrenoNaoEncontrado")),
	NOT_BUY_OWN_TERRAIN(MinecraftTerrain.getInstance().getMessages().getConfig().getString("ComprarProprioTerreno")),
	BROUGHT_TERRAIN(MinecraftTerrain.getInstance().getMessages().getConfig().getString("Terreno_Comprado")),
	IN_TERRAIN(MinecraftTerrain.getInstance().getMessages().getConfig().getString("VoceEsteSobreUmTerreno")),
	MIN_TERRAIN_SIZE(MinecraftTerrain.getInstance().getMessages().getConfig().getString("Tamanho_Minimo")),
	MAX_TERRAIN_SIZE(MinecraftTerrain.getInstance().getMessages().getConfig().getString("Tamanho_Maximo")),
	PAR_SIZE(MinecraftTerrain.getInstance().getMessages().getConfig().getString("Tamanho_Par")),
	;
	
//	Tamanho_Par
	public String message;
	Lang(String messag){
		message = messag;
	}
	public String build() {
		return message.replace("&", "§");
	}
	
}
