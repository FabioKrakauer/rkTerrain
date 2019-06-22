package br.com.rkDev.terrain.user;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

public class UserManager {
	
	private HashMap<UUID, User> users;
	
	public UserManager() {
		users = new HashMap<UUID, User>();
	}
	
	public HashMap<UUID, User> getUsers() {
		return users;
	}
	
	public User getUser(UUID unique) {
		return users.get(unique);
	}
	
	public User getUser(String name) {
		for(Entry<UUID, User> entry : users.entrySet()) {
			if(entry.getValue().getName().equalsIgnoreCase(name)) {
				return entry.getValue();
			}
		}
		return null;
	}
	
	public void removeUser(UUID unique) {
		if(getUser(unique) != null) {
			users.remove(unique);
		}
	}
	
	public void setUser(User user) {
		removeUser(user.getUUID());
		users.put(user.getUUID(), user);
	}
}
