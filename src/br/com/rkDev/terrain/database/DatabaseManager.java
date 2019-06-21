package br.com.rkDev.terrain.database;

import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import br.com.rkDev.terrain.MinecraftTerrain;

public class DatabaseManager {

	private Database database;
	private ArrayList<String> setupCommands = new ArrayList<String>();
	public DatabaseManager() {
		FileConfiguration config = MinecraftTerrain.getInstance().getConfig();
		String host = config.getString("MySQL.Host");
		int port = config.getInt("MySQL.Port");
		String database = config.getString("MySQL.Database");
		String user = config.getString("MySQL.User");
		String password = config.getString("MySQL.Password");
		this.database = new Database(host, port, database, user, password);
		
		
	}
	public Database getDatabase() {
		return this.database;
	}
	public void addCommandSetup(String command) {
		this.setupCommands.add(command);
	}
	public void setup() {
		this.setupCommands.forEach(command -> {
			Query query = new Query(command);
			query.execute();
		});
		this.setupCommands.clear();
	}
}
