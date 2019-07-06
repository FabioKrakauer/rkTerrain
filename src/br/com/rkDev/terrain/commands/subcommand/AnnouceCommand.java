package br.com.rkDev.terrain.commands.subcommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import br.com.rkDev.terrain.MinecraftTerrain;
import br.com.rkDev.terrain.Utils;
import br.com.rkDev.terrain.config.Lang;
import br.com.rkDev.terrain.database.Query;
import br.com.rkDev.terrain.manage.Terrain;
import br.com.rkDev.terrain.user.User;

public class AnnouceCommand {
	
	private User user;
	private String[] args;
	
	public AnnouceCommand(User user, String[] args) {
		this.user = user;
		this.args = args;
		execute();
	}
	
	private void execute() {
		Player p = Bukkit.getPlayer(user.getName());
		if(args.length < 2) {
			p.sendMessage(Lang.INVALID_ARGS.build().replace("%comando%", "/terreno anunciar <valor>"));
			return;
		}
		Terrain terrain = MinecraftTerrain.getInstance().getTerrainManager().getTerrain(p.getLocation());
		if(terrain == null) {
			p.sendMessage(Lang.NULL_TERRAIN.build());
			return;
		}
		
		if(terrain.getOwner().getId() != user.getId()) {
			p.sendMessage(Lang.NOT_YOUR_TERRAIN.build());
			return;
		}
		
		if(terrain.isOnSale()) {
			p.sendMessage(Lang.IS_ON_SALE.build());
			return;
		}
		
		String value = args[1];
		value = value.replace(",", ".");
		if(!Utils.isDobule(value)) {
			p.sendMessage(Lang.ONLY_DOUBLE.build());
			return;
		}
		double doubleValue = Double.parseDouble(value);
		Query update = new Query("UPDATE `terrain` SET `sale`='"+value+"' WHERE `id`='"+terrain.getId()+"'");
		update.execute();
		MinecraftTerrain.getInstance().getTerrainManager().getCache().downloadTerrain(terrain.getId());
		p.sendMessage(Lang.TERRAIN_ANNOUCED.build().replace("%valor%", Utils.formatDouble(doubleValue)));
	}
}
