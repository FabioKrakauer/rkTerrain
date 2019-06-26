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
		
		addCommandSetup("CREATE TABLE IF NOT EXISTS `user` ( `id` INT(15) NULL AUTO_INCREMENT , `name` VARCHAR(16) NOT NULL , `uuid` VARCHAR(40) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;");
		addCommandSetup("CREATE TABLE IF NOT EXISTS `terrain` ( `id` INT(30) NULL AUTO_INCREMENT , `user_id` INT(15) NOT NULL , `cuboid` VARCHAR(255) NOT NULL , `spawn` VARCHAR(255) NOT NULL , `sale` DOUBLE(40,2) NOT NULL, PRIMARY KEY (`id`)) ENGINE = InnoDB;");
		addCommandSetup("CREATE TABLE IF NOT EXISTS `user_terrain` ( `id` INT(30) NULL AUTO_INCREMENT , `user_id` INT(30) NOT NULL , `terrain_id` INT(30) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;");
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
