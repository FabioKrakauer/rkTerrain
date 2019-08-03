package br.com.rkDev.terrain.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import br.com.rkDev.terrain.manage.Terrain;
import br.com.rkDev.terrain.user.User;


public class UserJoinInTerrainEvent extends Event implements Cancellable{

	private User user;
	private Terrain terrain;
	
	private boolean cancelled;
	public UserJoinInTerrainEvent(User user, Terrain terrain) {
		this.user = user;
		this.terrain = terrain;
	}
	
	public User getUser() {
		return user;
	}
	public Terrain getTerrain() {
		return terrain;
	}
	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
		
	}
	
	private static final HandlerList handlers = new HandlerList();
	 
	@Override
	public HandlerList getHandlers() {
	    return handlers;
	}
	 
	public static HandlerList getHandlerList() {
	    return handlers;
	}
}
