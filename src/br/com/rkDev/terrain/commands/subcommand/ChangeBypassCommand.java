package br.com.rkDev.terrain.commands.subcommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import br.com.rkDev.terrain.MinecraftTerrain;
import br.com.rkDev.terrain.config.Lang;
import br.com.rkDev.terrain.user.User;

public class ChangeBypassCommand {
	private User user;

	public ChangeBypassCommand(User user) {
		this.user = user;
		execute();
	}

	private void execute() {
		Player p = Bukkit.getPlayer(user.getName());
		if(user.isAdmin()) {
			if(MinecraftTerrain.getInstance().getUserManager().isWithBypass(user)) {
				MinecraftTerrain.getInstance().getUserManager().removeBypass(user);
				p.sendMessage(Lang.BYPASS_DESACTIVATED.build());
			}else {
				MinecraftTerrain.getInstance().getUserManager().addBypass(user);
				p.sendMessage(Lang.BYPASS_ACTIVATED.build());
			}
		}else {
			p.sendMessage(Lang.NO_PERMISSION.build());
		}
	}
}
