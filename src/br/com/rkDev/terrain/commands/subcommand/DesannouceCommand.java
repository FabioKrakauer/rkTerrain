package br.com.rkDev.terrain.commands.subcommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import br.com.rkDev.terrain.MinecraftTerrain;
import br.com.rkDev.terrain.config.Lang;
import br.com.rkDev.terrain.database.Query;
import br.com.rkDev.terrain.manage.Terrain;
import br.com.rkDev.terrain.user.User;

public class DesannouceCommand {
	
	private User user;
	
	public DesannouceCommand(User user) {
		this.user = user;
		execute();
	}
	
	private void execute() {
		Player p = Bukkit.getPlayer(user.getName());
		Terrain terrain = MinecraftTerrain.getInstance().getTerrainManager().getTerrain(p.getLocation());
		if(terrain == null) {
			p.sendMessage(Lang.NULL_TERRAIN.build());
			return;
		}
		if(terrain.getOwner().getId() != user.getId()) {
			p.sendMessage(Lang.NOT_YOUR_TERRAIN.build());
			return;
		}
		if(!terrain.isOnSale()) {
			p.sendMessage(Lang.TERRAIN_NOTSALE.build());
			return;
		}
		Query query = new Query("UPDATE `terrain` SET `sale`='0' WHERE `id`='"+terrain.getId()+"'");
		query.execute();
		MinecraftTerrain.getInstance().getTerrainManager().getCache().downloadTerrain(terrain.getId());
		p.sendMessage(Lang.DESANNOUCE_TERRAIN.build());
	}

}
