package br.com.rkDev.terrain.commands.subcommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import br.com.rkDev.terrain.MinecraftTerrain;
import br.com.rkDev.terrain.config.Lang;
import br.com.rkDev.terrain.database.Query;
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
			if(terrain.getOwner().getId() == user.getId()) {
				p.sendMessage(Lang.NOT_BUY_OWN_TERRAIN.build());
				return;
			}
			if(!terrain.isOnSale()) {
				p.sendMessage(Lang.TERRAIN_NOTSALE.build());
				return;
			}
			if(terrain.getSaleValue() > user.getMoney()) {
				p.sendMessage(Lang.NO_CREDITS.build().replace("%valor%", terrain.getSaleValue() + ""));
			}
			Query updateOwner = new Query("UPDATE `terrain` SET `user_id`='"+user.getId()+"', `sale`='0' WHERE `id`='"+terrain.getId()+"'");
			Query removeFriends = new Query("DELETE FROM `user_terrain` WHERE `terrain_id`='"+terrain.getId()+"'");
			updateOwner.execute();
			removeFriends.execute();
			terrain.getOwner().addMoney(terrain.getSaleValue());
			user.removeMoney(terrain.getSaleValue());
			
			p.sendMessage(Lang.BROUGHT_TERRAIN.build().replace("%valor%", terrain.getSaleValue() + ""));
		}
	}
}
