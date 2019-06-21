package br.com.rkDev.terrain.database;

import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import br.com.rkDev.terrain.MinecraftTerrain;

public class DatabaseManager {

	private Database database;
	private ArrayList<String> setupCommands = new ArrayList<String>();
	public DatabaseManager() {
		FileConfiguration config = MinecraftTerrain.getInstance().getConfig();
		String host = config.getString("Database.Host");
		int port = config.getInt("Database.Port");
		String database = config.getString("Database.Database");
		String user = config.getString("Database.User");
		String password = config.getString("Database.Password");
		this.database = new Database(host, port, database, user, password);

		addCommandSetup("CREATE TABLE IF NOT EXISTS `user` ( `id` INT(10) NULL AUTO_INCREMENT , `username` VARCHAR(18) NOT NULL , `uuid` VARCHAR(50) NOT NULL , `coins` DOUBLE(20, 2), `global_rank` INT(5) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;");
		addCommandSetup("CREATE TABLE IF NOT EXISTS `global_ranks` ( `id` INT(5) NULL AUTO_INCREMENT , `name` VARCHAR(50) NOT NULL , `tag` VARCHAR(15) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;");
		addCommandSetup("CREATE TABLE IF NOT EXISTS `group_permissions` ( `id` INT(15) NULL AUTO_INCREMENT , `group_id` INT(5) NOT NULL , `permission` VARCHAR(55) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;");
		addCommandSetup("CREATE TABLE IF NOT EXISTS `user_permissions` ( `id` INT(15) NULL AUTO_INCREMENT , `user_id` INT(10) NOT NULL , `permission` VARCHAR(55) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;");
		addCommandSetup("CREATE TABLE IF NOT EXISTS `user_banned` ( `id` INT(15) NULL AUTO_INCREMENT , `user_id` INT(10) NOT NULL , `time` INT(40) NOT NULL , `staff_id` INT(10) NOT NULL , `reason` VARCHAR(250) NOT NULL , `date` VARCHAR(19) NOT NULL , `at` BIGINT(40) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;");
		addCommandSetup("CREATE TABLE IF NOT EXISTS `user_muted` ( `id` INT(15) NULL AUTO_INCREMENT , `user_id` INT(10) NOT NULL , `time` INT(40) NOT NULL , `staff_id` INT(10) NOT NULL , `reason` VARCHAR(250) NOT NULL , `date` VARCHAR(19) NOT NULL , `at` BIGINT(40) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;");
		addCommandSetup("CREATE TABLE IF NOT EXISTS `history_punish` ( `id` INT(25) NULL AUTO_INCREMENT , `punish_id` INT(25) NOT NULL , `user_id` INT(10) NOT NULL , `staff_id` INT(10) NOT NULL , `type` INT(5) NOT NULL , `time` INT(30) NOT NULL , `reason` VARCHAR(250) NOT NULL , `date` VARCHAR(19) NOT NULL , `at` BIGINT(40) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;");
		addCommandSetup("CREATE TABLE IF NOT EXISTS `region` ( `id` INT(10) NULL AUTO_INCREMENT , `name` VARCHAR(40) NOT NULL , `spawn` VARCHAR(255) NOT NULL , `location` VARCHAR(255) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;");
		addCommandSetup("CREATE TABLE IF NOT EXISTS `region_flag` ( `id` INT(20) NULL AUTO_INCREMENT , `region_id` INT(20) NOT NULL , `flag_id` INT(10) NOT NULL , `value` TINYINT(1) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;");
		addCommandSetup("CREATE TABLE IF NOT EXISTS `plot` ( `id` INT(20) NULL AUTO_INCREMENT , `owner_id` INT(10) NOT NULL , `region_id` INT(15) NOT NULL , `size` INT(5) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;");
		addCommandSetup("CREATE TABLE IF NOT EXISTS `plot_member` ( `id` INT(10) NULL AUTO_INCREMENT , `plot_id` INT(20) NOT NULL , `user_id` INT(10) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;");
		addCommandSetup("CREATE TABLE IF NOT EXISTS `in_sale_plot` ( `id` INT(10) NULL AUTO_INCREMENT , `plot_id` INT(20) NOT NULL , `value` INT(10) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;");
		addCommandSetup("CREATE TABLE IF NOT EXISTS `cla` ( `id` INT(20) NULL AUTO_INCREMENT , `name` VARCHAR(20) NOT NULL , `tag` VARCHAR(10) NOT NULL , `owner` INT(3) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;");
		addCommandSetup("CREATE TABLE IF NOT EXISTS `cla_member` ( `id` INT(20) NULL AUTO_INCREMENT , `cla_id` INT(20) NOT NULL , `user_id` INT(10) NOT NULL , `rank` INT(3) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;");
		addCommandSetup("CREATE TABLE IF NOT EXISTS `mine` ( `id` INT(10) NULL AUTO_INCREMENT , `name` VARCHAR(40) NOT NULL , `cuboid` VARCHAR(255) NOT NULL , `delay` INT(5) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;");
		addCommandSetup("CREATE TABLE IF NOT EXISTS `mine_block` ( `id` INT(20) NULL AUTO_INCREMENT , `mine_id` INT(20) NOT NULL , `block` VARCHAR(20) NOT NULL , `quantity` INT(10) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;");
		addCommandSetup("CREATE TABLE IF NOT EXISTS `user_cases` ( `id` INT(15) NULL AUTO_INCREMENT , `user_id` INT(10) NOT NULL , `cases` INT(10) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;");
		addCommandSetup("CREATE TABLE IF NOT EXISTS `hologram` ( `id` INT(10) NULL AUTO_INCREMENT , `name` VARCHAR(50) NOT NULL , `location` VARCHAR(255) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;");
		addCommandSetup("CREATE TABLE IF NOT EXISTS `hologram_line` ( `id` INT(10) NULL AUTO_INCREMENT , `hologram_id` INT(10) NOT NULL , `text` VARCHAR(255) NOT NULL , PRIMARY KEY (`id`)) ENGINE = InnoDB;");
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
