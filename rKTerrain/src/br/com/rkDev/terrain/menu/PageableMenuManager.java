package br.com.rkDev.terrain.menu;

import java.util.HashMap;

import br.com.rkDev.terrain.user.User;


public class PageableMenuManager {
	
	private HashMap<Integer, PageableMenu> userpm;
	
	public PageableMenuManager() {
		this.userpm = new HashMap<>();
	}
	
	public PageableMenu getPageableMenuUser(User user) {
		return this.userpm.get(user.getId());
	}
	public void setPageableMenuUser(User user, PageableMenu pm) {
		if(this.userpm.containsKey(user.getId())) {
			this.userpm.remove(user.getId());
		}
		this.userpm.put(user.getId(), pm);
		
	}

}
