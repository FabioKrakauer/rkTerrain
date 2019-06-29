package br.com.rkDev.terrain.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.rkDev.terrain.MinecraftTerrain;
import br.com.rkDev.terrain.commands.subcommand.BuyCommand;
import br.com.rkDev.terrain.config.Lang;
import br.com.rkDev.terrain.user.User;

public class TerrainCommand implements CommandExecutor{

	
	public TerrainCommand() {
		MinecraftTerrain.INSTACE.getCommand("terreno").setExecutor(this);
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("terreno")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(Lang.ONLY_PLAYER.build());
				return true;
			}
			Player p = (Player)sender;
			User user = MinecraftTerrain.getInstance().getUserManager().getUser(p.getUniqueId());
			if(args.length == 0) {
				p.sendMessage("§e/terreno comprar <tamanho> §8- §7Compra um terreno!");
				p.sendMessage("§e/terreno anunciar §8- §7Anuncia um terreno!");
				p.sendMessage("§e/terreno ir §8- §7Teleporta para seu terreno!");
				p.sendMessage("§e/terreno painel §8- §7Abre painel de configuração do terreno!");
				p.sendMessage("§e/terreno desanunciar §8- §7Remove anuncio do terreno!");
				if(user.isAdmin()) {
					p.sendMessage("§e/terreno bypass §8- §7Ativa modo administrador dentro dos terrenos!");
				}
				return true;
			}
			String command = args[0];
			if(command.equalsIgnoreCase("comprar")) {
				new BuyCommand(user, args);
			}
		}
		return false;
	}
	
	

}
