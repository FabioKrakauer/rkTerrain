package br.com.rkDev.terrain.user;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import br.com.rkDev.terrain.MinecraftTerrain;
import br.com.rkDev.terrain.database.Query;

public class UserListener implements Listener{
	
	public UserListener() {
		Bukkit.getPluginManager().registerEvents(this, MinecraftTerrain.getInstance());
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoinFirstTime(PlayerLoginEvent e) {
		String name = e.getPlayer().getName();
		String uuid = e.getPlayer().getUniqueId().toString();
		if(MinecraftTerrain.getInstance().getUserManager().getUser(name) == null && MinecraftTerrain.getInstance().getUserManager().getUser(uuid) == null) {
			Query add = new Query("INSERT INTO `user`(`id`, `name`, `uuid`) VALUES (NULL, '"+name+"', '"+uuid+"')");
			int userID = add.insertGetID("id");
			MinecraftTerrain.getInstance().getUserManager().getCache().downloadUser(userID);
		}else if(MinecraftTerrain.getInstance().getUserManager().getUser(name) == null && MinecraftTerrain.getInstance().getUserManager().getUser(uuid) != null) {
			User user = MinecraftTerrain.getInstance().getUserManager().getUser(uuid);
			Query change = new Query("UPDATE `user` SET `name`='"+name+"' WHERE `uuid`='"+uuid.toString()+"'");
			change.execute();
			MinecraftTerrain.getInstance().getUserManager().getCache().downloadUser(user.getId());
		}
	}
}