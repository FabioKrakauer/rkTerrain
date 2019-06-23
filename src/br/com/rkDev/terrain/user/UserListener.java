package br.com.rkDev.terrain.user;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import br.com.rkDev.terrain.MinecraftTerrain;
import br.com.rkDev.terrain.database.Query;

public class UserListener implements Listener{
	
	public UserListener() {
		Bukkit.getPluginManager().registerEvents(this, MinecraftTerrain.getInstance());
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoinFirstTime(AsyncPlayerPreLoginEvent e) {
		String name = e.getName();
		if(MinecraftTerrain.getInstance().getUserManager().getUser(name) == null && MinecraftTerrain.getInstance().getUserManager().getUser(e.getUniqueId()) == null) {
			Query add = new Query("INSERT INTO `user`(`id`, `name`, `uuid`) VALUES (NULL, '"+name+"', '"+e.getUniqueId().toString()+"')");
			int userID = 0;
			try {
				userID = add.insertGetID("id");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			MinecraftTerrain.getInstance().getUserManager().getCache().downloadUser(userID);
		}else if(MinecraftTerrain.getInstance().getUserManager().getUser(name) == null && MinecraftTerrain.getInstance().getUserManager().getUser(e.getUniqueId()) != null) {
			User user = MinecraftTerrain.getInstance().getUserManager().getUser(e.getUniqueId());
			Query change = new Query("UPDATE `user` SET `name`='"+e.getName()+"' WHERE `uuid`='"+e.getUniqueId().toString()+"'");
			change.execute();
			MinecraftTerrain.getInstance().getUserManager().getCache().downloadUser(user.getId());
		}
	}
	

}
