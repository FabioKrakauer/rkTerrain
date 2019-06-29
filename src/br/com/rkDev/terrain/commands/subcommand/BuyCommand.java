package br.com.rkDev.terrain.commands.subcommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import br.com.rkDev.terrain.MinecraftTerrain;
import br.com.rkDev.terrain.config.Lang;
import br.com.rkDev.terrain.manage.Terrain;
import br.com.rkDev.terrain.user.User;

public class BuyCommand {

	private User user;
	private String[] args;

	public BuyCommand(User user, String[] args) {
		this.user = user;
		this.args = args;
		execute();
	}

	private void execute() {
		Player p = Bukkit.getPlayer(user.getName());
		if(args.length < 2) {
			Terrain terrain = MinecraftTerrain.getInstance().getTerrainManager().getTerrain(p.getLocation());
			
			if(terrain == null) {
				p.sendMessage(Lang.NULL_TERRAIN.build());
				return;
			}
			if(!terrain.isOnSale()) {
				p.sendMessage(Lang.TERRAIN_NOTSALE.build());
				return;
			}
			if(terrain.getSaleValue() > user.getMoney()) {
				p.sendMessage(Lang.NO_CREDITS.build().replace("%valor%", terrain.getSaleValue() + ""));
			}
			
		}
	}
}
